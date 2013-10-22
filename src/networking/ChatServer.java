package networking;

import diceroller.DiceRoll;
import diceroller.DiceRoller;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * To be used by the Dungeon Master, the server will communicate with connected
 * clients, sending their rolls and chat communications to each other.
 *
 * @author Jacob Dorman
 */
public class ChatServer implements Runnable
{

    private ServerSocket serverSocket;
    private HashMap<InetAddress, Client> clientMap;
    private static final ConsoleLog log = new ConsoleLog();
    private static final String PROTOCOL_COMMANDS = "Commands:"
            + "\n/who (display all players)"
            + "\n/tell player_name message (send a personal message)"
            + "\n/#d# (ex. 1d20)"
            + "\n/clear (clears the chat window)"
            + "\n/connect address:port"
            + "\n/disconnect (disconnect from the server)";

    /**
     * Creates a new chat server operating at the passed port.
     *
     * @param port the port to operate the server on
     * @throws IOException
     */
    public ChatServer(int port) throws IOException
    {
        System.out.println(log + "Server starting on port " + port);
        serverSocket = new ServerSocket(port);
        clientMap = new HashMap<>();
    }

    /**
     * Cleanly closes the connection of the passed client.
     *
     * @throws IOException
     */
    private void cleanUpConnection(InetAddress address)
    {
        //Verify that the client is online before cleaning up (this prevents
        //duplicate clean ups)
        if (clientMap.get(address).online)
        {
            Client targetClient = clientMap.get(address);
            targetClient.disconnectClient();
            targetClient.reader.fromClient.close();
            targetClient.writer.toClient.close();
            System.out.println(log + targetClient.name + " has fallen into a trance.");
            sendToAllConnectedUsers(targetClient.name + " has fallen into a trance.", null);
        }
    }

    /**
     * @return a list of server clients that have connected to the server
     */
    private String getUserList()
    {
        String serverClients = "Server clients:\n";
        Set<InetAddress> keySet = clientMap.keySet();
        for (InetAddress s : keySet)
        {
            //Retrieve each client's name
            Client client = clientMap.get(s);
            serverClients += client.name;
            //If the client is online, mark them as online
            if (clientMap.get(s).online)
            {
                serverClients += " [Online]";
            } else
            {
                serverClients += " [Offline]";
            }
            serverClients += "\n";
        }
        //Remove the final newline character
        serverClients = serverClients.substring(0, serverClients.length() - 1);
        return serverClients;
    }

    /**
     * Returns true if the passed name is a client that has connected to the
     * server.
     *
     * @param name the name to check
     * @return true if the name has been taken by a client
     */
    private boolean isNameTaken(String name)
    {
        Set<InetAddress> keySet = clientMap.keySet();
        for (InetAddress i : keySet)
        {
            if (clientMap.get(i).name.equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Sends the given string to all connected clients except the given client.
     *
     * @param message the message to send
     */
    private void sendToAllConnectedUsers(String message, Client exception)
    {
        Set<InetAddress> keySet = clientMap.keySet();
        for (InetAddress inetAddress : keySet)
        {
            Client nextClient = clientMap.get(inetAddress);
            if (nextClient != exception && nextClient.online)
            {
                nextClient.writer.queue.offer(message);
            }
        }
    }

    /**
     * Retrieves the desired Client using the given name.
     *
     * @param name the name to search for
     * @return the Client with the given name if they exist. Otherwise null.
     */
    private Client getClient(String name)
    {
        Set<InetAddress> keySet = clientMap.keySet();
        for (InetAddress i : keySet)
        {
            Client client = clientMap.get(i);
            if (client.name.equalsIgnoreCase(name))
            {
                return client;
            }
        }
        return null;
    }

    /**
     * Continually checks for new connections. If the new connection is a
     * returning user, assigns them their old name. If the new client is a new
     * user, defaults their name to their IP address.
     */
    @Override
    public void run()
    {
        while (!Thread.interrupted())
        {
            try
            {
                Socket newClient = serverSocket.accept();
                //Each client stores their address, and uses it for their
                //default name
                InetAddress clientAddress = newClient.getInetAddress();
                Client client;
                //Client has connected in the past, reconnect them
                if (clientMap.containsKey(clientAddress))
                {
                    client = clientMap.get(clientAddress);
                    client.reconnect(newClient);
                    System.out.println(log + "Player reconnected from "
                            + clientAddress);
                } //Client hasn't connected before
                else
                {
                    client = new Client(newClient, clientAddress);
                    clientMap.put(clientAddress, client);
                    System.out.println(log + "Player connected from "
                            + clientAddress);
                }
                //Request name from client
                client.requestName();
            } catch (IOException ex)
            {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * A client that that allows for reading and writing from the client socket.
     */
    class Client
    {

        private String name;
        private InetAddress address;
        private Socket client;
        private boolean online;
        private ChatWriter writer;
        private ChatReader reader;
        private Thread writerThread;
        private Thread readerThread;

        /**
         * Creates a new client, and begins watching for input and output from
         * this client.
         *
         * @param client
         * @throws IOException
         */
        public Client(Socket client, InetAddress address) throws IOException
        {
            this.online = true;
            this.client = client;
            this.address = address;
            //Default name is the address
            this.name = address.toString();
            this.writer = new ChatWriter();
            this.reader = new ChatReader();
            startThreads();
        }

        /**
         * @return this client's Internet address
         */
        public InetAddress getClientAddress()
        {
            return address;
        }

        /**
         * Reconnect this client using the given socket.
         *
         * @param socket the new connection the client established
         */
        public void reconnect(Socket socket) throws IOException
        {
            online = true;
            client = socket;
            writer = new ChatWriter();
            reader = new ChatReader();
            startThreads();
        }

        /**
         * @return true if the client is currently online
         */
        public boolean isOnline()
        {
            return online;
        }

        /**
         * Starts the reading and writing threads for this client.
         */
        private void startThreads()
        {
            writerThread = new Thread(writer);
            writerThread.start();
            readerThread = new Thread(reader);
            readerThread.start();
        }

        /**
         * Interrupts the client's threads, cutting off communication to and
         * from this client. Also marks this client as being offline.
         */
        public void disconnectClient()
        {
            readerThread.interrupt();
            writerThread.interrupt();
            online = false;
        }

        /**
         * Updates this client's name.
         *
         * @param newName the new name for the client
         */
        public void changeClientName(String newName)
        {
            //Update name
            System.out.println(log + name + " is now " + newName + ".");
            this.name = newName;
            sendToAllConnectedUsers(name + " wakes up from their trance like state.", this);
            writer.queue.add("Your name is now " + name);
        }

        /**
         * Requests the player's name from the client, and sets the client's
         * name to match the player.
         */
        private void requestName()
        {
            System.out.println(log + "Requesting player name from " + name);
            writer.queue.add(ProtocolCommands.GET_NAME.toString());
        }

        /**
         * Reads input from this client and acts upon it.
         */
        private class ChatReader extends Thread
        {

            private Scanner fromClient;
            private final String[] INVALID_INPUT_MESSAGES =
            {
                "Amin delotha lle.",
                "Dolle naa lost.",
                "Lle lakwenien?",
                "Llie n'vanima ar' lle atara lanneina.",
                "Antolle ulua sulrim.",
                "Auta miqula orqu."
            };

            /**
             * Creates a new ChatReader to receive input from the client.
             *
             * @throws IOException
             */
            public ChatReader() throws IOException
            {
                fromClient = new Scanner(client.getInputStream());
            }

            /**
             * Continually waits for input from the client, and interprets it
             * when it arrives.
             */
            @Override
            public synchronized void run()
            {
                while (!Thread.interrupted())
                {
                    String s;
                    try
                    {
                        while ((s = fromClient.nextLine()) != null)
                        {
                            interpretInput(s);
                        }
                    } catch (NoSuchElementException | IllegalStateException e)
                    {
                        break;
                    }
                    try
                    {
                        Thread.sleep(100);
                    } catch (InterruptedException ex)
                    {
                        Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                cleanUpConnection(address);
            }

            /**
             * Interprets the input given. Can rename the user, send a message
             * to another client, or display a list of users.
             *
             * @param s the string of input
             */
            private void interpretInput(String s)
            {
                //Check for built in protocol
                if (ProtocolCommands.isClientCommand(s))
                {
                    //See if the client is updating their name
                    if (s.contains(ProtocolCommands.SET_NAME.toString()))
                    {
                        //Shorten command to name
                        s = s.substring(8);
                        //Extract first name
                        String[] split = s.split(" ");
                        System.out.println(log + name + " changing name to " + split[0]);
                        //Update name
                        changeClientName(split[0]);
                        writer.queue.add("You feel connected by an unseen force...");
                        return;
                    }
                }
                //Check to see if user wants the connected user list
                if (s.equalsIgnoreCase("/who"))
                {
                    System.out.println(log + name + " is requesting a connected "
                            + "client list.");
                    writer.queue.offer(getUserList());
                    return;
                }
                //Check to see if sending a message to another user
                if (userSentMessage(s))
                {
                    return;
                }
                //Check to see if user is disconnecting
                if (s.equalsIgnoreCase("/disconnect"))
                {
                    cleanUpConnection(address);
                    return;
                }
                //Check to see if the user is asking for a command list
                if (s.equalsIgnoreCase("/help"))
                {
                    writer.queue.offer(PROTOCOL_COMMANDS);
                    return;
                }
                //Check to see if the user is rolling dice
                if (userIsRolling(s))
                {
                    return;
                }
                //Say message
                if (s.charAt(0) != '\\')
                {
                    sendToAllConnectedUsers(name + " says, \"" + s + "\"", getClient(name));
                    writer.queue.offer("You say, \"" + s + "\"");
                    return;
                }
                //User input wasn't meaningful
                writer.queue.offer(getRandomInvalidInputMessage() + " (type /help for a command list)");

            }

            /**
             * Checks to see if the passed input is a user attempting to send a
             * message to another client, and if so returns true.
             *
             * @param input the client's input
             * @return true if the input is properly formatted
             */
            private boolean userSentMessage(String input)
            {
                //Check to see if sending a message to another user
                if (input.length() > 7 && input.substring(0, 6).equalsIgnoreCase("/tell "))
                {
                    String targetName = "";
                    for (int i = 6; i < input.length(); i++)
                    {
                        char nextChar = input.charAt(i);
                        if (Character.isLetter(nextChar))
                        {
                            targetName += nextChar;
                        } //If this is a client name and there is more of this
                        //string, try to send a message
                        else if (nextChar == ' ' && input.length() > i + 1)
                        {
                            String message = input.substring(i + 1, input.length());
                            //If the target user exists
                            if (isNameTaken(targetName))
                            {
                                Client targetClient = getClient(targetName);
                                //See if the user is online
                                if (targetClient.online)
                                {
                                    //See if they are taking to themselves
                                    if (name.equals(targetClient.name))
                                    {
                                        writer.queue.offer("You talk to yourself, \""
                                                + message + "\"");
                                        sendToAllConnectedUsers(name + " mumbles something quietly to themselves.", getClient(name));
                                        return true;
                                    } else
                                    {
                                        targetClient.writer.queue.offer(name
                                                + " tells you, \"" + message + "\"");
                                        //Show that the message was sent
                                        writer.queue.offer("You tell " + targetName + ", \"" + message + "\"");
                                        return true;
                                    }
                                } else
                                {
                                    writer.queue.offer(targetName
                                            + " is in a trance at the moment, and"
                                            + " probably isn't listening.");
                                    return true;
                                }
                            } //No user exists with that name
                            else
                            {
                                writer.queue.offer("You talk to your imaginary "
                                        + "friend, " + targetName + ".");
                                return true;
                            }
                            //The user entered malformed input
                        } else
                        {
                            break;
                        }
                    }
                }
                return false;
            }

            /**
             * Returns a random invalid input message as declared in the
             * ChatReader class.
             *
             * @return the randomly selected invalid input message
             */
            private String getRandomInvalidInputMessage()
            {
                Random rand = new Random();
                int index = rand.nextInt(INVALID_INPUT_MESSAGES.length);
                return INVALID_INPUT_MESSAGES[index];
            }

            /**
             * Displays the dice roll to everyone and returns true if the user
             * is rolling dice.
             *
             * @param input
             * @return
             */
            private boolean userIsRolling(String input)
            {
                if (input.length() < 3 || input.charAt(0) != '/' || !Character.isDigit(input.charAt(1)))
                {
                    return false;
                }
                if (!input.contains("d"))
                {
                    return false;
                }
                int dIndex = input.indexOf('d');
                //Get number of dice and the number of sides
                int numberOfDice;
                int numberOfSides;
                try
                {
                    String numberOfDiceString = input.substring(1, dIndex);
                    numberOfDice = Integer.parseInt(numberOfDiceString);
                    String numberOfSidesString = input.substring(dIndex + 1, input.length());
                    numberOfSides = Integer.parseInt(numberOfSidesString);
                } catch (Exception e)
                {
                    return false;
                }
                //Make sure the numbers are in bounds
                if (numberOfDice > 50 || numberOfSides > 100) //Roll the dice
                {
                    writer.queue.offer("That makes no sense."
                            + "\nI'm going to have to ask you to calm "
                            + "down and take a deep breath."
                            + "\n Look at your life, look at your choices.");
                    return true;
                }
                DiceRoll rollDice = DiceRoller.rollDice(numberOfDice, numberOfSides);
                String result = name + " rolled " + numberOfDice + "d"
                        + numberOfSides + ": " + rollDice;
                if (rollDice.getDiceCount() > 1)
                {
                    result += " (" + rollDice.getTotalRoll() + ")";
                }
                sendToAllConnectedUsers(result, null);
                System.out.println(result);
                return true;
            }
        }

        /**
         * Writes messages to this client.
         */
        private class ChatWriter implements Runnable
        {

            private LinkedBlockingQueue queue;
            private PrintWriter toClient;

            /**
             * Creates a new ChatWriter that allows the server to write to a
             * client.
             *
             * @throws IOException
             */
            public ChatWriter() throws IOException
            {
                queue = new LinkedBlockingQueue();
                toClient = new PrintWriter(client.getOutputStream(), true);
            }

            /**
             * Continually waits for a queued message to send to the client, and
             * sends them in the order they are received.
             */
            @Override
            public synchronized void run()
            {
                while (!Thread.interrupted())
                {
                    //If there's something in the queue, retrieve it and send it
                    if (!queue.isEmpty())
                    {
                        String poll = (String) queue.poll();
                        //System.out.println(log + "Sending " + poll + " to '" + name + "'");
                        toClient.println(poll);
                    }
                    //Sleep to prevent rediculous CPU usage costs, haha
                    try
                    {
                        Thread.sleep(100);
                    } catch (InterruptedException ex)
                    {
                        //In case the client disconnects
                        cleanUpConnection(address);
                        return;
                    }
                }
                cleanUpConnection(address);
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        final int PORT = 1337;
        ChatServer server = new ChatServer(PORT);
        new Thread(server).start();
    }
}

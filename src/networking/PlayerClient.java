package networking;

import character.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 * A player client to be used with the DMServer. This client accepts messages
 * from the server and sends messages to the server.
 */
public class PlayerClient
{

    private boolean connected;
    private Player player;
    private JTextArea output;
    private JTextField commandBar;
    private Socket server;
    private ClientReader reader;
    private Thread readerThread;
    private PrintWriter toServer;

    /**
     * Creates a new ChatClient with the passed name. Attempts to connect to the
     * passed address and port.
     *
     * @param address the address of the server
     * @param port the port of the server
     * @throws UnknownHostException
     * @throws IOException
     */
    public PlayerClient(Player player, String address, int port, JTextArea output, JTextField commandBar) throws UnknownHostException, IOException
    {
        this.player = player;
        this.output = output;
        this.commandBar = commandBar;
        addWritingActionListener();
        this.server = new Socket(address, port);
        reader = new ClientReader();
        toServer = new PrintWriter(server.getOutputStream(), true);
        System.out.println("Connection established on " + address + ":" + port);
        connected = true;
    }

    /**
     * Creates a new ChatClient with only the passed name, and allows the user
     * to connect manually later.
     *
     * @param name the name of the player
     */
    public PlayerClient(Player player, JTextArea output, JTextField commandBar) throws IOException
    {
        this.player = player;
        this.output = output;
        this.commandBar = commandBar;
        addWritingActionListener();
        connected = false;
        commandBar.setText("/connect address:port");
    }

    /**
     * Attempts to connect to the passed address and port.
     *
     * @param address
     * @param port
     */
    public void connect(String address, int port) throws UnknownHostException, IOException
    {
        this.server = new Socket(address, port);
        reader = new ClientReader();
        toServer = new PrintWriter(server.getOutputStream(), true);
        System.out.println("Connection established on " + address + ":" + port);
        connected = true;
        commandBar.setText("/help");
    }

    /**
     * Cleanly closes the connection to the server by interrupting the reading
     * and listening threads, closing the connections to the reader and writer,
     * and finally closing the server connection.
     *
     * @throws IOException
     */
    private void cleanUpConnection() throws IOException
    {
        readerThread.interrupt();
        reader.fromServer.close();
        server.close();
        output.append("\nYou no longer feel connected by an unseen force...");
        connected = false;
    }

    /**
     * @return true if this client is currently connected to a server
     */
    public boolean isConnected()
    {
        return connected;
    }

    /**
     * When enter is pressed, the command is interpreted and sent to the server.
     */
    private void addWritingActionListener()
    {
        commandBar.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String text = commandBar.getText();
                commandBar.selectAll();
                //Otherwise attempt to send command to the server if connected
                if (!connected)
                {
                    //Check to see if the user is connecting
                    if (text.contains("/connect "))
                    {
                        try
                        {
                            //Connect to the address and port
                            text = text.substring(9);
                            text = text.trim();
                            String[] split = text.split(":");
                            String address = split[0];
                            int port = Integer.parseInt(split[1]);
                            output.append("\nAttempting to connect to " + address + ":" + port);
                            System.out.println("Attempting to connect to " + address + ":" + port);
                            connect(address, port);
                        } catch (Exception ex)
                        {
                            output.append("\nYou've failed to reconnect with reality.");
                        }
                    } else
                    {
                        output.append("\nYou seem out of touch with reality... (use /connect address:port)");
                    }
                } //Check to see if the user is disconnecting
                else if (text.equalsIgnoreCase("/disconnect"))
                {
                    try
                    {
                        toServer.println(text);
                        cleanUpConnection();
                        return;
                    } catch (IOException ex)
                    {
                        Logger.getLogger(PlayerClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } //Otherwise, send command to the server
                else if (text.equalsIgnoreCase("/clear"))
                {
                    output.setText("");
                    DefaultCaret caret = (DefaultCaret) output.getCaret();
                    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                } else
                {
                    toServer.println(text);
                }
            }
        });
    }

    /**
     * A reader for a client program that receives messages from the server and
     * prints them to console.
     */
    public class ClientReader implements Runnable
    {

        private BufferedReader fromServer;

        /**
         * Create a new client reader.
         *
         * @throws IOException
         */
        public ClientReader() throws IOException
        {
            fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
            readerThread = new Thread(this);
            readerThread.start();
        }

        /**
         * Print any incoming messages from the server to the chat window.
         */
        @Override
        public void run()
        {
            String s;
            while (!Thread.interrupted())
            {
                try
                {
                    if ((s = fromServer.readLine()) != null)
                    {
                        //Check to see if the server is sending a built in command
                        if (ProtocolCommands.isServerCommand(s))
                        {
                            if (s.equals(ProtocolCommands.GET_NAME.toString()))
                            {
                                //Announce intent to send name and attach name
                                toServer.println(ProtocolCommands.SET_NAME + player.getBasicInfo().getName());
                            }
                        } //Otherwise just display the server's message
                        else
                        {
                            output.append("\n" + s);
                        }
                    }
                } catch (IOException ex)
                {
                    break;
                }
            }
            //Clean up connection in case of error
            try
            {
                cleanUpConnection();
            } catch (IOException ex)
            {
                Logger.getLogger(PlayerClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

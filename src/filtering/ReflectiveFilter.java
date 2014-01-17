package filtering;

import character.feats.Feat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 * 
 * @author Japhez
 */
public class ReflectiveFilter {

    private HashMap<JTextField, Method> map;

    public ReflectiveFilter() {
        map = new HashMap<>();
    }

    public void addDataSource(JTextField field, Method retrievalMethod) {
        map.put(field, retrievalMethod);
    }

    public HashMap<JTextField, Method> getMap() {
        return map;
    }

    public static boolean passesStandAloneFilter(Object object, String filterText, Method source) {
        String data = "";
        try {
            data = (String) source.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ReflectiveFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!data.toLowerCase().contains(filterText.toLowerCase())) {
            return false;
        }
        return true;
    }

    /**
     * @SupressWarnings
     * @param object
     * @return 
     */
    private boolean passesFilter(Object object) {
        for (JTextField tf : map.keySet()) {
            String data = "";
            try {
                data = (String) map.get(tf).invoke(object);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ReflectiveFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!data.toLowerCase().contains(tf.getText().toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Feat feat = new Feat("Name", "Desc", "Book", 46, null);
        JTextField tf = new JTextField("nam");
        ReflectiveFilter filterMap = new ReflectiveFilter();
        try {
            filterMap.addDataSource(tf, feat.getClass().getDeclaredMethod("getName"));
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(ReflectiveFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean filterResult = filterMap.passesFilter(feat);
        System.out.println(filterResult);
    }
}

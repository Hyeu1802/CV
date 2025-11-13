package View;

import javax.swing.JButton;
import java.util.ArrayList;
import View.AdminFrame1;
import View.UserFrame1;

/**
 *
 * @author Lappro
 */
public class SharedTables {

    private static ArrayList<JButton> tables = new ArrayList<>();
    private static ArrayList<Boolean> tableStates = new ArrayList<>();
    private static ArrayList<AdminFrame1> adminFrames = new ArrayList<>();
    private static ArrayList<UserFrame1> userFrames = new ArrayList<>();

    public static void addTable(JButton table) {
        tables.add(table);
        tableStates.add(false);
    }

    public static ArrayList<JButton> getTables() {
        return tables;
    }

    public static ArrayList<Boolean> getTableStates() {
        return tableStates;
    }

    public static void setTableState(int index, boolean state) {
        if (index >= 0 && index < tableStates.size()) {
            tableStates.set(index, state);
        }
    }

    public static void removeTable(int index){
        if(index >= 0 && index < tables.size()){
            tables.remove(index);
            tableStates.remove(index);
        }
        
        for(AdminFrame1 adminFrame : adminFrames){
            if(adminFrame != null){
                adminFrame.updateTableRemove(index);
            }
        }
    }

    public static void registerAdmin(AdminFrame1 adminFrame) {
        adminFrames.add(adminFrame);
    }
}
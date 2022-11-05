package tools;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {"idofcomplaint",
            "username",
            "Product",
            "Company",
            "Status",
            "RegistrationDate",
            "ForwardDate",
            "ResponseDate",
            "PickupDate",
            "CloseDate",
            "Description"
    };
    public Object[][] data = null;

    public Object[][] parseCsv(ArrayList<String> listofstrs){
        Object[][] results = new Object[listofstrs.size()][];
        int i =0;
        for(String s: listofstrs){
            String[] tabstr = s.split(";");
            ArrayList<Object> objectArrayList = new ArrayList<>();

            for(String str:tabstr){
                String ss = new String(str);
                objectArrayList.add(ss);

            }
            Object[] row = objectArrayList.toArray();
            results[i]=row;
            i++;
        }
        return results;
    }
    public MyTableModel(){
        ArrayList<String> strs = new ArrayList<>();
        /*strs.add("3;Kate;Phone;Samsung;tosend;29.05.2022;29.05.2022;29.05.2022;29.05.2022;29.05.2022;XXXXX");
        strs.add("4;William;Phone;Nokia;tosend;29.06.2022;29.05.2022;01.06.2022;03.06.2022;04.06.2022;XXXXX");*/

        String s =" ; ; ; ; ; ; ; ; ; ; ";
        for(int i =1;i<=15;i++)
            strs.add(s);

        data=parseCsv(strs);
        //table.re
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }


    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }
    public void setValueAt(Object value, int row, int col) {


        data[row][col] = value;
        fireTableCellUpdated(row+1, col);


    }

    public void removeRow(int row){
        fireTableRowsDeleted(row,row);
    }
    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}

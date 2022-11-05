package tools;

import java.net.URL;
import java.util.ArrayList;

public class MainFrame {
    public static void main(String[] args) throws Exception {
        String s ="file:/C:/DB/"+"Complaintable.csv";
        URL url= new URL(s);
        FileWriterReader reader = new FileWriterReader();
        MultiplePanels multiplePanels = new MultiplePanels(reader.read(url));
    }
}

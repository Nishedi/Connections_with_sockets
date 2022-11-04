package tools;
import SocketPackage.SocketClient;

import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MultiplePanels implements ActionListener {
    ComplianceDB complianceDB = new ComplianceDB();
    TableDemo tableDemo = new TableDemo();
    JFrame windows = new JFrame("MultiplePanels");
    JPanel panel_01 = new JPanel();
    JPanel panel_02 = new JPanel();
    JButton click = new JButton("Login");
    JButton click2 = new JButton("Send");
    JButton click3 = new JButton("Refresh");
    JButton click4 = new JButton("Close");
    JButton click5 = new JButton("Refresh view");

    JLabel text2= new JLabel("Login as "+"no logged");
    JLabel text = new JLabel("Button is clicked");
    public String username="Johny";
    public String password="Johny123";
    public String userlogin = "Client";
    Socket socket =null;
    SocketClient client = null;

    public MultiplePanels(ArrayList<String> ListStr) {
        //complianceDB.StringList.add("3;Kate;Phone;Samsung;tosend;29.05.2022;29.05.2022;29.05.2022;29.05.2022;29.05.2022;XXXXX");
        //complianceDB.StringList.add("4;William;Phone;Nokia;tosend;29.06.2022;29.05.2022;01.06.2022;03.06.2022;04.06.2022;XXXXX");
        panel_01.setBackground(Color.cyan);
        panel_02.setBackground(Color.darkGray);
        panel_02.add(click);
        panel_02.add(click2);
        panel_02.add(click3);
        panel_02.add(click4);
        panel_02.add(click5);
        panel_02.add(text2);
        text.setForeground(Color.BLUE);
       // panel_01.add

        text.setVisible(false);
        click.addActionListener(this);
        click2.addActionListener(this);
        click3.addActionListener(this);
        click4.addActionListener(this);
        click5.addActionListener(this);

      //  windows.add(panel_01,BorderLayout.CENTER);
        windows.add(panel_02,BorderLayout.PAGE_START);
        windows.add(tableDemo,BorderLayout.CENTER);
        windows.setSize(400,400);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setVisible(true);
       // System.out.println(ListStr.get(0));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        System.out.println(actionEvent.getActionCommand());
        if(actionEvent.getActionCommand().compareTo("Send")==0) {
            //System.out.println("at sent");
            Complaints comp =null;
            for(Complaints S: complianceDB.CompliencesList){
                comp = S;
                comp.changeHead(userlogin,username,password);

                comp.command="save";
                if(comp.status.compareTo("tosend")==0) {
                    client.sendMessage(comp.toString());
                }
            }
        }


        if(actionEvent.getActionCommand().compareTo("Close")==0) {
            windows.dispose();
        }
        if(actionEvent.getActionCommand().compareTo("Login")==0) {
            try {
                socket = new Socket("localhost", 1234);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            client = new SocketClient(socket);
            client.listenForMessage();

            complianceDB.CreateAndLoadTable(username);

        }
        if(actionEvent.getActionCommand().compareTo("Refresh view")==0) {
            //System.out.println("at refresh wiev");

            ArrayList<Complaints> listofcomplains = new ArrayList<>();
            while(client.numberofmessages()>0){
                String str=client.getAndRemove();
                Complaints comp = new Complaints(str);
                listofcomplains.add(comp);
            }
            complianceDB.update(listofcomplains);



            MyTableModel model=(MyTableModel) tableDemo.table.getModel();
            for(int row = 0; row < complianceDB.rowCount();row++){

                Object[] rowObject= complianceDB.rowObject(row);
                for(int i =0;i< model.getColumnCount();i++){
                    model.setValueAt(rowObject[i],row,i);
                    System.out.println(rowObject[i]);
                }
            }
            tableDemo.repaint();
        }

        if(actionEvent.getActionCommand().compareTo("Refresh")==0) {
            Complaints comp = new Complaints(userlogin,username,password);
            comp.command="getAll";
            client.sendMessage(comp.toString());
        }
        text.setVisible(true);
        panel_01.setBackground(Color.yellow);
        panel_02.setBackground(Color.green);
    }


}

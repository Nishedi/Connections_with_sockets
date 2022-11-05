package tools;
import SocketPackage.SocketClient;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class MultiplePanels implements ActionListener {
    ComplianceDB complianceDB = new ComplianceDB();
    TableDemo tableDemo = new TableDemo();
    JFrame windows = new JFrame("MultiplePanels");
    JPanel panel_01 = new JPanel();
    JPanel panel_02 = new JPanel();
    JButton click = new JButton("Login");
    JButton click2 = new JButton("Send");
    JButton click4 = new JButton("Close");
    JButton click3 = new JButton("CloseCompliance");
    JLabel text2= new JLabel("Login as "+"no logged");
    JTextArea textArea = new JTextArea();

    JLabel text = new JLabel("Button is clicked");
    public String username="Johny";
    public String password="Johny123";
    public String userlogin = "Client";
    Socket socket =null;
    SocketClient client = null;
    Timer timer = null;
    boolean authorization = false;

    public MultiplePanels(ArrayList<String> ListStr) {
        timer = new Timer(1000, timerListener);//ustawienie wyzwalania (100ms)
        timer.setDelay(2000);
        timer.start();//uruchomienie timera
        textArea.setText("Johny: Johny123");
        textArea.setPreferredSize(new Dimension(200,23));

        panel_01.setBackground(Color.cyan);
        panel_02.setBackground(Color.darkGray);
        panel_02.add(click);
        panel_02.add(textArea);
        panel_02.add(click2);
        panel_02.add(click4);
        panel_02.add(click3);
        panel_02.add(text2);
        text.setForeground(Color.BLUE);

        text.setVisible(false);
        click.addActionListener(this);
        click2.addActionListener(this);
        click3.addActionListener(closeComplianceListener);
        click4.addActionListener(closeListener);
        windows.add(panel_02,BorderLayout.PAGE_START);
        windows.add(tableDemo,BorderLayout.CENTER);
        windows.setSize(1000,400);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setVisible(true);

    }
    ActionListener timerListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if(authorization==false)return;
            if(client!=null) {
                ArrayList<Complaints> listofcomplains = new ArrayList<>();
                while (client.numberofmessages() > 0) {
                    String str = client.getAndRemove();
                    Complaints comp = new Complaints(str);
                    if(comp.command.compareTo("Wrong authorization!")==0) {
                        authorization = false;
                        JOptionPane.showMessageDialog(null, "Wrong login or password");

                    }
                    listofcomplains.add(comp);
                }
                complianceDB.update(listofcomplains);
                for(Complaints S: listofcomplains){
                    System.out.println(S.CurrentDate);

                }
                MyTableModel model = (MyTableModel) tableDemo.table.getModel();
                for (int row = 0; row < complianceDB.rowCount(); row++) {
                    Object[] rowObject = complianceDB.rowObject(row);
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        model.setValueAt(rowObject[i], row, i);
                    }
                }
                Complaints comp = new Complaints(userlogin,username,password);
                comp.command="getAll";
                client.sendMessage(comp.toString());
                tableDemo.repaint();
            }
        }
    };

    ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            windows.dispose();
        }
    };

    ActionListener closeComplianceListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(Complaints comp: complianceDB.CompliencesList){
                if(comp.status.compareTo("pickup")==0) {
                    comp.changeHead(userlogin,username,password);
                    comp.command="save";
                    comp.CloseDate= comp.CurrentDate;
                    comp.status="Closed";
                    client.sendMessage(comp.toString());
                    }
                tableDemo.repaint();
            }
        }
    };


    @Override
    public void actionPerformed(ActionEvent actionEvent){
        //System.out.println(actionEvent.getActionCommand());
        if(actionEvent.getActionCommand().compareTo("Send")==0) {
            Complaints comp =null;
            for(Complaints S: complianceDB.CompliencesList){
                comp = S;
                comp.changeHead(userlogin,username,password);
                comp.command="save";
                if(comp.status.compareTo("tosend")==0) {
                    comp.RegistrationDate= comp.CurrentDate;
                    client.sendMessage(comp.toString());
                }
                tableDemo.repaint();
            }
        }

       /* if(actionEvent.getActionCommand().compareTo("Close")==0) {
            windows.dispose();
        }*/
        if(actionEvent.getActionCommand().compareTo("Login")==0) {
            authorization=true;
            String value = textArea.getText();
            String[] tab=value.split(":");
            username=tab[0].trim();
            password=tab[1].trim();

            try {
                socket = new Socket("localhost", 1234);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            client = new SocketClient(socket);
            client.listenForMessage();


            complianceDB.CreateAndLoadTable(username);

        }
      /*  if(actionEvent.getActionCommand().compareTo("Refresh view")==0) {
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
                    //System.out.println(rowObject[i]);
                }
            }
            tableDemo.repaint();
        }*/

       /* if(actionEvent.getActionCommand().compareTo("Refresh")==0) {
            Complaints comp = new Complaints(userlogin,username,password);
            comp.command="getAll";
            client.sendMessage(comp.toString());
        }*/
        text.setVisible(true);
        panel_01.setBackground(Color.yellow);
        panel_02.setBackground(Color.green);
    }
}

package SocketPackage;

import tools.Complaints;

import java.io.*;
import java.net.Socket;


public class Client {
    public static void main(String[]args) throws IOException, InterruptedException {
        String userlogin = "SocketPackage.Client";
        String username = "Johny";
        String password = "Johny123";

        Socket socket = new Socket("localhost", 1234);
        SocketClient client = new SocketClient(socket);
        client.listenForMessage();
        Complaints comp = new Complaints();
        for(int i=0;i<=4;i++) {
            comp= new Complaints(userlogin,username,password);
            comp.command="askforcomplienceid";
            client.sendMessage( comp.toString());


            while (client.listofids.size() == 0) {
                Thread.sleep(100);
            }
            String complienceid = "";
            if (client.numberofmessages() > 0) {
                String str = client.getAndRemove();
                comp = new Complaints(str);
                System.out.println(comp);
                complienceid = comp.argument;
                System.out.println("arg= "+complienceid);
            }

            comp= new Complaints(userlogin,username,password);
            comp.idofcomplaint=complienceid;
            comp.command="save";
            comp.status="tosend";
            System.out.println("comppp= "+comp.toString());
            client.sendMessage(comp.toString());


        }

        while(1==1){
            comp = new Complaints(userlogin,username,password);
            comp.command="getAll";
            client.sendMessage(comp.toString());
            Thread.sleep(1000);
            while(client.numberofmessages()>0){
                String str=client.getAndRemove();
                comp = new Complaints(str);
                System.out.println(comp);
                if((comp.status.compareTo("tocorrect")==0)||(comp.status.compareTo("rejected")==0)) {
                    comp.status = "tosend";
                    comp.changeHead(userlogin,username,password);
                    client.sendMessage(comp.toString());
                }
            }
        }
    }
}

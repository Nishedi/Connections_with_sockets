import tools.Complaints;

import java.io.*;
import java.net.Socket;
import java.util.*;
public class Seller {
    public static void main(String[]args) throws IOException, InterruptedException {
        String userlogin = "Seller";
        String username = "Kate";
        String password = "Kate123";
        Complaints comp = new Complaints();

        Socket socket = new Socket("localhost", 1234);
        SocketClient seller = new SocketClient(socket);
        seller.listenForMessage();


        while(1==1){
            comp = new Complaints(userlogin,username,password);
            comp.command="getAllSeller";
            seller.sendMessage(comp.toString());
            Thread.sleep(1000);
            while(seller.numberofmessages()>0){
                String str=seller.getAndRemove();
                comp = new Complaints(str);
                System.out.println(comp);
                Random rand = new Random();
                int d = rand.nextInt(0,2);
                int d2 = rand.nextInt(1,15);
                System.out.println(comp.RegistrationDate+" "+comp.CurrentDate+" "+d2);
                d=0;
                String strtoloss;
                /*if(d==0) strtoloss = "atproducer";
                else strtoloss = "tocorrect";
                comp.status=strtoloss;*/
                comp.changeHead(userlogin,username,password);
                seller.sendMessage(comp.toString());
            }
        }
    }
}

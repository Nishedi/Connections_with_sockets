package SocketPackage;

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
                //System.out.println(comp);

                Random rand = new Random();
                System.out.println(comp.status);
                if(comp.status.compareTo("atseller")==0) {

                    int d = rand.nextInt(0, 2);
                    int d2 = rand.nextInt(1, 4);
                    //System.out.println(comp.RegistrationDate + " " + comp.CurrentDate + " " + d2);
                    Integer RegistrationDateInt = Integer.valueOf(comp.RegistrationDate);
                    Integer CurrentDateInt = Integer.valueOf(comp.CurrentDate);
                    Integer diffrence = CurrentDateInt - RegistrationDateInt;
                    System.out.println(diffrence+" "+d2);
                    if (diffrence > d2) {
                        comp.status = "atproducer";
                        comp.changeHead(userlogin, username, password);
                        comp.ForwardDate = comp.CurrentDate;
                        seller.sendMessage(comp.toString());
                    }
                }
                if(comp.status.compareTo("confirmed")==0){
                    int d = rand.nextInt(0, 2);
                    int d2 = rand.nextInt(1, 4);
                    Integer ResponseDateInt = Integer.valueOf(comp.ResponseDate);
                    Integer CurrentDateInt = Integer.valueOf(comp.CurrentDate);
                    Integer diffrence = CurrentDateInt - ResponseDateInt;
                    System.out.println(diffrence+" "+ d2);
                    if (diffrence > d2) {
                        comp.status = "pickup";
                        comp.changeHead(userlogin, username, password);
                        comp.PickupDate = comp.CurrentDate;
                        seller.sendMessage(comp.toString());
                    }
                }

            }
        }
    }
}

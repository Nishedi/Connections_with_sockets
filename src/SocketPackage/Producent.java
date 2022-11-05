package SocketPackage;

import tools.Complaints;

import java.io.*;
import java.net.Socket;
import java.util.Random;
public class Producent {
    public static void main(String[]args) throws IOException, InterruptedException {
        String userlogin = "Producer";
        String username = "Kate";
        String password = "Kate123";
        Complaints comp = new Complaints();
        Socket socket = new Socket("localhost", 1234);
        SocketClient producent = new SocketClient(socket);
        producent.listenForMessage();

        while(1==1){
            comp = new Complaints(userlogin,username,password);
            comp.command="getAllProducer";
            producent.sendMessage(comp.toString());
            Thread.sleep(1000);

            while(producent.numberofmessages()>0){
                String str=producent.getAndRemove();
                comp = new Complaints(str);
                System.out.println(comp);
                Random rand = new Random();
                int d = rand.nextInt(0,2);
                int d2 = rand.nextInt(0,14);
                d=1;

                Integer RegistrationDateInt = Integer.valueOf(comp.ForwardDate);
                Integer CurrentDateInt = Integer.valueOf(comp.CurrentDate);
                Integer diffrence = CurrentDateInt - RegistrationDateInt;
                if(diffrence>d2) {

                    String strtoloss;
                    if(d==0) strtoloss = "rejected";
                    else strtoloss = "confirmed";
                    comp.status=strtoloss;
                    comp.changeHead(userlogin, username, password);
                    comp.ResponseDate = comp.CurrentDate;
                    producent.sendMessage(comp.toString());
                }
            }
        }
    }
}

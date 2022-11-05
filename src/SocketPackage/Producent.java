package SocketPackage;

import tools.Complaints;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Producent {
    public static void main(String[]args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        String userlogin = "Producer";
        String username = "Samsung";
        String password = "Samsung123";
        Scanner scan = new Scanner(System.in);
        System.out.println("Type Company login: ");
        username = scan.nextLine();
        System.out.println("Type Company password: ");
        password = scan.nextLine();
        Complaints comp = new Complaints();
        Socket socket = new Socket("localhost", 1234);
        SocketClient producent = new SocketClient(socket);
        producent.listenForMessage();

        while(1==1){
            comp.updateFromString("login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";");
            comp.command="getAllProducer";
            producent.sendMessage(comp.toString());
            Thread.sleep(1000);

            while(producent.numberofmessages()>0){
                String str=producent.getAndRemove();
                System.out.println(str);
                comp = new Complaints();
                comp.updateFromString(str);
                if(comp.command.compareTo("Wrong authorization!")==0){
                    System.out.println("Wrong authorization!");
                    return;
                }
                Random rand = new Random();
                int d = rand.nextInt(0,14);
                Integer RegistrationDateInt = Integer.valueOf(comp.ForwardDate);
                Integer CurrentDateInt = Integer.valueOf(comp.CurrentDate);
                Integer diffrence = CurrentDateInt - RegistrationDateInt;
                if(diffrence>d) {
                    String string = "login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";";
                    comp.updateFromString(string);
                    comp.status="confirmed";
                    comp.ResponseDate = comp.CurrentDate;
                    producent.sendMessage(comp.toString());
                }
            }
        }
    }
}

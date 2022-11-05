package SocketPackage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient {
    public static ArrayList<String> listofids = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    public int numberofmessages(){
        return listofids.size();
    }

    public String getAndRemove(){
        if(numberofmessages()==0) return null;
        String str = listofids.get(0);
        listofids.remove(0);
        return str;
    }

    public SocketClient(Socket socket){
        try{
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String messageToSend){
        try{
            if (socket.isConnected()){
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()){
                    try{
                        msgFromGroupChat=bufferedReader.readLine();
                        listofids.add(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public void close(){
        closeEverything(socket, bufferedReader, bufferedWriter);
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket!= null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


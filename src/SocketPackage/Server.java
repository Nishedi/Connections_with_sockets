package SocketPackage;

import tools.Menegerofusers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;


public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) throws Exception {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (Exception e){
        closeServerSocket();
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket!=null)
                serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        String s ="file:/C:/DB/"+"usernames.txt";
        URL url= new URL(s);
        Menegerofusers menegerofusers = new Menegerofusers();

        menegerofusers.load_data(url);
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }


}

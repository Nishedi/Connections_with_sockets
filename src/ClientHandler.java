import tools.Complaints;
import tools.Menegerofusers;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ClientHandler implements Runnable {
    public static Map<String, Complaints> mapofprovided=new HashMap<>();
    public static Integer numbertosend = 0;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static Menegerofusers menegerofusers;
    SimpleDateFormat formatter= null;
    long starttime=0;

    public ClientHandler(Socket socket) throws Exception {
        try{
            starttime = System.currentTimeMillis();
            String s ="file:/C:/DB/"+"usernames.txt";
            URL url= new URL(s);
            this.menegerofusers = new Menegerofusers();
            menegerofusers.load_data(url);
            this.socket=socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        String messageFromClient;
        while(socket.isConnected()){

            try{
                messageFromClient=bufferedReader.readLine();
                //System.out.println(messageFromClient);
                Complaints complain = new Complaints(messageFromClient);
                System.out.println(complain.toString());
                System.out.println("msg= "+messageFromClient);
                int b = complain.parseResults;
                if(b==-1)continue;

                String username = complain.username;
                String password = complain.password;
                String command = complain.command;
                long time=System.currentTimeMillis();
                Long diffrence = (time - starttime)/1000;
                if(b==1){
                    Complaints comp = new Complaints("server","","");
                    comp.command="Invalid data format!";
                    comp.RegistrationDate=diffrence.toString();
                    SendMessage(comp.toString());
                    continue;
                }

                if(b!=1){

                    //check authorization
                    boolean authorization = false;
                    if(menegerofusers.mapofusers.containsKey(username)==true){
                        if(menegerofusers.mapofusers.get(username).password.compareTo(password)==0){
                            authorization = true;
                        }
                    }
                    if(authorization==false) {
                        Complaints comp = new Complaints("server","","");
                        comp.command="Wrong authorization!";
                        comp.CurrentDate=diffrence.toString();
                        SendMessage(comp.toString());
                        continue;
                    }

                    if(command.compareTo("askforcomplienceid")==0){
                        numbertosend++;
                        Complaints comp = new Complaints("server","","");
                        comp.command="ComplienceID";
                        comp.argument=numbertosend.toString();
                        comp.CurrentDate=diffrence.toString();
                        SendMessage(comp.toString());
                        continue;
                    }

                    if(command.compareTo("save")==0){


                        //System.out.println("DIF = "+diffrence);

                        //String[] tabdate = formatter.format(date).split(" ");
                        //System.out.println(tabdate[0]);
                        if(complain.login.compareTo("Client")==0)
                        complain.RegistrationDate=diffrence.toString();
                        System.out.println("compdate+"+complain.RegistrationDate);
                        String key = complain.idofcomplaint;
                        if(!mapofprovided.containsKey(key)){

                            mapofprovided.put(key,complain);
                        }

                        if(complain.status.compareTo("tosend")==0) {
                            complain.status = "atseller";
                        }
                        mapofprovided.put(key, complain);
                        continue;
                    }


                    if(command.compareTo("getAllSeller")==0){
                        for(String s: mapofprovided.keySet()){
                            if(mapofprovided.get(s).status.compareTo("atseller")==0) {
                                Complaints comp = mapofprovided.get(s);
                                comp.CurrentDate=diffrence.toString();
                                SendMessage(comp.toString());
                            }
                        }
                        continue;
                    }

                    if(command.compareTo("getAllProducer")==0){

                        for(String s: mapofprovided.keySet()){

                            if(mapofprovided.get(s).status.compareTo("atproducer")==0) {
                                Complaints comp = mapofprovided.get(s);
                                comp.CurrentDate=diffrence.toString();
                                SendMessage(comp.toString());
                            }
                        }
                        continue;
                    }

                    if(command.compareTo("getAll")==0){

                        for(String s: mapofprovided.keySet()){
                            Complaints comptemp = mapofprovided.get(s);
                            comptemp.changeHead("Server","","");
                            comptemp.CurrentDate=diffrence.toString();
                            SendMessage(comptemp.toString());
                            //mapofprovided.get(s).toString()
                            }
                        continue;
                    }
                   // System.out.println(messageFromClient);
                    SendMessage(messageFromClient);
                }
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void SendMessage(String messageTosent){
        try{
            bufferedWriter.write(messageTosent);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void removeClientHandler(){
        System.out.println("Remove client Handler");
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

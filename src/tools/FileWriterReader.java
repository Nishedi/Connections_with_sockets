package tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FileWriterReader {
    public ArrayList<String> read(URL url) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader ReadF = null;
            ReadF = new BufferedReader(new InputStreamReader(url.openStream()));
            String numstring = ReadF.readLine();
            try {
                while (numstring != null) {
                    list.add(numstring);
                    numstring = ReadF.readLine();
                }
            } catch (Exception er) {
                return null;
            }
            ReadF.close();
        }catch (Exception x){
            return null;
        }

        return list;
    }

    /*public static void savetootherfile(String name, Map<String, Complaints> mapofcomplains) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        for(String s: mapofcomplains.keySet()){
            list.add(mapofcomplains.get(s).toSString());
        }
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(name);//nazwa pliku

            for(String s: list)
                fWriter.write(s+"\n");

        } finally {
            if (fWriter != null) {
                fWriter.close();
            }
        }
    }*/
}

package tools;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menegerofusers {
    public Map<String, User> mapofusers = new HashMap<>();
    public void load_data(URL url) throws Exception {
        FileWriterReader reader = new FileWriterReader();
        ArrayList<String> readlist = null;
        int i =0;
        do {
            i++;

            readlist = reader.read(url);
            if (readlist == null) {
                System.out.println("Błąd odczytu pliku");
                System.out.println(url.toString());
                Thread.sleep(1000);
            }else{
                break;
            }

        }while(i<50);
        if(readlist==null)
            readlist=new ArrayList<>();
        for(String s: readlist) {
            loadtolist(s);
        }
    }

    public void loadtolist(String str){
        User user = new User();
        user.parseClientfromString(str);
        //listofcomplaints.add(complain);
        mapofusers.put(user.username,user);
    }

    /*public void show(Map<String, Complaints> mapofcomplains) {
        for(String s: mapofcomplains.keySet())
            System.out.println("|"+ String.format("%3s", mapofcomplains.get(s).idofcomplaint)+"|"+String.format("%12s",mapofcomplains.get(s).status+"|"));
    }*/

    /*public Map<String, Complaints> preparetosent(String status){
        Map<String, Complaints> map = new HashMap<>();
        for(String s: mapofcomplains.keySet()){
            if(mapofcomplains.get(s).status.compareTo(status)==0){
                map.put(mapofcomplains.get(s).idofcomplaint, mapofcomplains.get(s));
            }
        }
        return map;
    }
*/


}

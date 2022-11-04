package tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplianceDB {
    public ArrayList<Complaints> CompliencesList = new ArrayList<>();


    public void CreateAndLoadTable(String username){
       // System.out.println("atlogin");
        String s ="file:/C:/DB/"+"Complaintable.csv";
        URL url= null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        FileWriterReader reader = new FileWriterReader();
        ArrayList<String>lstr=new ArrayList<>();
        try {
            lstr=reader.read(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for(String str:lstr){
            Complaints complaints = new Complaints();
            complaints.parseFromString2(str);
            if(complaints.Client.compareTo(username)==0){
                CompliencesList.add(complaints);
            }
        }
    }
    public int rowCount(){
        return CompliencesList.size();
    }

    public Object[] rowObject(int nr){
        Complaints complaints = CompliencesList.get(nr);
        ArrayList<Object> listofobj = new ArrayList<>();
        listofobj.add(complaints.idofcomplaint);
        listofobj.add(complaints.Client);
        listofobj.add(complaints.Product);
        listofobj.add(complaints.Company);
        listofobj.add(complaints.status);
        listofobj.add(complaints.RegistrationDate);
        listofobj.add(complaints.ForwardDate);
        listofobj.add(complaints.ResponseDate);
        listofobj.add(complaints.PickupDate);
        listofobj.add(complaints.CloseDate);
        listofobj.add(complaints.description);

        return listofobj.toArray();
    }
    public void update(ArrayList<Complaints> complaints){
        Map<String, Complaints> map = new HashMap<>();// klucz numer reklamacji

        for(Complaints comp: complaints){
            map.put(comp.idofcomplaint, comp);
        }
        for(int i = 0 ; i<=CompliencesList .size()-1;i++){
            if(map.containsKey(CompliencesList.get(i).idofcomplaint)){
               CompliencesList.set(i,map.get(complaints.get(i).idofcomplaint));
            }
        }
    }
}

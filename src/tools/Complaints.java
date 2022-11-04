package tools;

public class Complaints {
    public String idofcomplaint="";
    public String Product ="";
    public String Client ="";
    public String status="";
    public String description="";
    public String RegistrationDate ="";
    public String ForwardDate ="";
    public String ResponseDate ="";
    public String PickupDate ="";
    public String CloseDate ="";
    public String login ="";
    public String password ="";
    public String username ="";
    public String command="";
    public String argument="";
    public int parseResults=0;
    public String Company = "";
    public String CurrentDate = "???";

    public Complaints(String str){
        parseFromString(str);
    }

    public Complaints(String login, String username, String password){
        changeHead(login, username, password);

    }
    public Complaints(){}
    public void parseFromString(String str){
        parseResults = 0;
        String[] tabstr = str.split(";");
        if(tabstr.length==0){
            parseResults=-1;
            return;
        }
        login=tabstr[0];
        if(tabstr.length<=3) {
            parseResults=1;
            return;
        }
        username =tabstr[1];
        password=tabstr[2];
        command=tabstr[3];
        if(tabstr.length>4)
            idofcomplaint = tabstr[4];
        if(tabstr.length>5)
            status = tabstr[5];
        if(tabstr.length>6)
            Product = tabstr[6];
        if(tabstr.length>7)
            Client = tabstr[7];
        if(tabstr.length>8)
            description = tabstr[8];
        if(tabstr.length>9)
            RegistrationDate = tabstr[9];
        if(tabstr.length>10)
            ForwardDate = tabstr[10];
        if(tabstr.length>11)
            ResponseDate = tabstr[11];
        if(tabstr.length>12)
            PickupDate = tabstr[12];
        if(tabstr.length>13)
            CloseDate = tabstr[13];
        if(tabstr.length>14)
            argument = tabstr[14];
        if(tabstr.length>15)
            Company = tabstr[15];
        if(tabstr.length>16)
            CurrentDate=tabstr[16];
    }
    public void parseFromString2(String str){
        String[] tabs=str.split(";");
        idofcomplaint=tabs[0];
        Client=tabs[1];
        Product=tabs[2];
        Company = tabs[3];
        status = tabs[4];
        RegistrationDate=tabs[5];
        ForwardDate=tabs[6];
        ResponseDate=tabs[7];
        PickupDate=tabs[8];
        CloseDate=tabs[9];
        description=tabs[10];
    }
    public String toString(){
        String str="";
        str=login+";"+ username +";"+password+";"+command+";"+idofcomplaint+";"+status+";"+ Product +";"+ Client +";"+description+";"+ RegistrationDate +";"+ ForwardDate +";"+ ResponseDate +";"+ PickupDate +";"+ CloseDate +";"+argument+";"+Company+";"+CurrentDate+";";
        return str;
    }

    public void changeHead(String login, String username, String password){
        this.login=login;
        this.username=username;
        this.password=password;
    }
}

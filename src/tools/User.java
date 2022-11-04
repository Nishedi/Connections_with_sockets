package tools;

public class User {
   public String username;
   public String password;
   public String function;
   public String instruction;
   public String arguments;

    public User(){}

    public User(String username, String password, String function) {
        this.username = username;
        this.password = password;
        this.function = function;
    }
    public void parseClientfromString(String str){
        String[] tabstr = str.split(";");
        username=tabstr[0];
        password=tabstr[1];
        function=tabstr[2];
    }
}

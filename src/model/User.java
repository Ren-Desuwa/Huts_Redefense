package model;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String email;
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public User(int id, String username, String password, String email) {
        this.user_id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public int getUser_Id() {return user_id;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    
    public void setUser_Id(int id) {this.user_id = id;}
    public void setPassword(String password) {this.password = password;}
    public void setUsername(String username) {this.username = username;}
    public void setEmail(String email) {this.email = email;}
    
}

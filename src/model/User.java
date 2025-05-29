package model;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String email;
    
    public User() {}
    
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

/*
 * File: User.java
 *
 * Description:
 * This file defines the `User` class, which represents a user in the system. 
 * It encapsulates the user's information, including their unique ID, username, password, and email address.
 * The class provides getter and setter methods to access and modify the user's attributes.
 *
 * Variables:
 * - `user_id` (int): A unique identifier for the user.
 * - `username` (String): The username of the user.
 * - `password` (String): The password of the user.
 * - `email` (String): The email address of the user.
 *
 * Constructors:
 * 1. `User()`: Default constructor that initializes an empty user object.
 * 2. `User(String username, String password, String email)`:
 *    - Initializes a user object with the specified username, password, and email.
 * 3. `User(int id, String username, String password, String email)`:
 *    - Initializes a user object with the specified ID, username, password, and email.
 *
 * Methods:
 * 1. `getUser_Id()`: Returns the unique ID of the user.
 * 2. `getUsername()`: Returns the username of the user.
 * 3. `getPassword()`: Returns the password of the user.
 * 4. `getEmail()`: Returns the email address of the user.
 * 5. `setUser_Id(int id)`: Sets the unique ID of the user.
 * 6. `setUsername(String username)`: Sets the username of the user.
 * 7. `setPassword(String password)`: Sets the password of the user.
 * 8. `setEmail(String email)`: Sets the email address of the user.
 *
 * Usage:
 * The `User` class is used to represent and manage user information in the system. 
 * It is commonly used in authentication, profile management, and database operations involving user data.
 */

package database;

import java.sql.*;

import model.User;

public class User_Manager {
	
	private Connection connection;
	
	private User currentUser;
	
	public User_Manager(Connection connection) {
		this.connection = connection;
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUserNull() {
		this.currentUser = null;
	}
	
	public void addUser(String username, String password, String email) throws SQLException {
		if (getUserByUsername(username) != null) {
			System.out.println("Username already exists");
			return;
		}
		if (getUserByEmail(email) != null) {
			System.out.println("Email already exists");
			return;
		}
		String sqlscript = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript, Statement.RETURN_GENERATED_KEYS)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, password);
			prepared_statement.setString(3, email);
			prepared_statement.executeUpdate();
			
			// Get the generated ID
			try (ResultSet resultset = prepared_statement.getGeneratedKeys()) {
				if (resultset.next()) {
					int id = resultset.getInt(1);
					System.out.println("Inserted user with ID: " + id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void addUser(User user) throws SQLException {
		addUser(user.getUsername(), user.getPassword(), user.getEmail());
	}
	
	public void deleteUser(int userId) throws SQLException {
		String sqlscript = "DELETE FROM users WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, userId);
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void deleteUser(User user) throws SQLException {
		deleteUser(user.getUser_Id());
	}
	
	public void updateUser(User user, String username, String password, String email) throws SQLException {
		String sqlscript = "UPDATE users SET username = ?, password = ?, email = ? WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, password);
			prepared_statement.setString(3, email);
			prepared_statement.setInt(4, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public User getUserById(int userId) throws SQLException {
		String sqlscript = "SELECT * FROM users WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, userId);
			ResultSet resultSet = prepared_statement.executeQuery();
			if (resultSet.next()) {
				return new User(
					resultSet.getInt("user_id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public User getUserByUsername(String username) throws SQLException {
		// Using GLOB operator for case-sensitive comparison
		String sqlscript = "SELECT * FROM users WHERE username = ? COLLATE BINARY";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			ResultSet resultSet = prepared_statement.executeQuery();
			if (resultSet.next()) {
				return new User(
					resultSet.getInt("user_id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public User getUserByEmail(String email) throws SQLException {
		String sqlscript = "SELECT * FROM users WHERE email = ? COLLATE NOCASE";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, email);
			ResultSet resultSet = prepared_statement.executeQuery();
			if (resultSet.next()) {
				return new User(
					resultSet.getInt("user_id"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					resultSet.getString("email")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public boolean checkUserEmail(String username, String email) {
	    try {
	        // Check if username exists
	        if (getUserByUsername(username) != null || getUserByEmail(email) != null) {
	            return true;
	        }
	        
	        return false;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean UsernameEmailMatch(String username, String email){
		String sqlscript = "SELECT * FROM users WHERE username = ? COLLATE BINARY AND email = ? COLLATE NOCASE";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, email);
			ResultSet resultSet = prepared_statement.executeQuery();
			return resultSet.next(); //
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean UsernamePasswordMatch(String username, String password) throws SQLException {
		String sqlscript = "SELECT * FROM users WHERE username = ? COLLATE BINARY AND password = ? COLLATE BINARY";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, username);
			prepared_statement.setString(2, password);
			ResultSet resultSet = prepared_statement.executeQuery();
			return resultSet.next(); // If a row is returned, the username and password match // true else false
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}
	
	public void updateUserPassword(User user, String password) throws SQLException {
		String sqlscript = "UPDATE users SET password = ? WHERE user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, password);
			prepared_statement.setInt(2, user.getUser_Id());
			prepared_statement.executeUpdate(); // UPDATE users SET password = password WHERE id = 2?
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
}
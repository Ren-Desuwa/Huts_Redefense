package database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class Reading_Manager {
	
	private Connection connection;
	
	public Reading_Manager(Connection connection) {
		this.connection = connection;
	}

	public void addReading(User user, LocalDate date, String type, double reading, double rate, double total_price) throws SQLException {
		String sqlscript = "INSERT INTO readings (user_id, date, type, reading, rate, total_price) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript, Statement.RETURN_GENERATED_KEYS)) {
			prepared_statement.setInt(1, user.getUser_Id()); // user_id
			prepared_statement.setString(2, date.toString()); // date
			prepared_statement.setString(3, type); // type
			prepared_statement.setDouble(3, reading);
			prepared_statement.setDouble(4, rate);
			prepared_statement.setDouble(5, total_price);
			prepared_statement.executeUpdate();
			
			// Get the generated ID
			try (ResultSet rs = prepared_statement.getGeneratedKeys()) {
				if (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Inserted reading with ID: " + id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void addReading(User user, LocalDate date, String type, double reading, double total_price) throws SQLException {
		String sqlscript = "INSERT INTO readings (user_id, date, type, reading, rate, total_price) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript, Statement.RETURN_GENERATED_KEYS)) {
			prepared_statement.setInt(1, user.getUser_Id()); // user_id
			prepared_statement.setString(2, date.toString()); // date
			prepared_statement.setString(3, type); // type
			prepared_statement.setDouble(4, reading); // reading
			prepared_statement.setDouble(5, total_price); // total_price
			prepared_statement.executeUpdate();
			
			// Get the generated ID
			try (ResultSet rs = prepared_statement.getGeneratedKeys()) {
				if (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Inserted reading with ID: " + id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void addReading(User user, Reading reading) throws SQLException {
		addReading(user, reading.getDate(), reading.getType(), reading.getReading(), reading.getRate(), reading.getTotal_Price());
	}
	
	public void deleteReading(User user, int readingId) throws SQLException {
		String sqlscript = "DELETE FROM readings WHERE rading_id = ? AND user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, readingId);
			prepared_statement.setInt(2, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteReading(Reading reading) throws SQLException {
		String sqlscript = "DELETE FROM readings WHERE rading_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, reading.getReading_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteReading(User user, Reading reading) throws SQLException {
		deleteReading(user, reading.getReading_Id());
	}
	
	public void updateReading(User user, int readingId, LocalDate date, String type, double reading, double rate, double total_price) throws SQLException {
		String sqlscript = "UPDATE readings SET date = ?, type = ?, reading = ?, rate = ?, total_price = ? WHERE reading_id = ? AND user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, date.toString()); //yyyy-mm-dd
			prepared_statement.setString(2, type);
			prepared_statement.setDouble(3, reading);
			prepared_statement.setDouble(4, rate);
			prepared_statement.setDouble(5, total_price);
			prepared_statement.setInt(6, readingId);
			prepared_statement.setInt(7, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void updateReading(User user, int readingId, LocalDate date, String type, double reading, double total_price) throws SQLException {
		String sqlscript = "UPDATE readings SET date = ?, type = ?, reading = ?, total_price = ? WHERE reading_id = ? AND user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setString(1, date.toString()); //yyyy-mm-dd
			prepared_statement.setString(2, type);
			prepared_statement.setDouble(3, reading);
			prepared_statement.setDouble(4, total_price);
			prepared_statement.setInt(5, readingId);
			prepared_statement.setInt(6, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	//updateReading(user, reading.getReading_Id(), reading.getDate(), reading.getType(), reading.getReading(), reading.getTotal_Price());
	
	public void updateReading(User user, Reading reading) throws SQLException {
		updateReading(user, reading.getReading_Id(), reading.getDate(), reading.getType(), reading.getReading(), reading.getRate(), reading.getTotal_Price());
	}
	
	public Reading getReadingById(User user, int readingId) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE reading_id = ? AND user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, readingId);
			prepared_statement.setInt(2, user.getUser_Id());
			try (ResultSet rs = prepared_statement.executeQuery()) {
				if (rs.next()) {
					return new Reading(
						rs.getInt("reading_id"),
						rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date")),
						rs.getString("type"),
						rs.getDouble("reading"),
						rs.getDouble("rate"),
						rs.getDouble("total_price")
					);
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public Reading getReadingById(int readingId) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE reading_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, readingId);
			try (ResultSet rs = prepared_statement.executeQuery()) {
				if (rs.next()) {
					return new Reading(
						rs.getInt("reading_id"),
						rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date")),
						rs.getString("type"),
						rs.getDouble("reading"),
						rs.getDouble("rate"),
						rs.getDouble("total_price")
					);
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public Reading getReadingById(User user, Reading reading) throws SQLException {
		return getReadingById(user, reading.getReading_Id());
	}
	
	public Reading getReadingById(Reading reading) throws SQLException {
		return getReadingById(reading.getReading_Id());
	}
	
	public List<Reading> getReadingsByUserId(User user) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? ORDER BY date ASC";
		List<Reading> list = new ArrayList<>();
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			try (ResultSet rs = prepared_statement.executeQuery()) {
				while (rs.next()) {
					list.add(new Reading(
						rs.getInt("reading_id"),
						rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date")),
						rs.getString("type"),
						rs.getDouble("reading"),
						rs.getDouble("rate"),
						rs.getDouble("total_price")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return list;
	}
	
	public List<Reading> getReadingsByTime(User user, LocalDate startDate, LocalDate endDate) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND date >= ? AND date <= ? ORDER BY date ASC";
		List<Reading> list = new ArrayList<>();
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			prepared_statement.setString(2, startDate.toString());
			prepared_statement.setString(3, endDate.toString());
			try (ResultSet rs = prepared_statement.executeQuery()) {
				while (rs.next()) {
					list.add(new Reading(
						rs.getInt("reading_id"),
						rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date")),
						rs.getString("type"),
						rs.getDouble("reading"),
						rs.getDouble("rate"),
						rs.getDouble("total_price")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return list;
	}
	
	public Reading getLatestReadingByType(User user,String type) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND type = ? ORDER BY date DESC LIMIT 1";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			prepared_statement.setString(2, type);
			try (ResultSet rs = prepared_statement.executeQuery()) {
				if (rs.next()) {
					return new Reading(
						rs.getInt("reading_id"),
						rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date")),
						rs.getString("type"),
						rs.getDouble("reading"),
						rs.getDouble("rate"),
						rs.getDouble("total_price")
					);
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public List<Reading> getAllReadingsByType(User user, String type) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND type = ? ORDER BY date ASC";
		List<Reading> list = new ArrayList<>();
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			prepared_statement.setString(2, type);
			try (ResultSet rs = prepared_statement.executeQuery()) {
				while (rs.next()) {
					list.add(new Reading(
						rs.getInt("reading_id"),
						rs.getInt("user_id"),
						LocalDate.parse(rs.getString("date")),
						rs.getString("type"),
						rs.getDouble("reading"),
						rs.getDouble("rate"),
						rs.getDouble("total_price")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return list;
	}
	
}

package database;

import java.awt.Color;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			prepared_statement.setDouble(4, reading);
			prepared_statement.setDouble(5, rate);
			prepared_statement.setDouble(6, total_price);
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
		String sqlscript = "INSERT INTO readings (user_id, date, type, reading, total_price) VALUES (?, ?, ?, ?, ?)";
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
		String sqlscript = "DELETE FROM readings WHERE reading_id = ? AND user_id = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, readingId);
			prepared_statement.setInt(2, user.getUser_Id());
			prepared_statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteReading(Reading reading) throws SQLException {
		String sqlscript = "DELETE FROM readings WHERE reading_id = ?";
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
	
	public List<Reading> getReadingsByDate(User user, LocalDate startDate, LocalDate endDate) throws SQLException {
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
	
	public List<Reading> getReadingsByDateAndType(User user, LocalDate startDate, LocalDate endDate, String type) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND date >= ? AND date <= ? AND type = ? ORDER BY date ASC";
		List<Reading> list = new ArrayList<>();
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			prepared_statement.setString(2, startDate.toString());
			prepared_statement.setString(3, endDate.toString());
			prepared_statement.setString(4, type);
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
	
	public Reading getLatestReadingByType(User user, String type) throws SQLException {
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
	
	public boolean isReadingExists(User user, String type) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND type = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, user.getUser_Id());
			prepared_statement.setString(2, type);
			try (ResultSet rs = prepared_statement.executeQuery()) {
				return rs.next(); // if a record exists, rs.next() will return true else false
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false; // return false if an error occurs
	}
	
	public List<Reading> getAllReadingsByType(User user, String type) throws SQLException {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND type = ? ORDER BY date DESC";
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
	
	// NEW METHODS MOVED FROM HOME_PANEL
	
	/**
     * Groups readings by month and calculates either sum of readings or sum of total price
     * 
     * @param readings List of readings to group
     * @param usePrice If true, uses total_price field; if false, uses reading field
     * @return Map with Month as key and summed value as value
     */
    public Map<Month, Double> groupReadingsByMonth(List<Reading> readings, boolean usePrice) {
        Map<Month, Double> monthlyData = new HashMap<>();
        
        if (readings != null) {
            for (Reading reading : readings) {
                LocalDate readingDate = reading.getDate();
                Month month = readingDate.getMonth();
                
                double value = usePrice ? reading.getTotal_Price() : reading.getReading();
                
                // Add value to existing month or create new entry
                monthlyData.put(month, monthlyData.getOrDefault(month, 0.0) + value);
            }
        }
        
        return monthlyData;
    }
    
    /**
     * Gets utility usage data organized by month for a specific time period
     * 
     * @param user The user to get data for
     * @param utilityType The type of utility ("electricity", "water", "gas")
     * @param months Number of months to look back from current date
     * @param usePrice Whether to use price values (true) or reading values (false)
     * @return Map with Month as key and value as double
     */
    public Map<Month, Double> getMonthlyUtilityData(User user, String utilityType, int months, boolean usePrice) 
            throws SQLException {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months);
        
        List<Reading> readings = getReadingsByDateAndType(user, startDate, endDate, utilityType);
        return groupReadingsByMonth(readings, usePrice);
    }
    
    /**
     * Gets combined monthly expense data for all utilities
     * 
     * @param user The user to get data for 
     * @param months Number of months to look back from current date
     * @return Map with Month as key and total expense as value
     */
    public Map<Month, Double> getMonthlyTotalExpenses(User user, int months) throws SQLException {
        Map<Month, Double> totalExpenses = new HashMap<>();
        
        // Get data for each utility type
        Map<Month, Double> electricityExpenses = getMonthlyUtilityData(user, "electricity", months, true);
        Map<Month, Double> waterExpenses = getMonthlyUtilityData(user, "water", months, true);
        Map<Month, Double> gasExpenses = getMonthlyUtilityData(user, "gas", months, true);
        
        // Get all months from any of the maps
        for (Month month : electricityExpenses.keySet()) {
            double totalForMonth = electricityExpenses.getOrDefault(month, 0.0) +
                                  waterExpenses.getOrDefault(month, 0.0) +
                                  gasExpenses.getOrDefault(month, 0.0);
            totalExpenses.put(month, totalForMonth);
        }
        
        // Add months from water expenses that might not be in electricity
        for (Month month : waterExpenses.keySet()) {
            if (!totalExpenses.containsKey(month)) {
                double totalForMonth = waterExpenses.get(month) +
                                      gasExpenses.getOrDefault(month, 0.0);
                totalExpenses.put(month, totalForMonth);
            }
        }
        
        // Add months from gas expenses that might not be in either electricity or water
        for (Month month : gasExpenses.keySet()) {
            if (!totalExpenses.containsKey(month)) {
                totalExpenses.put(month, gasExpenses.get(month));
            }
        }
        
        return totalExpenses;
    }
    
    /**
     * Gets total expenses for all utilities in a specified date range
     * 
     * @param user The user to get data for
     * @param startDate The start date
     * @param endDate The end date
     * @return Total expenses for all utilities in the date range
     */
    public double getTotalExpensesInRange(User user, LocalDate startDate, LocalDate endDate) throws SQLException {
        double totalExpenses = 0.0;
        
        List<Reading> allReadings = getReadingsByDate(user, startDate, endDate);
        for (Reading reading : allReadings) {
            totalExpenses += reading.getTotal_Price();
        }
        
        return totalExpenses;
    }
    
    /**
     * Gets latest readings for all utility types
     * 
     * @param user The user to get readings for
     * @return Map with utility type as key and Reading object as value
     */
    public Map<String, Reading> getLatestReadingsForAllTypes(User user) throws SQLException {
        Map<String, Reading> latestReadings = new HashMap<>();
        
        // Common utility types
        String[] utilityTypes = {"electricity", "water", "gas"};
        
        for (String type : utilityTypes) {
            Reading reading = getLatestReadingByType(user, type);
            if (reading != null) {
                latestReadings.put(type, reading);
            }
        }
        
        return latestReadings;
    }
    
    /**
     * Calculates the total combined cost of the latest readings for all utilities
     * 
     * @param user The user to calculate for
     * @return The total cost of all latest utility readings
     */
    public double getTotalLatestCost(User user) throws SQLException {
        Map<String, Reading> latestReadings = getLatestReadingsForAllTypes(user);
        double totalCost = 0.0;
        
        for (Reading reading : latestReadings.values()) {
            totalCost += reading.getTotal_Price();
        }
        
        return totalCost;
    }
    
    public String getTrend(User user, String type) throws SQLException {
        String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND type = ? ORDER BY date DESC LIMIT 2";
        try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, type);
            try (ResultSet rs = prepared_statement.executeQuery()) {
                if (rs.next()) {
                    double latestReading = rs.getDouble("reading");
                    if (rs.next()) {
                        double previousReading = rs.getDouble("reading");
                        double percentageChange = ((latestReading - previousReading) / previousReading) * 100;
                        return String.format("%.1f%% consumption from last month", percentageChange);
                    }
                    return "No previous reading";
                }
                return "No readings available";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error calculating trend";
        }
    }
    public Color getTrendColor(User user, String type) throws SQLException {
		String trend = getTrend(user, type);
		if (trend.contains("No previous reading") || trend.contains("No readings available")) {
			return Color.GRAY; // Neutral color for no data
		}
		
		double percentageChange = Double.parseDouble(trend.replace("% consumption from last month", ""));
		if (percentageChange < 0) {
			return new Color(0,156,74); // Positive trend
		} else if (percentageChange > 0) {
			return new Color(255,0,0); // Negative trend
		} else {
			return Color.GRAY; // No change
		}
	}

	public Reading getReadingByMonth(User currentUser, String string, LocalDate previousMonth) {
		String sqlscript = "SELECT * FROM readings WHERE user_id = ? AND type = ? AND date = ?";
		try (PreparedStatement prepared_statement = connection.prepareStatement(sqlscript)) {
			prepared_statement.setInt(1, currentUser.getUser_Id());
			prepared_statement.setString(2, string);
			prepared_statement.setString(3, previousMonth.toString());
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
}
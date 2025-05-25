package database;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.*;
import view.panel.Electricity_Panel;
import view.panel.Water_Panel;
import view.panel.misc.Edit_Reading_Window;

public class Reading_Manager {
    
    private Connection database_connection;
    private double last_trend_percentage;
    
    /**
     * Constructor for Reading_Manager
     * @param database_connection Database connection object
     */
    public Reading_Manager(Connection database_connection) {
        this.database_connection = database_connection;
    }

    /**
     * Add a reading with rate information
     * @param user User who owns the reading
     * @param date Date of the reading
     * @param type Type of reading (electricity, water, gas)
     * @param reading Reading value
     * @param rate Rate applied to the reading
     * @param total_price Total price calculated
     * @throws SQLException If database operation fails
     */
    public void addReading(User user, LocalDate date, String type, double reading, double rate, double total_price) throws SQLException {
        String sql_script = "INSERT INTO readings (user_id, date, type, reading, rate, total_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script, Statement.RETURN_GENERATED_KEYS)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, date.toString());
            prepared_statement.setString(3, type);
            prepared_statement.setDouble(4, reading);
            prepared_statement.setDouble(5, rate);
            prepared_statement.setDouble(6, total_price);
            prepared_statement.executeUpdate();
            
            // Get the generated ID
            try (ResultSet result_set = prepared_statement.getGeneratedKeys()) {
                if (result_set.next()) {
                    int reading_id = result_set.getInt(1);
                    System.out.println("Inserted reading with ID: " + reading_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Add a reading without rate information
     * @param user User who owns the reading
     * @param date Date of the reading
     * @param type Type of reading (electricity, water, gas)
     * @param reading Reading value
     * @param total_price Total price calculated
     * @throws SQLException If database operation fails
     */
    public void addReading(User user, LocalDate date, String type, double reading, double total_price) throws SQLException {
        String sql_script = "INSERT INTO readings (user_id, date, type, reading, total_price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script, Statement.RETURN_GENERATED_KEYS)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, date.toString());
            prepared_statement.setString(3, type);
            prepared_statement.setDouble(4, reading);
            prepared_statement.setDouble(5, total_price);
            prepared_statement.executeUpdate();
            
            // Get the generated ID
            try (ResultSet result_set = prepared_statement.getGeneratedKeys()) {
                if (result_set.next()) {
                    int reading_id = result_set.getInt(1);
                    System.out.println("Inserted reading with ID: " + reading_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Add a reading using a Reading object
     * @param user User who owns the reading
     * @param reading Reading object to add
     * @throws SQLException If database operation fails
     */
    public void addReading(User user, Reading reading) throws SQLException {
        addReading(user, reading.getDate(), reading.getType(), reading.getReading(), reading.getRate(), reading.getTotal_Price());
    }
    
    /**
     * Delete a reading by ID
     * @param user User who owns the reading
     * @param reading_id ID of the reading to delete
     * @throws SQLException If database operation fails
     */
    public void deleteReading(User user, int reading_id) throws SQLException {
        String sql_script = "DELETE FROM readings WHERE reading_id = ? AND user_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, reading_id);
            prepared_statement.setInt(2, user.getUser_Id());
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Delete a reading using Reading object
     * @param reading Reading object to delete
     * @throws SQLException If database operation fails
     */
    public void deleteReading(Reading reading) throws SQLException {
        String sql_script = "DELETE FROM readings WHERE reading_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, reading.getReading_Id());
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Delete a reading using User and Reading objects
     * @param user User who owns the reading
     * @param reading Reading object to delete
     * @throws SQLException If database operation fails
     */
    public void deleteReading(User user, Reading reading) throws SQLException {
        deleteReading(user, reading.getReading_Id());
    }
    
    /**
     * Update a reading with rate information
     * @param user User who owns the reading
     * @param reading_id ID of the reading to update
     * @param date Updated date
     * @param type Updated type
     * @param reading Updated reading value
     * @param rate Updated rate
     * @param total_price Updated total price
     * @throws SQLException If database operation fails
     */
    public void updateReading(User user, int reading_id, LocalDate date, String type, double reading, double rate, double total_price) throws SQLException {
        String sql_script = "UPDATE readings SET date = ?, type = ?, reading = ?, rate = ?, total_price = ? WHERE reading_id = ? AND user_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setString(1, date.toString());
            prepared_statement.setString(2, type);
            prepared_statement.setDouble(3, reading);
            prepared_statement.setDouble(4, rate);
            prepared_statement.setDouble(5, total_price);
            prepared_statement.setInt(6, reading_id);
            prepared_statement.setInt(7, user.getUser_Id());
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Update a reading without rate information
     * @param user User who owns the reading
     * @param reading_id ID of the reading to update
     * @param date Updated date
     * @param type Updated type
     * @param reading Updated reading value
     * @param total_price Updated total price
     * @throws SQLException If database operation fails
     */
    public void updateReading(User user, int reading_id, LocalDate date, String type, double reading, double total_price) throws SQLException {
        String sql_script = "UPDATE readings SET date = ?, type = ?, reading = ?, total_price = ? WHERE reading_id = ? AND user_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setString(1, date.toString());
            prepared_statement.setString(2, type);
            prepared_statement.setDouble(3, reading);
            prepared_statement.setDouble(4, total_price);
            prepared_statement.setInt(5, reading_id);
            prepared_statement.setInt(6, user.getUser_Id());
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Update a reading using a Reading object
     * @param user User who owns the reading
     * @param reading Reading object with updated information
     * @throws SQLException If database operation fails
     */
    public void updateReading(User user, Reading reading) throws SQLException {
        updateReading(user, reading.getReading_Id(), reading.getDate(), reading.getType(), reading.getReading(), reading.getRate(), reading.getTotal_Price());
    }
    
    /**
     * Get a reading by ID for a specific user
     * @param user User who owns the reading
     * @param reading_id ID of the reading to retrieve
     * @return Reading object if found, null otherwise
     * @throws SQLException If database operation fails
     */
    public Reading getReading_By_Id(User user, int reading_id) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE reading_id = ? AND user_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, reading_id);
            prepared_statement.setInt(2, user.getUser_Id());
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                if (result_set.next()) {
                    return new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get a reading by ID regardless of user
     * @param reading_id ID of the reading to retrieve
     * @return Reading object if found, null otherwise
     * @throws SQLException If database operation fails
     */
    public Reading getReading_By_Id(int reading_id) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE reading_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, reading_id);
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                if (result_set.next()) {
                    return new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get a reading by Reading object for a specific user
     * @param user User who owns the reading
     * @param reading Reading object containing the ID to search for
     * @return Reading object if found, null otherwise
     * @throws SQLException If database operation fails
     */
    public Reading getReading_By_Id(User user, Reading reading) throws SQLException {
        return getReading_By_Id(user, reading.getReading_Id());
    }
    
    /**
     * Get a reading by Reading object regardless of user
     * @param reading Reading object containing the ID to search for
     * @return Reading object if found, null otherwise
     * @throws SQLException If database operation fails
     */
    public Reading getReading_By_Id(Reading reading) throws SQLException {
        return getReading_By_Id(reading.getReading_Id());
    }
    
    /**
     * Get all readings for a specific user
     * @param user User whose readings to retrieve
     * @return List of Reading objects
     * @throws SQLException If database operation fails
     */
    public List<Reading> getReadings_By_User_Id(User user) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE user_id = ? ORDER BY date ASC";
        List<Reading> reading_list = new ArrayList<>();
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                while (result_set.next()) {
                    reading_list.add(new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return reading_list;
    }
    
    /**
     * Get readings within a date range for a specific user
     * @param user User whose readings to retrieve
     * @param start_date Start date of the range
     * @param end_date End date of the range
     * @return List of Reading objects
     * @throws SQLException If database operation fails
     */
    public List<Reading> getReadings_By_Date(User user, LocalDate start_date, LocalDate end_date) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE user_id = ? AND date >= ? AND date <= ? ORDER BY date ASC";
        List<Reading> reading_list = new ArrayList<>();
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, start_date.toString());
            prepared_statement.setString(3, end_date.toString());
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                while (result_set.next()) {
                    reading_list.add(new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return reading_list;
    }
    
    /**
     * Get readings within a date range for a specific user and type
     * @param user User whose readings to retrieve
     * @param start_date Start date of the range
     * @param end_date End date of the range
     * @param type Type of reading (electricity, water, gas)
     * @return List of Reading objects
     * @throws SQLException If database operation fails
     */
    public List<Reading> getReadings_By_Date_And_Type(User user, LocalDate start_date, LocalDate end_date, String type) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE user_id = ? AND date >= ? AND date <= ? AND type = ? ORDER BY date ASC";
        List<Reading> reading_list = new ArrayList<>();
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, start_date.toString());
            prepared_statement.setString(3, end_date.toString());
            prepared_statement.setString(4, type);
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                while (result_set.next()) {
                    reading_list.add(new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return reading_list;
    }
    
    /**
     * Get the latest reading of a specific type for a user
     * @param user User whose reading to retrieve
     * @param type Type of reading (electricity, water, gas)
     * @return Latest Reading object if found, null otherwise
     * @throws SQLException If database operation fails
     */
    public Reading getLatest_Reading_By_Type(User user, String type) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE user_id = ? AND type = ? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, type);
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                if (result_set.next()) {
                    return new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public double getLatestMonthReadingSum(User user, String type, String field) throws SQLException {
        Reading latestReading = getLatest_Reading_By_Type(user, type);
        if (latestReading == null) {
            return 0.0;
        }
        LocalDate latestDate = latestReading.getDate();
        int year = latestDate.getYear();
        int month = latestDate.getMonthValue();
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        String columnToSum;
        switch (field.toLowerCase()) {
            case "total":
                columnToSum = "total_price";
                break;
            case "rate":
                columnToSum = "rate";
                break;
            case "reading":
            default:
                columnToSum = "reading";
                break;
        }

        String sql = "SELECT SUM(" + columnToSum + ") as total FROM readings WHERE user_id = ? AND type = ? AND date >= ? AND date <= ?";
        try (PreparedStatement ps = database_connection.prepareStatement(sql)) {
            ps.setInt(1, user.getUser_Id());
            ps.setString(2, type);
            ps.setString(3, startOfMonth.toString());
            ps.setString(4, endOfMonth.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }

    
    /**
     * Check if readings of a specific type exist for a user
     * @param user User to check
     * @param type Type of reading (electricity, water, gas)
     * @return true if readings exist, false otherwise
     * @throws SQLException If database operation fails
     */
    public boolean isReading_Exists(User user, String type) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE user_id = ? AND type = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, type);
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                return result_set.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get all readings of a specific type for a user
     * @param user User whose readings to retrieve
     * @param type Type of reading (electricity, water, gas)
     * @return List of Reading objects
     * @throws SQLException If database operation fails
     */
    public List<Reading> getAll_Readings_By_Type(User user, String type) throws SQLException {
        String sql_script = "SELECT * FROM readings WHERE user_id = ? AND type = ? ORDER BY date DESC";
        List<Reading> reading_list = new ArrayList<>();
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, type);
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                while (result_set.next()) {
                    reading_list.add(new Reading(
                        result_set.getInt("reading_id"),
                        result_set.getInt("user_id"),
                        LocalDate.parse(result_set.getString("date")),
                        result_set.getString("type"),
                        result_set.getDouble("reading"),
                        result_set.getDouble("rate"),
                        result_set.getDouble("total_price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return reading_list;
    }
    
    /**
     * Group readings by month and calculate sums
     * @param readings List of readings to group
     * @param use_price If true, uses total_price field; if false, uses reading field
     * @return Map with Month as key and summed value as value
     */
    public Map<Month, Double> groupReadings_By_Month(List<Reading> readings, int year, String field) {
        Map<Month, Double> monthly_data = new HashMap<>();

        if (readings == null || readings.isEmpty()) {
            return monthly_data;
        }

        // Track the earliest and latest months that have readings
        int earliestMonth = 12;
        int latestMonth = 1;

        for (Reading reading : readings) {
            LocalDate date = reading.getDate();
            if (date.getYear() == year) {
                int monthValue = date.getMonthValue();
                Month month = date.getMonth();
                double value;
                switch (field.toLowerCase()) {
					case "total":
						value = reading.getTotal_Price();
						break;
					case "rate":
						value = reading.getRate();
						break;
					case "reading":
					default:
						value = reading.getReading();
						break;
				}

                monthly_data.put(month, monthly_data.getOrDefault(month, 0.0) + value);

                if (monthValue < earliestMonth) earliestMonth = monthValue;
                if (monthValue > latestMonth) latestMonth = monthValue;
            }
        }

        // Fill in missing months within the range
        for (int m = earliestMonth; m <= latestMonth; m++) {
            Month month = Month.of(m);
            monthly_data.putIfAbsent(month, 0.0);
        }

        return monthly_data;
    }


    // Update the getMonthly_Utility_Data method to include year parameter
    public Map<Month, Double> getMonthly_Utility_Data(User user, String utility_type, int year, String field) 
            throws SQLException {
        LocalDate end_date = LocalDate.of(year, 12, 31); // Whole Year
        LocalDate start_date = LocalDate.of(year, 1, 1);
        
        List<Reading> readings = getReadings_By_Date_And_Type(user, start_date, end_date, utility_type);
        return groupReadings_By_Month(readings, year, field);
    }

    // Update the getMonthly_Total_Expenses method to include year parameter
    public Map<Month, Double> getMonthly_Total_Expenses(User user, int year) throws SQLException {
        Map<Month, Double> total_expenses = new HashMap<>();
        
        // Get data for each utility type
        Map<Month, Double> electricity_expenses = getMonthly_Utility_Data(user, "electricity", year, "total");
        Map<Month, Double> water_expenses = getMonthly_Utility_Data(user, "water", year, "total");
        Map<Month, Double> gas_expenses = getMonthly_Utility_Data(user, "gas", year, "total");
        
        // Combine expenses from all utility types
        for (Month month : Month.values()) {
            double total_for_month = electricity_expenses.getOrDefault(month, 0.0) +
                                    water_expenses.getOrDefault(month, 0.0) +
                                    gas_expenses.getOrDefault(month, 0.0);
            if (total_for_month > 0) {
                total_expenses.put(month, total_for_month);
            }
        }
        
        return total_expenses;
    }
    
    /**
     * Gets total expenses for all utilities in a specified date range
     * @param user The user to get data for
     * @param start_date The start date
     * @param end_date The end date
     * @return Total expenses for all utilities in the date range
     * @throws SQLException If database operation fails
     */
    public double getTotal_Expenses_In_Range(User user, LocalDate start_date, LocalDate end_date) throws SQLException {
        double total_expenses = 0.0;
        
        List<Reading> all_readings = getReadings_By_Date(user, start_date, end_date);
        for (Reading reading : all_readings) {
            total_expenses += reading.getTotal_Price();
        }
        
        return total_expenses;
    }
    
    /**
     * Gets latest readings for all utility types
     * @param user The user to get readings for
     * @return Map with utility type as key and Reading object as value
     * @throws SQLException If database operation fails
     */
    public Map<String, Reading> getLatest_Readings_For_All_Types(User user) throws SQLException {
        Map<String, Reading> latest_readings = new HashMap<>();
        
        // Common utility types
        String[] utility_types = {"electricity", "water", "gas"};
        
        for (String type : utility_types) {
            Reading reading = getLatest_Reading_By_Type(user, type);
            if (reading != null) {
                latest_readings.put(type, reading);
            }
        }
        
        return latest_readings;
    }
    
    /**
     * Calculates the total combined cost of the latest readings for all utilities
     * @param user The user to calculate for
     * @return The total cost of all latest utility readings
     * @throws SQLException If database operation fails
     */
    public double getTotal_Latest_Cost(User user) throws SQLException {
        Map<String, Reading> latest_readings = getLatest_Readings_For_All_Types(user);
        double total_cost = 0.0;
        
        for (Reading reading : latest_readings.values()) {
            total_cost += reading.getTotal_Price();
        }
        
        return total_cost;
    }
    
    /**
     * Gets the trend for a specific utility type comparing current month to previous month
     * @param user User to get trend for
     * @param type Type of utility
     * @return Formatted string showing percentage change
     * @throws SQLException If database operation fails
     */
    public String getTrend(User user, String type, String field) throws SQLException {
        String columnToSum;
        switch (field.toLowerCase()) {
            case "total":
                columnToSum = "total_price";
                break;
            case "rate":
                columnToSum = "rate";
                break;
            case "reading":
            default:
                columnToSum = "reading";
                break;
        }

        String sql = "SELECT strftime('%Y-%m', date) AS month, SUM(" + columnToSum + ") as total " +
                     "FROM readings " +
                     "WHERE user_id = ? AND type = ? " +
                     "GROUP BY month " +
                     "ORDER BY month DESC " +
                     "LIMIT 2";

        try (PreparedStatement statement = database_connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUser_Id());
            statement.setString(2, type);

            try (ResultSet rs = statement.executeQuery()) {
                List<Double> totals = new ArrayList<>();
                while (rs.next()) {
                    totals.add(rs.getDouble("total"));
                }

                if (totals.size() < 2) {
                    last_trend_percentage = 0;
                    return "Not enough monthly data";
                }

                double latest = totals.get(0);
                double previous = totals.get(1);

                if (previous > 0) {
                    last_trend_percentage = ((latest - previous) / previous) * 100;
                    String sign = last_trend_percentage > 0 ? "+" : "";
                    return String.format("%s%.1f%% from previous month", sign, last_trend_percentage);
                } else {
                    last_trend_percentage = 0;
                    return "Previous month's reading is 0";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            last_trend_percentage = 0;
            throw e;
        }
    }


    
    /**
     * Gets the overall trend comparing current month to previous month across all utility types
     * @param user User to get trend for
     * @return Formatted string showing percentage change
     * @throws SQLException If database operation fails
     */
    public String getTrend_Overall(User user, String field) throws SQLException {
        LocalDate current_date = LocalDate.now();
        LocalDate first_day_current_month = current_date.withDayOfMonth(1);
        LocalDate first_day_previous_month = first_day_current_month.minusMonths(1);
        LocalDate last_day_previous_month = first_day_current_month.minusDays(1);

        String columnToSum;
        switch (field.toLowerCase()) {
            case "total":
                columnToSum = "total_price";
                break;
            case "rate":
                columnToSum = "rate";
                break;
            case "reading":
            default:
                columnToSum = "reading";
                break;
        }

        String sql_current_month = "SELECT SUM(" + columnToSum + ") as total FROM readings WHERE user_id = ? AND date >= ? AND date <= ?";
        String sql_previous_month = "SELECT SUM(" + columnToSum + ") as total FROM readings WHERE user_id = ? AND date >= ? AND date <= ?";

        try {
            double current_month_total = 0;
            double previous_month_total = 0;

            // Get current month total
            try (PreparedStatement statement = database_connection.prepareStatement(sql_current_month)) {
                statement.setInt(1, user.getUser_Id());
                statement.setString(2, first_day_current_month.toString());
                statement.setString(3, current_date.toString());
                try (ResultSet result_set = statement.executeQuery()) {
                    if (result_set.next() && result_set.getObject("total") != null) {
                        current_month_total = result_set.getDouble("total");
                    }
                }
            }

            // Get previous month total
            try (PreparedStatement statement = database_connection.prepareStatement(sql_previous_month)) {
                statement.setInt(1, user.getUser_Id());
                statement.setString(2, first_day_previous_month.toString());
                statement.setString(3, last_day_previous_month.toString());
                try (ResultSet result_set = statement.executeQuery()) {
                    if (result_set.next() && result_set.getObject("total") != null) {
                        previous_month_total = result_set.getDouble("total");
                    }
                }
            }

            if (previous_month_total > 0 && current_month_total > 0) {
                last_trend_percentage = ((current_month_total - previous_month_total) / previous_month_total) * 100;
                String sign = last_trend_percentage > 0 ? "+" : "";
                return String.format("%s%.1f%% from last month", sign, last_trend_percentage);
            }
            last_trend_percentage = 0;
            return "No previous data";

        } catch (SQLException e) {
            e.printStackTrace();
            last_trend_percentage = 0;
            throw e;
        }
    }


    
    /**
     * Gets the color for trend display based on the trend percentage
     * @param user User for context (unused but kept for consistency)
     * @param type Type of utility (unused but kept for consistency)
     * @return Color based on trend (green for decrease, red for increase, gray for no change/data)
     */
    public Color getTrend_Color(User user, String type) {
        if (last_trend_percentage < 0) {
            return new Color(0, 150, 0); // Green for decrease
        } else if (last_trend_percentage > 0) {
            return new Color(255, 0, 0); // Red for increase
        } else {
            return Color.GRAY; // Gray for no change or no data
        }
    }

    /**
     * Gets a reading for a specific month
     * @param current_user User who owns the reading
     * @param type Type of reading (electricity, water, gas)
     * @param previous_month The month to get reading for
     * @return Reading object if found, null otherwise
     */
    
    /**
     * Gets the total number of readings for a user
     * @param current_user User whose readings to count
     * @return The count of readings
     */
    public int getTotal_Readings(User current_user) {
        String sql_script = "SELECT COUNT(*) as total FROM readings WHERE user_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, current_user.getUser_Id());
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                if (result_set.next()) {
                    return result_set.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Updates UI labels with reading data and trend information
     * @param current_user User who owns the reading
     * @param reading Reading to display
     * @param value_label Label to display reading value
     * @param trend_label Label to display trend information
     * @param type Type of reading
     */
    public void updateReading_Label(User current_user, Reading reading, JLabel value_label, JLabel trend_label, JLabel unit_label, String utility_type,String field) {
        if (reading == null) {
            value_label.setText("No Data");
            trend_label.setText("No Data");
        } else {
            try {
            	if (utility_type.equals("gas")) {
					value_label.setText(String.valueOf((int) getLatestMonthReadingSum(current_user, utility_type, field)));
				} else {
					value_label.setText(String.valueOf(getLatestMonthReadingSum(current_user, utility_type, field)));					
				}
				
            	if (field.equals("reading")) {
					unit_label.setText(utility_type.equals("gas") ? "Qty" : utility_type.equals("water") ? "m³" : "kWh");
				} else if (field.equals("rate")) {
					unit_label.setText("Php/kWh");
				} else {
					unit_label.setText("Php");
				}
				
				// Update value label
				value_label.setFont(new Font("monoFont", Font.BOLD, 20));
            	
				// Update trend label
                String trend = getTrend(current_user, utility_type, field);
                trend_label.setText(trend);
                trend_label.setForeground(getTrend_Color(current_user, utility_type));
            } catch (SQLException e) {
                e.printStackTrace();
                trend_label.setText("Error calculating trend");
            }
        }
    }
    
    /**
     * Creates a JList with formatted reading data
     * @param utility_panel Parent panel
     * @param database_manager Database manager for data operations
     * @param user User whose readings to display
     * @param type Type of readings to display
     * @return JList component with formatted reading data
     */
    public JList<String> getReadings_As_JList(JPanel utility_panel, Database_Manager database_manager, User user, String type) {
    	List<Reading> all_readings;
    	try {
    		all_readings = getAll_Readings_By_Type(user, type);
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    	MouseAdapter list_click_listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<?> list = (JList<?>) e.getSource();
                int selected_index = list.getSelectedIndex();
                
                if (selected_index >= 0) {
                    int response = javax.swing.JOptionPane.showConfirmDialog(null, 
                        "Do you want to edit this reading?", "Edit Reading", 
                        javax.swing.JOptionPane.YES_NO_OPTION);
                        
                    if (response == javax.swing.JOptionPane.YES_OPTION) {
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    
                                    Reading selected_reading = null;
                                    
                                    if (selected_index < all_readings.size()) {
                                        selected_reading = all_readings.get(selected_index);
                                    }
                                    
                                    Edit_Reading_Window edit_reading_panel = new Edit_Reading_Window(
                                        (JFrame) SwingUtilities.getWindowAncestor(utility_panel),
                                        database_manager, user, utility_panel, type, selected_reading
                                    );
                                    edit_reading_panel.loadReadingData(selected_reading, selected_index);
                                    edit_reading_panel.setVisible(true);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        };
        
        try {
            if (!isReading_Exists(user, type)) {
                JList<String> list = new JList<>(new String[] {"No readings found.", "Please add a reading."});
                list.setFont(new Font("monoFont", Font.PLAIN, 15));
                list.setPreferredSize(new Dimension(429, 448));
                list.setFixedCellHeight(30);
                return list;
            }
            
            String[] readings = new String[all_readings.size()];
            for (int i = 0; i < all_readings.size(); i++) {
                Reading reading = all_readings.get(i);
                String unit = "";
                switch(type) {
                    case "electricity":
                        unit = "kWh";
                        break;
                    case "water":
                        unit = "m³";
                        break;
                    case "gas":
                        unit = "Qty";
                        break;
                    default:
                        unit = "";
                        break;
                }
                readings[i] = String.format("    %-23s %-23s %-19s %-10s", 
                    reading.getDate(), 
                    reading.getReading() + unit, 
                    reading.getRate() + "Php", 
                    reading.getTotal_Price() + "Php");
            }
            
            JList<String> list = new JList<>(readings);
            list.setFont(new Font("monoFont", Font.PLAIN, 13));
            list.setPreferredSize(new Dimension(429, 448));
            list.setFixedCellHeight(30);
            list.addMouseListener(list_click_listener);
            
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return new JList<>(new String[] {"Error fetching readings."});
        }
    }
    
    /**
     * Gets list of years for which readings exist for a specific type
     * @param user User whose reading years to retrieve
     * @param type Type of reading (electricity, water, gas)
     * @return Array of years in descending order
     * @throws SQLException If database operation fails
     */
    public int[] getReading_Years(User user, String type) throws SQLException {
        String sql_script = "SELECT DISTINCT strftime('%Y', date) as year FROM readings WHERE user_id = ? AND type = ? ORDER BY year DESC";
        List<Integer> years = new ArrayList<>();
        
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setInt(1, user.getUser_Id());
            prepared_statement.setString(2, type);
            try (ResultSet result_set = prepared_statement.executeQuery()) {
                while (result_set.next()) {
                    years.add(Integer.parseInt(result_set.getString("year")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return years.stream().mapToInt(Integer::intValue).toArray();
    }
}
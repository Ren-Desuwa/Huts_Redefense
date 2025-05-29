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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.*;
import view.panel.Utility_Panel;
import view.panel.misc.Edit_Reading_Window;

public class Reading_Manager {
    
    private Connection database_connection;
    private double last_trend_percentage;
    
    protected Reading_Manager(Connection database_connection) {
        this.database_connection = database_connection;
    }

    //========================================================================================================================================
    // CRUD Operations
    //========================================================================================================================================
    
    // Adds a new reading to the database
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

    // Deletes a reading from the database
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

    // Updates a reading in the database
    public void updateReading(User user, Reading reading) throws SQLException {
        String sql_script = "UPDATE readings SET date = ?, type = ?, reading = ?, rate = ?, total_price = ? WHERE reading_id = ? AND user_id = ?";
        try (PreparedStatement prepared_statement = database_connection.prepareStatement(sql_script)) {
            prepared_statement.setString(1, reading.getDate().toString());
            prepared_statement.setString(2, reading.getType());
            prepared_statement.setDouble(3, reading.getReading_Id());
            prepared_statement.setDouble(4, reading.getRate());
            prepared_statement.setDouble(5, reading.getTotal_Price());
            prepared_statement.setInt(6, reading.getReading_Id());
            prepared_statement.setInt(7, user.getUser_Id());
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    //================================================================================================================================================
    // Basic Reading Retrieval 
    //================================================================================================================================================
    
    // Gets the latest reading of a specific type for a user
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

    // Gets all readings for a user of a specific type, ordered by date descending
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
    
    // Gets readings for a user within a date range and of a specific type, ordered by date ascending
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
    
    // Gets the total number of readings for a user
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
    
    //=================================================================================================================================================
    // Data Grouping and Analysis Methods
    //=================================================================================================================================================
    
    // Groups readings by month and calculates the total for each month	
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
    
    //=================================================================================================================================================
    // Trend Calculation and Reporting Methods
    //=================================================================================================================================================

    // Gets the trend of readings for a user, optionally filtered by type and field
    public String getTrend(User user, String type, String field) throws SQLException {
        String columnToSum = switch (field.toLowerCase()) {
            case "total" -> "total_price";
            case "rate" -> "rate";
            default -> "reading";
        };

        String dateCol = "strftime('%Y-%m', date)";
        String sql = (type != null)
            ? "SELECT " + dateCol + " AS month, SUM(" + columnToSum + ") as total FROM readings WHERE user_id = ? AND type = ? GROUP BY month ORDER BY month DESC LIMIT 2"
            : "SELECT " + dateCol + " AS month, SUM(" + columnToSum + ") as total FROM readings WHERE user_id = ? GROUP BY month ORDER BY month DESC LIMIT 2";

        try (PreparedStatement stmt = database_connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getUser_Id());
            if (type != null) stmt.setString(2, type);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Double> totals = new ArrayList<>();
                while (rs.next()) totals.add(rs.getDouble("total"));

                if (totals.size() < 2) {
                    last_trend_percentage = 0;
                    return "Not enough data";
                }

                double current = totals.get(0), previous = totals.get(1);
                if (previous > 0) {
                    last_trend_percentage = ((current - previous) / previous) * 100;
                    String sign = last_trend_percentage > 0 ? "+" : "";
                    return String.format("%s%.1f%% from previous month", sign, last_trend_percentage);
                } else {
                    last_trend_percentage = 0;
                    return "Previous month's value is 0";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            last_trend_percentage = 0;
            throw e;
        }
    }

	// Gets the color for the trend based on the last trend percentage	
    public Color getTrend_Color() {
        if (last_trend_percentage < 0) {
            return new Color(0, 150, 0); // Green for decrease
        } else if (last_trend_percentage > 0) {
            return new Color(255, 0, 0); // Red for increase
        } else {
            return Color.GRAY; // Gray for no change or no data
        }
    }
    
    //=================================================================================================================================================
    // Utility Methods
    //=================================================================================================================================================
    
    
    // Gets the total cost of the latest readings for each utility type
    public double getTotal_Latest_Cost(User user) throws SQLException {
        double totalCost = 0.0;
        String[] utilityTypes = {"electricity", "water", "gas"};

        for (String type : utilityTypes) {
            Reading latestReading = getLatest_Reading_By_Type(user, type);
            if (latestReading != null) {
                totalCost += latestReading.getTotal_Price();
            }
        }
        return totalCost;
    }

    // Gets the sum of readings for the latest month for a specific type and field
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
    
    //=================================================================================================================================================
    // UI Update Methods
    //=================================================================================================================================================
    
    // Updates the reading labels with the latest month's data and trend
    public void updateReading_Label(User current_user, Reading reading, JLabel value_label, JLabel trend_label, JLabel unit_label, String utility_type,String field) {
        if (reading == null) {
            value_label.setText("No Data");
            trend_label.setText("No Data");
        } else {
            try {
            	// Get the latest month's reading sum for the specified utility type and field
            	double latestValue = getLatestMonthReadingSum(current_user, utility_type, field);
                String formattedValue = (utility_type.equals("gas") && field.equals("reading")) // Gas readings are integers
                        ? String.valueOf((int) latestValue) // Gas readings are integers
                        : String.valueOf(latestValue); // Other readings can be decimal
                value_label.setText(formattedValue);
                value_label.setFont(new Font("monoFont", Font.BOLD, 20));
				
                // Update unit label based on field and utility type
                String unit = 
                	switch (field) {
                    	case "reading" -> 
                    		switch (utility_type) {
		                        case "gas" -> "Kg";
		                        case "water" -> "m³";
		                        default -> "kWh";
                    		};
                    	case "rate" -> "Php/kWh";
                    	case "total" -> "Php";
                    	default -> "";
                };
                unit_label.setText(unit);
				
				// Update value label
				value_label.setFont(new Font("monoFont", Font.BOLD, 20));
            	
				// Update trend label
                String trend = getTrend(current_user, utility_type, field);
                trend_label.setText(trend);
                trend_label.setForeground(getTrend_Color());
            } catch (SQLException e) {
                e.printStackTrace();
                trend_label.setText("Error calculating trend");
            }
        }
    }
    
    // Gets the readings as a JList for display in the utility panel
    // This method creates a JList of readings formatted for display, allowing users to edit readings by clicking on them.
    public JList<String> getReadings_As_JList(Utility_Panel utility_panel, Database_Manager database_manager, User user, String utility_type) {
        List<Reading> all_readings;
        try {
            all_readings = getAll_Readings_By_Type(user, utility_type);
        } catch (SQLException e) {
            e.printStackTrace();
            return new JList<>(new String[]{"Error fetching readings."});
        }

        if (all_readings == null || all_readings.isEmpty()) {
            JList<String> list = new JList<>(new String[]{"No readings found.", "Please add a reading."});
            list.setFont(new Font("monoFont", Font.PLAIN, 15));
            list.setPreferredSize(new Dimension(429, 448));
            list.setFixedCellHeight(30);
            return list;
        }

        String unit = switch (utility_type) {
            case "electricity" -> "kWh";
            case "water" -> "m³";
            case "gas" -> "kg";
            default -> "";
        };

        String[] readingsArray = all_readings.stream().map(reading ->
            String.format("    %-25s %-27s %-21s %-10s",
                reading.getDate(),
                reading.getReading() + unit,
                reading.getRate() + "Php",
                reading.getTotal_Price() + "Php")
        ).toArray(String[]::new);

        JList<String> list = new JList<>(readingsArray);
        list.setFont(new Font("monoFont", Font.PLAIN, 13));
        list.setPreferredSize(new Dimension(429, 448));
        list.setFixedCellHeight(30);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected_index = list.getSelectedIndex();
                if (selected_index < 0) return;

                int response = JOptionPane.showConfirmDialog(null,
                    "Do you want to edit this reading?", "Edit Reading",
                    JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    Reading selected_reading = all_readings.get(selected_index);
                    EventQueue.invokeLater(() -> {
                        try {
                            Edit_Reading_Window editWindow = new Edit_Reading_Window(
                                (JFrame) SwingUtilities.getWindowAncestor(utility_panel),
                                database_manager, user, utility_panel, utility_type, selected_reading
                            );
                            editWindow.setVisible(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
        });

        return list;
    }

    //=================================================================================================================================================
    // Additional Helper Methods
    //=================================================================================================================================================
    
    // Gets the distinct years of readings for a user and type
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
    
    // Checks if a reading exists for a user and type
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
}

/*
 * File: Reading_Manager.java
 *
 * Description:
 * The `Reading_Manager` class is responsible for managing all database operations related to utility readings (electricity, water, and gas). 
 * It provides methods for adding, updating, deleting, and retrieving readings, as well as calculating trends, grouping data, and generating reports. 
 * This class interacts with an SQLite database through a `Connection` object and ensures that all operations are performed securely and efficiently.
 *
 * Variables:
 * - `database_connection` (Connection): Represents the active connection to the SQLite database.
 * - `last_trend_percentage` (double): Stores the percentage change in readings for trend calculations.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Reading_Manager(Connection database_connection)`:
 *      - Initializes the `Reading_Manager` with the provided database connection.
 *      - Ensures that all database operations are performed using this connection.
 *
 * 2. **addReading(User user, LocalDate date, String type, double reading, double rate, double total_price)**:
 *    - Adds a new reading to the database.
 *    - Core Mechanics:
 *      - Uses a prepared statement to insert the reading into the `readings` table.
 *      - Prevents SQL injection by parameterizing the query.
 *      - Retrieves and prints the generated reading ID after insertion.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 3. **deleteReading(Reading reading)**:
 *    - Deletes a specific reading from the database.
 *    - Core Mechanics:
 *      - Uses a prepared statement to delete the reading identified by its `reading_id`.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 4. **updateReading(User user, Reading reading)**:
 *    - Updates an existing reading in the database.
 *    - Core Mechanics:
 *      - Uses a prepared statement to update the reading's details (date, type, reading value, rate, and total price).
 *      - Ensures that the update is applied only to the specified reading and user.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 5. **getLatest_Reading_By_Type(User user, String type)**:
 *    - Retrieves the most recent reading of a specific type for a user.
 *    - Core Mechanics:
 *      - Queries the database for the latest reading (ordered by date descending) of the specified type.
 *      - Returns a `Reading` object if a match is found, or `null` if no match exists.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 6. **getAll_Readings_By_Type(User user, String type)**:
 *    - Retrieves all readings of a specific type for a user, ordered by date descending.
 *    - Core Mechanics:
 *      - Queries the database for all readings of the specified type.
 *      - Returns a list of `Reading` objects.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 7. **groupReadings_By_Month(List<Reading> readings, int year, String field)**:
 *    - Groups readings by month and calculates the total for each month.
 *    - Core Mechanics:
 *      - Iterates through the readings and sums the specified field (e.g., reading, rate, or total price) for each month.
 *      - Fills in missing months with a value of 0.
 *      - Returns a map of `Month` to `Double` values.
 *
 * 8. **getReadings_By_Date_And_Type(User user, LocalDate start_date, LocalDate end_date, String type)**:
 *    - Retrieves readings for a user within a specific date range and of a specific type.
 *    - Core Mechanics:
 *      - Queries the database for readings within the specified date range and type.
 *      - Returns a list of `Reading` objects.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 9. **getMonthly_Utility_Data(User user, String utility_type, int year, String field)**:
 *    - Retrieves monthly data for a specific utility type and field (e.g., reading, rate, or total price).
 *    - Core Mechanics:
 *      - Calls `getReadings_By_Date_And_Type()` to fetch readings for the specified year and utility type.
 *      - Groups the readings by month using `groupReadings_By_Month()`.
 *      - Returns a map of `Month` to `Double` values.
 *    - Throws a `SQLException` if there is a database error.
 *
 * 10. **getMonthly_Total_Expenses(User user, int year)**:
 *     - Calculates the total expenses for all utility types (electricity, water, and gas) for each month of a specific year.
 *     - Core Mechanics:
 *       - Calls `getMonthly_Utility_Data()` for each utility type and sums the results for each month.
 *       - Returns a map of `Month` to `Double` values.
 *     - Throws a `SQLException` if there is a database error.
 *
 * 11. **getTotal_Latest_Cost(User user)**:
 *     - Calculates the total cost of the latest readings for all utility types.
 *     - Core Mechanics:
 *       - Calls `getLatest_Reading_By_Type()` for each utility type and sums the total prices.
 *       - Returns the total cost as a `double`.
 *     - Throws a `SQLException` if there is a database error.
 *
 * 12. **getTrend(User user, String type, String field)**:
 *     - Calculates the percentage change in readings for a specific utility type and field between the last two months.
 *     - Core Mechanics:
 *       - Queries the database for the sum of the specified field for the last two months.
 *       - Calculates the percentage change and stores it in `last_trend_percentage`.
 *       - Returns a formatted string describing the trend.
 *     - Throws a `SQLException` if there is a database error.
 *
 * 13. **getTrend_Color(User user, String type)**:
 *     - Determines the color to represent the trend based on the `last_trend_percentage`.
 *     - Core Mechanics:
 *       - Returns green for a decrease, red for an increase, and gray for no change or no data.
 *
 * 14. **getTotal_Readings(User current_user)**:
 *     - Retrieves the total number of readings for a user.
 *     - Core Mechanics:
 *       - Queries the database to count the number of readings for the user.
 *       - Returns the count as an `int`.
 *
 * 15. **getLatestMonthReadingSum(User user, String type, String field)**:
 *     - Calculates the sum of readings for the latest month for a specific utility type and field.
 *     - Core Mechanics:
 *       - Queries the database for the sum of the specified field for the latest month.
 *       - Returns the sum as a `double`.
 *     - Throws a `SQLException` if there is a database error.
 *
 * 16. **updateReading_Label(User current_user, Reading reading, JLabel value_label, JLabel trend_label, JLabel unit_label, String utility_type, String field)**:
 *     - Updates the labels in the UI with the latest month's data and trend for a specific utility type and field.
 *     - Core Mechanics:
 *       - Calls `getLatestMonthReadingSum()` and `getTrend()` to fetch the latest data and trend.
 *       - Updates the value, trend, and unit labels with the retrieved data.
 *       - Sets the trend label's color based on the trend.
 *     - Catches and handles any `SQLException` that occurs.
 *
 * 17. **getReadings_As_JList(Utility_Panel utility_panel, Database_Manager database_manager, User user, String type)**:
 *     - Retrieves readings as a `JList` for display in the utility panel.
 *     - Core Mechanics:
 *       - Calls `getAll_Readings_By_Type()` to fetch readings of the specified type.
 *       - Formats the readings for display in a `JList`.
 *       - Adds a mouse listener to allow editing of readings by clicking on them.
 *       - Returns the `JList` object.
 *
 * 18. **getReading_Years(User user, String type)**:
 *     - Retrieves the distinct years of readings for a user and type.
 *     - Core Mechanics:
 *       - Queries the database for distinct years of readings for the specified type.
 *       - Returns an array of integers representing the years.
 *     - Throws a `SQLException` if there is a database error.
 *
 * 19. **isReading_Exists(User user, String type)**:
 *     - Checks if any readings exist for a user and type.
 *     - Core Mechanics:
 *       - Queries the database to check for the existence of readings for the specified type.
 *       - Returns `true` if readings exist, otherwise `false`.
 *     - Throws a `SQLException` if there is a database error.
 *
 * Usage:
 * The `Reading_Manager` class is used to handle all database operations related to utility readings. 
 * It provides methods for adding, updating, deleting, and retrieving readings, as well as calculating trends and generating reports. 
 * This class is essential for managing and analyzing utility data in the application.
 */
/**
 * ================================================================================
 *                              READING_MANAGER CLASS
 * ================================================================================
 *
 * OVERVIEW:
 * ---------
 * The Reading_Manager class serves as the central data access layer for all utility 
 * reading operations in the application. This class is CRITICAL for the application's 
 * functionality as it manages all database interactions related to electricity, water, 
 * and gas meter readings. Any bugs in this class can cascade throughout the entire 
 * application, making debugging and maintenance extremely challenging.
 *
 * ARCHITECTURAL ROLE:
 * -------------------
 * This class follows the Data Access Object (DAO) pattern and acts as an intermediary 
 * between the application's business logic and the SQLite database. It encapsulates 
 * all SQL operations and provides a clean, type-safe interface for other components 
 * to interact with reading data.
 *
 * CRITICAL DEPENDENCIES:
 * ----------------------
 * - Database Connection: Uses java.sql.Connection for all database operations
 * - Model Classes: Heavily dependent on User and Reading model classes
 * - UI Components: Directly manipulates Swing components (JLabel, JList)
 * - View Layer: Integrates with Utility_Panel and Edit_Reading_Window
 *
 * INSTANCE VARIABLES:
 * -------------------
 * 1. database_connection (Connection):
 *    - The lifeline of this class - maintains active SQLite database connection
 *    - Used in ALL database operations throughout the class
 *    - If this connection fails, the entire class becomes non-functional
 *    - CRITICAL: Always check if connection is still valid before operations
 *
 * 2. last_trend_percentage (double):
 *    - Stores the most recent trend calculation result
 *    - Used by getTrend_Color() method to determine visual indicators
 *    - Updated every time getTrend() is called
 *    - IMPORTANT: This is stateful - multiple calls to getTrend() will overwrite this value
 *
 * ================================================================================
 *                            CORE CRUD OPERATIONS
 * ================================================================================
 *
 * addReading() - CREATE OPERATION:
 * --------------------------------
 * Purpose: Inserts new utility readings into the database
 * Critical Points:
 * - Uses PreparedStatement with RETURN_GENERATED_KEYS to get auto-generated IDs
 * - Parameterized queries prevent SQL injection attacks
 * - Date is converted to string format for SQLite storage
 * - DEBUGGING TIP: Check if user_id exists in users table before insertion
 * - DEBUGGING TIP: Verify date format is YYYY-MM-DD (LocalDate.toString())
 * - Exception handling: SQLException is caught, printed, and re-thrown
 *
 * deleteReading() - DELETE OPERATION:
 * -----------------------------------
 * Purpose: Removes specific readings from database using reading_id
 * Critical Points:
 * - Only requires reading_id (no user validation in this method)
 * - POTENTIAL BUG: No check if reading belongs to current user
 * - Uses try-with-resources for automatic statement cleanup
 * - DEBUGGING TIP: Verify reading_id exists before calling this method
 *
 * updateReading() - UPDATE OPERATION:
 * -----------------------------------
 * Purpose: Modifies existing reading data in database
 * Critical Points:
 * - Updates ALL fields: date, type, reading, rate, total_price
 * - Uses both reading_id AND user_id for security (prevents cross-user updates)
 * - POTENTIAL BUG: Line 3 sets reading_id instead of actual reading value!
 * - DEBUGGING TIP: This is a common source of data corruption - verify the reading parameter
 * - Parameter order is critical - wrong order will corrupt data
 *
 * ================================================================================
 *                            RETRIEVAL OPERATIONS
 * ================================================================================
 *
 * getLatest_Reading_By_Type():
 * ----------------------------
 * Purpose: Fetches the most recent reading for a specific utility type
 * Critical Points:
 * - Orders by date DESC with LIMIT 1 for efficiency
 * - Returns NULL if no reading found (always check for null!)
 * - Uses LocalDate.parse() to convert string back to LocalDate
 * - DEBUGGING TIP: Verify utility type strings match exactly ("electricity", "water", "gas")
 *
 * getAll_Readings_By_Type():
 * ---------------------------
 * Purpose: Retrieves all readings for a user and utility type, newest first
 * Critical Points:
 * - Returns empty ArrayList if no readings found
 * - Date ordering is DESC (newest first) for UI display
 * - Memory intensive for users with many readings
 * - DEBUGGING TIP: Large result sets may cause performance issues
 *
 * getReadings_By_Date_And_Type():
 * -------------------------------
 * Purpose: Fetches readings within specific date range
 * Critical Points:
 * - Uses inclusive date range (>= start_date AND <= end_date)
 * - Orders by date ASC (oldest first) for chronological analysis
 * - Date format must be YYYY-MM-DD for string comparison to work correctly
 * - DEBUGGING TIP: Ensure start_date is before end_date
 *
 * ================================================================================
 *                            DATA AGGREGATION METHODS
 * ================================================================================
 *
 * groupReadings_By_Month():
 * -------------------------
 * Purpose: Aggregates reading data by month for reporting and visualization
 * Critical Points:
 * - Groups readings by Month enum for type safety
 * - Supports three field types: "reading", "rate", "total"
 * - Fills missing months with 0.0 to ensure continuous data
 * - Tracks earliest and latest months to determine range
 * - DEBUGGING TIP: Null or empty readings list returns empty map
 * - DEBUGGING TIP: Year filtering is exact match only
 *
 * getMonthly_Utility_Data():
 * --------------------------
 * Purpose: Wrapper method that combines date filtering with monthly grouping
 * Critical Points:
 * - Creates full year date range (January 1 to December 31)
 * - Delegates to groupReadings_By_Month for actual aggregation
 * - Field parameter determines what data to aggregate
 * - DEBUGGING TIP: Year must be valid 4-digit year
 *
 * getMonthly_Total_Expenses():
 * ----------------------------
 * Purpose: Calculates combined expenses across all utility types by month
 * Critical Points:
 * - Hardcoded utility types: "electricity", "water", "gas"
 * - Sums total_price field from all three utility types
 * - Only includes months with expenses > 0 in result
 * - DEBUGGING TIP: Missing utility types will result in incomplete totals
 *
 * ================================================================================
 *                            TREND ANALYSIS SYSTEM
 * ================================================================================
 *
 * getTrend():
 * -----------
 * Purpose: Calculates percentage change between last two months
 * Critical Points:
 * - Uses SQLite's strftime() function for month grouping
 * - Supports optional type filtering (null = all utility types)
 * - Calculates percentage change: ((current - previous) / previous) * 100
 * - Updates instance variable last_trend_percentage for color determination
 * - Returns formatted string with +/- sign for UI display
 * - DEBUGGING TIP: Requires at least 2 months of data
 * - DEBUGGING TIP: Previous month value of 0 causes division issues
 *
 * getTrend_Color():
 * -----------------
 * Purpose: Determines color coding for trend display
 * Critical Points:
 * - Green (0, 150, 0) for negative trends (decreases - good for utilities)
 * - Red (255, 0, 0) for positive trends (increases - bad for utilities)
 * - Gray for no change or insufficient data
 * - DEPENDS ON: last_trend_percentage set by previous getTrend() call
 * - DEBUGGING TIP: Must call getTrend() before this method for accurate colors
 *
 * ================================================================================
 *                            UI INTEGRATION METHODS
 * ================================================================================
 *
 * updateReading_Label():
 * ----------------------
 * Purpose: Updates Swing labels with current reading data and trends
 * Critical Points:
 * - Handles null reading gracefully with "No Data" display
 * - Calls getLatestMonthReadingSum() for current month's aggregated data
 * - Formats gas readings as integers, others as decimals
 * - Sets appropriate units based on utility type and field
 * - Updates trend label with color coding
 * - DEBUGGING TIP: SQLException in trend calculation shows "Error calculating trend"
 * - DEBUGGING TIP: Font and color settings may be overridden by UI themes
 *
 * getReadings_As_JList():
 * -----------------------
 * Purpose: Creates interactive JList component for reading display and editing
 * Critical Points:
 * - Returns error message JList if SQLException occurs
 * - Returns placeholder JList if no readings exist
 * - Formats readings with fixed-width spacing for alignment
 * - Adds mouse click listener for editing functionality
 * - Units are hardcoded: electricity=kWh, water=m³, gas=kg
 * - DEBUGGING TIP: Array index out of bounds if all_readings size changes during display
 * - DEBUGGING TIP: Edit window creation may fail if UI components are not properly initialized
 *
 * ================================================================================
 *                            UTILITY HELPER METHODS
 * ================================================================================
 *
 * getTotal_Latest_Cost():
 * -----------------------
 * Purpose: Sums the cost of most recent readings across all utility types
 * Critical Points:
 * - Hardcoded utility types array: ["electricity", "water", "gas"]
 * - Null readings contribute 0 to total (safe handling)
 * - Returns total cost as double
 * - DEBUGGING TIP: If utility types change, update the array
 *
 * getTotal_Readings():
 * --------------------
 * Purpose: Counts total number of readings for a user
 * Critical Points:
 * - Uses COUNT(*) for efficiency
 * - Returns 0 if SQLException occurs (silent failure)
 * - DEBUGGING TIP: Silent exception handling may hide database issues
 *
 * getLatestMonthReadingSum():
 * ---------------------------
 * Purpose: Calculates sum of readings for the most recent month with data
 * Critical Points:
 * - Finds latest reading first to determine which month to sum
 * - Calculates full month range (1st to last day of month)
 * - Supports different field aggregation (reading, rate, total_price)
 * - Returns 0.0 if no readings found
 * - DEBUGGING TIP: Month calculation uses lengthOfMonth() for accuracy
 *
 * getReading_Years():
 * -------------------
 * Purpose: Extracts distinct years from readings for UI dropdowns
 * Critical Points:
 * - Uses SQLite's strftime('%Y', date) for year extraction
 * - Returns years in descending order (newest first)
 * - Converts String years to integers for type safety
 * - DEBUGGING TIP: Empty result if no readings exist for the utility type
 *
 * isReading_Exists():
 * -------------------
 * Purpose: Quick check for reading existence without data retrieval
 * Critical Points:
 * - Uses simple existence query for efficiency
 * - Returns boolean result
 * - Used for conditional UI logic
 * - DEBUGGING TIP: Does not validate data quality, only existence
 *
 * ================================================================================
 *                          COMMON DEBUGGING SCENARIOS
 * ================================================================================
 *
 * 1. CONNECTION ISSUES:
 *    - Check if database_connection is null or closed
 *    - Verify database file exists and is accessible
 *    - Look for "Connection is closed" SQLExceptions
 *
 * 2. DATA CORRUPTION:
 *    - updateReading() bug on line 3: sets reading_id instead of reading value
 *    - Date format mismatches (must be YYYY-MM-DD)
 *    - Null values in required fields
 *
 * 3. PERFORMANCE ISSUES:
 *    - Large result sets from getAll_Readings_By_Type()
 *    - Missing database indexes on user_id, type, and date columns
 *    - Inefficient date range queries
 *
 * 4. UI SYNCHRONIZATION:
 *    - JList updates not reflecting database changes
 *    - Trend colors not matching trend percentages
 *    - Labels showing stale data after updates
 *
 * 5. CONCURRENCY PROBLEMS:
 *    - Multiple threads accessing last_trend_percentage
 *    - Database locks during long-running operations
 *    - UI updates on wrong thread (not EDT)
 *
 * ================================================================================
 *                              MAINTENANCE NOTES
 * ================================================================================
 *
 *!!!! GRAPH HEAVY CLASS - USE CAUTION WHEN MODIFYING !!!!!
 * This class is heavily integrated with the UI and database, making it sensitive to changes.
 * Any modifications can have wide-ranging effects, so thorough testing is required.
 * 'getMonthly_Utility_Data()' and 'getMonthly_Total_Expenses()' are particularly sensitive to changes in utility types.
 *
 * WHEN MODIFYING THIS CLASS:
 * - Always use try-with-resources for database operations
 * - Maintain consistent exception handling patterns
 * - Update both CRUD and aggregation methods if database schema changes
 * - Test all UI integration methods after changes
 * - Verify thread safety for UI-related operations
 *
 * TESTING CHECKLIST:
 * - Test with empty database (no readings)
 * - Test with single reading per utility type
 * - Test with multiple years of data
 * - Test date boundary conditions (month/year edges)
 * - Test with null/invalid user objects
 * - Test database connection failure scenarios
 *
 * PERFORMANCE MONITORING:
 * - Monitor query execution times for large datasets
 * - Watch for memory usage in getAll_Readings_By_Type()
 * - Check for proper PreparedStatement cleanup
 * - Verify database connection pooling if implemented
 */

package view.panel.misc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import database.Database_Manager;
import model.User;
import view.panel.Utility_Panel;
import visuals.Rounded_Button;
import visuals.Rounded_Panel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class Add_Reading_Window extends JDialog {

	//========================================================================================================
	// Class Variables
	//========================================================================================================
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Database_Manager database_manager;
	private User current_user;
	private String utility_Type;
	private Utility_Panel parent_Panel;

	// UI Components for the title panel
	private JPanel panel_Title;
	private JLabel lbl_Title_AddNewReading;
	private JLabel lbl_Date;
	
	//date selection components
	private JLabel lbl_Date_Selection;
	private JLabel lbl_Day;
	private JLabel lbl_Month;
	private JLabel lbl_Year;
	@SuppressWarnings("rawtypes")
	private JComboBox combo_box_Day; 
	@SuppressWarnings("rawtypes")
	private JComboBox combo_box_Month;
	@SuppressWarnings("rawtypes")
	private JComboBox combo_box_Year;
	
	// Input fields for reading, rate, and total price
	private JLabel lbl_Reading;
	private JTextField tf_Reading;
	private JLabel lbl_Rate;
	private JTextField tf_Rate;
	private JLabel lbl_TotalPrice;
	private JTextField tf_TotalPrice;
	
	
	// Buttons for adding and canceling the reading
	private JButton btn_Add;
	private JButton btn_Cancel;
	
	// Labels for incorrect signage
	private JLabel lbl_Incorrect_Signage2;
	private JLabel lbl_Incorrect_Signage3;
	private JLabel lbl_Incorrect_Signage1;
	
	public Add_Reading_Window(JFrame parent ,Database_Manager database_manager, User current_user,Utility_Panel parent_Panel, String utility_type) {
		super(parent, "Add Reading", true);
	    this.database_manager = database_manager;
	    this.current_user = current_user;
	    this.parent_Panel = parent_Panel; // Set the parent panel Electricity_Panel, Water_Panel or Gas_Panel
	    this.utility_Type = utility_type;  // Set the reading type electricity, water or gas
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(287, 50, 450, 535);
		setBackground(new Color(213, 213, 213));
		setTitle("Add Reading");
		setResizable(false);
		
		initialize_UI();
		create_Action_Listeners();

		setup_data();
	}
	
	@SuppressWarnings("rawtypes")
	private void initialize_UI() {
		
		//=======================================================================================================
		// Main Content Pane
		//=======================================================================================================
		
		// Create the main content pane with a light gray background and empty border
		contentPane = new JPanel();
		contentPane.setBackground(new Color(213, 213, 213));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//=======================================================================================================
		// Electricity Consumption Title Panel
		//=======================================================================================================
		
		// Create a rounded panel for the title section
		panel_Title = new Rounded_Panel();
		panel_Title.setBackground(new Color(255, 255, 255));
		panel_Title.setLayout(null);
		panel_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_Title);
		
		// Create and configure the date label
		lbl_Date = new JLabel("Date");
		lbl_Date.setBounds(236, 8, 170, 54);
		panel_Title.add(lbl_Date);
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		// Create and configure the title label
		lbl_Title_AddNewReading = new JLabel("Add New Reading");
		lbl_Title_AddNewReading.setBounds(13, 20, 393, 54);
		panel_Title.add(lbl_Title_AddNewReading);
		lbl_Title_AddNewReading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddNewReading.setFont(new Font("Tahoma", Font.PLAIN, 35));
		
		//========================================================================================================
		// Date Selection Labels and ComboBoxes
		//========================================================================================================
		
		// Create and configure the date selection labels and combo boxes
		lbl_Date_Selection = new JLabel("Date");
		lbl_Date_Selection.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Date_Selection.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Date_Selection.setBounds(10, 119, 114, 22);
		contentPane.add(lbl_Date_Selection);
		
		// Create and configure the day, month, and year labels
		lbl_Day = new JLabel("Day");
		lbl_Day.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Day.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Day.setBounds(10, 143, 114, 22);
		contentPane.add(lbl_Day);
		
		lbl_Month = new JLabel("Month");
		lbl_Month.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Month.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Month.setBounds(157, 143, 114, 22);
		contentPane.add(lbl_Month);
		
		lbl_Year = new JLabel("Year");
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Year.setBounds(306, 143, 114, 22);
		contentPane.add(lbl_Year);
		
		// Create and configure the combo boxes for day, month, and year selection
		combo_box_Day = new JComboBox();
		combo_box_Day.setFont(new Font("Tahoma", Font.PLAIN, 17));
		combo_box_Day.setBounds(10, 164, 120, 45);
		combo_box_Day.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(combo_box_Day);
		
		combo_box_Month = new JComboBox();
		combo_box_Month.setFont(new Font("Tahoma", Font.PLAIN, 17));
		combo_box_Month.setBounds(157, 164, 120, 45);
		combo_box_Month.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(combo_box_Month);
		
		combo_box_Year = new JComboBox();
		combo_box_Year.setFont(new Font("Tahoma", Font.PLAIN, 17));
		combo_box_Year.setBounds(306, 164, 120, 45);
		combo_box_Year.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(combo_box_Year);
		
		//=======================================================================================================
		// Reading, Rate, and Total Price Labels and TextFields
		//=======================================================================================================
		
		// Create and configure the labels and text fields for reading
		lbl_Reading = new JLabel("Reading");
		lbl_Reading.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Reading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Reading.setBounds(10, 216, 163, 22);
		contentPane.add(lbl_Reading);
		
		tf_Reading = new JTextField();
		tf_Reading.setText("Enter Reading");
		tf_Reading.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Reading.setColumns(10);
		tf_Reading.setBounds(10, 241, 416, 45);
		contentPane.add(tf_Reading);
		
		// Create and configure the labels and text fields for rate
		lbl_Rate = new JLabel("Rate (Php)");
		lbl_Rate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Rate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Rate.setBounds(10, 297, 141, 22);
		contentPane.add(lbl_Rate);
		
		tf_Rate = new JTextField();
		tf_Rate.setText("Enter Rate");
		tf_Rate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Rate.setColumns(10);
		tf_Rate.setBounds(10, 321, 416, 45);
		contentPane.add(tf_Rate);
		
		// Create and configure the labels and text fields for total price
		lbl_TotalPrice = new JLabel("Total Price (Php)");
		lbl_TotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_TotalPrice.setBounds(10, 377, 192, 22);
		contentPane.add(lbl_TotalPrice);
		
		tf_TotalPrice = new JTextField();
		tf_TotalPrice.setText("Total Price");
		tf_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_TotalPrice.setColumns(10);
		tf_TotalPrice.setBounds(10, 401, 416, 45);
		contentPane.add(tf_TotalPrice);
		
		//=======================================================================================================
		// Add and Cancel Buttons
		//=======================================================================================================
		
		btn_Add =  new Rounded_Button("Add", 25);
		btn_Add.setBackground(new Color(182, 182, 182));
		btn_Add.setForeground(Color.BLACK);
		btn_Add.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Add.setBounds(335, 458, 91, 34);
		contentPane.add(btn_Add);
		
		btn_Cancel = new Rounded_Button("Cancel", 25);
		btn_Cancel.setBackground(new Color(182, 182, 182));
		btn_Cancel.setForeground(Color.BLACK);
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setBounds(234, 458, 91, 34);
		contentPane.add(btn_Cancel);
		
		//=======================================================================================================
		// Incorrect Signage Labels
		//=======================================================================================================
		
		// Create and configure the labels for incorrect signage
		lbl_Incorrect_Signage1 = new JLabel("*");
		lbl_Incorrect_Signage1.setForeground(Color.RED);
		lbl_Incorrect_Signage1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage1.setBounds(413, 223, 23, 25);
		lbl_Incorrect_Signage1.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage1);
		
		lbl_Incorrect_Signage2 = new JLabel("*");
		lbl_Incorrect_Signage2.setForeground(Color.RED);
		lbl_Incorrect_Signage2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage2.setBounds(413, 304, 23, 25);
		lbl_Incorrect_Signage2.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage2);
		
		lbl_Incorrect_Signage3 = new JLabel("*");
		lbl_Incorrect_Signage3.setForeground(Color.RED);
		lbl_Incorrect_Signage3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage3.setBounds(413, 384, 23, 25);
		lbl_Incorrect_Signage3.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage3);
	}
	
	private void create_Action_Listeners() {
		combo_box_Year.addActionListener(e -> {
			updateMonthComboBox();
			updateDayComboBox();
		});

		combo_box_Month.addActionListener(e -> {
			updateDayComboBox();
		});
		
		tf_Rate.addFocusListener(new FocusAdapter() {
			@Override public void focusGained(FocusEvent e) {if (tf_Rate.getText().equals("Enter Rate")) tf_Rate.setText("");}
			@Override public void focusLost(FocusEvent e) {if (tf_Rate.getText().isEmpty()) tf_Rate.setText("Enter Rate");}
		});
		
		tf_TotalPrice.addFocusListener(new FocusAdapter() {
			@Override public void focusGained(FocusEvent e) {if (tf_TotalPrice.getText().equals("Total Price")) tf_TotalPrice.setText("");}
			@Override public void focusLost(FocusEvent e) {if (tf_TotalPrice.getText().isEmpty()) tf_TotalPrice.setText("Total Price");}
		});
		
		btn_Add.addMouseListener(new MouseAdapter() {
			@Override public void mouseClicked(MouseEvent e) {addReading();}
			@Override public void mouseEntered(MouseEvent e) {btn_Add.setBackground(new Color(150, 150, 150));}
            @Override public void mouseExited(MouseEvent e) {btn_Add.setBackground(new Color(182, 182, 182));}
		});
		
		btn_Cancel.addMouseListener(new MouseAdapter() {
			@Override public void mouseClicked(MouseEvent e) {cancelAddReading();}
			@Override public void mouseEntered(MouseEvent e) {btn_Cancel.setBackground(new Color(150, 150, 150));}
			@Override public void mouseExited(MouseEvent e) {btn_Cancel.setBackground(new Color(182, 182, 182));}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void setup_data() {
		int currentYear = LocalDate.now().getYear();
		
		for (int i = currentYear; i >= 1975; i--) {
		    combo_box_Year.addItem(i);
		}
		combo_box_Year.setSelectedItem(currentYear);
		updateMonthComboBox();
		updateDayComboBox();
		
		String title;
	    String label;
	    String placeholder;

	    switch (utility_Type) {
	        case "electricity":
	            title = "Edit Electricity Reading";
	            label = "Reading (kWh)";
	            placeholder = "Enter Reading (kWh)";
	            break;
	        case "water":
	            title = "Edit Water Reading";
	            label = "Reading (m³)";
	            placeholder = "Enter Reading (m³)";
	            break;
	        case "gas":
	            title = "Edit Gas Reading";
	            label = "Reading (Qty)";
	            placeholder = "Enter Reading (Qty)";
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown reading type: " + utility_Type);
	    }

	    setTitle(title);
	    lbl_Reading.setText(label);
	    tf_Reading.setText(placeholder);
	    
	    tf_Reading.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            if (tf_Reading.getText().equals(placeholder)) {
	                tf_Reading.setText("");
	            }
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (tf_Reading.getText().isEmpty()) {
	                tf_Reading.setText(placeholder);
	            }
	        }
	    });
	}
	
	@SuppressWarnings("unchecked")
	private void updateMonthComboBox() {
		if (combo_box_Year.getSelectedItem() == null) return;
		
	    int selectedYear = (int) combo_box_Year.getSelectedItem();
	    int currentYear = LocalDate.now().getYear();
	    int currentMonth = LocalDate.now().getMonthValue();

	    combo_box_Month.removeAllItems();
	    int maxMonth = selectedYear == currentYear ? currentMonth : 12;
	    for (int i = maxMonth; i >= 1; i--) {
	        combo_box_Month.addItem(i);
	    }
	}

	@SuppressWarnings("unchecked")
	private void updateDayComboBox() {
		if (combo_box_Year.getSelectedItem() == null || combo_box_Month.getSelectedItem() == null) return;
		
	    Integer selectedYear = (Integer) combo_box_Year.getSelectedItem();
	    Integer selectedMonth = (Integer) combo_box_Month.getSelectedItem();

	    if (selectedYear == null || selectedMonth == null) return;

	    int currentYear = LocalDate.now().getYear();
	    int currentMonth = LocalDate.now().getMonthValue();
	    int currentDay = LocalDate.now().getDayOfMonth();

	    int maxDay = LocalDate.of(selectedYear, selectedMonth, 1).lengthOfMonth();
	    if (selectedYear == currentYear && selectedMonth == currentMonth) {
	        maxDay = currentDay;
	    }

	    combo_box_Day.removeAllItems();
	    for (int i = maxDay; i >= 1; i--) {
	        combo_box_Day.addItem(i);
	    }
	}
	
	public void cancelAddReading() {
	    String reading = tf_Reading.getText();
	    String rate = tf_Rate.getText();
	    String totalPrice = tf_TotalPrice.getText();

	    // Inline normalization of placeholder
	    if (utility_Type.equals("electricity") && reading.equals("Enter Reading (kWh)")) {
	        reading = "Enter Reading";
	    } else if (utility_Type.equals("water") && reading.equals("Enter Reading (m³)")) {
	        reading = "Enter Reading";
	    } else if (utility_Type.equals("gas") && reading.equals("Enter Reading (Qty)")) {
	        reading = "Enter Reading";
	    }

	    // Placeholder checks inline
	    if (!reading.equals("Enter Reading") || 
	        !rate.equals("Enter Rate") || 
	        !totalPrice.equals("Total Price")) {
	        
	        int response = JOptionPane.showConfirmDialog(
	            this, "Are you sure you want to cancel?", "Confirm Cancel", JOptionPane.YES_NO_OPTION
	        );
	        if (response == JOptionPane.YES_OPTION) {
	            this.dispose();
	        }
	    } else {
	        this.dispose();
	    }
	}

	public void addReading() {
	    String reading = tf_Reading.getText();
	    String rate = tf_Rate.getText();
	    String totalPrice = tf_TotalPrice.getText();

	    // Check for placeholder texts
	    if (reading.equals("Enter Reading") && 
	        rate.equals("Enter Rate") && 
	        totalPrice.equals("Total Price")) {
	    	lbl_Incorrect_Signage1.setVisible(true);
	    	lbl_Incorrect_Signage2.setVisible(true);
	    	lbl_Incorrect_Signage3.setVisible(true);
	    	JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
	        
	        return;
	    }
	    
	    if(reading.equals("Enter Reading")) {
	    	lbl_Incorrect_Signage1.setVisible(true);
	    	JOptionPane.showMessageDialog(this, "Please enter a reading value.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    if(rate.equals("Enter Rate") && totalPrice.equals("Total Price")) {
	    	lbl_Incorrect_Signage2.setVisible(true);
	    	lbl_Incorrect_Signage3.setVisible(true);
	    	JOptionPane.showMessageDialog(this, "Please enter a rate value or a total price.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    if(utility_Type.equals("gas")) {
	    	try {
	    		@SuppressWarnings("unused")
	    		int readingValGas = Integer.parseInt(reading);
	    	} catch (NumberFormatException e) {
	    		lbl_Incorrect_Signage1.setVisible(true);
	    		JOptionPane.showMessageDialog(this, "Please enter a valid reading value must be a whole number.", "Error", JOptionPane.ERROR_MESSAGE);
	    		return;
	    	}
 	    }
	    
	    try {	
	    	double readingVal = Double.parseDouble(reading);
	    	double rateVal;
	    	double totalVal;
	    	
	    	if(rate.equals("Enter Rate")) {
	    		rateVal = 0;
	    		totalVal = Double.parseDouble(totalPrice);
		    } else if (totalPrice.equals("Total Price")) {
		    	rateVal = Double.parseDouble(rate);
		    	totalVal = 0;
		    } else {
		    	rateVal = Double.parseDouble(rate);
		    	totalVal = Double.parseDouble(totalPrice);
		    }
	    	
	        // Check for negative values
	        if (readingVal < 0) {
	            lbl_Incorrect_Signage1.setVisible(true);
	            JOptionPane.showMessageDialog(this, "Reading value cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        } 
	        //make red not show
	        lbl_Incorrect_Signage1.setVisible(false); // Hide the red signage if reading is valid
	        if (rateVal < 0) {
	        	lbl_Incorrect_Signage2.setVisible(true);
	        	JOptionPane.showMessageDialog(this, "Rate value cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        //make red not show
	        lbl_Incorrect_Signage2.setVisible(false); // Hide the red signage if rate is valid
	        if (totalVal < 0) {
	        	lbl_Incorrect_Signage3.setVisible(true);
	        	JOptionPane.showMessageDialog(this, "Total value cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        //make red not show
	        lbl_Incorrect_Signage3.setVisible(false); // Hide the red signage if total price is valid

	        // Auto-calculate missing values
	        if (rateVal == 0 && totalVal > 0) {
	            // Calculate rate from total price and reading
	            rateVal = totalVal / readingVal;
	            tf_Rate.setText(String.format("%.2f", rateVal));
	        } else if (totalVal == 0 && rateVal > 0) {
	        	
	            // Calculate total from reading and rate
	            totalVal = readingVal * rateVal;
	            tf_TotalPrice.setText(String.format("%.2f", totalVal));
	        } else if (rateVal == 0 && totalVal == 0) {
	            lbl_Incorrect_Signage2.setVisible(true);
	            lbl_Incorrect_Signage3.setVisible(true);
	            JOptionPane.showMessageDialog(this, "Either Rate or Total Price must have a value.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }	

	        LocalDate date = LocalDate.of(
	            (int) combo_box_Year.getSelectedItem(),
	            (int) combo_box_Month.getSelectedItem(),
	            (int) combo_box_Day.getSelectedItem()
	        );

	        if (utility_Type.equals("gas")) {
	        	database_manager.getReadingManager().addReading(current_user, date, utility_Type, (int) readingVal, rateVal, totalVal);
	        } else {
	        	database_manager.getReadingManager().addReading(current_user, date, utility_Type, readingVal, rateVal, totalVal);
	        }
	        
	        // Update appropriate panel
	        parent_Panel.Panel_Refresh();
	        parent_Panel.Refresh_Graph();


	        JOptionPane.showMessageDialog(this, "Reading added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	        this.dispose();

	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, "Error adding reading: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}
/*
 * File: Add_Reading_Window.java
 *
 * Description:
 * This file defines the `Add_Reading_Window` class, which is a `JDialog` used for adding a new utility reading (electricity, water, or gas).
 * It provides a graphical interface for entering the reading details, including the date, reading value, rate, and total price.
 * The class interacts with the `Database_Manager` to validate and save the new reading information.
 *
 * Variables:
 *
 * - **Database and User Models**:
 *   - `database_manager` (Database_Manager): Manages database operations, including reading-related actions.
 *   - `current_user` (User): Represents the currently logged-in user.
 *   - `utility_Type` (String): Specifies the type of utility (electricity, water, or gas).
 *   - `parent_Panel` (Utility_Panel): The parent panel (Electricity_Panel, Water_Panel, or Gas_Panel) to refresh after adding a new reading.
 *
 * - **Main Content Pane**:
 *   - `contentPane` (JPanel): The main container for the dialog's components.
 *
 * - **Input Fields**:
 *   - `tf_Reading` (JTextField): Input field for the reading value.
 *   - `tf_Rate` (JTextField): Input field for the rate.
 *   - `tf_TotalPrice` (JTextField): Input field for the total price.
 *   - `combo_box_Day` (JComboBox): Dropdown for selecting the day of the reading date.
 *   - `combo_box_Month` (JComboBox): Dropdown for selecting the month of the reading date.
 *   - `combo_box_Year` (JComboBox): Dropdown for selecting the year of the reading date.
 *
 * - **Labels**:
 *   - `lbl_Date` (JLabel): Displays the current date in the title panel.
 *   - `lbl_Title_AddNewReading` (JLabel): Displays the title "Add New Reading".
 *   - `lbl_Reading` (JLabel): Label for the reading input field.
 *   - `lbl_Rate` (JLabel): Label for the rate input field.
 *   - `lbl_TotalPrice` (JLabel): Label for the total price input field.
 *   - `lbl_Incorrect_Signage1`, `lbl_Incorrect_Signage2`, `lbl_Incorrect_Signage3` (JLabel): Red asterisks (*) displayed next to input fields if validation fails.
 *
 * - **Buttons**:
 *   - `btn_Add` (JButton): Button to submit the new reading and save it to the database.
 *   - `btn_Cancel` (JButton): Button to cancel the operation and close the dialog.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Add_Reading_Window(JFrame parent, Database_Manager database_manager, User current_user, Utility_Panel parent_Panel, String utility_type)`:
 *      - Initializes the dialog with the provided parent frame, database manager, current user, parent panel, and utility type.
 *      - Calls `initialize_UI()` to set up the user interface, `create_Action_Listeners()` to add event listeners, and `setup_data()` to configure the initial data.
 *
 * 2. **initialize_UI()**:
 *    - Sets up the dialog's properties (e.g., size, layout, title).
 *    - Creates and positions all UI components, including labels, text fields, combo boxes, and buttons.
 *    - Configures the title panel and validation indicators.
 *
 * 3. **create_Action_Listeners()**:
 *    - Adds event listeners to the combo boxes and buttons to handle user interactions.
 *    - Handles focus events for the text fields to manage placeholder text.
 *    - Handles button hover effects and click actions.
 *    - Calls `addReading()` when the "Add" button is clicked and `cancelAddReading()` when the "Cancel" button is clicked.
 *
 * 4. **setup_data()**:
 *    - Configures the UI based on the utility type (electricity, water, or gas).
 *    - Populates the year, month, and day combo boxes with valid date options.
 *    - Sets up placeholder text and labels for the reading input field based on the utility type.
 *
 * 5. **updateMonthComboBox()**:
 *    - Updates the month combo box based on the selected year.
 *    - Ensures that only valid months are displayed for the current year.
 *
 * 6. **updateDayComboBox()**:
 *    - Updates the day combo box based on the selected year and month.
 *    - Ensures that only valid days are displayed for the current month and year.
 *
 * 7. **cancelAddReading()**:
 *    - Checks if any input fields have been modified.
 *    - If changes are detected, prompts the user with a confirmation dialog to discard unsaved changes.
 *    - Closes the dialog if the user confirms or if no changes are detected.
 *
 * 8. **addReading()**:
 *    - Validates the input fields and saves the new reading to the database.
 *    - Key conditions:
 *      - Checks if the reading, rate, or total price fields contain placeholder text and displays an error message if true.
 *      - Checks if the reading value is negative and displays an error message if true.
 *      - Checks if the rate or total price is negative and displays an error message if true.
 *      - Automatically calculates the rate or total price if one of them is missing.
 *      - If all validations pass:
 *        - Saves the new reading to the database using `addReading()` from the `Database_Manager`.
 *        - Refreshes the parent panel and graph to reflect the new reading.
 *        - Displays a success message and closes the dialog.
 *    - Catches and handles any exceptions that occur during the reading addition process.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for adding a new utility reading.
 * It ensures that the input data is validated before saving to the database and provides feedback to the user in case of errors or success.
 */

package view.panel.misc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.border.LineBorder;

import database.Database_Manager;
import model.Reading;
import model.User;
import view.panel.Utility_Panel;
import visuals.RoundedButton;
import visuals.RoundedPanel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Edit_Reading_Window extends JDialog {

	private static final long serialVersionUID = 1L;
	
	// Database and Data Models
	private Database_Manager database_manager;
	private Utility_Panel parentPanel; // Electricity_Panel, Water_Panel, or Gas_Panel
	private User current_user;
	private Reading selectedReading; // The reading to be edited
	private String readingType; // electricity, water, or gas

	// Header
	private JPanel panel_Header;
	private JLabel lbl_DateToday;
	private JLabel lbl_Title_EditReading;

	// Reading Selection
	private JLabel lbl_Reading_Selection;
	private JComboBox<String> combo_box_Edit_Reading_Selection;
	private JLabel lbl_Column_Date;
	private JLabel lbl_Column_Reading;
	private JLabel lbl_Column_Rate;
	private JLabel lbl_Column_TotalPrice;

	// Inputs
	private JPanel panel_Date;
	private JLabel lbl_Date_Selected;
	private JLabel lbl_Title_Date;
	private JTextField tf_Reading;
	private JLabel lbl_Reading;
	private JTextField tf_Rate;
	private JLabel lbl_Rate;
	private JTextField tf_TotalPrice;
	private JLabel lbl_TotalPrice;

	// Buttons
	private JButton btn_Edit;
	private JButton btn_Cancel;
	private JButton btn_Delete;

	// Main Content Pane
	private JPanel contentPane;

	// Constructor to initialize the Edit Reading Window
	public Edit_Reading_Window(JFrame parent, Database_Manager database_manager, User current_user, Utility_Panel panel_type, String type, Reading selectedReading) {
		super(parent, "Edit Reading", true);
		this.database_manager = database_manager;
		this.current_user = current_user;
		this.parentPanel = panel_type;
		this.readingType = type;
		this.selectedReading = selectedReading;
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(287, 50, 450, 635);
		setBackground(new Color(213, 213, 213));
		setTitle("Edit Reading");
		setResizable(false);
		
		initialize_UI();
		create_Action_Listeners();
		setupData();
	}

	// Method to initialize the UI components of the dialog
	private void initialize_UI() {
		
		//=====================================================================================================
		// main content pane
		//=====================================================================================================
		
		// Create the main content pane with a light gray background
		contentPane = new JPanel();
		contentPane.setBackground(new Color(213, 213, 213));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//=====================================================================================================
		// header panel
		//=====================================================================================================
		
		// Create a rounded panel for the header
		panel_Header = new RoundedPanel();
		panel_Header.setBackground(new Color(255, 255, 255));
		panel_Header.setLayout(null);
		panel_Header.setBounds(10, 11, 416, 97);
		contentPane.add(panel_Header);
		
		// Add a label for the title of the header
		lbl_Title_EditReading = new JLabel("Edit Reading");
		lbl_Title_EditReading.setBounds(13, 20, 393, 54);
		lbl_Title_EditReading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_EditReading.setFont(new Font("Tahoma", Font.PLAIN, 35));
		panel_Header.add(lbl_Title_EditReading);
		
		// Add a label for the current time
		lbl_DateToday = new JLabel("Date");
		lbl_DateToday.setBounds(236, 8, 170, 54);
		lbl_DateToday.setVerticalAlignment(SwingConstants.TOP);
		lbl_DateToday.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_DateToday.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lbl_DateToday.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel_Header.add(lbl_DateToday);
		
		//=====================================================================================================
		// READING SELECTION
		//=====================================================================================================
		
		// Add a label for reading selection
		lbl_Reading_Selection = new JLabel("Select a Reading to edit");
		lbl_Reading_Selection.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Reading_Selection.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Reading_Selection.setBounds(10, 130, 239, 22);
		contentPane.add(lbl_Reading_Selection);
		
		// Add a label for the current time
		combo_box_Edit_Reading_Selection = getAllReadings();
		combo_box_Edit_Reading_Selection.setFont(new Font("Tahoma", Font.PLAIN, 17));
		combo_box_Edit_Reading_Selection.setBounds(10, 175, 416, 45);
		contentPane.add(combo_box_Edit_Reading_Selection);
		
		// column labels for the reading selection table
		lbl_Column_Date = new JLabel("Date");
		lbl_Column_Date.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Column_Date.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Column_Date.setBounds(6, 154, 114, 22);
		contentPane.add(lbl_Column_Date);
		
		// column labels for the reading selection table
		lbl_Column_Reading = new JLabel("Reading");
		lbl_Column_Reading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Column_Reading.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Column_Reading.setBounds(116, 154, 98, 22);
		contentPane.add(lbl_Column_Reading);
		
		// column labels for the reading selection table
		lbl_Column_Rate = new JLabel("Rate");
		lbl_Column_Rate.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Column_Rate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Column_Rate.setBounds(209, 154, 87, 22);
		contentPane.add(lbl_Column_Rate);
		
		// column labels for the reading selection table
		lbl_Column_TotalPrice = new JLabel("Total Price");
		lbl_Column_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Column_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Column_TotalPrice.setBounds(310, 154, 87, 22);
		contentPane.add(lbl_Column_TotalPrice);
		
		//=====================================================================================================
		// Date panel
		//=====================================================================================================
		
		// Create a panel for displaying the selected date
		panel_Date = new JPanel();
		panel_Date.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Date.setBackground(new Color(255, 255, 255));
		panel_Date.setBounds(10, 261, 416, 43);
		contentPane.add(panel_Date);
		panel_Date.setLayout(null);
		
		// Add a label for the selected date
		lbl_Title_Date = new JLabel("Date");
		lbl_Title_Date.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Title_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Title_Date.setBounds(10, 232, 114, 22);
		contentPane.add(lbl_Title_Date);
		
		// Add a label for the selected date
		lbl_Date_Selected = new JLabel("Selected Date");
		lbl_Date_Selected.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_Date_Selected.setBounds(5, 0, 406, 43);
		panel_Date.add(lbl_Date_Selected);
		
		//=====================================================================================================
		// Input Fields
		//=====================================================================================================
		
		// Add a text field for the reading input
		tf_Reading = new JTextField("");
		tf_Reading.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Reading.setColumns(10);
		tf_Reading.setBounds(10, 336, 416, 45);
		contentPane.add(tf_Reading);
		
		// Add a label for the reading input field
		lbl_Reading = new JLabel("Reading");
		lbl_Reading.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Reading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Reading.setBounds(10, 311, 163, 22);
		contentPane.add(lbl_Reading);

		// Add a label for the rate input field
		lbl_Rate = new JLabel("Rate (Php)");
		lbl_Rate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Rate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Rate.setBounds(10, 392, 141, 22);
		contentPane.add(lbl_Rate);
		
		// Add a text field for the rate input
		tf_Rate = new JTextField("Enter Rate");
		tf_Rate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Rate.setColumns(10);
		tf_Rate.setBounds(10, 416, 416, 45);
		contentPane.add(tf_Rate);
		
		// Add a label for the total price input field
		lbl_TotalPrice = new JLabel("Total Price (Php)");
		lbl_TotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_TotalPrice.setBounds(10, 472, 192, 22);
		contentPane.add(lbl_TotalPrice);
		
		// Add a text field for the total price input
		tf_TotalPrice = new JTextField("Total Price");
		tf_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_TotalPrice.setColumns(10);
		tf_TotalPrice.setBounds(10, 496, 416, 45);
		contentPane.add(tf_TotalPrice);
		
		//=====================================================================================================
		// Buttons
		//=====================================================================================================
		
		// Add buttons for editing, canceling, and deleting readings
		btn_Edit =  new RoundedButton("Update", 25);
		btn_Edit.setBackground(new Color(182, 182, 182));
		btn_Edit.setForeground(Color.BLACK);
		btn_Edit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Edit.setBounds(335, 553, 91, 34);
		contentPane.add(btn_Edit);
		
		// Add a cancel button to close the dialog without saving changes
		btn_Cancel = new RoundedButton("Cancel", 25);
		btn_Cancel.setBackground(new Color(182, 182, 182));
		btn_Cancel.setForeground(Color.BLACK);
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setBounds(133, 552, 91, 34);
		contentPane.add(btn_Cancel);
		
		// Add a delete button to remove the selected reading
		btn_Delete = new RoundedButton("Delete", 25);
		btn_Delete.setBackground(new Color(182, 182, 182));
		btn_Delete.setForeground(Color.BLACK);
		btn_Delete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Delete.setBounds(234, 553, 91, 34);
		contentPane.add(btn_Delete);
	}
	
	// Method to create action listeners for the components
	private void create_Action_Listeners() {
		combo_box_Edit_Reading_Selection.addActionListener(e ->  selectedReading());
		tf_Rate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tf_Rate.getText().equals("Enter Rate")) {
					tf_Rate.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tf_Rate.getText().isEmpty()) {
					tf_Rate.setText("Enter Rate");
				}
			}
		});
		
		tf_TotalPrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tf_TotalPrice.getText().equals("Total Price")) {
					tf_TotalPrice.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tf_TotalPrice.getText().isEmpty()) {
					tf_TotalPrice.setText("Total Price");
				}
			}
		});
		
		btn_Edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { updateReading(); }
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Edit.setBackground(new Color(150, 150, 150));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Edit.setBackground(new Color(182, 182, 182));
			}
		});
		btn_Cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancelAddReading();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Cancel.setBackground(new Color(150, 150, 150));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Cancel.setBackground(new Color(182, 182, 182));
			}
		});
		btn_Delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteselectedReading();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Delete.setBackground(new Color(150, 150, 150));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Delete.setBackground(new Color(182, 182, 182));
			}
		});
	}
	
	// Method to set up the data and initial state of the dialog based on the reading type
	private void setupData() {
		String placeholder;
		switch (readingType.toLowerCase()) {
		    case "electricity":
		        setTitle("Edit Electricity Reading");
		        lbl_Reading.setText("Reading (kWh)");
		        placeholder = "Enter Reading (kWh)";
		        break;
		    case "water":
		        setTitle("Edit Water Reading");
		        lbl_Reading.setText("Reading (m³)");
		        placeholder = "Enter Reading (m³)";
		        break;
		    case "gas":
		        setTitle("Edit Gas Reading");
		        lbl_Reading.setText("Reading (Qty)");
		        placeholder = "Enter Reading (Qty)";
		        break;
		    default:
		        placeholder = "Enter Reading";
		        break;
		}

		// Set initial placeholder
		tf_Reading.setText(placeholder);

		// Add placeholder behavior
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
		
		try {
		    List<Reading> allReadings = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, readingType);
		    
		    for (int i = 0; i < combo_box_Edit_Reading_Selection.getItemCount(); i++) {
		        String item = combo_box_Edit_Reading_Selection.getItemAt(i);
		        if (item != null && !item.startsWith("No readings") && !item.startsWith("Error")) {
		            // Check if we have enough readings and the index is valid
		            if (i < allReadings.size()) {
		                Reading reading = allReadings.get(i);
		                
		                // Compare by reading_id directly from the Reading object
		                if (reading.getReading_Id() == selectedReading.getReading_Id()) {
		                    combo_box_Edit_Reading_Selection.setSelectedIndex(i);
		                    selectedReading(); // Call this to populate the fields
		                    break;
		                }
		            }
		        }
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		    // Handle the exception appropriately
		}
	}
	
	// Method to populate the input fields with the selected reading's details
	private void selectedReading() {
		String selected = (String) combo_box_Edit_Reading_Selection.getSelectedItem();
		String[] parts = selected.trim().split("\\s+");
		if (parts.length >= 4) {
		    String date = parts[0];
		    String reading = parts[1];
		    String rate = parts[2];
		    String totalPrice = parts[3];
	        
		    switch (readingType.toLowerCase()) {
		        case "electricity":
		            reading = reading.replace("kWh", "").trim();
		            break;
		        case "water":
		            reading = reading.replace("m³", "").trim();
		            break;
		        case "gas":
		            reading = reading.replace("Qty", "").trim();
		            break;
		        default:
		            break;
		    }
		    
	        double readingValue = Double.parseDouble(reading);
	        double rateValue = Double.parseDouble(rate.replace("Php", ""));
	        double totalPriceValue = Double.parseDouble(totalPrice.replace("Php", ""));
	        
	        lbl_Date_Selected.setText(date);
	        tf_Reading.setText(String.valueOf(readingValue));
	        tf_Rate.setText(String.valueOf(rateValue));
	        tf_TotalPrice.setText(String.valueOf(totalPriceValue));
	    } else {
	        lbl_Date_Selected.setText("Selected Date");
	    }
	}
	
	// Method to delete the selected reading from the database and refresh the parent panel
	private void deleteselectedReading() {
		if (!hasValidInput()) {
			return; 
		}
		
		try {
			int selectedIndex = combo_box_Edit_Reading_Selection.getSelectedIndex();
			if (selectedIndex < 0) {
				JOptionPane.showMessageDialog(this, "No reading selected.", "Error", JOptionPane.ERROR_MESSAGE);
			    return;
			}
			List<Reading> allReadings = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, readingType);
			Reading selectedReading = allReadings.get(selectedIndex);
			
			database_manager.getReadingManager().deleteReading(selectedReading);
			
			getAllReadings();
			parentPanel.Panel_Refresh();
			parentPanel.Refresh_Graph();
			
			JOptionPane.showMessageDialog(this, "Reading Deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
			
			this.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to update the reading in the database and refresh the parent panel
	private void updateReading() {
		if (!hasValidInput()) {
			return; 
		}
		
		try {
			double readingValue = Double.parseDouble(tf_Reading.getText());
			double rateValue = Double.parseDouble(tf_Rate.getText());
			double totalPriceValue = Double.parseDouble(tf_TotalPrice.getText());
			
			int selectedIndex = combo_box_Edit_Reading_Selection.getSelectedIndex();
			if (selectedIndex < 0) {
			    JOptionPane.showMessageDialog(this, "No reading selected.", "Error", JOptionPane.ERROR_MESSAGE);
			    return;
			}
			
			List<Reading> allReadings = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, readingType);
			Reading selectedReading = allReadings.get(selectedIndex);
			
			selectedReading.setReading(readingValue);
			selectedReading.setRate(rateValue);
			selectedReading.setTotal_Price(totalPriceValue);
			
			database_manager.getReadingManager().updateReading(current_user,selectedReading);
			
			getAllReadings();
			parentPanel.Panel_Refresh();
			parentPanel.Refresh_Graph();
			
			JOptionPane.showMessageDialog(this, "Reading updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
			
			this.dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to cancel the add reading operation and close the dialog
	private void cancelAddReading() {
		if (hasValidInput()) {
			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		} else {
			this.dispose();
		}
	}
	
	// Method to fetch all readings of the specified type and populate the combo box
	private JComboBox<String> getAllReadings() {
		try {
			if (!database_manager.getReadingManager().isReading_Exists(current_user, readingType)) {
				combo_box_Edit_Reading_Selection = new JComboBox<>(new String[] {"No readings found in." + readingType , "Please add a reading."});
				return combo_box_Edit_Reading_Selection;
			}
			List<Reading> all_readings = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, readingType);
			
			String Unit = "";
			String Spacing = "";
			if(readingType.equals("electricity")) {
				Unit = "kWh";
				Spacing = "  %-12s %-11s %-11s %-10s";
			} else if (readingType.equals("water")) {
				Unit = "m³";
				Spacing = "  %-14s %-10s %-11s %-10s";
			} else if (readingType.equals("gas")) {
				Unit = "Qty";
				Spacing = "  %-15s %-10s %-12s %-10s";
			}
			String[] readings = new String[all_readings.size()];
			for (int i = 0; i < all_readings.size(); i++) {
				Reading reading = all_readings.get(i);
				readings[i] = String.format(Spacing , reading.getDate(), reading.getReading() + Unit , reading.getRate() + "Php", reading.getTotal_Price() + "Php");
			}
			combo_box_Edit_Reading_Selection = new JComboBox<>(readings);
			return combo_box_Edit_Reading_Selection;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new JComboBox<>(new String[] {"Error fetching readings."});
		}
	}
	
	// Method to check if the input fields are valid
	private boolean hasValidInput() {
		String reading = tf_Reading.getText();
		String rate = tf_Rate.getText();
		String totalPrice = tf_TotalPrice.getText();
		
		switch (readingType) {
			case "electricity":
				reading = reading.replace("Enter Reading (kWh)", "Enter Reading");
				break;
			case "water":
				reading = reading.replace("Enter Reading (m³)", "Enter Reading");
				break;
			case "gas":
				reading = reading.replace("Enter Reading (Qty)", "Enter Reading");
				break;
			default:
				break;
		}
		
		if (reading.isEmpty() || reading.equals("Enter Reading") || rate.isEmpty() || rate.equals("Enter Rate") || totalPrice.isEmpty() || totalPrice.equals("Total Price")) {
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}

/*
 * File: Edit_Reading_Window.java
 *
 * Description:
 * This file defines the `Edit_Reading_Window` class, which is a `JDialog` used for editing a specific utility reading (electricity, water, or gas).
 * It provides a graphical interface for selecting a reading, modifying its details, and saving or deleting the changes.
 * The class interacts with the `Database_Manager` to validate and update the reading information.
 *
 * Variables:
 *
 * - **Database and Data Models**:
 *   - `database_manager` (Database_Manager): Manages database operations, including reading-related actions.
 *   - `parentPanel` (Utility_Panel): The parent panel (Electricity_Panel, Water_Panel, or Gas_Panel) to refresh after updates.
 *   - `current_user` (User): Represents the currently logged-in user.
 *   - `selectedReading` (Reading): The reading currently being edited.
 *   - `readingType` (String): Specifies the type of reading (electricity, water, or gas).
 *
 * - **Header Components**:
 *   - `panel_Header` (JPanel): The header panel containing the title and date.
 *   - `lbl_DateToday` (JLabel): Displays the current date in the header.
 *   - `lbl_Title_EditReading` (JLabel): Displays the title of the window ("Edit Reading").
 *
 * - **Reading Selection Components**:
 *   - `lbl_Reading_Selection` (JLabel): Label for the reading selection combo box.
 *   - `combo_box_Edit_Reading_Selection` (JComboBox<String>): Dropdown for selecting a reading to edit.
 *   - `lbl_Column_Date` (JLabel): Column header for the date in the combo box.
 *   - `lbl_Column_Reading` (JLabel): Column header for the reading value in the combo box.
 *   - `lbl_Column_Rate` (JLabel): Column header for the rate in the combo box.
 *   - `lbl_Column_TotalPrice` (JLabel): Column header for the total price in the combo box.
 *
 * - **Input Components**:
 *   - `panel_Date` (JPanel): Panel for displaying the selected date.
 *   - `lbl_Date_Selected` (JLabel): Displays the date of the selected reading.
 *   - `lbl_Title_Date` (JLabel): Label for the selected date.
 *   - `tf_Reading` (JTextField): Input field for the reading value.
 *   - `lbl_Reading` (JLabel): Label for the reading input field.
 *   - `tf_Rate` (JTextField): Input field for the rate.
 *   - `lbl_Rate` (JLabel): Label for the rate input field.
 *   - `tf_TotalPrice` (JTextField): Input field for the total price.
 *   - `lbl_TotalPrice` (JLabel): Label for the total price input field.
 *
 * - **Button Components**:
 *   - `btn_Edit` (JButton): Button to save the edited reading.
 *   - `btn_Cancel` (JButton): Button to cancel the operation and close the dialog.
 *   - `btn_Delete` (JButton): Button to delete the selected reading.
 *
 * - **Main Content Pane**:
 *   - `contentPane` (JPanel): The main container for the dialog's components.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Edit_Reading_Window(JFrame parent, Database_Manager database_manager, User current_user, Utility_Panel panel_type, String type, Reading selectedReading)`:
 *      - Initializes the dialog with the provided parent frame, database manager, current user, parent panel, reading type, and selected reading.
 *      - Calls `initialize_UI()`, `create_Action_Listeners()`, and `setupData()` to set up the UI, add event listeners, and load data.
 *
 * 2. **initialize_UI()**:
 *    - Sets up the dialog's properties (e.g., size, layout, title).
 *    - Creates and positions all UI components, including labels, text fields, combo boxes, and buttons.
 *
 * 3. **create_Action_Listeners()**:
 *    - Adds event listeners to the combo box and buttons to handle user interactions.
 *    - Handles button hover effects and click actions.
 *    - Calls `selectedReading()`, `updateReading()`, `deleteselectedReading()`, and `cancelAddReading()` based on user actions.
 *
 * 4. **setupData()**:
 *    - Configures the UI based on the reading type (electricity, water, or gas).
 *    - Pre-selects the reading in the combo box based on the `selectedReading`.
 *    - Key conditions:
 *      - If the reading type is electricity, water, or gas, updates the labels and placeholders accordingly.
 *      - If the reading ID matches the selected reading, sets the combo box index and populates the fields.
 *
 * 5. **getAllReadings()**:
 *    - Fetches all readings of the specified type from the database and populates the combo box.
 *    - Key conditions:
 *      - If no readings exist, displays a placeholder message in the combo box.
 *      - Formats the combo box items to include the date, reading, rate, and total price.
 *
 * 6. **selectedReading()**:
 *    - Populates the input fields with the details of the selected reading from the combo box.
 *    - Key conditions:
 *      - Extracts the date, reading, rate, and total price from the selected combo box item.
 *      - Updates the input fields and labels with the extracted values.
 *
 * 7. **updateReading()**:
 *    - Validates the input fields and updates the reading in the database.
 *    - Key conditions:
 *      - Checks if the input fields are empty and displays an error message if true.
 *      - Validates the numeric values for the reading, rate, and total price.
 *      - Updates the reading in the database and refreshes the parent panel.
 *
 * 8. **deleteselectedReading()**:
 *    - Deletes the selected reading from the database.
 *    - Key conditions:
 *      - Prompts the user for confirmation before deleting the reading.
 *      - Refreshes the parent panel after deletion.
 *
 * 9. **cancelAddReading()**:
 *    - Closes the dialog without saving any changes.
 *    - Key conditions:
 *      - If changes are detected, prompts the user for confirmation before closing.
 *
 * 10. **hasValidInput()**:
 *     - Validates the input fields to ensure they are not empty or contain placeholder text.
 *     - Displays an error message if validation fails.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for editing or deleting a specific utility reading.
 * It ensures that the input data is validated before updating the database and provides feedback to the user in case of errors or success.
 */


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
import view.panel.Electricity_Panel;
import view.panel.Gas_Panel;
import view.panel.Water_Panel;
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


public class Add_Reading_Panel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Database_Manager database_manager;
	private User current_user;
	private String readingType;
	private JPanel parentPanel;

	
	private JTextField tf_Reading;
	private JTextField tf_Rate;
	private JTextField tf_TotalPrice;
	private JPanel panel_Electricity_Consumption_Title;
	private JLabel lbl_Date;
	private JLabel lbl_Title_AddNewReading;
	private JLabel lbl_Date_1;
	@SuppressWarnings("rawtypes")
	private JComboBox combo_box_Day; 
	@SuppressWarnings("rawtypes")
	private JComboBox combo_box_Month;
	@SuppressWarnings("rawtypes")
	private JComboBox combo_box_Year;
	private JLabel lbl_Reading;
	private JLabel lbl_Rate;
	private JLabel lbl_TotalPrice;
	private JButton btn_Add;
	private JButton btn_Cancel;
	private JLabel lbl_Day;
	private JLabel lbl_Month;
	private JLabel lbl_Year;
	
	public Add_Reading_Panel(JFrame parent ,Database_Manager database_manager, User current_user,JPanel panel_type, String type) {
		super(parent, "Add Reading", true);
	    this.database_manager = database_manager;
	    this.current_user = current_user;
	    this.parentPanel = panel_type; // Set the parent panel Electricity_Panel, Water_Panel or Gas_Panel
	    this.readingType = type;  // Set the reading type electricity, water or gas
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(400, 50, 450, 535);
		setBackground(new Color(213, 213, 213));
		setTitle("Add Reading");
		setResizable(false);
		
		initialize_UI();
		create_Action_Listeners();

		setup_data();
	}
	
	@SuppressWarnings("rawtypes")
	private void initialize_UI() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(213, 213, 213));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_Electricity_Consumption_Title = new Rounded_Panel();
		panel_Electricity_Consumption_Title.setBackground(new Color(255, 255, 255));
		panel_Electricity_Consumption_Title.setLayout(null);
		panel_Electricity_Consumption_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_Electricity_Consumption_Title);
		
		lbl_Date = new JLabel("Date");
		lbl_Date.setBounds(236, 8, 170, 54);
		panel_Electricity_Consumption_Title.add(lbl_Date);
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		lbl_Title_AddNewReading = new JLabel("Add New Reading");
		lbl_Title_AddNewReading.setBounds(13, 20, 393, 54);
		panel_Electricity_Consumption_Title.add(lbl_Title_AddNewReading);
		lbl_Title_AddNewReading.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_AddNewReading.setFont(new Font("Tahoma", Font.PLAIN, 35));
		
		lbl_Date_1 = new JLabel("Date");
		lbl_Date_1.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Date_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Date_1.setBounds(10, 119, 114, 22);
		contentPane.add(lbl_Date_1);
		
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
		
		configureReadingUI(readingType);
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
	
	private void configureReadingUI(String readingType) {
	    String title;
	    String label;
	    String placeholder;

	    switch (readingType.toLowerCase()) {
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
	            throw new IllegalArgumentException("Unknown reading type: " + readingType);
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
	
	public void cancelAddReading() {
	    String reading = tf_Reading.getText();
	    String rate = tf_Rate.getText();
	    String totalPrice = tf_TotalPrice.getText();

	    // Inline normalization of placeholder
	    if (readingType.equals("electricity") && reading.equals("Enter Reading (kWh)")) {
	        reading = "Enter Reading";
	    } else if (readingType.equals("water") && reading.equals("Enter Reading (m³)")) {
	        reading = "Enter Reading";
	    } else if (readingType.equals("gas") && reading.equals("Enter Reading (Qty)")) {
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

	    // Inline placeholder checks
	    if (reading.equals("Enter Reading") || 
	        rate.equals("Enter Rate") || 
	        totalPrice.equals("Total Price")) {
	        
	        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    try {
	        double readingVal = Double.parseDouble(reading);
	        double rateVal = Double.parseDouble(rate);
	        double totalVal = Double.parseDouble(totalPrice);

	        if (readingVal < 0 || rateVal < 0 || totalVal < 0) {
	            JOptionPane.showMessageDialog(this, "Please enter positive values.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        LocalDate date = LocalDate.of(
	            (int) combo_box_Year.getSelectedItem(),
	            (int) combo_box_Month.getSelectedItem(),
	            (int) combo_box_Day.getSelectedItem()
	        );

	        database_manager.getReadingManager().addReading(current_user, date, readingType, readingVal, rateVal, totalVal);

	        if (parentPanel instanceof Electricity_Panel) {
	            ((Electricity_Panel) parentPanel).Panel_Refresh();
	            ((Electricity_Panel) parentPanel).Refresh_Graph();
	        } else if (parentPanel instanceof Water_Panel) {
	            ((Water_Panel) parentPanel).Panel_Refresh();
	            ((Water_Panel) parentPanel).Refresh_Graph();
	        } else if (parentPanel instanceof Gas_Panel) {
	            ((Gas_Panel) parentPanel).Panel_Refresh();
	            ((Gas_Panel) parentPanel).Refresh_Graph();
	        }

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

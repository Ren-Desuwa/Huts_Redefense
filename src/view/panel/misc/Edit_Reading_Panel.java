package view.panel.misc;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.border.LineBorder;

import database.Database_Manager;
import model.Reading;
import model.User;
import view.panel.Electricity_Panel;
import view.panel.Gas_Panel;
import view.panel.Water_Panel;
import visuals.Rounded_Button;
import visuals.Rounded_Panel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Edit_Reading_Panel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Database_Manager database_manager;
	private User current_user;
	private JPanel parentPanel; // Electricity_Panel, Water_Panel or Gas_Panel
	private String readingType; // electricity, water or gas
	private Electricity_Panel electricitypanel;
	private Water_Panel waterpanel;
	private Gas_Panel gaspanel;
	private Reading current_reading;
	
	private JTextField tf_Reading;
	private JTextField tf_Rate;
	private JTextField tf_TotalPrice;
	private JPanel panel_Electricity_Consumption_Title;
	private JLabel lbl_DateToday;
	private JLabel lbl_Title_EditReading;
	private JLabel lbl_Title_Date;
	private JLabel lbl_Reading;
	private JLabel lbl_Rate;
	private JLabel lbl_TotalPrice;
	private JButton btn_Edit;
	private JButton btn_Cancel;
	private JLabel lbl_Column_Date;
	private JLabel lbl_Column_Reading;
	private JLabel lbl_Column_Rate;
	private JPanel panel_Date;
	private JLabel lbl_DateSelected;
	private JButton btn_Delete;
	private JLabel lbl_Column_TotalPrice;
	private JComboBox<String> cB_Edit_Reading_Selection;
	private JLabel lblTime;
	
	
		public Edit_Reading_Panel(JFrame parent, Database_Manager database_manager, User current_user, JPanel panel_type, String type) {
		    // Same as original constructor
		    super(parent, "Edit Reading", true);
		    this.database_manager = database_manager;
		    this.current_user = current_user;
		    this.parentPanel = panel_type; // Set the parent panel Electricity_Panel, Water_Panel or Gas_Panel
		    this.readingType = type;  // Set the reading type electricity, water or gas
			
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 635);
			setBackground(new Color(213, 213, 213));
			setTitle("Edit Reading");
			setResizable(false);
			
			initialize_UI();
			setLabels();
		}
		
		public Edit_Reading_Panel(JFrame parent, Database_Manager database_manager, User current_user, JPanel panel_type, String type, Reading selectedReading) {
			this(parent, database_manager, current_user, panel_type, type); // Call the original constructor
		
			// If a specific reading was selected, pre-select it in the combo box
			if (selectedReading != null) {
				this.current_reading = selectedReading;
				preSelectReading(selectedReading);
			}
		}
		
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
			
			lbl_DateToday = new JLabel("Date");
			lbl_DateToday.setBounds(236, 8, 170, 54);
			panel_Electricity_Consumption_Title.add(lbl_DateToday);
			lbl_DateToday.setVerticalAlignment(SwingConstants.TOP);
			lbl_DateToday.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_DateToday.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			lbl_DateToday.setFont(new Font("Tahoma", Font.PLAIN, 17));
			
			lbl_Title_EditReading = new JLabel("Edit Reading");
			lbl_Title_EditReading.setBounds(13, 20, 393, 54);
			panel_Electricity_Consumption_Title.add(lbl_Title_EditReading);
			lbl_Title_EditReading.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Title_EditReading.setFont(new Font("Tahoma", Font.PLAIN, 35));
			
			lblTime = new JLabel("Time");
			lblTime.setVerticalAlignment(SwingConstants.TOP);
			lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTime.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblTime.setBounds(134, 8, 170, 41);
			lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
	        panel_Electricity_Consumption_Title.add(lblTime);
			
			JLabel lbl_Reading_Selection = new JLabel("Select a Reading to edit");
			lbl_Reading_Selection.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Reading_Selection.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Reading_Selection.setBounds(10, 130, 239, 22);
			contentPane.add(lbl_Reading_Selection);
			
			cB_Edit_Reading_Selection = getAllReadings();
			cB_Edit_Reading_Selection.setFont(new Font("Tahoma", Font.PLAIN, 17));
			cB_Edit_Reading_Selection.setBounds(10, 175, 416, 45);
			cB_Edit_Reading_Selection.addActionListener(e -> {
			    selectedReading();
			});
			contentPane.add(cB_Edit_Reading_Selection);
			
			lbl_Title_Date = new JLabel("Date");
			lbl_Title_Date.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Title_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Title_Date.setBounds(10, 232, 114, 22);
			contentPane.add(lbl_Title_Date);
			
			lbl_Column_Date = new JLabel("Date");
			lbl_Column_Date.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Column_Date.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Column_Date.setBounds(6, 154, 114, 22);
			contentPane.add(lbl_Column_Date);
			
			lbl_Column_Reading = new JLabel("Reading");
			lbl_Column_Reading.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Column_Reading.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Column_Reading.setBounds(124, 154, 98, 22);
			contentPane.add(lbl_Column_Reading);
			
			
			lbl_Column_Rate = new JLabel("Rate");
			lbl_Column_Rate.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Column_Rate.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Column_Rate.setBounds(217, 154, 87, 22);
			contentPane.add(lbl_Column_Rate);
			
			lbl_Reading = new JLabel("Reading");
			lbl_Reading.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Reading.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Reading.setBounds(10, 311, 163, 22);
			contentPane.add(lbl_Reading);
			
			tf_Reading = new JTextField("");
			tf_Reading.setFont(new Font("Tahoma", Font.PLAIN, 15));
			tf_Reading.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					if (tf_Reading.getText().equals("Enter Reading")) {
						tf_Reading.setText("");
					}
				}
				@Override
				public void focusLost(FocusEvent e) {
					if (tf_Reading.getText().isEmpty()) {
						tf_Reading.setText("Enter Reading");
					}
				}
			});
			tf_Reading.setColumns(10);
			tf_Reading.setBounds(10, 336, 416, 45);
			contentPane.add(tf_Reading);
			
			lbl_Rate = new JLabel("Rate (Php)");
			lbl_Rate.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Rate.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Rate.setBounds(10, 392, 141, 22);
			contentPane.add(lbl_Rate);
			
			tf_Rate = new JTextField("Enter Rate");
			tf_Rate.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
			tf_Rate.setColumns(10);
			tf_Rate.setBounds(10, 416, 416, 45);
			contentPane.add(tf_Rate);
			
			lbl_TotalPrice = new JLabel("Total Price (Php)");
			lbl_TotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_TotalPrice.setBounds(10, 472, 192, 22);
			contentPane.add(lbl_TotalPrice);
			
			tf_TotalPrice = new JTextField("Total Price");
			tf_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
			tf_TotalPrice.setColumns(10);
			tf_TotalPrice.setBounds(10, 496, 416, 45);
			contentPane.add(tf_TotalPrice);
			
			btn_Edit =  new Rounded_Button("Update", 25);
			btn_Edit.setBackground(new Color(182, 182, 182));
			btn_Edit.setForeground(Color.BLACK);
			btn_Edit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					updateReading();
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					btn_Edit.setBackground(new Color(150, 150, 150));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_Edit.setBackground(new Color(182, 182, 182));
				}
			});
			btn_Edit.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btn_Edit.setBounds(335, 553, 91, 34);
			contentPane.add(btn_Edit);
			
			btn_Cancel = new Rounded_Button("Cancel", 25);
			btn_Cancel.setBackground(new Color(182, 182, 182));
			btn_Cancel.setForeground(Color.BLACK);
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
			btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btn_Cancel.setBounds(133, 552, 91, 34);
			contentPane.add(btn_Cancel);
			
			panel_Date = new JPanel();
			panel_Date.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_Date.setBackground(new Color(255, 255, 255));
			panel_Date.setBounds(10, 261, 416, 43);
			contentPane.add(panel_Date);
			panel_Date.setLayout(null);
			
			lbl_DateSelected = new JLabel("Selected Date");
			lbl_DateSelected.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lbl_DateSelected.setBounds(5, 0, 406, 43);
			panel_Date.add(lbl_DateSelected);
			
			btn_Delete = new Rounded_Button("Delete", 25);
			btn_Delete.setBackground(new Color(182, 182, 182));
			btn_Delete.setForeground(Color.BLACK);
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
			btn_Delete.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btn_Delete.setBounds(234, 553, 91, 34);
			contentPane.add(btn_Delete);
			
			lbl_Column_TotalPrice = new JLabel("Total Price");
			lbl_Column_TotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Column_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Column_TotalPrice.setBounds(311, 154, 87, 22);
			contentPane.add(lbl_Column_TotalPrice);
		}
		
		private void preSelectReading(Reading selectedReading) {
		    // Find the reading in the combo box and select it
		    for (int i = 0; i < cB_Edit_Reading_Selection.getItemCount(); i++) {
		        String item = cB_Edit_Reading_Selection.getItemAt(i);
		        if (item != null && !item.startsWith("No readings") && !item.startsWith("Error")) {
		            // Extract the date from the combo box item
		            String date = item.trim().split("\\s+")[0];
		            
		            // If the dates match (since readings are uniquely identified by date)
		            if (date.equals(selectedReading.getDate())) {
		                cB_Edit_Reading_Selection.setSelectedIndex(i);
		                selectedReading(); // Call this to populate the fields
		                break;
		            }
		        }
		    }
		}
		
		private void setLabels() {
			if (readingType.equals("electricity")) {
				setTitle("Edit Electricity Reading");
				lbl_Reading.setText("Reading (kWh)");
				
				tf_Reading.setText("Enter Reading (kWh)");
				tf_Reading.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (tf_Reading.getText().equals("Enter Reading (kWh)")) {
							tf_Reading.setText("");
						}
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (tf_Reading.getText().isEmpty()) {
							tf_Reading.setText("Enter Reading (kWh)");
						}
					}
				});
			} 
			if (readingType.equals("water")) {
				setTitle("Edit Water Reading");
				lbl_Reading.setText("Reading (m³)");
				
				tf_Reading.setText("Enter Reading (m³)");
				tf_Reading.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (tf_Reading.getText().equals("Enter Reading (m³)")) {
							tf_Reading.setText("");
						}
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (tf_Reading.getText().isEmpty()) {
							tf_Reading.setText("Enter Reading (m³)");
						}
					}
				});
			}
			if (readingType.equals("gas")) {
				setTitle("Edit Gas Reading");
				lbl_Reading.setText("Reading (Qty)");
				
				
				
				tf_Reading.setText("Enter Reading (Qty)");
				tf_Reading.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent e) {
						if (tf_Reading.getText().equals("Enter Reading (Qty)")) {
							tf_Reading.setText("");
						}
					}
					@Override
					public void focusLost(FocusEvent e) {
						if (tf_Reading.getText().isEmpty()) {
							tf_Reading.setText("Enter Reading (Qty)");
						}
					}
				});
			}
		}
		
		public void selectedReading() {
			String selected = (String) cB_Edit_Reading_Selection.getSelectedItem();
			if (selected != null && !selected.startsWith("No readings") && !selected.startsWith("Error")) {
		        String date = selected.trim().split("\\s+")[0];
		        String reading = selected.trim().split("\\s+")[1];
		        String rate = selected.trim().split("\\s+")[2];
		        String totalPrice = selected.trim().split("\\s+")[3];
		        
		        double readingValue = Double.parseDouble(reading.replace("kWh", ""));
		        double rateValue = Double.parseDouble(rate.replace("Php", ""));
		        double totalPriceValue = Double.parseDouble(totalPrice.replace("Php", ""));
		        
		        lbl_DateSelected.setText(date);
		        tf_Reading.setText(String.valueOf(readingValue));
		        tf_Rate.setText(String.valueOf(rateValue));
		        tf_TotalPrice.setText(String.valueOf(totalPriceValue));
		    } else {
		        lbl_DateSelected.setText("Selected Date");
		    }
		}
		
		public void deleteselectedReading() {
			String reading = tf_Reading.getText();
			String rate = tf_Rate.getText();
			String totalPrice = tf_TotalPrice.getText();
			
			if (reading.equals("Enter Reading") || rate.equals("Enter Rate") || totalPrice.equals("Total Price")) {
				javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				double readingValue = Double.parseDouble(reading);
				double rateValue = Double.parseDouble(rate);
				double totalPriceValue = Double.parseDouble(totalPrice);
				
				int selectedIndex = cB_Edit_Reading_Selection.getSelectedIndex();
				if (selectedIndex < 0) {
				    javax.swing.JOptionPane.showMessageDialog(this, "No reading selected.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				    return;
				}
				List<Reading> allReadings = database_manager.getReadingManager().getAllReadingsByType(current_user, readingType);
				Reading selectedReading = allReadings.get(selectedIndex);
				
				selectedReading.setReading(readingValue);
				selectedReading.setRate(rateValue);
				selectedReading.setTotal_Price(totalPriceValue);
				
				database_manager.getReadingManager().deleteReading(current_user, selectedReading);
				
				getAllReadings();
				if (parentPanel instanceof Electricity_Panel) {
					electricitypanel = (Electricity_Panel) parentPanel;
					electricitypanel.Panel_Refresh();
					electricitypanel.Refresh_Graph();
				} 
				if (parentPanel instanceof Water_Panel) {
					waterpanel = (Water_Panel) parentPanel;
					waterpanel.Panel_Refresh();
					waterpanel.Refresh_Graph();
				}
				if (parentPanel instanceof Gas_Panel) {
					gaspanel = (Gas_Panel) parentPanel;
					gaspanel.Panel_Refresh();
					gaspanel.Refresh_Graph();
				}
				
				javax.swing.JOptionPane.showMessageDialog(this, "Reading Deleted successfully.", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
				
				this.dispose();
			} catch (NumberFormatException e) {
				javax.swing.JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		public void updateReading() {
			String reading = tf_Reading.getText();
			String rate = tf_Rate.getText();
			String totalPrice = tf_TotalPrice.getText();
			
			if (reading.equals("Enter Reading") || rate.equals("Enter Rate") || totalPrice.equals("Total Price")) {
				javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				double readingValue = Double.parseDouble(reading);
				double rateValue = Double.parseDouble(rate);
				double totalPriceValue = Double.parseDouble(totalPrice);
				
				int selectedIndex = cB_Edit_Reading_Selection.getSelectedIndex();
				if (selectedIndex < 0) {
				    javax.swing.JOptionPane.showMessageDialog(this, "No reading selected.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				    return;
				}
				List<Reading> allReadings = database_manager.getReadingManager().getAllReadingsByType(current_user, readingType);
				Reading selectedReading = allReadings.get(selectedIndex);
				
				selectedReading.setReading(readingValue);
				selectedReading.setRate(rateValue);
				selectedReading.setTotal_Price(totalPriceValue);
				
				database_manager.getReadingManager().updateReading(current_user,selectedReading);
				
				getAllReadings();
				
				if (parentPanel instanceof Electricity_Panel) {
					electricitypanel = (Electricity_Panel) parentPanel;
					electricitypanel.Panel_Refresh();
					electricitypanel.Refresh_Graph();
				} 
				if (parentPanel instanceof Water_Panel) {
					waterpanel = (Water_Panel) parentPanel;
					waterpanel.Panel_Refresh();
					waterpanel.Refresh_Graph();
				}
				if (parentPanel instanceof Gas_Panel) {
					gaspanel = (Gas_Panel) parentPanel;
					gaspanel.Panel_Refresh();
					gaspanel.Refresh_Graph();
				}
				
				
				
				javax.swing.JOptionPane.showMessageDialog(this, "Reading updated successfully.", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
				
				this.dispose();
			} catch (NumberFormatException e) {
				javax.swing.JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void cancelAddReading() {
			String reading = tf_Reading.getText();
			String rate = tf_Rate.getText();
			String totalPrice = tf_TotalPrice.getText();
			
			if (!reading.equals("Enter Reading") || !rate.equals("Enter Rate") || !totalPrice.equals("Total Price")) {
				int response = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm Cancel", javax.swing.JOptionPane.YES_NO_OPTION);
				if (response == javax.swing.JOptionPane.YES_OPTION) {
					this.dispose();
				}
			} else {
				this.dispose();
			}
		}
		
		private JComboBox<String> getAllReadings() {
			try {
				if (!database_manager.getReadingManager().isReadingExists(current_user, readingType)) {
					cB_Edit_Reading_Selection = new JComboBox<>(new String[] {"No readings found in." + readingType , "Please add a reading."});
					return cB_Edit_Reading_Selection;
				}
				List<Reading> all_readings = database_manager.getReadingManager().getAllReadingsByType(current_user, readingType);
				
				String[] readings = new String[all_readings.size()];
				for (int i = 0; i < all_readings.size(); i++) {
					Reading reading = all_readings.get(i);
					readings[i] = String.format("  %-13s %-11s %-9s %-10s", reading.getDate(), reading.getReading() + "kWh", reading.getRate() + "Php", reading.getTotal_Price() + "Php");
				}
				cB_Edit_Reading_Selection = new JComboBox<>(readings);
				return cB_Edit_Reading_Selection;
			}
			catch (Exception e) {
				e.printStackTrace();
				return new JComboBox<>(new String[] {"Error fetching readings."});
			}
		}
	}

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
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.border.LineBorder;

import database.Database_Manager;
import model.Reading;
import model.User;
import view.panel.Electricity_Panel;

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
	
	private JTextField tf_Reading;
	private JTextField tf_Rate;
	private JTextField tf_TotalPrice;
	private JPanel panel_Electricity_Consumption_Title;
	private JLabel lbl_DateToday;
	private JLabel lbl_Title_Electricity_Consumption;
	private JLabel lbl_Title_Date;
	private JLabel lbl_Reading;
	private JLabel lbl_Rate;
	private JLabel lbl_TotalPrice;
	private JButton btn_Edit;
	private JButton btn_Cancel;
	private JLabel lbl_Day;
	private JLabel lbl_Month;
	private JLabel lbl_Year;
	private Electricity_Panel electricitypanel;
	private JPanel panel_Date;
	private JLabel lbl_DateSelected;
	private JButton btn_Delete;
	
	
		public Edit_Reading_Panel(JFrame parent, Database_Manager database_manager, User current_user, Electricity_Panel utilitypanel) {
			super(parent, "Edit Reading", true);
			this.database_manager = database_manager;
			this.current_user = current_user;
			this.electricitypanel = utilitypanel;
			
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 635);
			
			setTitle("Edit Reading");
			setResizable(false);
			
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			panel_Electricity_Consumption_Title = new JPanel();
			panel_Electricity_Consumption_Title.setLayout(null);
			panel_Electricity_Consumption_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			panel_Electricity_Consumption_Title.setBounds(10, 11, 416, 97);
			contentPane.add(panel_Electricity_Consumption_Title);
			
			lbl_DateToday = new JLabel("Date");
			lbl_DateToday.setBounds(236, 11, 170, 54);
			panel_Electricity_Consumption_Title.add(lbl_DateToday);
			lbl_DateToday.setVerticalAlignment(SwingConstants.TOP);
			lbl_DateToday.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_DateToday.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			lbl_DateToday.setFont(new Font("Tahoma", Font.PLAIN, 17));
			
			lbl_Title_Electricity_Consumption = new JLabel("Edit Reading");
			lbl_Title_Electricity_Consumption.setBounds(13, 20, 393, 54);
			panel_Electricity_Consumption_Title.add(lbl_Title_Electricity_Consumption);
			lbl_Title_Electricity_Consumption.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_Title_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
			
			JLabel lbl_Reading_Selection = new JLabel("Select a Reading to edit");
			lbl_Reading_Selection.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Reading_Selection.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Reading_Selection.setBounds(10, 130, 239, 22);
			contentPane.add(lbl_Reading_Selection);
			
			JComboBox<String> list = getAllReadings();
			JComboBox cB_Edit_Reading_Selection = new JComboBox();
			cB_Edit_Reading_Selection.setFont(new Font("Tahoma", Font.PLAIN, 17));
			cB_Edit_Reading_Selection.setBounds(10, 158, 416, 45);
			contentPane.add(cB_Edit_Reading_Selection);
			
			lbl_Title_Date = new JLabel("Date");
			lbl_Title_Date.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Title_Date.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Title_Date.setBounds(10, 214, 114, 22);
			contentPane.add(lbl_Title_Date);
			
			lbl_Day = new JLabel("Day");
			lbl_Day.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Day.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Day.setBounds(10, 238, 114, 22);
			contentPane.add(lbl_Day);
			
			lbl_Month = new JLabel("Month");
			lbl_Month.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Month.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Month.setBounds(157, 238, 114, 22);
			contentPane.add(lbl_Month);
			
			
			lbl_Year = new JLabel("Year");
			lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Year.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lbl_Year.setBounds(306, 238, 114, 22);
			contentPane.add(lbl_Year);
			
			lbl_Reading = new JLabel("Reading (kWh)");
			lbl_Reading.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_Reading.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbl_Reading.setBounds(10, 311, 163, 22);
			contentPane.add(lbl_Reading);
			
			tf_Reading = new JTextField("Enter Reading");
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
			
			btn_Edit =  new JButton("Edit");
			btn_Edit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			btn_Edit.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btn_Edit.setBounds(335, 553, 91, 34);
			contentPane.add(btn_Edit);
			
			btn_Cancel = new JButton("Cancel");
			btn_Cancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cancelAddReading();
				}
			});
			btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btn_Cancel.setBounds(133, 552, 91, 34);
			contentPane.add(btn_Cancel);
			
			panel_Date = new JPanel();
			panel_Date.setBackground(new Color(255, 255, 255));
			panel_Date.setBounds(10, 261, 416, 43);
			contentPane.add(panel_Date);
			panel_Date.setLayout(null);
			
			lbl_DateSelected = new JLabel("New label");
			lbl_DateSelected.setBounds(0, 0, 416, 43);
			panel_Date.add(lbl_DateSelected);
			
			btn_Delete = new JButton("Delete");
			btn_Delete.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btn_Delete.setBounds(234, 553, 91, 34);
			contentPane.add(btn_Delete);
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
				if (!database_manager.getReadingManager().isReadingExists(current_user, "electricity")) {
					JComboBox<String> cB_Edit_Reading_Selection = new JComboBox<>(new String[] {"No readings found.", "Please add a reading."});
					return cB_Edit_Reading_Selection;
				}
				List<Reading> all_readings = database_manager.getReadingManager().getAllReadingsByType(current_user, "electricity");
				
				String[] readings = new String[all_readings.size()];
				for (int i = 0; i < all_readings.size(); i++) {
					Reading reading = all_readings.get(i);
					readings[i] = String.format("  %-19s %-21s %-17s %-10s", reading.getDate(), reading.getReading() + "kWh", reading.getRate() + "Php", reading.getTotal_Price() + "Php");
				}
				JComboBox<String> cB_Edit_Reading_Selection = new JComboBox<>(readings);
				return cB_Edit_Reading_Selection;
			}
			catch (Exception e) {
				e.printStackTrace();
				return new JComboBox<>(new String[] {"Error fetching readings."});
			}
		}
		
	}

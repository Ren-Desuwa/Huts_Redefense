package view.panel.misc;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.border.LineBorder;

import database.Database_Manager;
import model.User;
import view.panel.Electricity_Panel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Edit_Reading_Panel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Database_Manager database_manager;
	private User current_user;
	
	private JTextField tf_Reading;
	private JTextField tf_Rate;
	private JTextField tf_TotalPrice;
	private JPanel panel_Electricity_Consumption_Title;
	private JLabel lbl_Date;
	private JLabel lbl_Title_Electricity_Consumption;
	private JLabel lbl_Date_1;
	private JComboBox cB_Day;
	private JComboBox cB_Month;
	private JComboBox cB_Year;
	private JLabel lbl_Reading;
	private JLabel lbl_Rate;
	private JLabel lbl_TotalPrice;
	private JButton btn_Add;
	private JButton btn_Cancel;
	private JLabel lbl_Day;
	private JLabel lbl_Month;
	private JLabel lbl_Year;
	private Electricity_Panel electricitypanel;
	
	public Edit_Reading_Panel(Database_Manager database_manager, User current_user, Electricity_Panel utilitypanel) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		this.electricitypanel = utilitypanel;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 535);
		
		setTitle("Add Reading");
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
		
		lbl_Date = new JLabel("Date");
		lbl_Date.setBounds(236, 11, 170, 54);
		panel_Electricity_Consumption_Title.add(lbl_Date);
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		lbl_Title_Electricity_Consumption = new JLabel("Add New Reading");
		lbl_Title_Electricity_Consumption.setBounds(13, 20, 393, 54);
		panel_Electricity_Consumption_Title.add(lbl_Title_Electricity_Consumption);
		lbl_Title_Electricity_Consumption.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Electricity_Consumption.setFont(new Font("Tahoma", Font.PLAIN, 35));
		
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
		
		cB_Day = new JComboBox();
		cB_Day.setFont(new Font("Tahoma", Font.PLAIN, 17));
		for (int i = 1; i <= 31; i++) {
			cB_Day.addItem(i);
			}
		
		lbl_Year = new JLabel("Year");
		lbl_Year.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Year.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_Year.setBounds(306, 143, 114, 22);
		contentPane.add(lbl_Year);
		cB_Day.setBounds(10, 164, 120, 45);
		contentPane.add(cB_Day);
		
		cB_Month = new JComboBox();
		cB_Month.setFont(new Font("Tahoma", Font.PLAIN, 17));
		for (int i = 1; i <= 12; i++) {
			cB_Month.addItem(i);
			}
		cB_Month.setBounds(157, 164, 120, 45);
		contentPane.add(cB_Month);
		
		cB_Year = new JComboBox();
		cB_Year.setFont(new Font("Tahoma", Font.PLAIN, 17));
		for (int i = 1975; i <= 2025; i++) {
			cB_Year.addItem(i);
			}
		cB_Year.setBounds(306, 164, 120, 45);
		contentPane.add(cB_Year);
		
		lbl_Reading = new JLabel("Reading (kWh)");
		lbl_Reading.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Reading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Reading.setBounds(10, 216, 163, 22);
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
		tf_Reading.setBounds(10, 241, 416, 45);
		contentPane.add(tf_Reading);
		
		lbl_Rate = new JLabel("Rate (Php)");
		lbl_Rate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Rate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Rate.setBounds(10, 297, 141, 22);
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
		tf_Rate.setBounds(10, 321, 416, 45);
		contentPane.add(tf_Rate);
		
		lbl_TotalPrice = new JLabel("Total Price (Php)");
		lbl_TotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_TotalPrice.setBounds(10, 377, 192, 22);
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
		tf_TotalPrice.setBounds(10, 401, 416, 45);
		contentPane.add(tf_TotalPrice);
		
		btn_Add =  new JButton("Add");
		btn_Add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addReading();
			}
		});
		btn_Add.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Add.setBounds(335, 458, 91, 34);
		contentPane.add(btn_Add);
		
		btn_Cancel = new JButton("Cancel");
		btn_Cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancelAddReading();
			}
		});
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setBounds(234, 458, 91, 34);
		contentPane.add(btn_Cancel);
	}
	
	public void cancelAddReading() {
		String reading = tf_Reading.getText();
		String rate = tf_Rate.getText();
		String totalPrice = tf_TotalPrice.getText();
		LocalDate date = LocalDate.of((int)cB_Year.getSelectedItem(), (int)cB_Month.getSelectedItem(), (int)cB_Day.getSelectedItem());
		
		if (!reading.equals("Enter Reading") || !rate.equals("Enter Rate") || !totalPrice.equals("Total Price")) {
			int response = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm Cancel", javax.swing.JOptionPane.YES_NO_OPTION);
			if (response == javax.swing.JOptionPane.YES_OPTION) {
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
		LocalDate date = LocalDate.of((int)cB_Year.getSelectedItem(), (int)cB_Month.getSelectedItem(), (int)cB_Day.getSelectedItem());
		
		if (reading.equals("Enter Reading") || rate.equals("Enter Rate") || totalPrice.equals("Total Price")) {
			javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			double readingValue = Double.parseDouble(reading);
			double rateValue = Double.parseDouble(rate);
			double totalPriceValue = Double.parseDouble(totalPrice);
			
			if (readingValue < 0 || rateValue < 0 || totalPriceValue < 0) {
				javax.swing.JOptionPane.showMessageDialog(this, "Please enter positive values.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			database_manager.getReadingManager().addReading(current_user , date, "electricity", readingValue, rateValue, totalPriceValue);
			electricitypanel.Electricity_Panel_Refresh();
			javax.swing.JOptionPane.showMessageDialog(this, "Reading added successfully.", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			
		} catch (NumberFormatException e) {
			javax.swing.JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			javax.swing.JOptionPane.showMessageDialog(this, "Error adding reading: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
}

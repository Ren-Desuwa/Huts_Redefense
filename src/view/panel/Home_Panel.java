package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
import model.Reading;
import model.User;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;
	
	private JLabel lbl_Electricity_Reading_Value;
	private JLabel lbl_Water_Reading_Value;
	private JLabel lbl_Gas_Reading_Value;
	private JLabel lbl_OverAll_Reading_Value;
	private JPanel panel_Electricity_Graph;
	private JPanel panel_Water_Graph;
	private JPanel panel_Gas_Graph;
	private JPanel panel_behind1;
	private JPanel panel_behind2;
	private JPanel panel_behind3;
	private JPanel panel_Welcome_Title;
	private JPanel panel_Information;
	private JPanel panel_Electricity_Info;
	private JLabel lbl_Title_Electricity_Info;
	private JPanel panel_Gas_Info;
	private JPanel panel_Overall_Info;
	private JPanel panel_tips;
	private JLabel lblWater;
	private JLabel lbl_Gas;
	private JPanel panel_OverAll_Graph;
	private JLabel lblNewLabel_1;
	
	public Home_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
		panel_Welcome_Title = new JPanel();
		panel_Welcome_Title.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Welcome_Title.setBounds(21, 11, 944, 85);
		add(panel_Welcome_Title);
		panel_Welcome_Title.setLayout(null);
		
		JLabel lbl_Title_Welcome = new JLabel("Welcome");
		lbl_Title_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_Welcome.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Title_Welcome.setBounds(10, 0, 182, 87);
		panel_Welcome_Title.add(lbl_Title_Welcome);
		
		JLabel lbl_Username = new JLabel("User");
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lbl_Username.setBounds(202, 0, 206, 87);
		lbl_Username.setText(current_user.getUsername());
		panel_Welcome_Title.add(lbl_Username);
		
		JLabel lbl_Date = new JLabel("Date");
		lbl_Date.setVerticalAlignment(SwingConstants.TOP);
		lbl_Date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Date.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Date.setBounds(764, 11, 170, 54);
		lbl_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		panel_Welcome_Title.add(lbl_Date);
		
		panel_Electricity_Graph = new JPanel();
		panel_Electricity_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Electricity_Graph.setBounds(504, 157, 413, 365);
		add(panel_Electricity_Graph);
		panel_Electricity_Graph.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Electricity");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(160, 118, 112, 85);
		panel_Electricity_Graph.add(lblNewLabel);
		
		panel_Water_Graph = new JPanel();
		panel_Water_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Water_Graph.setBounds(504, 157, 413, 365);
		add(panel_Water_Graph);
		panel_Water_Graph.setLayout(null);
		
		lblWater = new JLabel("Water");
		lblWater.setBounds(138, 129, 74, 21);
		lblWater.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel_Water_Graph.add(lblWater);
		
		panel_Gas_Graph = new JPanel();
		panel_Gas_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Gas_Graph.setBounds(504, 157, 413, 365);
		add(panel_Gas_Graph);
		panel_Gas_Graph.setLayout(null);
		
		lbl_Gas = new JLabel("Gas");
		lbl_Gas.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Gas.setBounds(148, 138, 112, 85);
		panel_Gas_Graph.add(lbl_Gas);
		
		panel_OverAll_Graph = new JPanel();
		panel_OverAll_Graph.setLayout(null);
		panel_OverAll_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_OverAll_Graph.setBounds(504, 157, 413, 365);
		add(panel_OverAll_Graph);
		
		lblNewLabel_1 = new JLabel("OverAll");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(160, 118, 112, 85);
		panel_OverAll_Graph.add(lblNewLabel_1);
		
		panel_behind1 = new JPanel();
		panel_behind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind1.setBounds(520, 142, 413, 365);
		add(panel_behind1);
		
		panel_behind2 = new JPanel();
		panel_behind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind2.setBounds(536, 129, 413, 356);
		add(panel_behind2);
		
		panel_behind3 = new JPanel();
		panel_behind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind3.setBounds(552, 114, 413, 347);
		add(panel_behind3);
		
		panel_Information = new JPanel();
		panel_Information.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Information.setBounds(21, 114, 467, 408);
		add(panel_Information);
		panel_Information.setLayout(null);
		
		panel_Electricity_Info = new JPanel();
		panel_Electricity_Info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_Electricity_Graph.setVisible(true);
				panel_Water_Graph.setVisible(false);
				panel_Gas_Graph.setVisible(false);
			}
		});
		panel_Electricity_Info.setBounds(10, 28, 447, 77);
		panel_Information.add(panel_Electricity_Info);
		panel_Electricity_Info.setLayout(null);
		
		lbl_Title_Electricity_Info = new JLabel("Electricity");
		lbl_Title_Electricity_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_Electricity_Info.setBounds(10, 21, 156, 32);
		panel_Electricity_Info.add(lbl_Title_Electricity_Info);
		
		lbl_Electricity_Reading_Value = new JLabel();
		lbl_Electricity_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Electricity_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Electricity_Reading_Value.setBounds(259, 21, 100, 32);
		panel_Electricity_Info.add(lbl_Electricity_Reading_Value);
		
		JLabel lbl_Electricity_Reading_Unit = new JLabel("KwH");
		lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Electricity_Reading_Unit.setBounds(369, 22, 68, 32);
		panel_Electricity_Info.add(lbl_Electricity_Reading_Unit);
		
		JPanel panel_Water_Info = new JPanel();
		panel_Water_Info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_Electricity_Graph.setVisible(false);
				panel_Water_Graph.setVisible(true);
				panel_Gas_Graph.setVisible(false);
				panel_OverAll_Graph.setVisible(false);
			}
		});
		panel_Water_Info.setBounds(10, 116, 447, 77);
		panel_Information.add(panel_Water_Info);
		panel_Water_Info.setLayout(null);
		
		JLabel lbl_Title_Water_Info = new JLabel("Water");
		lbl_Title_Water_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_Water_Info.setBounds(10, 22, 156, 32);
		panel_Water_Info.add(lbl_Title_Water_Info);
		
		lbl_Water_Reading_Value = new JLabel();
		lbl_Water_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Water_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Water_Reading_Value.setBounds(261, 21, 100, 32);
		panel_Water_Info.add(lbl_Water_Reading_Value);
		
		JLabel lbl_Water_Reading_Unit = new JLabel("m³");
		lbl_Water_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Water_Reading_Unit.setBounds(369, 22, 68, 32);
		panel_Water_Info.add(lbl_Water_Reading_Unit);
		
		panel_Gas_Info = new JPanel();
		panel_Gas_Info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_Electricity_Graph.setVisible(false);
				panel_Water_Graph.setVisible(false);
				panel_Gas_Graph.setVisible(true);
				panel_OverAll_Graph.setVisible(false);
			}
		});
		panel_Gas_Info.setBounds(10, 204, 447, 77);
		panel_Information.add(panel_Gas_Info);
		panel_Gas_Info.setLayout(null);
		
		JLabel lbl_Title_Gas_Info = new JLabel("Gas");
		lbl_Title_Gas_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_Gas_Info.setBounds(10, 23, 156, 32);
		panel_Gas_Info.add(lbl_Title_Gas_Info);
		
		lbl_Gas_Reading_Value = new JLabel();
		lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Gas_Reading_Value.setBounds(259, 22, 100, 32);
		panel_Gas_Info.add(lbl_Gas_Reading_Value);
		
		JLabel lbl_Gas_Reading_Unit = new JLabel("Qty");
		lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Gas_Reading_Unit.setBounds(369, 23, 68, 32);
		panel_Gas_Info.add(lbl_Gas_Reading_Unit);
		
		panel_Overall_Info = new JPanel();
		panel_Overall_Info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_Electricity_Graph.setVisible(false);
				panel_Water_Graph.setVisible(false);
				panel_Gas_Graph.setVisible(false);
				panel_OverAll_Graph.setVisible(true);
			}
		});
		panel_Overall_Info.setBounds(10, 292, 447, 77);
		panel_Information.add(panel_Overall_Info);
		panel_Overall_Info.setLayout(null);
		
		JLabel lbl_Title_OverAll_Info = new JLabel("Overall Expenses");
		lbl_Title_OverAll_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_OverAll_Info.setBounds(10, 22, 260, 32);
		panel_Overall_Info.add(lbl_Title_OverAll_Info);
		
		lbl_OverAll_Reading_Value = new JLabel();
		lbl_OverAll_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_OverAll_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_OverAll_Reading_Value.setBounds(258, 21, 101, 32);
		panel_Overall_Info.add(lbl_OverAll_Reading_Value);
		
		JLabel lbl_OverAll_Reading_Unit = new JLabel("Php");
		lbl_OverAll_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_OverAll_Reading_Unit.setBounds(369, 22, 68, 32);
		panel_Overall_Info.add(lbl_OverAll_Reading_Unit);
		
		panel_tips = new JPanel();
		panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_tips.setBounds(21, 543, 944, 134);
		add(panel_tips);
		panel_tips.setLayout(null);
		
		JLabel lbl_Title_Tips = new JLabel("Money Saving Tips");
		lbl_Title_Tips.setBounds(10, 0, 243, 36);
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panel_tips.add(lbl_Title_Tips);
		
		JLabel lbl_Electricity_Tips = new JLabel("Electricity Tip - Replace traditional light bulbs with LED bulbs. They use up to 75% less energy and last much longer.");
		lbl_Electricity_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Electricity_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Electricity_Tips.setBounds(10, 43, 902, 29);
		panel_tips.add(lbl_Electricity_Tips);
		
		JLabel lbl_Water_Tips = new JLabel("Gas Tip - Lower your water heater temperature to 120°F to save energy while still providing comfortable hot water.");
		lbl_Water_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Water_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Water_Tips.setBounds(10, 104, 909, 21);
		panel_tips.add(lbl_Water_Tips);
		
		JLabel lblGas_Tips = new JLabel("Water Tip - Fix leaky faucets promptly. Even a small drip can waste several gallons of water per day.");
		lblGas_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lblGas_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblGas_Tips.setBounds(10, 77, 924, 21);
		panel_tips.add(lblGas_Tips);
		
		setupData();
	}
	
	public void setupData() {
		
		try {
			Reading electricity_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "Electricity");
			Reading water_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "Water");
			Reading gas_reading = database_manager.getReadingManager().getLatestReadingByType(current_user, "Gas");
			
			if (electricity_reading == null) {
				lbl_Electricity_Reading_Value.setText("No Data");
			} else {
				lbl_Electricity_Reading_Value.setText(String.valueOf(electricity_reading.getReading()));
			}
			
			if (water_reading == null) {
				lbl_Water_Reading_Value.setText("No Data");
			} else {
				lbl_Water_Reading_Value.setText(String.valueOf(water_reading.getReading()));
			}
			
			if (gas_reading == null) {
				lbl_Gas_Reading_Value.setText("No Data");
			} else {
				lbl_Gas_Reading_Value.setText(String.valueOf(gas_reading.getReading()));
			}
			
			if (electricity_reading == null || water_reading == null || gas_reading == null) {
				lbl_OverAll_Reading_Value.setText("No Data");
			} else {
				double total_price = electricity_reading.getTotal_Price() + water_reading.getTotal_Price() + gas_reading.getTotal_Price();
				lbl_Electricity_Reading_Value.setText(String.valueOf(total_price));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

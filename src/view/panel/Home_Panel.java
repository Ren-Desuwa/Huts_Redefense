package view.panel;

import javax.swing.JPanel;

import database.Database_Manager;
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

public class Home_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private User current_user;

	/**
	 * Create the panel.
	 */
	public Home_Panel(Database_Manager database_manager, User current_user) {
		this.database_manager = database_manager;
		this.current_user = current_user;
		setPreferredSize(new Dimension(986, 688));
		setLayout(null);
		
		JPanel panel_Electricity_Graph = new JPanel();
		panel_Electricity_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Electricity_Graph.setBounds(504, 157, 413, 365);
		add(panel_Electricity_Graph);
		
		JPanel panel_Water_Graph = new JPanel();
		panel_Water_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Water_Graph.setBounds(504, 157, 413, 313);
		add(panel_Water_Graph);
		
		JPanel panel_Gas_Graph = new JPanel();
		panel_Gas_Graph.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Gas_Graph.setBounds(504, 157, 413, 313);
		add(panel_Gas_Graph);
		
		JPanel panel_behind1 = new JPanel();
		panel_behind1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind1.setBounds(520, 142, 413, 365);
		add(panel_behind1);
		
		JPanel panel_behind2 = new JPanel();
		panel_behind2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind2.setBounds(536, 129, 413, 356);
		add(panel_behind2);
		
		JPanel panel_behind3 = new JPanel();
		panel_behind3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_behind3.setBounds(552, 114, 413, 347);
		add(panel_behind3);
		
		JPanel panel_Welcome_Title = new JPanel();
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
		
		JPanel panel_Information = new JPanel();
		panel_Information.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Information.setBounds(21, 114, 467, 408);
		add(panel_Information);
		panel_Information.setLayout(null);
		
		JPanel panel_Electricity_Info = new JPanel();
		panel_Electricity_Info.setBounds(10, 28, 447, 77);
		panel_Information.add(panel_Electricity_Info);
		panel_Electricity_Info.setLayout(null);
		
		JLabel lbl_Title_Electricity_Info = new JLabel("Electricity");
		lbl_Title_Electricity_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_Electricity_Info.setBounds(10, 21, 156, 32);
		panel_Electricity_Info.add(lbl_Title_Electricity_Info);
		
		JLabel lbl_Electricity_Readingg_Value = new JLabel("279");
		lbl_Electricity_Readingg_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Electricity_Readingg_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Electricity_Readingg_Value.setBounds(291, 21, 68, 32);
		panel_Electricity_Info.add(lbl_Electricity_Readingg_Value);
		
		JLabel lbl_Electricity_Reading_Unit = new JLabel("KwH");
		lbl_Electricity_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Electricity_Reading_Unit.setBounds(369, 22, 68, 32);
		panel_Electricity_Info.add(lbl_Electricity_Reading_Unit);
		
		JPanel panel_Water_Info = new JPanel();
		panel_Water_Info.setBounds(10, 116, 447, 77);
		panel_Information.add(panel_Water_Info);
		panel_Water_Info.setLayout(null);
		
		JLabel lbl_Title_Water_Info = new JLabel("Water");
		lbl_Title_Water_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_Water_Info.setBounds(10, 22, 156, 32);
		panel_Water_Info.add(lbl_Title_Water_Info);
		
		JLabel lbl_Water_Reading_Value = new JLabel("52");
		lbl_Water_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Water_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Water_Reading_Value.setBounds(291, 21, 68, 32);
		panel_Water_Info.add(lbl_Water_Reading_Value);
		
		JLabel lbl_Water_Readingg_Unit = new JLabel("m³");
		lbl_Water_Readingg_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Water_Readingg_Unit.setBounds(369, 22, 68, 32);
		panel_Water_Info.add(lbl_Water_Readingg_Unit);
		
		JPanel panel_Gas_Info = new JPanel();
		panel_Gas_Info.setBounds(10, 204, 447, 77);
		panel_Information.add(panel_Gas_Info);
		panel_Gas_Info.setLayout(null);
		
		JLabel lbl_Title_Gas_Info = new JLabel("Gas");
		lbl_Title_Gas_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_Gas_Info.setBounds(10, 23, 156, 32);
		panel_Gas_Info.add(lbl_Title_Gas_Info);
		
		JLabel lbl_Gas_Reading_Value = new JLabel("2");
		lbl_Gas_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Gas_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Gas_Reading_Value.setBounds(291, 22, 68, 32);
		panel_Gas_Info.add(lbl_Gas_Reading_Value);
		
		JLabel lbl_Gas_Reading_Unit = new JLabel("Qty");
		lbl_Gas_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_Gas_Reading_Unit.setBounds(369, 23, 68, 32);
		panel_Gas_Info.add(lbl_Gas_Reading_Unit);
		
		JPanel panel_Overall_Info = new JPanel();
		panel_Overall_Info.setBounds(10, 292, 447, 77);
		panel_Information.add(panel_Overall_Info);
		panel_Overall_Info.setLayout(null);
		
		JLabel lbl_Title_OverAll_Info = new JLabel("Overall Expenses");
		lbl_Title_OverAll_Info.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lbl_Title_OverAll_Info.setBounds(10, 22, 260, 32);
		panel_Overall_Info.add(lbl_Title_OverAll_Info);
		
		JLabel lbl_OverAll_Reading_Value = new JLabel("6000");
		lbl_OverAll_Reading_Value.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_OverAll_Reading_Value.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_OverAll_Reading_Value.setBounds(291, 21, 68, 32);
		panel_Overall_Info.add(lbl_OverAll_Reading_Value);
		
		JLabel lbl_OverAll_Reading_Unit = new JLabel("Php");
		lbl_OverAll_Reading_Unit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_OverAll_Reading_Unit.setBounds(369, 22, 68, 32);
		panel_Overall_Info.add(lbl_OverAll_Reading_Unit);
		
		JPanel panel_tips = new JPanel();
		panel_tips.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_tips.setBounds(21, 543, 944, 134);
		add(panel_tips);
		panel_tips.setLayout(null);
		
		JLabel lbl_Title_Tips = new JLabel("Money Saving Tips");
		lbl_Title_Tips.setBounds(10, 0, 243, 36);
		lbl_Title_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Title_Tips.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panel_tips.add(lbl_Title_Tips);
		
		JLabel lbl_Electricity_Tips = new JLabel("- Save Energy");
		lbl_Electricity_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Electricity_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Electricity_Tips.setBounds(10, 47, 243, 21);
		panel_tips.add(lbl_Electricity_Tips);
		
		JLabel lbl_Water_Tips = new JLabel("- Save Energy");
		lbl_Water_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Water_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_Water_Tips.setBounds(10, 69, 243, 21);
		panel_tips.add(lbl_Water_Tips);
		
		JLabel lblGas_Tips = new JLabel("- Save Energy");
		lblGas_Tips.setHorizontalAlignment(SwingConstants.LEFT);
		lblGas_Tips.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblGas_Tips.setBounds(10, 91, 243, 21);
		panel_tips.add(lblGas_Tips);
		

	}
}

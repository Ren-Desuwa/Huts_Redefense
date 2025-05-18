package view.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;
import view.Main_Frame;

public class New_Password_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private User current_user;
	private Database_Manager database_manager;
	
	private JPanel contentPane;
	private JPasswordField pf_Password;
	private JPasswordField pf_ConfirmPassword;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;

	public New_Password_Window(Database_Manager database_manager, User current_user) {
		this.current_user = current_user;
		this.database_manager = database_manager;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn = new JButton("Change Password");
		btn.setBounds(285, 385, 138, 44);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btn);
		
		pf_Password = new JPasswordField();
		pf_Password.setBounds(268, 200, 171, 44);
		contentPane.add(pf_Password);
		
		pf_ConfirmPassword = new JPasswordField();
		pf_ConfirmPassword.setBounds(268, 278, 171, 44);
		contentPane.add(pf_ConfirmPassword);
		
		lblNewLabel_1 = new JLabel("Forgot Password");
		lblNewLabel_1.setBounds(267, 47, 171, 46);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_4 = new JLabel("New Password");
		lblNewLabel_4.setBounds(247, 175, 97, 14);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Confirm Password");
		lblNewLabel_5.setBounds(247, 253, 97, 14);
		contentPane.add(lblNewLabel_5);
	}

}

package view.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class Retrive_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private JPanel contentPane;

	private JTextField tf_Username;
	private JTextField tf_Email;
	private JLabel lbl_Title_Forgot_Password;
	private JLabel lbl_Username;
	private JLabel lbl_Email;

	public Retrive_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		
		setTitle("Forgot Password");
		setResizable(false);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_Title_Forgot_Password = new JLabel("Forgot Password");
		lbl_Title_Forgot_Password.setBounds(267, 47, 171, 46);
		lbl_Title_Forgot_Password.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Title_Forgot_Password.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Title_Forgot_Password);
		
		lbl_Username = new JLabel("Username");
		lbl_Username.setBounds(246, 176, 97, 14);
		contentPane.add(lbl_Username);
		
		tf_Username = new JTextField();
		tf_Username.setText("Enter Username");
		tf_Username.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tf_Username.getText().equals("Enter Username")) {
					tf_Username.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tf_Username.getText().isEmpty()) {
					tf_Username.setText("Enter Username");
				}
			}
		});
		tf_Username.setBounds(267, 201, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		lbl_Email = new JLabel("Email");
		lbl_Email.setBounds(246, 252, 97, 14);
		contentPane.add(lbl_Email);
		
		tf_Email = new JTextField();
		tf_Email.setText("Enter Email");
		tf_Email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tf_Email.getText().equals("Enter Email")) {
					tf_Email.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (tf_Email.getText().isEmpty()) {
					tf_Email.setText("Enter Email");
				}
			}
		});
		tf_Email.setBounds(267, 277, 171, 44);
		tf_Email.setColumns(10);
		contentPane.add(tf_Email);
			
		JButton btn_Confirm = new JButton("Confirm");
		btn_Confirm.setBounds(300, 385, 109, 44);
		btn_Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoConfirmation();
			}
		});
		contentPane.add(btn_Confirm);
	}
	private void gotoConfirmation() {
		String username = tf_Username.getText();
		String email = tf_Email.getText();
		
		if (username.isEmpty() || email.isEmpty()) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (username.equals("Enter Username") || email.equals("Enter Email")) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		try {
			if (!database_manager.getUserManager().UsernamePasswordMatch(username, email)) {
				System.out.println("Username and email do not match.");
			}
			
			User current_user = database_manager.getUserManager().getUserByUsername(username);
			
			this.dispose();
			
			New_Password_Window newpassword = new New_Password_Window(database_manager, current_user);
			newpassword.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

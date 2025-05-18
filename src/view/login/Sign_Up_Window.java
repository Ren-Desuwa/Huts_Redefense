package view.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sign_Up_Window extends JFrame {
	
	private Database_Manager database_manager;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Username;
	private JTextField tf_Email;
	private JPasswordField pf_Password;
	private JPasswordField pf_ConfirmPassword;
	private JLabel lbl_Title_Sign_Up;
	private JLabel lbl_Username;
	private JLabel lbl_Email;
	private JLabel lbl_Password;
	private JLabel lbl_Confirm_Password;
	
	public Sign_Up_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_Title_Sign_Up = new JLabel("Sign Up");
		lbl_Title_Sign_Up.setBounds(285, 47, 138, 46);
		lbl_Title_Sign_Up.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Title_Sign_Up.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Title_Sign_Up);
		
		lbl_Username = new JLabel("Username");
		lbl_Username.setBounds(246, 123, 97, 14);
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
		tf_Username.setBounds(267, 148, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		lbl_Email = new JLabel("Email");
		lbl_Email.setBounds(246, 199, 97, 14);
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
		tf_Email.setBounds(267, 224, 171, 44);
		tf_Email.setColumns(10);
		contentPane.add(tf_Email);
		
		lbl_Password = new JLabel("Password");
		lbl_Password.setBounds(246, 275, 97, 14);
		contentPane.add(lbl_Password);
		
		pf_Password = new JPasswordField();
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (String.valueOf(pf_Password.getPassword()).equals("Enter Password")) {
					pf_Password.setText("");
					pf_Password.setEchoChar('\u2022'); // Bullet character
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (pf_Password.getPassword().length == 0) {
					pf_Password.setText("Enter Password");
					pf_Password.setEchoChar((char) 0); // Show text again
				}
			}
		});
		pf_Password.setBounds(267, 300, 171, 44);
		contentPane.add(pf_Password);
		
		lbl_Confirm_Password = new JLabel("Confirm Password");
		lbl_Confirm_Password.setBounds(246, 353, 97, 14);
		contentPane.add(lbl_Confirm_Password);
		
		pf_ConfirmPassword = new JPasswordField();
		pf_ConfirmPassword.setText("Confirm Password");
		pf_ConfirmPassword.setEchoChar((char) 0);
		pf_ConfirmPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (String.valueOf(pf_ConfirmPassword.getPassword()).equals("Confirm Password")) {
					pf_ConfirmPassword.setText("");
					pf_ConfirmPassword.setEchoChar('\u2022'); // Bullet character
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (pf_ConfirmPassword.getPassword().length == 0) {
					pf_ConfirmPassword.setText("Confirm Password");
					pf_ConfirmPassword.setEchoChar((char) 0); // Show text again
				}
			}
		});
		pf_ConfirmPassword.setBounds(267, 378, 171, 44);
		contentPane.add(pf_ConfirmPassword);
		
		JButton btn_Sign_Up = new JButton("Sign Up");
		btn_Sign_Up.setBounds(300, 463, 109, 44);
		btn_Sign_Up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp();
			}
		});
		contentPane.add(btn_Sign_Up);
		
		JLabel lbl_Login = new JLabel("Log in");
		lbl_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goToLogIn();
			}
		});
		lbl_Login.setBounds(321, 518, 70, 25);
		lbl_Login.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Login);
	}
	
	private void goToLogIn() {
		// Close the current window
		this.dispose();
		
		// Open the Sign In window
		Log_In_Window LogInWindow = new Log_In_Window(database_manager);
		LogInWindow.setVisible(true);
	}
	
	private void SignUp() {
		String username = tf_Username.getText();
		String email = tf_Email.getText();
		String password = String.valueOf(pf_Password.getPassword());
		String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
		
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (username.equals("Enter Username") || email.equals("Enter Email") || password.equals("Enter Password") || confirmPassword.equals("Confirm Password")) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			System.out.println("Passwords do not match.");
			return;
		}
		
		try {
			// Add user to the database
			database_manager.getUserManager().addUser(username, password, email);
			System.out.println("User registered successfully.");
			
			// Optionally, you can close the window or redirect to another page
			this.dispose();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error registering user.");
		}
	}
}

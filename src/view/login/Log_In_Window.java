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
import model.User;
import view.Main_Frame;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.Color;

public class Log_In_Window extends JFrame {

	private Database_Manager database_manager;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Username;
	private JPasswordField pf_Password;
	private JLabel lbl_Title_Log_In;
	private JLabel lbl_Username;
	private JLabel lbl_Password;
	private JLabel lbl_Forgot_Password;
	private JLabel lbl_Incorrect_Signage1;
	private JLabel lbl_Incorrect_Signaage2;

	public Log_In_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		
		setTitle("Log In");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_Title_Log_In = new JLabel("Log In");
		lbl_Title_Log_In.setBounds(285, 47, 138, 46);
		lbl_Title_Log_In.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Title_Log_In.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Title_Log_In);
		
		lbl_Username = new JLabel("Username");
		lbl_Username.setBounds(248, 129, 97, 14);
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
		tf_Username.setBounds(269, 154, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		lbl_Password = new JLabel("Password");
		lbl_Password.setBounds(248, 209, 97, 14);
		contentPane.add(lbl_Password);
		
		pf_Password = new JPasswordField();
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (String.valueOf(pf_Password.getPassword()).equals("Enter Password")) {
					pf_Password.setText("");
					pf_Password.setEchoChar('\u2022');
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
		pf_Password.setBounds(269, 234, 171, 44);
		contentPane.add(pf_Password);
		
		lbl_Forgot_Password = new JLabel("Forgot Password?");
		lbl_Forgot_Password.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbl_Forgot_Password.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_Forgot_Password.setText("<html><u>Forgot Password?</u></html>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_Forgot_Password.setText("Forgot Password?");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				gotoForgotPassword();
			}
		});
		lbl_Forgot_Password.setBounds(356, 289, 157, 14);
		lbl_Forgot_Password.setVisible(false); // Hide initially
		contentPane.add(lbl_Forgot_Password);
		
		JButton btn = new JButton("Log In");
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login();
			}
		});
		btn.setBounds(301, 331, 109, 44);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		contentPane.add(btn);
		
		JLabel lbl_SignUp = new JLabel("Sign Up");
		lbl_SignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goToSignUp();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_SignUp.setText("<html><u>Sign Up</u></html>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_SignUp.setText("Sign Up");
			}
		});
		lbl_SignUp.setBounds(322, 386, 70, 25);
		lbl_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_SignUp);
		
		lbl_Incorrect_Signage1 = new JLabel("*");
		lbl_Incorrect_Signage1.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signage1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage1.setBounds(440, 220, 61, 25);
		lbl_Incorrect_Signage1.setVisible(false); // Hide initially
		contentPane.add(lbl_Incorrect_Signage1);
		
		lbl_Incorrect_Signaage2 = new JLabel("*");
		lbl_Incorrect_Signaage2.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signaage2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signaage2.setBounds(440, 140, 61, 25);
		lbl_Incorrect_Signaage2.setVisible(false); // Hide initially
		contentPane.add(lbl_Incorrect_Signaage2);
		
	}
	private void goToSignUp() {
		// Close the current window
		this.dispose();
		
		// Open the Sign Up window
		Sign_Up_Window SignUpWindow = new Sign_Up_Window(database_manager);
		SignUpWindow.setVisible(true);
	}
	
	private void Login() {
		String username = tf_Username.getText();
		String password = String.valueOf(pf_Password.getPassword());
		
		if (username.isEmpty() || password.isEmpty()) {
			System.out.println("Please fill in all fields.");
			lbl_Incorrect_Signage1.setVisible(true); // Show label
			lbl_Incorrect_Signaage2.setVisible(true); // Show label
			return;
		}
		
		if (username.equals("Enter Username") || password.equals("Enter Password")) {
			System.out.println("Please fill in all fields.");
			lbl_Incorrect_Signage1.setVisible(true); // Show label
			lbl_Incorrect_Signaage2.setVisible(true); // Show label
			return;
		}
		
		try {
			if (!database_manager.getUserManager().UsernamePasswordMatch(username, password)) {
				System.out.println("Passwords do not match.");
				lbl_Forgot_Password.setVisible(true); // Show label
				lbl_Incorrect_Signage1.setVisible(true); // Show label
				lbl_Incorrect_Signaage2.setVisible(true); // Show label
				return;
			}
			System.out.println("User Log In successfully.");
			
			User current_user = database_manager.getUserManager().getUserByUsername(username);
			
			this.dispose();
			
			Main_Frame mainframe = new Main_Frame(database_manager, current_user);
			mainframe.setVisible(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void gotoForgotPassword() {
		// Close the current window
		this.dispose();
		
		// Open the Forgot Password window
		Retrive_Window retrivewindow = new Retrive_Window(database_manager);
		retrivewindow.setVisible(true);
	}
}

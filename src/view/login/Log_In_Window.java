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
import java.awt.EventQueue;

import visuals.Rounded_Button;
import visuals.Rounded_Panel;

public class Log_In_Window extends JFrame {

	private Database_Manager database_manager;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Username;
	private JPasswordField pf_Password;
	private JLabel lbl_Username;
	private JLabel lbl_Password;
	private JLabel lbl_Forgot_Password;
	private JLabel lbl_Incorrect_Signage1;
	private JLabel lbl_Incorrect_Signaage2;
	private Rounded_Panel panel_LogIn_Title;
	private JLabel lbl_Title_LogIn;
	private JButton btn;
	private JLabel lbl_SignUp;

	public Log_In_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		
		setTitle("Log In");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 450, 620);
		setBackground(new Color(213, 213, 213));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(213, 213, 213));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_LogIn_Title = new Rounded_Panel();
		panel_LogIn_Title.setLayout(null);
		panel_LogIn_Title.setBackground(Color.WHITE);
		panel_LogIn_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_LogIn_Title);
		
		lbl_Title_LogIn = new JLabel("Log In");
		lbl_Title_LogIn.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_LogIn.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbl_Title_LogIn.setBounds(13, 20, 393, 54);
		panel_LogIn_Title.add(lbl_Title_LogIn);
		
		lbl_Username = new JLabel("Username");
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Username.setBounds(10, 181, 114, 22);
		contentPane.add(lbl_Username);
							
		tf_Username = new JTextField();
		tf_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
		tf_Username.setBounds(20, 214, 396, 45);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		lbl_Password = new JLabel("Password");
		lbl_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Password.setBounds(10, 270, 114, 22);
		contentPane.add(lbl_Password);
		
		pf_Password = new JPasswordField();
		pf_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
		pf_Password.setBounds(20, 303, 396, 45);
		contentPane.add(pf_Password);
		
		lbl_Forgot_Password = new JLabel("Forgot Password?");
		lbl_Forgot_Password.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Forgot_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
				openForgotPassword();
			}
		});
		lbl_Forgot_Password.setBounds(269, 359, 147, 22);
		lbl_Forgot_Password.setVisible(false); // Hide initially
		contentPane.add(lbl_Forgot_Password);
		
		btn = new Rounded_Button("Log In", 25);
		btn.setBackground(new Color(182, 182, 182));
		btn.setForeground(Color.BLACK);
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(new Color(150, 150, 150));
				btn.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBackground(new Color(182, 182, 182));
				btn.setForeground(Color.BLACK);
			}
		});
		btn.setBounds(160, 492, 109, 44);
		btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(btn);
		
		lbl_SignUp = new JLabel("Sign Up");
		lbl_SignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_SignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openSignUp();
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
		lbl_SignUp.setBounds(181, 547, 70, 25);
		lbl_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_SignUp);
		
		lbl_Incorrect_Signage1 = new JLabel("*");
		lbl_Incorrect_Signage1.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signage1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage1.setBounds(403, 287, 23, 25);
		lbl_Incorrect_Signage1.setVisible(false); // Hide initially
		contentPane.add(lbl_Incorrect_Signage1);
		
		lbl_Incorrect_Signaage2 = new JLabel("*");
		lbl_Incorrect_Signaage2.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signaage2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signaage2.setBounds(403, 197, 23, 25);
		lbl_Incorrect_Signaage2.setVisible(false); // Hide initially
		contentPane.add(lbl_Incorrect_Signaage2);
		
	}
	private void openSignUp() {
	    
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Log_In_Window.this.dispose();
	                Sign_Up_Window SignUpWindow = new Sign_Up_Window(database_manager);
	                SignUpWindow.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
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
			
		    
		    EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            try {
		                Log_In_Window.this.dispose();
		                Main_Frame mainFrame = new Main_Frame(database_manager, current_user);
		                mainFrame.setVisible(true);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    });
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void openForgotPassword() {
	    
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Log_In_Window.this.dispose();
	                Retrive_Window retrive_window = new Retrive_Window(database_manager);
	                retrive_window.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
}

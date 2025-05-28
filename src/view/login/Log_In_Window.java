package view.login;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.awt.Color;
import java.awt.EventQueue;

import visuals.RoundedButton;
import visuals.RoundedPanel;

/**
 * A window for user login
 * This window allows users to log in to the application
 */
public class Log_In_Window extends JFrame {

	// Database and user fields
	private Database_Manager database_manager;
	
	// Serial version UID
	private static final long serialVersionUID = 1L;
	
	// Panel configuration
	private JPanel contentPane;
	private RoundedPanel panel_LogIn_Title;
	private JLabel lbl_Title_LogIn;
	
	// Input fields
	private JTextField tf_Username;
	private JPasswordField pf_Password;
	
	// Labels
	private JLabel lbl_Username;
	private JLabel lbl_Password;
	private JLabel lbl_Forgot_Password;
	private JLabel lbl_Incorrect_Signage1;
	private JLabel lbl_Incorrect_Signage2;
	private JLabel lbl_SignUp;
	
	// Buttons
	private JButton btn;

	public Log_In_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		setTitle("Log In");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 450, 620);
		setBackground(new Color(213, 213, 213));

		initialize_UI_Components();
		create_Action_Listeners();
	}
	
	private void initialize_UI_Components() {
		
		//==============================================================================================
		// UI CREATION - MAIN CONTENT PANE
		//==============================================================================================
		
		// Create the main content pane with a light gray background
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(213, 213, 213));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//==============================================================================================
		// UI CREATION - TITLE SECTION
		//==============================================================================================
		
		// Create a rounded panel for the title section
		panel_LogIn_Title = new RoundedPanel();
		panel_LogIn_Title.setLayout(null);
		panel_LogIn_Title.setBackground(Color.WHITE);
		panel_LogIn_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_LogIn_Title);
		
		// Create and configure the title label
		lbl_Title_LogIn = new JLabel("Log In");
		lbl_Title_LogIn.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_LogIn.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbl_Title_LogIn.setBounds(13, 20, 393, 54);
		panel_LogIn_Title.add(lbl_Title_LogIn);
		
		//==============================================================================================
		// UI CREATION - USERNAME SECTION
		//==============================================================================================
		
		// Create and configure the username label and text field
		lbl_Username = new JLabel("Username");
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Username.setBounds(10, 181, 114, 22);
		contentPane.add(lbl_Username);
							
		// Create and configure the username text field
		tf_Username = new JTextField();
		tf_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Username.setText("Enter Username");
		tf_Username.setBounds(20, 214, 396, 45);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		//==============================================================================================
		// UI CREATION - PASSWORD SECTION
		//==============================================================================================
		
		// Create and configure the password label and field
		lbl_Password = new JLabel("Password");
		lbl_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Password.setBounds(10, 270, 114, 22);
		contentPane.add(lbl_Password);
		
		// Create and configure the password field
		pf_Password = new JPasswordField();
		pf_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.setBounds(20, 303, 396, 45);
		contentPane.add(pf_Password);
		
		//==============================================================================================
		// UI CREATION - FORGOT PASSWORD
		//==============================================================================================
		
		// Create and configure the forgot password label
		lbl_Forgot_Password = new JLabel("Forgot Password?");
		lbl_Forgot_Password.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Forgot_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_Forgot_Password.setBounds(269, 359, 147, 22);
		lbl_Forgot_Password.setVisible(false); // Hide initially
		contentPane.add(lbl_Forgot_Password);
		
		//==============================================================================================
		// UI CREATION - LOGIN BUTTON
		//==============================================================================================
		
		// Create and configure the login button
		btn = new RoundedButton("Log In", 25);
		btn.setBackground(new Color(182, 182, 182));
		btn.setForeground(Color.BLACK);
		btn.setBounds(160, 492, 109, 44);
		btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(btn);
		
		//==============================================================================================
		// UI CREATION - SIGN UP LINK
		//==============================================================================================
		
		// Create and configure the sign-up label
		lbl_SignUp = new JLabel("Sign Up");
		lbl_SignUp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_SignUp.setBounds(181, 547, 70, 25);
		lbl_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_SignUp);
		
		//==============================================================================================
		// UI CREATION - ERROR INDICATORS
		//==============================================================================================
		
		lbl_Incorrect_Signage1 = new JLabel("*");
		lbl_Incorrect_Signage1.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signage1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage1.setBounds(403, 287, 23, 25);
		lbl_Incorrect_Signage1.setVisible(false); // Hide initially
		contentPane.add(lbl_Incorrect_Signage1);
		
		lbl_Incorrect_Signage2 = new JLabel("*");
		lbl_Incorrect_Signage2.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signage2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage2.setBounds(403, 197, 23, 25);
		lbl_Incorrect_Signage2.setVisible(false); // Hide initially
		contentPane.add(lbl_Incorrect_Signage2);
	}
	
	private void create_Action_Listeners() {
		//==============================================================================================
		// FOCUS LISTENERS - USERNAME FIELD
		//==============================================================================================
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
		
		//==============================================================================================
		// FOCUS LISTENERS - PASSWORD FIELD
		//==============================================================================================
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
		
		//==============================================================================================
		// MOUSE LISTENERS - FORGOT PASSWORD LINK
		//==============================================================================================
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
		
		//==============================================================================================
		// MOUSE LISTENERS - LOGIN BUTTON
		//==============================================================================================
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(new Color(150, 150, 150));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBackground(new Color(182, 182, 182));
			}
		});
		
		//==============================================================================================
		// MOUSE LISTENERS - SIGN UP LINK
		//==============================================================================================
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
	}
	
	private void Login() {
		String username = tf_Username.getText();
		String password = String.valueOf(pf_Password.getPassword());
		
		// Validate inputs
		if (username.isEmpty() || password.isEmpty()) {
			lbl_Incorrect_Signage1.setVisible(true); // Show label
			lbl_Incorrect_Signage2.setVisible(true); // Show label
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Check for placeholder text
		if (username.equals("Enter Username") || password.equals("Enter Password")) {
			lbl_Incorrect_Signage1.setVisible(true); // Show label
			lbl_Incorrect_Signage2.setVisible(true); // Show label
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!database_manager.getUserManager().UsernamePasswordMatch(username, password)) {
			lbl_Forgot_Password.setVisible(true); // Show label
			lbl_Incorrect_Signage1.setVisible(true); // Show label
			lbl_Incorrect_Signage2.setVisible(true); // Show label
			JOptionPane.showMessageDialog(this, "Incorrect username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Get user and navigate to main frame
		User current_user = database_manager.getUserManager().getUserByUsername(username);
		JOptionPane.showMessageDialog(this, "Welcome " + current_user.getUsername() + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
		
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

	private void openForgotPassword() {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Log_In_Window.this.dispose();
	                Retrieve_Window retrieve_window = new Retrieve_Window(database_manager);
	                retrieve_window.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
}
/*
 * File: Log_In_Window.java
 *
 * Description:
 * This file defines the `Log_In_Window` class, which is a `JFrame` used for user authentication.
 * It provides a graphical interface for users to log in to the application by entering their username and password.
 * The class interacts with the `Database_Manager` to validate user credentials and navigate to the main application window upon successful login.
 *
 * Variables:
 *
 * - **Database and User Fields**:
 *   - `database_manager` (Database_Manager): Manages database operations, including user-related actions.
 *
 * - **UI Components**:
 *   - `contentPane` (JPanel): The main container for the window's components.
 *   - `panel_LogIn_Title` (Rounded_Panel): A rounded panel for displaying the title of the login window.
 *   - `lbl_Title_LogIn` (JLabel): Displays the title "Log In" at the top of the window.
 *   - `lbl_Username`, `lbl_Password` (JLabel): Labels for the username and password input fields.
 *   - `tf_Username` (JTextField): Input field for the user's username.
 *   - `pf_Password` (JPasswordField): Input field for the user's password.
 *   - `lbl_Forgot_Password` (JLabel): A clickable label for navigating to the password retrieval window.
 *   - `lbl_SignUp` (JLabel): A clickable label for navigating to the sign-up window.
 *   - `lbl_Incorrect_Signage1`, `lbl_Incorrect_Signage2` (JLabel): Labels to indicate validation errors for specific fields.
 *   - `btn` (Rounded_Button): Button to submit the login form.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Log_In_Window(Database_Manager)`:
 *      - Initializes the login window with the provided database manager.
 *      - Calls `initialize_UI_Components()` to set up the UI and `create_Action_Listeners()` to add event listeners.
 *
 * 2. **initialize_UI_Components()**:
 *    - Configures the layout and properties of the login window.
 *    - Creates and positions all UI components, including labels, text fields, and buttons.
 *    - Organizes the components into sections for title, username, password, and navigation links.
 *
 * 3. **create_Action_Listeners()**:
 *    - Adds event listeners to handle user interactions with the input fields, buttons, and labels.
 *    - Handles focus events for input fields to manage placeholder text.
 *    - Handles mouse events for the "Log In" button, "Forgot Password?" label, and "Sign Up" label.
 *
 * 4. **Login()**:
 *    - Validates the input fields and authenticates the user credentials.
 *    - Key conditions:
 *      - Checks if any input field is empty or contains placeholder text and displays an error message if true.
 *      - If the username and password do not match, displays an error message and shows the "Forgot Password?" label.
 *    - If authentication is successful:
 *      - Retrieves the user from the database.
 *      - Displays a success message and navigates to the `Main_Frame` for the authenticated user.
 *
 * 5. **openSignUp()**:
 *    - Navigates to the `Sign_Up_Window` when the "Sign Up" label is clicked.
 *
 * 6. **openForgotPassword()**:
 *    - Navigates to the `Retrieve_Window` when the "Forgot Password?" label is clicked.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for logging in to the application.
 * It ensures that the input data is validated before authenticating the user and provides feedback in case of errors or success.
 */

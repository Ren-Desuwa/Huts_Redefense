package view.login;

import java.awt.Color;
import java.awt.EventQueue;
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

import visuals.RoundedButton;
import visuals.RoundedPanel;

public class Sign_Up_Window extends JFrame {
	
	// Database and user fields
	private Database_Manager database_manager;

	// Serial version UID
	private static final long serialVersionUID = 1L;
	
	// Panel configuration
	private JPanel contentPane;
	private RoundedPanel panel_SignUp_Title;
	private JLabel lbl_Title_SignUp;
	
	// Input fields
	private JTextField tf_Username;
	private JTextField tf_Email;
	private JPasswordField pf_Password;
	private JPasswordField pf_ConfirmPassword;
	
	// Labels
	private JLabel lbl_Username;
	private JLabel lbl_Email;
	private JLabel lbl_Password;
	private JLabel lbl_Confirm_Password;
	private JLabel lbl_Login;
	
	// Buttons
	private JButton btn_Sign_Up;
	private JLabel lbl_Incorrect_Signage3;
	private JLabel lbl_Incorrect_Signage2;
	private JLabel lbl_Incorrect_Signage1;
	private JLabel lbl_Incorrect_Signage4;
	
	public Sign_Up_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		
		initialize_Window_Properties();
		initialize_UI_Components();
		create_Action_Listeners();
	}
	
	private void initialize_Window_Properties() {
		setTitle("Sign Up");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 450, 620);
		setBackground(new Color(213, 213, 213));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(213, 213, 213));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void initialize_UI_Components() {
		//==============================================================================================
		// UI CREATION - TITLE SECTION
		//==============================================================================================
		
		// Create a rounded panel for the title
		panel_SignUp_Title = new RoundedPanel();
		panel_SignUp_Title.setLayout(null);
		panel_SignUp_Title.setBackground(Color.WHITE);
		panel_SignUp_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_SignUp_Title);
		
		// Create and configure the title label
		lbl_Title_SignUp = new JLabel("Sign Up");
		lbl_Title_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_SignUp.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbl_Title_SignUp.setBounds(13, 20, 393, 54);
		panel_SignUp_Title.add(lbl_Title_SignUp);
		
		//==============================================================================================
		// UI CREATION - USERNAME SECTION
		//==============================================================================================
		
		// Create and configure the username label and text field
		lbl_Username = new JLabel("Username");
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Username.setBounds(12, 119,114, 22);
		contentPane.add(lbl_Username);
		
		// Create and configure the username text field
		tf_Username = new JTextField();
		tf_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Username.setText("Enter Username");
		tf_Username.setBounds(22, 151, 396, 45);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		//==============================================================================================
		// UI CREATION - EMAIL SECTION
		//==============================================================================================
		
		// Create and configure the email label and text field
		lbl_Email = new JLabel("Email");
		lbl_Email.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Email.setBounds(12, 207, 114, 22);
		contentPane.add(lbl_Email);
		
		// Create and configure the email text field
		tf_Email = new JTextField();
		tf_Email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Email.setText("Enter Email");
		tf_Email.setBounds(22, 240, 396, 45);
		tf_Email.setColumns(10);
		contentPane.add(tf_Email);
		
		//==============================================================================================
		// UI CREATION - PASSWORD SECTION
		//==============================================================================================
		
		// Create and configure the password label and password field
		lbl_Password = new JLabel("Password");
		lbl_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Password.setBounds(12, 296, 114, 22);
		contentPane.add(lbl_Password);
		
		// Create and configure the password field
		pf_Password = new JPasswordField();
		pf_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.setBounds(22, 329, 396, 45);
		contentPane.add(pf_Password);
		
		//==============================================================================================
		// UI CREATION - CONFIRM PASSWORD SECTION
		//==============================================================================================
		
		// Create and configure the confirm password label and password field
		lbl_Confirm_Password = new JLabel("Confirm Password");
		lbl_Confirm_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Confirm_Password.setBounds(12, 385, 212, 22);
		contentPane.add(lbl_Confirm_Password);
		
		// Create and configure the confirm password field
		pf_ConfirmPassword = new JPasswordField();
		pf_ConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_ConfirmPassword.setText("Confirm Password");
		pf_ConfirmPassword.setEchoChar((char) 0);
		pf_ConfirmPassword.setBounds(24, 418, 396, 45);
		contentPane.add(pf_ConfirmPassword);
		
		//==============================================================================================
		// UI CREATION - SIGN UP BUTTON
		//==============================================================================================
		
		// Create and configure the sign-up button
		btn_Sign_Up = new RoundedButton("Sign Up", 25);
		btn_Sign_Up.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_Sign_Up.setBackground(new Color(182, 182, 182));
		btn_Sign_Up.setForeground(Color.BLACK);
		btn_Sign_Up.setBounds(160, 492, 109, 44);
		contentPane.add(btn_Sign_Up);
		
		//==============================================================================================
		// UI CREATION - LOGIN LINK
		//==============================================================================================
		
		// Create and configure the login label
		lbl_Login = new JLabel("Log in");
		lbl_Login.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbl_Login.setBounds(181, 547, 70, 25);
		lbl_Login.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Login);
		
		//==============================================================================================
		// UI CREATION - INCORRECT SIGNAGE
		//==============================================================================================
		
		lbl_Incorrect_Signage1 = new JLabel("*");
		lbl_Incorrect_Signage1.setForeground(Color.RED);
		lbl_Incorrect_Signage1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage1.setBounds(403, 134, 23, 25);
		lbl_Incorrect_Signage1.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage1);
		
		lbl_Incorrect_Signage2 = new JLabel("*");
		lbl_Incorrect_Signage2.setForeground(Color.RED);
		lbl_Incorrect_Signage2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage2.setBounds(403, 222, 23, 25);
		lbl_Incorrect_Signage2.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage2);
		
		lbl_Incorrect_Signage3 = new JLabel("*");
		lbl_Incorrect_Signage3.setForeground(new Color(255, 0, 0));
		lbl_Incorrect_Signage3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage3.setBounds(403, 311, 23, 25); 
		lbl_Incorrect_Signage3.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage3);
		
		lbl_Incorrect_Signage4 = new JLabel("*");
		lbl_Incorrect_Signage4.setForeground(Color.RED);
		lbl_Incorrect_Signage4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Incorrect_Signage4.setBounds(403, 400, 23, 25);
		lbl_Incorrect_Signage4.setVisible(false);
		contentPane.add(lbl_Incorrect_Signage4);
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
		// FOCUS LISTENERS - EMAIL FIELD
		//==============================================================================================
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
		
		//==============================================================================================
		// FOCUS LISTENERS - PASSWORD FIELD
		//==============================================================================================
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
		
		//==============================================================================================
		// FOCUS LISTENERS - CONFIRM PASSWORD FIELD
		//==============================================================================================
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
		
		//==============================================================================================
		// MOUSE LISTENERS - SIGN UP BUTTON
		//==============================================================================================
		btn_Sign_Up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SignUp();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Sign_Up.setBackground(new Color(150, 150, 150));
				btn_Sign_Up.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Sign_Up.setBackground(new Color(182, 182, 182));
				btn_Sign_Up.setForeground(Color.BLACK);
			}
		});
		
		//==============================================================================================
		// MOUSE LISTENERS - LOGIN LINK
		//==============================================================================================
		lbl_Login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openLogIn();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_Login.setText("<html><u>Log in</u></html>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_Login.setText("Log in");
			}
		});
	}
	
	private void openLogIn() {
		EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	Sign_Up_Window.this.dispose();
	                Log_In_Window logInWindow = new Log_In_Window(database_manager);
	                logInWindow.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	private void SignUp() {
		String username = tf_Username.getText();
		String email = tf_Email.getText();
		String password = String.valueOf(pf_Password.getPassword());
		String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
		
		// Validate inputs
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			lbl_Incorrect_Signage1.setVisible(true);
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage3.setVisible(true);
			lbl_Incorrect_Signage4.setVisible(true);
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (username.equals("Enter Username") || email.equals("Enter Email") || password.equals("Enter Password") || confirmPassword.equals("Confirm Password")) {
			lbl_Incorrect_Signage1.setVisible(true);
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage3.setVisible(true);
			lbl_Incorrect_Signage4.setVisible(true);
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!database_manager.getUserManager().validEmail(email)) {
			lbl_Incorrect_Signage1.setVisible(false);
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage3.setVisible(false);
			lbl_Incorrect_Signage4.setVisible(false);
			JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE); 
			return;
		}
		
		if (database_manager.getUserManager().checkUserEmail(username, email)) {
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage3.setVisible(true);
			lbl_Incorrect_Signage4.setVisible(false);
			lbl_Incorrect_Signage1.setVisible(false);
			JOptionPane.showMessageDialog(this, "User Email already exisist", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Check if password meets criteria
		if (!password.equals(confirmPassword)) {
			lbl_Incorrect_Signage4.setVisible(true);
			lbl_Incorrect_Signage1.setVisible(true);
			lbl_Incorrect_Signage2.setVisible(false);
			lbl_Incorrect_Signage3.setVisible(false);
			JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			// Add user to the database
			database_manager.getUserManager().addUser(username, password, email);
			User user = database_manager.getUserManager().getUserByEmail(email);
			JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername() + "!", "Signup Successful", JOptionPane.INFORMATION_MESSAGE);
			
			
			// Navigate to main frame
			EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            try {
		                Sign_Up_Window.this.dispose();
		                Main_Frame mainFrame = new Main_Frame(database_manager, user);
		                mainFrame.setVisible(true);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    });
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error registering user.");
		}
	}
}
/*
 * File: Sign_Up_Window.java
 *
 * Description:
 * This file defines the `Sign_Up_Window` class, which is a `JFrame` used for user registration. 
 * It provides a graphical interface for creating a new user account by entering a username, email, and password. 
 * The class interacts with the `Database_Manager` to validate and save the new user information.
 *
 * Variables:
 *
 * - **Database and User Fields**:
 *   - `database_manager` (Database_Manager): Manages database operations, including user-related actions.
 *
 * - **UI Components**:
 *   - `contentPane` (JPanel): The main container for the window's components.
 *   - `panel_SignUp_Title` (Rounded_Panel): A rounded panel for displaying the title of the sign-up window.
 *   - `lbl_Title_SignUp` (JLabel): Displays the title "Sign Up" at the top of the window.
 *   - `lbl_Username`, `lbl_Email`, `lbl_Password`, `lbl_Confirm_Password` (JLabel): Labels for the input fields.
 *   - `tf_Username` (JTextField): Input field for the user's username.
 *   - `tf_Email` (JTextField): Input field for the user's email.
 *   - `pf_Password` (JPasswordField): Input field for the user's password.
 *   - `pf_ConfirmPassword` (JPasswordField): Input field for confirming the user's password.
 *   - `btn_Sign_Up` (Rounded_Button): Button to submit the sign-up form.
 *   - `lbl_Login` (JLabel): A clickable label to navigate to the login window.
 *   - `lbl_Incorrect_Signage1`, `lbl_Incorrect_Signage2`, `lbl_Incorrect_Signage3`, `lbl_Incorrect_Signage4` (JLabel): Labels to indicate validation errors for specific fields.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Sign_Up_Window(Database_Manager)`:
 *      - Initializes the sign-up window with the provided database manager.
 *      - Calls `initialize_Window_Properties()` to set up the window properties, `initialize_UI_Components()` to create the UI, and `create_Action_Listeners()` to add event listeners.
 *
 * 2. **initialize_Window_Properties()**:
 *    - Configures the window's properties, such as size, layout, and background color.
 *
 * 3. **initialize_UI_Components()**:
 *    - Creates and positions all UI components, including labels, text fields, password fields, and buttons.
 *    - Organizes the components into sections for title, username, email, password, and confirm password.
 *
 * 4. **create_Action_Listeners()**:
 *    - Adds event listeners to handle user interactions with the input fields, buttons, and labels.
 *    - Handles focus events for input fields to manage placeholder text.
 *    - Handles mouse events for the "Sign Up" button and "Log in" label.
 *
 * 5. **openLogIn()**:
 *    - Navigates to the `Log_In_Window` when the "Log in" label is clicked.
 *
 * 6. **SignUp()**:
 *    - Validates the input fields and creates a new user account in the database.
 *    - Key conditions:
 *      - Checks if any input field is empty or contains placeholder text and displays an error message if true.
 *      - Validates the email format using `validEmail()` and displays an error if invalid.
 *      - Checks if the username or email already exists in the database and displays an error if true.
 *      - Ensures that the password and confirm password fields match and displays an error if they do not.
 *    - If all validations pass:
 *      - Adds the new user to the database using `addUser()`.
 *      - Retrieves the newly created user and displays a success message.
 *      - Navigates to the `Main_Frame` for the newly registered user.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for creating a new user account. 
 * It ensures that the input data is validated before saving the user information to the database and provides feedback to the user in case of errors or success.
 */

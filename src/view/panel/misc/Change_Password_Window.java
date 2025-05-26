package view.panel.misc;


import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import visuals.Rounded_Button;
import visuals.Rounded_Panel;


public class Change_Password_Window extends JDialog {

	private static final long serialVersionUID = 1L;
	private User current_user;
	private Database_Manager database_manager;
	
	private JPanel contentPane;
	private JPasswordField pf_Password;
	private JPasswordField pf_ConfirmPassword;
	private JLabel lbl_New_Password;
	private JLabel lbl_Confirm_Password;
	private Rounded_Panel panel_ChangePass_Title;
	private JButton btn_Change_Password;
	private JButton btn_Cancel;
	private JLabel lbl_Title_ChangePass;
	private JLabel lbl_Incorrect_Signage1;
	private JLabel lbl_Incorrect_Signage2;
		
	public Change_Password_Window(JFrame parent, Database_Manager database_manager, User current_user) {
		super(parent, "New Password", true);
		this.current_user = current_user;
		this.database_manager = database_manager;
		
		setTitle("New Password");
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 50, 450, 620);
		setBackground(new Color(213, 213, 213));
		
		initialize_UI();
		create_Action_Listeners();
	}
	
	private void initialize_UI() {
		
		//=========================================================================================================
		// Main Content Panel
		//=========================================================================================================
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(213, 213, 213));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//=========================================================================================================
		// Title Panel
		//=========================================================================================================
		
		// Create a rounded panel for the title
		panel_ChangePass_Title = new Rounded_Panel();
		panel_ChangePass_Title.setLayout(null);
		panel_ChangePass_Title.setBackground(Color.WHITE);
		panel_ChangePass_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_ChangePass_Title);
		
		// label for the title
		lbl_Title_ChangePass = new JLabel("Change Password");
		lbl_Title_ChangePass.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_ChangePass.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbl_Title_ChangePass.setBounds(13, 20, 393, 54);
		panel_ChangePass_Title.add(lbl_Title_ChangePass);
		
		//=========================================================================================================
		// Input Fields
		//=========================================================================================================
		
		// Label and field for new password
		lbl_New_Password = new JLabel("New Password");
		lbl_New_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_New_Password.setBounds(10, 181, 211, 22);
		contentPane.add(lbl_New_Password);
		
		// Password field for new password
		pf_Password = new JPasswordField();
		pf_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.setBounds(20, 214, 396, 45);
		contentPane.add(pf_Password);
		
		// Label and field for confirm password
		lbl_Confirm_Password = new JLabel("Confirm Password");
		lbl_Confirm_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Confirm_Password.setBounds(10, 270, 211, 22);
		contentPane.add(lbl_Confirm_Password);
		
		// Password field for confirm password
		pf_ConfirmPassword = new JPasswordField();
		pf_ConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_ConfirmPassword.setText("Confirm Password");
		pf_ConfirmPassword.setEchoChar((char) 0);
		pf_ConfirmPassword.setBounds(20, 303, 396, 45);
		contentPane.add(pf_ConfirmPassword);
		
		//=========================================================================================================
		// Buttons
		//=========================================================================================================
		
		// Button to change password
		btn_Change_Password = new Rounded_Button("Change Password", 25);
		btn_Change_Password.setBackground(new Color(182, 182, 182));
		btn_Change_Password.setForeground(Color.BLACK);
		btn_Change_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_Change_Password.setBounds(108, 492, 211, 44);
		contentPane.add(btn_Change_Password);
		
		// Button to cancel the change password action
		btn_Cancel = new Rounded_Button("Cancel", 25);
		btn_Cancel.setForeground(Color.BLACK);
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setBackground(new Color(182, 182, 182));
		btn_Cancel.setBounds(168, 547, 91, 34);
		contentPane.add(btn_Cancel);
		
		//=========================================================================================================
		// Incorrect Signage Labels
		//=========================================================================================================
		
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
		
		btn_Cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancelChangePass();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Cancel.setBackground(new Color(150, 150, 150));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Cancel.setBackground(new Color(182, 182, 182));
			}
		});
		
		btn_Change_Password.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChangePassword();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Change_Password.setBackground(new Color(150, 150, 150));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Change_Password.setBackground(new Color(182, 182, 182));
			}
		});
		
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
	}
	
	private void cancelChangePass() {
		String password = String.valueOf(pf_Password.getPassword());
		String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
		
		if (!password.isEmpty() || !confirmPassword.isEmpty()) {
			int response = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm Cancel", javax.swing.JOptionPane.YES_NO_OPTION);
			if (response == javax.swing.JOptionPane.YES_OPTION) {
				this.dispose();
			}
		} else {
			this.dispose();
		}
	}
	
	private void ChangePassword() {
		String password = String.valueOf(pf_Password.getPassword());
		String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
		
		if (password.isEmpty() || confirmPassword.isEmpty()) {
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage1.setVisible(true);
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (password.equals("Enter Password") || confirmPassword.equals("Confirm Password")) {
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage1.setVisible(true);
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage1.setVisible(true);
			JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			database_manager.getUserManager().updateUserPassword(current_user, password);
			JOptionPane.showMessageDialog(Change_Password_Window.this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
			
			EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            try {
		                
		                database_manager.getUserManager().setCurrentUser(current_user);
		                // create confimation window
		                
		                Change_Password_Window.this.dispose();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    });
		} catch (Exception e) {
			System.out.println("Error changing password: " + e.getMessage());
		}
	}
}

/*
 * File: Change_Password_Window.java
 *
 * Description:
 * This file defines the `Change_Password_Window` class, which is a `JDialog` used for changing the password of the currently logged-in user. 
 * It provides a graphical interface for entering a new password and confirming it. The class interacts with the `Database_Manager` to validate 
 * and update the user's password securely.
 *
 * Variables:
 *
 * - **Database and User Models**:
 *   - `current_user` (User): Represents the currently logged-in user whose password is being changed.
 *   - `database_manager` (Database_Manager): Manages database operations, including user-related actions.
 *
 * - **Main Content Pane**:
 *   - `contentPane` (JPanel): The main container for the dialog's components.
 *
 * - **Input Fields**:
 *   - `pf_Password` (JPasswordField): Input field for the new password.
 *   - `pf_ConfirmPassword` (JPasswordField): Input field for confirming the new password.
 *   - `lbl_New_Password` (JLabel): Label for the new password input field.
 *   - `lbl_Confirm_Password` (JLabel): Label for the confirm password input field.
 *
 * - **Title Panel**:
 *   - `panel_ChangePass_Title` (Rounded_Panel): A custom rounded panel for displaying the title of the dialog.
 *   - `lbl_Title_ChangePass` (JLabel): Displays the title "Change Password".
 *
 * - **Buttons**:
 *   - `btn_Change_Password` (JButton): Button to submit the new password and update it in the database.
 *   - `btn_Cancel` (JButton): Button to cancel the operation and close the dialog.
 *
 * - **Validation Indicators**:
 *   - `lbl_Incorrect_Signage1` (JLabel): Displays a red asterisk (*) next to the new password field if validation fails.
 *   - `lbl_Incorrect_Signage2` (JLabel): Displays a red asterisk (*) next to the confirm password field if validation fails.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Change_Password_Window(JFrame parent, Database_Manager database_manager, User current_user)`:
 *      - Initializes the dialog with the provided parent frame, database manager, and current user.
 *      - Calls `initialize_UI()` to set up the user interface and `create_Action_Listeners()` to add event listeners.
 *
 * 2. **initialize_UI()**:
 *    - Sets up the dialog's properties (e.g., size, layout, title).
 *    - Creates and positions all UI components, including labels, password fields, and buttons.
 *    - Configures the title panel and validation indicators.
 *
 * 3. **create_Action_Listeners()**:
 *    - Adds event listeners to the password fields and buttons to handle user interactions.
 *    - Handles focus events for the password fields to manage placeholder text and echo characters.
 *    - Handles button hover effects and click actions.
 *    - Calls `ChangePassword()` when the "Change Password" button is clicked and `cancelChangePass()` when the "Cancel" button is clicked.
 *
 * 4. **cancelChangePass()**:
 *    - Checks if any input fields have been modified.
 *    - If changes are detected, prompts the user with a confirmation dialog to discard unsaved changes.
 *    - Closes the dialog if the user confirms or if no changes are detected.
 *
 * 5. **ChangePassword()**:
 *    - Validates the input fields and updates the user's password in the database.
 *    - Key conditions:
 *      - Checks if the password or confirm password fields are empty and displays an error message if true.
 *      - Checks if the password or confirm password fields contain placeholder text and displays an error message if true.
 *      - Checks if the password and confirm password fields match. If not, displays an error message.
 *      - If all validations pass:
 *        - Updates the user's password in the database using `updateUserPassword()`.
 *        - Displays a success message and closes the dialog.
 *    - Catches and handles any exceptions that occur during the password update process.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for changing a user's password. 
 * It ensures that the input data is validated before updating the database and provides feedback to the user in case of errors or success.
 */

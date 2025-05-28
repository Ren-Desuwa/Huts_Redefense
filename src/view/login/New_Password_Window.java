package view.login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;
import visuals.RoundedButton;
import visuals.RoundedPanel;


public class New_Password_Window extends JFrame {

    // Database and user fields
	private static final long serialVersionUID = 1L;
    private User current_user;
    private Database_Manager database_manager;
    
    // Panel configuration
    private JPanel contentPane;
    private RoundedPanel panel_NewPass_Title;
    private JLabel lbl_Title_NewPass;
        
    // Password input fields
    private JPasswordField pf_Password;
    private JPasswordField pf_ConfirmPassword;
    private JLabel lbl_New_Password;
    private JLabel lbl_Confirm_Password;
    
    // Buttons
    private JButton btn_Change_Password;
    private JButton btn_Cancel;
    private JLabel lbl_Incorrect_Signage1;
    private JLabel lbl_Incorrect_Signage2;
    

    public New_Password_Window(Database_Manager database_manager, User current_user) {
        this.current_user = current_user;
        this.database_manager = database_manager;
        
        setTitle("New Password");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 50, 450, 620);
        setBackground(new Color(213, 213, 213));

        initialize_UI_Components();
        create_Action_Listeners();
    }

    private void initialize_UI_Components() {
    	//================================================================================================
		// UI CREATION - MAIN FRAME
		//==============================================================================================
			
		// Main content pane
    	contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(213, 213, 213));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    	
        //==============================================================================================
        // UI CREATION - TITLE SECTION
        //==============================================================================================
        
        // Title panel
        panel_NewPass_Title = new RoundedPanel();
        panel_NewPass_Title.setLayout(null);
        panel_NewPass_Title.setBackground(Color.WHITE);
        panel_NewPass_Title.setBounds(10, 11, 416, 97);
        contentPane.add(panel_NewPass_Title);
        
        // Title label
        lbl_Title_NewPass = new JLabel("New Password");
        lbl_Title_NewPass.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_NewPass.setFont(new Font("Tahoma", Font.BOLD, 35));
        lbl_Title_NewPass.setBounds(13, 20, 393, 54);
        panel_NewPass_Title.add(lbl_Title_NewPass);
        
        //==============================================================================================
        // UI CREATION - PASSWORD FIELDS
        //==============================================================================================
        
        // New Password field
        lbl_New_Password = new JLabel("New Password");
        lbl_New_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_New_Password.setBounds(10, 181, 211, 22);
        contentPane.add(lbl_New_Password);
        
        // Password input field
        pf_Password = new JPasswordField();
        pf_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
        pf_Password.setText("Enter Password");
        pf_Password.setEchoChar((char) 0);
        pf_Password.setBounds(20, 214, 396, 45);
        contentPane.add(pf_Password);
        
        // Confirm Password field
        lbl_Confirm_Password = new JLabel("Confirm Password");
        lbl_Confirm_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Confirm_Password.setBounds(10, 270, 211, 22);
        contentPane.add(lbl_Confirm_Password);
        
        // Confirm Password input field
        pf_ConfirmPassword = new JPasswordField();
        pf_ConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        pf_ConfirmPassword.setText("Confirm Password");
        pf_ConfirmPassword.setEchoChar((char) 0);
        pf_ConfirmPassword.setBounds(20, 303, 396, 45);
        contentPane.add(pf_ConfirmPassword);
        
        //==============================================================================================
        // UI CREATION - BUTTONS
        //==============================================================================================
        
        // Change Password button
        btn_Change_Password = new RoundedButton("Change Password", 25);
        btn_Change_Password.setBackground(new Color(182, 182, 182));
        btn_Change_Password.setForeground(Color.BLACK);
        btn_Change_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btn_Change_Password.setBounds(108, 492, 211, 44);
        contentPane.add(btn_Change_Password);
        
        // Cancel button
        btn_Cancel = new RoundedButton("Cancel", 25);
        btn_Cancel.setForeground(Color.BLACK);
        btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btn_Cancel.setBackground(new Color(182, 182, 182));
        btn_Cancel.setBounds(168, 547, 91, 34);
        contentPane.add(btn_Cancel);
        
        //==============================================================================================
        // UI CREATION - INCORRECT SIGNAGE
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
        // FOCUS LISTENERS - PASSWORD FIELDS
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
        // MOUSE LISTENERS - BUTTONS
        //==============================================================================================
        btn_Change_Password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePassword();
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
        
        btn_Cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cancelNewPassword();
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
    }
    

    private void cancelNewPassword() {
        String password = String.valueOf(pf_Password.getPassword());
        String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
        
        if (!password.equals("Enter Password") || !confirmPassword.equals("Confirm Password")) {
            int response = javax.swing.JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to cancel?", 
                "Confirm Cancel", 
                javax.swing.JOptionPane.YES_NO_OPTION);
                
            if (response == javax.swing.JOptionPane.YES_OPTION) {
                this.dispose();
                openLogIn();
            }
        } else {
            this.dispose();
            openLogIn();
        }
    }

    private void openLogIn() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    New_Password_Window.this.dispose();
                    Log_In_Window loginWindow = new Log_In_Window(database_manager);
                    loginWindow.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changePassword() {
        String password = String.valueOf(pf_Password.getPassword());
        String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
        
        // Validate inputs
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            lbl_Incorrect_Signage1.setVisible(true);
            lbl_Incorrect_Signage2.setVisible(true);
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.equals("Enter Password") || confirmPassword.equals("Confirm Password")) {
            lbl_Incorrect_Signage1.setVisible(true);
            lbl_Incorrect_Signage2.setVisible(true);
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            lbl_Incorrect_Signage1.setVisible(true);
            lbl_Incorrect_Signage2.setVisible(true);
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update password
        try {
            database_manager.getUserManager().updateUser(current_user, current_user.getUsername(), password, current_user.getEmail());
            JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Open the login window
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        New_Password_Window.this.dispose();
                        Log_In_Window loginWindow = new Log_In_Window(database_manager);
                        loginWindow.setVisible(true);
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
 * File: New_Password_Window.java
 *
 * Description:
 * This file defines the `New_Password_Window` class, which is a `JFrame` used for resetting a user's password.
 * It provides a graphical interface for entering and confirming a new password. The class interacts with the 
 * `Database_Manager` to validate and update the user's password in the database.
 *
 * Variables:
 *
 * - **Database and User Fields**:
 *   - `database_manager` (Database_Manager): Manages database operations, including user-related actions.
 *   - `current_user` (User): Represents the currently logged-in user whose password is being reset.
 *
 * - **UI Components**:
 *   - `contentPane` (JPanel): The main container for the window's components.
 *   - `panel_NewPass_Title` (Rounded_Panel): A rounded panel for displaying the title of the window.
 *   - `lbl_Title_NewPass` (JLabel): Displays the title "New Password" at the top of the window.
 *   - `lbl_New_Password` (JLabel): Label for the new password input field.
 *   - `lbl_Confirm_Password` (JLabel): Label for the confirm password input field.
 *   - `pf_Password` (JPasswordField): Input field for the new password.
 *   - `pf_ConfirmPassword` (JPasswordField): Input field for confirming the new password.
 *   - `lbl_Incorrect_Signage1`, `lbl_Incorrect_Signage2` (JLabel): Labels to indicate validation errors for specific fields.
 *
 * - **Buttons**:
 *   - `btn_Change_Password` (Rounded_Button): Button to submit the new password.
 *   - `btn_Cancel` (Rounded_Button): Button to cancel the operation and navigate back to the login window.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `New_Password_Window(Database_Manager, User)`:
 *      - Initializes the window with the provided database manager and current user.
 *      - Calls `initialize_UI_Components()` to set up the UI and `create_Action_Listeners()` to add event listeners.
 *
 * 2. **initialize_UI_Components()**:
 *    - Configures the layout and properties of the window.
 *    - Creates and positions all UI components, including labels, password fields, and buttons.
 *    - Organizes the components into sections for title, password input, and buttons.
 *
 * 3. **create_Action_Listeners()**:
 *    - Adds event listeners to handle user interactions with the password fields and buttons.
 *    - Handles focus events for password fields to manage placeholder text.
 *    - Handles mouse events for the "Change Password" and "Cancel" buttons.
 *
 * 4. **changePassword()**:
 *    - Validates the input fields and updates the user's password in the database.
 *    - Key conditions:
 *      - Checks if any input field is empty or contains placeholder text and displays an error message if true.
 *      - Ensures that the new password and confirm password fields match and displays an error if they do not.
 *    - If all validations pass:
 *      - Updates the user's password in the database using `updateUserPassword()`.
 *      - Displays a success message and navigates to the `Log_In_Window`.
 *
 * 5. **cancelNewPassword()**:
 *    - Closes the window and navigates back to the `Log_In_Window`.
 *    - Key conditions:
 *      - If changes are detected in the password fields, prompts the user for confirmation before closing.
 *
 * 6. **openLogIn()**:
 *    - Opens the `Log_In_Window` when the "Cancel" button is clicked or after successfully changing the password.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for resetting a user's password.
 * It ensures that the input data is validated before updating the database and provides feedback to the user in case of errors or success.
 */

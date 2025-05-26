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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;
import visuals.Rounded_Button;
import visuals.Rounded_Panel;


public class Retrieve_Window extends JFrame {

	private static final long serialVersionUID = 1L;
    
	//Database manager
    private Database_Manager database_manager;
    
    // Panel configuration
    private JPanel contentPane;
    private Rounded_Panel panel_ConfirmUser_Title;
    private JLabel lbl_Title_ConfirmUser;
    
    // Input fields
    private JTextField tf_Username;
    private JTextField tf_Email;
    private JLabel lbl_Username;
    private JLabel lbl_Email;
    
    // Buttons
    private JButton btn_Confirm;
    private Rounded_Button btn_Cancel;
    private JLabel lbl_Incorrect_Signage1;
    private JLabel lbl_Incorrect_Signage2;


    public Retrieve_Window(Database_Manager database_manager) {
        this.database_manager = database_manager;
        
        setTitle("Forgot Password");
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
        setContentPane(contentPane);
        contentPane.setLayout(null);
    	
        //==============================================================================================
        // UI CREATION - TITLE SECTION
        //==============================================================================================
        
        // Create a rounded panel for the title section
        panel_ConfirmUser_Title = new Rounded_Panel();
        panel_ConfirmUser_Title.setLayout(null);
        panel_ConfirmUser_Title.setBackground(Color.WHITE);
        panel_ConfirmUser_Title.setBounds(10, 11, 416, 97);
        contentPane.add(panel_ConfirmUser_Title);
        
        // Create and configure the title label
        lbl_Title_ConfirmUser = new JLabel("Confirm User");
        lbl_Title_ConfirmUser.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Title_ConfirmUser.setFont(new Font("Tahoma", Font.BOLD, 35));
        lbl_Title_ConfirmUser.setBounds(13, 20, 393, 54);
        panel_ConfirmUser_Title.add(lbl_Title_ConfirmUser);
        
        //==============================================================================================
        // UI CREATION - INPUT FIELDS
        //==============================================================================================
        
        // Create and configure the username and email labels and text fields
        lbl_Username = new JLabel("Username");
        lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Username.setBounds(10, 181, 114, 22);
        contentPane.add(lbl_Username);
        
        // Create the username text field with a placeholder
        tf_Username = new JTextField();
        tf_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
        tf_Username.setText("Enter Username");
        tf_Username.setBounds(20, 214, 396, 45);
        contentPane.add(tf_Username);
        tf_Username.setColumns(10);
        
        // Create the email label and text field
        lbl_Email = new JLabel("Email");
        lbl_Email.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lbl_Email.setBounds(10, 270, 114, 22);
        contentPane.add(lbl_Email);
        
        // Create the email text field with a placeholder
        tf_Email = new JTextField();
        tf_Email.setFont(new Font("Tahoma", Font.PLAIN, 15));
        tf_Email.setText("Enter Email");
        tf_Email.setBounds(20, 303, 396, 45);
        tf_Email.setColumns(10);
        contentPane.add(tf_Email);
        
        //==============================================================================================
        // UI CREATION - BUTTONS
        //==============================================================================================
        
        // Create and configure the confirm and cancel buttons
        btn_Confirm = new Rounded_Button("Confirm", 25);
        btn_Confirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btn_Confirm.setBackground(new Color(182, 182, 182));
        btn_Confirm.setForeground(Color.BLACK);
        btn_Confirm.setBounds(160, 492, 109, 44);
        contentPane.add(btn_Confirm);
        
        // Create and configure the cancel button
        btn_Cancel = new Rounded_Button("Cancel", 25);
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
        // FOCUS LISTENERS - INPUT FIELDS
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
        // MOUSE LISTENERS - BUTTONS
        //==============================================================================================
        btn_Confirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmation();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_Confirm.setBackground(new Color(150, 150, 150));
                btn_Confirm.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn_Confirm.setBackground(new Color(182, 182, 182));
                btn_Confirm.setForeground(Color.BLACK);
            }
        });
        
        btn_Cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cancelRetrieve();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_Cancel.setBackground(new Color(150, 150, 150));
                btn_Cancel.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn_Cancel.setBackground(new Color(182, 182, 182));
                btn_Cancel.setForeground(Color.BLACK);
            }
        });
    }
    

    private void cancelRetrieve() {
        String username = tf_Username.getText();
        String email = tf_Email.getText();
        
        if (!username.equals("Enter Username") || !email.equals("Enter Email")) {
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
                    Retrieve_Window.this.dispose();
                    Log_In_Window logInWindow = new Log_In_Window(database_manager);
                    logInWindow.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    

    private void confirmation() {
        String username = tf_Username.getText();
        String email = tf_Email.getText();
        
        // Validate inputs
        if (username.isEmpty() || email.isEmpty()) {
			lbl_Incorrect_Signage2.setVisible(true);
			lbl_Incorrect_Signage1.setVisible(true);
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (username.equals("Enter Username") || email.equals("Enter Email")) {
            lbl_Incorrect_Signage2.setVisible(true);
            lbl_Incorrect_Signage1.setVisible(true);
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Check if username and email match
            if (!database_manager.getUserManager().UsernameEmailMatch(username, email)) {
                lbl_Incorrect_Signage2.setVisible(true);
                lbl_Incorrect_Signage1.setVisible(true);
                JOptionPane.showMessageDialog(this, "Username and email do not match", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get the user and open the new password window
            User current_user = database_manager.getUserManager().getUserByUsername(username);
            JOptionPane.showMessageDialog(this, "Username and email match", "Success", JOptionPane.INFORMATION_MESSAGE);
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Retrieve_Window.this.dispose();
                        New_Password_Window newPasswordWindow = new New_Password_Window(database_manager, current_user);
                        newPasswordWindow.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving user: " + e.getMessage());
        }
    }
}
/*
 * File: Retrieve_Window.java
 *
 * Description:
 * This file defines the `Retrieve_Window` class, which is a `JFrame` used for retrieving a user's account when they forget their password.
 * It provides a graphical interface for confirming the user's identity by entering their username and email.
 * The class interacts with the `Database_Manager` to validate the provided information and navigate to the password reset window.
 *
 * Variables:
 *
 * - **Database and User Fields**:
 *   - `database_manager` (Database_Manager): Manages database operations, including user-related actions.
 *
 * - **UI Components**:
 *   - `contentPane` (JPanel): The main container for the window's components.
 *   - `panel_ConfirmUser_Title` (Rounded_Panel): A rounded panel for displaying the title of the retrieve window.
 *   - `lbl_Title_ConfirmUser` (JLabel): Displays the title "Confirm User" at the top of the window.
 *   - `lbl_Username`, `lbl_Email` (JLabel): Labels for the username and email input fields.
 *   - `tf_Username` (JTextField): Input field for the user's username.
 *   - `tf_Email` (JTextField): Input field for the user's email.
 *   - `btn_Confirm` (Rounded_Button): Button to submit the confirmation form.
 *   - `btn_Cancel` (Rounded_Button): Button to cancel the operation and navigate back to the login window.
 *   - `lbl_Incorrect_Signage1`, `lbl_Incorrect_Signage2` (JLabel): Labels to indicate validation errors for specific fields.
 *
 * Functions:
 *
 * 1. **Constructor**:
 *    - `Retrieve_Window(Database_Manager)`:
 *      - Initializes the retrieve window with the provided database manager.
 *      - Calls `initialize_UI_Components()` to set up the UI and `create_Action_Listeners()` to add event listeners.
 *
 * 2. **initialize_UI_Components()**:
 *    - Configures the layout and properties of the retrieve window.
 *    - Creates and positions all UI components, including labels, text fields, and buttons.
 *    - Organizes the components into sections for title, username, and email input fields.
 *
 * 3. **create_Action_Listeners()**:
 *    - Adds event listeners to handle user interactions with the input fields and buttons.
 *    - Handles focus events for input fields to manage placeholder text.
 *    - Handles mouse events for the "Confirm" and "Cancel" buttons.
 *
 * 4. **confirmation()**:
 *    - Validates the input fields and checks if the username and email match in the database.
 *    - Key conditions:
 *      - Checks if any input field is empty or contains placeholder text and displays an error message if true.
 *      - If the username and email do not match, displays an error message.
 *      - If the username and email match:
 *        - Retrieves the user from the database.
 *        - Navigates to the `New_Password_Window` for resetting the password.
 *
 * 5. **cancelRetrieve()**:
 *    - Closes the retrieve window and navigates back to the `Log_In_Window`.
 *    - Key conditions:
 *      - If changes are detected in the input fields, prompts the user for confirmation before closing.
 *
 * 6. **openLogIn()**:
 *    - Opens the `Log_In_Window` when the "Cancel" button is clicked.
 *
 * Usage:
 * This class is used to provide a user-friendly interface for retrieving a user's account when they forget their password.
 * It ensures that the input data is validated before proceeding to the password reset process and provides feedback to the user in case of errors or success.
 */

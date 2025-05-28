package view.panel.misc;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;
import view.panel.Profile_Panel;
import visuals.CirclePanel;
import visuals.RoundedButton;

public class Edit_Profile_Window extends JDialog {
    private static final long serialVersionUID = 1L;
    private final Database_Manager database_manager;
    private final User current_user;
    private Profile_Panel profile_Panel;
    
    private JPanel contentPane;
    private CirclePanel panel_initials;
    private JLabel lbl_Initials;
    private JLabel lbl_Username;
    private JTextField tf_Username;
    private JLabel lbl_Email;
    private JTextField tf_Email;
	private JPanel panel_Buttons;
	private RoundedButton btn_Save;
	private RoundedButton btn_Cancel;

    public Edit_Profile_Window(Profile_Panel profile_Panel, Database_Manager database_manager, User current_user) {
        this.database_manager = database_manager;
        this.current_user = current_user;
        this.profile_Panel = profile_Panel;
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Edit Profile");
        setModal(true);
        setResizable(false);
        setBounds(100, 100, 400, 450);
        setLocationRelativeTo(null);
        
        initialize();
        create_Action_Listeners();
        loadUserData();
    }

    private void initialize() {
        
    	//=====================================================================================================
		// main content pane
		//=====================================================================================================

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //======================================================================================================
        // Initials Panel
        //======================================================================================================
        panel_initials = new CirclePanel(150);
        panel_initials.setBounds(125, 20, 150, 150);
        panel_initials.setLayout(new BorderLayout(0, 0));
        contentPane.add(panel_initials);
        
        lbl_Initials = new JLabel();
        lbl_Initials.setForeground(new Color(255, 255, 255));
        lbl_Initials.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Initials.setFont(new Font("Tahoma", Font.BOLD, 48));
        panel_initials.add(lbl_Initials);

        //======================================================================================================
        // Username
        //======================================================================================================
        
        // Username Label
        lbl_Username = new JLabel("Username:");
        lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbl_Username.setBounds(50, 190, 80, 25);
        contentPane.add(lbl_Username);

        // Username TextField
        tf_Username = new JTextField();
        tf_Username.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tf_Username.setBounds(140, 190, 200, 25);
        contentPane.add(tf_Username);

        //======================================================================================================
        // Email Label
        //======================================================================================================
        
        // Email Label
        lbl_Email = new JLabel("Email:");
        lbl_Email.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbl_Email.setBounds(50, 230, 80, 25);
        contentPane.add(lbl_Email);

        // Email TextField
        tf_Email = new JTextField();
        tf_Email.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tf_Email.setBounds(140, 230, 200, 25);
        contentPane.add(tf_Email);

        //======================================================================================================
        // Button Panel
        //======================================================================================================
        
        // Button Panel
        panel_Buttons = new JPanel();
        panel_Buttons.setBounds(0, 350, 384, 40);
        panel_Buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        contentPane.add(panel_Buttons);

        // Save Button
        btn_Save = new RoundedButton("Save", 15);
        btn_Save.setPreferredSize(new Dimension(100, 30));
        btn_Save.setBackground(new Color(68, 162, 255));
        btn_Save.setForeground(Color.WHITE);
        panel_Buttons.add(btn_Save);

        // Cancel Button
        btn_Cancel = new RoundedButton("Cancel", 15);
        btn_Cancel.setPreferredSize(new Dimension(100, 30));
        btn_Cancel.setBackground(new Color(200, 200, 200));
        panel_Buttons.add(btn_Cancel);
    }
    
    private void create_Action_Listeners() {
    	
		btn_Save.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) { saveProfile(); }
			@Override
			public void mouseEntered(MouseEvent e) { btn_Save.setBackground(new Color(50, 130, 220)); }
			@Override
			public void mouseExited(MouseEvent e) { btn_Save.setBackground(new Color(68, 162, 255)); }
		});
		
		btn_Cancel.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) { cancelEdit(); }
			@Override
			public void mouseEntered(MouseEvent e) { btn_Cancel.setBackground(new Color(180, 180, 180)); }
			@Override
			public void mouseExited(MouseEvent e) { btn_Cancel.setBackground(new Color(200, 200, 200)); }
		});
		
	}

    private void loadUserData() {
        if (current_user != null) {
        	String username = current_user.getUsername();
            tf_Username.setText(username);
            tf_Email.setText(current_user.getEmail());
            
            // Set initials based on username
            if (username != null && !username.trim().isEmpty()) {
				// Split the username into words and extract initials
                String[] words = username.trim().split("\\s+");
                String initials;

                // If there are two or more words, take the first letter of the first two words
                if (words.length >= 2) {
                    initials = ("" + words[0].charAt(0) + words[1].charAt(0)).toUpperCase();
                } else {
					// If there's only one word, take the first two letters of that word
                    String firstWord = words[0];
                    initials = firstWord.substring(0, Math.min(2, firstWord.length())).toUpperCase();
                }

                lbl_Initials.setText(initials);
            } else {
                lbl_Initials.setText("UN");
            }
        }
    }

    private void saveProfile() {
        String username = tf_Username.getText().trim();
        String email = tf_Email.getText().trim();

        // if empty, show error message
        if (username.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username And Email cannot be empty", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!database_manager.getUserManager().validEmail(email)) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid email address", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if username or email already exists in the database
        User existingUser = database_manager.getUserManager().getUserByUsername(username);
        if (existingUser != null && existingUser.getUser_Id() != current_user.getUser_Id()) {
            JOptionPane.showMessageDialog(this, 
                "Username already exists", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        existingUser = database_manager.getUserManager().getUserByEmail(email);
        // Check if email already exists in the database
        if (existingUser != null && existingUser.getUser_Id() != current_user.getUser_Id()) {
            JOptionPane.showMessageDialog(this, 
                "Email already exists", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if the username and email are the same as the current user
        if (username.equals(current_user.getUsername()) && email.equals(current_user.getEmail())) {
			JOptionPane.showMessageDialog(this, 
				"No changes made", 
				"Info", 
				JOptionPane.INFORMATION_MESSAGE);
			dispose();
			return;
		}

        try {
            database_manager.getUserManager().updateUser(current_user, username, current_user.getPassword(), email);
            current_user.setUsername(username);
            current_user.setEmail(email);

            JOptionPane.showMessageDialog(this, 
                "Profile updated successfully", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            profile_Panel.updateUserInfo(current_user);
            
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    private void cancelEdit() {
        String currentUsername = tf_Username.getText().trim();
        String currentEmail = tf_Email.getText().trim();

        // Check if username or email has changed
        if (!currentUsername.equals(current_user.getUsername()) || !currentEmail.equals(current_user.getEmail())) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel? Unsaved changes will be lost.",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                dispose();
            }
        } else {
            dispose();
        }
    }
}
/*
 * File: Edit_Profile_Window.java
 * 
 * Description:
 * This file defines the `Edit_Profile_Window` class, which is a `JDialog` used for editing a user's profile. 
 * It provides a graphical interface for updating the username and email of the current user. 
 * The class interacts with the `Database_Manager` to validate and save the updated user information.
 * 
 * Variables:
 * - `database_manager` (Database_Manager): Manages database operations, including user-related actions.
 * - `current_user` (User): Represents the currently logged-in user whose profile is being edited.
 * - `contentPane` (JPanel): The main container for the dialog's components.
 * - `tf_Username` (JTextField): Input field for the user's username.
 * - `tf_Email` (JTextField): Input field for the user's email.
 * - `lbl_Initials` (JLabel): Displays the user's initials in the circular panel.
 * - `panel_initials` (Circle_Panel): A custom circular panel to display the user's initials.
 * - `btn_Save` (Rounded_Button): Button to save the updated profile information.
 * - `btn_Cancel` (Rounded_Button): Button to cancel the operation and close the dialog.
 * - `profile_Panel` (Profile_Panel): Reference to the parent profile panel to update user information after saving.
 * - `lbl_Username` (JLabel): Label for the username input field.
 * - `lbl_Email` (JLabel): Label for the email input field.
 * - `panel_Buttons` (JPanel): Container for the save and cancel buttons.
 * 
 * Functions:
 * 
 * 1. `Edit_Profile_Window(Profile_Panel, Database_Manager, User)`:
 *    - Constructor that initializes the dialog with the provided profile panel, database manager, and current user.
 *    - Calls `initialize()` to set up the UI, `create_Action_Listeners()` to add button actions, and `loadUserData()` to populate the fields with the current user's data.
 * 
 * 2. `initialize()`:
 *    - Sets up the dialog's properties (e.g., size, layout, title).
 *    - Creates and positions all UI components, including labels, text fields, and buttons.
 * 
 * 3. `create_Action_Listeners()`:
 *    - Adds mouse listeners to the save and cancel buttons to handle user interactions.
 *    - Handles button hover effects and click actions.
 *    - Calls `saveProfile()` when the save button is clicked and `cancelEdit()` when the cancel button is clicked.
 * 
 * 4. `loadUserData()`:
 *    - Populates the username and email fields with the current user's data.
 *    - Extracts and displays the user's initials in the circular panel.
 *    - Key conditions:
 *      - If the username has two or more words, it displays the first letters of the first two words as initials.
 *      - If the username has only one word, it displays the first two letters of that word as initials.
 *      - If the username is null or consists of whitespace, it displays "UN" (for "Unknown") as the initials.
 * 
 * 5. `saveProfile()`:
 *    - Validates the input fields and updates the user's profile in the database.
 *    - Key conditions:
 *      - Checks if the username or email is empty and displays an error message if true.
 *      - Validates the email format using `validEmail()` and displays an error if invalid.
 *      - Checks if the username already exists in the database (excluding the current user) and displays an error if true.
 *      - Checks if the email already exists in the database (excluding the current user) and displays an error if true.
 *      - If no changes are made to the username or email, displays an informational message and closes the dialog.
 *    - If all validations pass:
 *      - Updates the user's information in the database using `updateUser()`.
 *      - Updates the `current_user` object with the new username and email.
 *      - Displays a success message and updates the parent `Profile_Panel` with the new user information.
 *      - Closes the dialog.
 *    - Catches and handles any `SQLException` that occurs during the database update.
 * 
 * 6. `cancelEdit()`:
 *    - Checks if the username or email has been modified.
 *    - If changes are detected, prompts the user with a confirmation dialog to discard unsaved changes.
 *    - Closes the dialog if the user confirms or if no changes are detected.
 * 
 * Usage:
 * This class is used to provide a user-friendly interface for editing a user's profile. 
 * It ensures that the input data is validated before updating the database and provides feedback to the user in case of errors or success.
 */

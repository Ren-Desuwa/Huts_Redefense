package view.panel.misc;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;
import view.panel.Profile_Panel;
import visuals.Circle_Panel;
import visuals.Rounded_Button;

public class Edit_Profile_Window extends JDialog {
    private static final long serialVersionUID = 1L;
    private final Database_Manager database_manager;
    private final User current_user;
    
    private JPanel contentPane;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JLabel lblInitials;
    private Circle_Panel initialsPanel;
    private Rounded_Button btnSave;
    private Rounded_Button btnCancel;
	private Profile_Panel profile_Panel;

    public Edit_Profile_Window(Profile_Panel profile_Panel, Database_Manager database_manager, User current_user) {
        this.database_manager = database_manager;
        this.current_user = current_user;
        this.profile_Panel = profile_Panel;
        initialize();
        loadUserData();
    }

    private void initialize() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Edit Profile");
        setModal(true);
        setResizable(false);
        setBounds(100, 100, 400, 450);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Initials Panel
        initialsPanel = new Circle_Panel(150);
        initialsPanel.setBounds(125, 20, 150, 150);
        initialsPanel.setLayout(new BorderLayout(0, 0));
        contentPane.add(initialsPanel);

        lblInitials = new JLabel();
        lblInitials.setForeground(new Color(255, 255, 255));
        lblInitials.setHorizontalAlignment(SwingConstants.CENTER);
        lblInitials.setFont(new Font("Tahoma", Font.BOLD, 48));
        initialsPanel.add(lblInitials);

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUsername.setBounds(50, 190, 80, 25);
        contentPane.add(lblUsername);

        // Username TextField
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtUsername.setBounds(140, 190, 200, 25);
        contentPane.add(txtUsername);

        // Email Label
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmail.setBounds(50, 230, 80, 25);
        contentPane.add(lblEmail);

        // Email TextField
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtEmail.setBounds(140, 230, 200, 25);
        contentPane.add(txtEmail);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 350, 384, 40);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        contentPane.add(buttonPanel);

        btnSave = new Rounded_Button("Save", 15);
        btnSave.setPreferredSize(new Dimension(100, 30));
        btnSave.setBackground(new Color(68, 162, 255));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveProfile());
        buttonPanel.add(btnSave);

        btnCancel = new Rounded_Button("Cancel", 15);
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setBackground(new Color(200, 200, 200));
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnCancel);
    }

    private void loadUserData() {
        if (current_user != null) {
            txtUsername.setText(current_user.getUsername());
            txtEmail.setText(current_user.getEmail());
            updateInitials(current_user.getUsername());
        }
    }

    private void updateInitials(String username) {
        if (username != null && !username.isEmpty()) {
            lblInitials.setText(username.substring(0, Math.min(2, username.length())).toUpperCase());
        } else {
            lblInitials.setText("UN");
        }
    }

    private void saveProfile() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();

        if (!validateInputs(username, email)) {
            return;
        }

        try {
            if (!checkExistingCredentials(username, email)) {
                return;
            }

            database_manager.getUserManager().updateUser(
                current_user, 
                username, 
                current_user.getPassword(), 
                email
            );

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

    private boolean validateInputs(String username, String email) {
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username cannot be empty", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid email address", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean checkExistingCredentials(String username, String email) throws SQLException {
        User existingUser = database_manager.getUserManager().getUserByUsername(username);
        if (existingUser != null && existingUser.getUser_Id() != current_user.getUser_Id()) {
            JOptionPane.showMessageDialog(this, 
                "Username already exists", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        existingUser = database_manager.getUserManager().getUserByEmail(email);
        if (existingUser != null && existingUser.getUser_Id() != current_user.getUser_Id()) {
            JOptionPane.showMessageDialog(this, 
                "Email already exists", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}

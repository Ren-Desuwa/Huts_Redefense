package view.panel.misc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class Change_Password_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private User current_user;
	private Database_Manager database_manager;
	
	private JPanel contentPane;
	private JPasswordField pf_Password;
	private JPasswordField pf_ConfirmPassword;
	private JLabel lbl_Title_Forgot_Password;
	private JLabel lbl_New_Password;
	private JLabel lbl_Confirm_Password;
		
	public Change_Password_Window(Database_Manager database_manager, User current_user) {
		this.current_user = current_user;
		this.database_manager = database_manager;
		
		setTitle("New Password");
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_Title_Forgot_Password = new JLabel("Forgot Password");
		lbl_Title_Forgot_Password.setBounds(267, 47, 171, 46);
		lbl_Title_Forgot_Password.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Title_Forgot_Password.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Title_Forgot_Password);
		
		lbl_New_Password = new JLabel("New Password");
		lbl_New_Password.setBounds(247, 175, 97, 14);
		contentPane.add(lbl_New_Password);
		
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
		pf_Password.setBounds(268, 200, 171, 44);
		contentPane.add(pf_Password);
		
		lbl_Confirm_Password = new JLabel("Confirm Password");
		lbl_Confirm_Password.setBounds(247, 253, 97, 14);
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
		pf_ConfirmPassword.setBounds(268, 278, 171, 44);
		contentPane.add(pf_ConfirmPassword);
		
		JButton btn_Change_Password = new JButton("Change Password");
		btn_Change_Password.setBounds(285, 385, 138, 44);
		btn_Change_Password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePassword();
			}
		});
		contentPane.add(btn_Change_Password);
	}
	private void ChangePassword() {
		String password = String.valueOf(pf_Password.getPassword());
		String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());
		
		if (password.isEmpty() || confirmPassword.isEmpty()) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (password.equals("Enter Password") || confirmPassword.equals("Confirm Password")) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			System.out.println("Passwords do not match.");
			return;
		}
		
		try {
			database_manager.getUserManager().updateUserPassword(current_user, password);
			System.out.println("Password changed successfully.");
			
			JFrame currentFrame = this;
			
			EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            try {
		                currentFrame.dispose();
		                database_manager.getUserManager().setCurrentUser(current_user);
		                
		                // create confimation window
		                createConfirmationWindow("Password Changed", "Your password has been changed successfully.");
		                
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    });
		} catch (Exception e) {
			System.out.println("Error changing password: " + e.getMessage());
		}
	}
	
	private void createConfirmationWindow(String title, String message) {
	    JDialog dialog = new JDialog((Frame) null, title, true);
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setLayout(new BorderLayout(10, 10));

	    // Message label with padding
	    JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
	    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    dialog.add(messageLabel, BorderLayout.CENTER);

	    // OK button panel
	    JPanel buttonPanel = new JPanel();
	    JButton okButton = new JButton("OK");
	    okButton.setPreferredSize(new Dimension(80, 30));
	    okButton.addActionListener(e -> dialog.dispose());
	    buttonPanel.add(okButton);
	    dialog.add(buttonPanel, BorderLayout.SOUTH);

	    // Padding around content
	    dialog.getRootPane().setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
	    dialog.pack();
	    dialog.setLocationRelativeTo(null);
	    dialog.setVisible(true);
	}
}

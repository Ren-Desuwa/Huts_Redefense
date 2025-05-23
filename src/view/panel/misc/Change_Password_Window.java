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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(213, 213, 213));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_ChangePass_Title = new Rounded_Panel();
		panel_ChangePass_Title.setLayout(null);
		panel_ChangePass_Title.setBackground(Color.WHITE);
		panel_ChangePass_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_ChangePass_Title);
		
		lbl_Title_ChangePass = new JLabel("Change Password");
		lbl_Title_ChangePass.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_ChangePass.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbl_Title_ChangePass.setBounds(13, 20, 393, 54);
		panel_ChangePass_Title.add(lbl_Title_ChangePass);
		
		lbl_New_Password = new JLabel("New Password");
		lbl_New_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_New_Password.setBounds(10, 181, 211, 22);
		contentPane.add(lbl_New_Password);
		
		pf_Password = new JPasswordField();
		pf_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.setBounds(20, 214, 396, 45);
		contentPane.add(pf_Password);
		
		lbl_Confirm_Password = new JLabel("Confirm Password");
		lbl_Confirm_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Confirm_Password.setBounds(10, 270, 211, 22);
		contentPane.add(lbl_Confirm_Password);
		
		pf_ConfirmPassword = new JPasswordField();
		pf_ConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pf_ConfirmPassword.setText("Confirm Password");
		pf_ConfirmPassword.setEchoChar((char) 0);
		pf_ConfirmPassword.setBounds(20, 303, 396, 45);
		contentPane.add(pf_ConfirmPassword);
		
		btn_Change_Password = new Rounded_Button("Change Password", 25);
		btn_Change_Password.setBackground(new Color(182, 182, 182));
		btn_Change_Password.setForeground(Color.BLACK);
		btn_Change_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_Change_Password.setBounds(108, 492, 211, 44);
		contentPane.add(btn_Change_Password);
		
		btn_Cancel = new Rounded_Button("Cancel", 25);
		btn_Cancel.setForeground(Color.BLACK);
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setBackground(new Color(182, 182, 182));
		btn_Cancel.setBounds(168, 547, 91, 34);
		contentPane.add(btn_Cancel);
		
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

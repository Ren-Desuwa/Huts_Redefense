package view.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.EventQueue;

import visuals.Rounded_Button;
import visuals.Rounded_Panel;

public class Retrive_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private Database_Manager database_manager;
	private JPanel contentPane;

	private JTextField tf_Username;
	private JTextField tf_Email;
	private JLabel lbl_Username;
	private JLabel lbl_Email;
	private Rounded_Panel panel_ConfirmUser_Title;
	private JButton btn_Confirm;
	private Rounded_Button btn_Cancel;

	public Retrive_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		
		setTitle("Forgot Password");
		setResizable(false);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 450, 620);
		setBackground(new Color(213, 213, 213));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(213, 213, 213));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel_ConfirmUser_Title = new Rounded_Panel();
		panel_ConfirmUser_Title.setLayout(null);
		panel_ConfirmUser_Title.setBackground(Color.WHITE);
		panel_ConfirmUser_Title.setBounds(10, 11, 416, 97);
		contentPane.add(panel_ConfirmUser_Title);
		
		JLabel lbl_Title_ConfirmUser = new JLabel("Confirm User");
		lbl_Title_ConfirmUser.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title_ConfirmUser.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbl_Title_ConfirmUser.setBounds(13, 20, 393, 54);
		panel_ConfirmUser_Title.add(lbl_Title_ConfirmUser);
		
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
		
		lbl_Email = new JLabel("Email");
		lbl_Email.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Email.setBounds(10, 270, 114, 22);
		contentPane.add(lbl_Email);
		
		tf_Email = new JTextField();
		tf_Email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Email.setText("Enter Email");
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
		tf_Email.setBounds(20, 303, 396, 45);
		tf_Email.setColumns(10);
		contentPane.add(tf_Email);
			
		btn_Confirm = new Rounded_Button("Confirm", 25);
		btn_Confirm.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_Confirm.setBackground(new Color(182, 182, 182));
		btn_Confirm.setForeground(Color.BLACK);
		btn_Confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Confirmation();
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
		btn_Confirm.setBounds(160, 492, 109, 44);
		contentPane.add(btn_Confirm);
		
		btn_Cancel = new Rounded_Button("Cancel", 25);
		btn_Cancel.setForeground(Color.BLACK);
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setBackground(new Color(182, 182, 182));
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
		btn_Cancel.setBounds(168, 547, 91, 34);
		contentPane.add(btn_Cancel);
	}
	
	private void cancelRetrieve() {
		String username = tf_Username.getText();
		String email = tf_Email.getText();
		
		if (!username.equals("Enter Username") || !email.equals("Enter Email")) {
			int response = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm Cancel", javax.swing.JOptionPane.YES_NO_OPTION);
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
	            	Retrive_Window.this.dispose();
	                Log_In_Window logInWindow = new Log_In_Window(database_manager);
	                logInWindow.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	private void Confirmation() {
		String username = tf_Username.getText();
		String email = tf_Email.getText();
		
		if (username.isEmpty() || email.isEmpty()) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (username.equals("Enter Username") || email.equals("Enter Email")) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		try {
			if (database_manager.getUserManager().UsernamePasswordMatch(username, email)) {
				System.out.println("Username and email do not match.");
			}
			
			User current_user = database_manager.getUserManager().getUserByUsername(username);
			
		    
		    EventQueue.invokeLater(new Runnable() {
		        public void run() {
		            try {
		                Retrive_Window.this.dispose();
		                New_Password_Window newPasswordWindow = new New_Password_Window(database_manager, current_user);
		                newPasswordWindow.setVisible(true);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    });
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

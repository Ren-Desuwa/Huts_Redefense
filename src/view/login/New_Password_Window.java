package view.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import model.User;
import view.Main_Frame;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class New_Password_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private User current_user;
	private Database_Manager database_manager;
	
	private JPanel contentPane;
	private JPasswordField pf_Password;
	private JPasswordField pf_Confirm_Password;
	private JLabel lbl_Title_Forgot_Password;
	private JLabel lbl_New_Password;
	private JLabel lbl_Confirm_Password;

	public New_Password_Window(Database_Manager database_manager, User current_user) {
		this.current_user = current_user;
		this.database_manager = database_manager;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_Change_Password = new JButton("Change Password");
		btn_Change_Password.setBounds(285, 385, 138, 44);
		btn_Change_Password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btn_Change_Password);
		
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
		
		pf_Confirm_Password = new JPasswordField();
		pf_Confirm_Password.setText("Confirm Password");
		pf_Confirm_Password.setEchoChar((char) 0);
		pf_Confirm_Password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (String.valueOf(pf_Confirm_Password.getPassword()).equals("Confirm Password")) {
					pf_Confirm_Password.setText("");
					pf_Confirm_Password.setEchoChar('\u2022'); // Bullet character
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (pf_Confirm_Password.getPassword().length == 0) {
					pf_Confirm_Password.setText("Confirm Password");
					pf_Confirm_Password.setEchoChar((char) 0); // Show text again
				}
			}
		});
		pf_Confirm_Password.setBounds(268, 278, 171, 44);
		contentPane.add(pf_Confirm_Password);
		
		lbl_Title_Forgot_Password = new JLabel("Forgot Password");
		lbl_Title_Forgot_Password.setBounds(267, 47, 171, 46);
		lbl_Title_Forgot_Password.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Title_Forgot_Password.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Title_Forgot_Password);
		
		lbl_New_Password = new JLabel("New Password");
		lbl_New_Password.setBounds(247, 175, 97, 14);
		contentPane.add(lbl_New_Password);
		
		lbl_Confirm_Password = new JLabel("Confirm Password");
		lbl_Confirm_Password.setBounds(247, 253, 97, 14);
		contentPane.add(lbl_Confirm_Password);
	}

}

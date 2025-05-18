package view.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.Database_Manager;
import view.Main_Frame;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Log_In_Window extends JFrame {

	private Database_Manager database_manager;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Username;
	private JPasswordField pf_Password;
	private JLabel lbl_Title_Log_In;
	private JLabel lbl_Username;
	private JLabel lbl_Password;

	public Log_In_Window(Database_Manager database_manager) {
		this.database_manager = database_manager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbl_Title_Log_In = new JLabel("Log In");
		lbl_Title_Log_In.setBounds(285, 47, 138, 46);
		lbl_Title_Log_In.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Title_Log_In.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Title_Log_In);
		
		lbl_Username = new JLabel("Username");
		lbl_Username.setBounds(248, 129, 97, 14);
		contentPane.add(lbl_Username);
		
		lbl_Password = new JLabel("Password");
		lbl_Password.setBounds(248, 209, 97, 14);
		contentPane.add(lbl_Password);
							
		tf_Username = new JTextField();
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
		tf_Username.setBounds(269, 154, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		JButton btn = new JButton("Log In");
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login();
			}
		});
		btn.setBounds(301, 331, 109, 44);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btn);
		
		pf_Password = new JPasswordField();
		pf_Password.setText("Enter Password");
		pf_Password.setEchoChar((char) 0);
		pf_Password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (String.valueOf(pf_Password.getPassword()).equals("Enter Password")) {
					pf_Password.setText("");
					pf_Password.setEchoChar('\u2022');
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
		pf_Password.setBounds(269, 234, 171, 44);
		contentPane.add(pf_Password);
		
		JLabel lbl_SignUp = new JLabel("Sign Up");
		lbl_SignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goToSignUp();
			}
		});
		lbl_SignUp.setBounds(322, 386, 70, 25);
		lbl_SignUp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_SignUp);
		
	}
	private void goToSignUp() {
		// Close the current window
		this.dispose();
		
		// Open the Sign Up window
		Sign_Up_Window SignUpWindow = new Sign_Up_Window(database_manager);
		SignUpWindow.setVisible(true);
	}
	
	private void Login() {
		String username = tf_Username.getText();
		String password = String.valueOf(pf_Password.getPassword());
		
		if (username.isEmpty() || password.isEmpty()) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		if (username.equals("Enter Username") || password.equals("Enter Password")) {
			System.out.println("Please fill in all fields.");
			return;
		}
		
		try {
			if (database_manager.getUserManager().UsernamePasswordMatch(username, password)) {
				System.out.println("Passwords do not match.");
				return;
			}
			System.out.println("User Log In successfully.");
			
			this.dispose();
			
			Main_Frame mainframe = new Main_Frame();
			mainframe.setVisible(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

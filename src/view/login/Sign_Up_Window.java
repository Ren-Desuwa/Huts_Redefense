package view.login;

import java.awt.EventQueue;
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

public class Sign_Up_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Username;
	private JTextField tf_Gmail;
	private JPasswordField pf_Password;
	private JPasswordField pf_ConfirmPassword;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	
	public Sign_Up_Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tf_Username = new JTextField();
		tf_Username.setBounds(267, 148, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		tf_Gmail = new JTextField();
		tf_Gmail.setBounds(267, 224, 171, 44);
		tf_Gmail.setColumns(10);
		contentPane.add(tf_Gmail);
		
		JButton btn = new JButton("Sign Up");
		btn.setBounds(300, 463, 109, 44);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btn);
		
		JLabel lblNewLabel = new JLabel("Log In");
		lblNewLabel.setBounds(321, 518, 70, 25);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		pf_Password = new JPasswordField();
		pf_Password.setBounds(267, 300, 171, 44);
		contentPane.add(pf_Password);
		
		pf_ConfirmPassword = new JPasswordField();
		pf_ConfirmPassword.setBounds(267, 378, 171, 44);
		contentPane.add(pf_ConfirmPassword);
		
		lblNewLabel_1 = new JLabel("Sign In");
		lblNewLabel_1.setBounds(285, 47, 138, 46);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setBounds(246, 123, 97, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Gmail");
		lblNewLabel_3.setBounds(246, 199, 97, 14);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setBounds(246, 275, 97, 14);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Confirm Password");
		lblNewLabel_5.setBounds(246, 353, 97, 14);
		contentPane.add(lblNewLabel_5);
	}
}

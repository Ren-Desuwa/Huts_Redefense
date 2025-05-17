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

public class Sign_In_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_Username;
	private JPasswordField pf_Password;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;

	public Sign_In_Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tf_Username = new JTextField();
		tf_Username.setBounds(269, 154, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		JButton btn = new JButton("Log Ina");
		btn.setBounds(301, 331, 109, 44);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btn);
		
		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setBounds(322, 386, 70, 25);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		pf_Password = new JPasswordField();
		pf_Password.setBounds(269, 234, 171, 44);
		contentPane.add(pf_Password);
		
		lblNewLabel_1 = new JLabel("Log In");
		lblNewLabel_1.setBounds(285, 47, 138, 46);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setBounds(248, 129, 97, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setBounds(248, 209, 97, 14);
		contentPane.add(lblNewLabel_4);
	}

}

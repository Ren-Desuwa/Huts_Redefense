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

import database.Database_Manager;

public class Retrive_Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField tf_Username;
	private JTextField tf_Gmail;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	public Retrive_Window(Database_Manager database_manager) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tf_Username = new JTextField();
		tf_Username.setBounds(267, 201, 171, 44);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		tf_Gmail = new JTextField();
		tf_Gmail.setBounds(267, 277, 171, 44);
		tf_Gmail.setColumns(10);
		contentPane.add(tf_Gmail);
		
		JButton btn = new JButton("Confirm");
		btn.setBounds(300, 385, 109, 44);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btn);
		
		lblNewLabel_1 = new JLabel("Forgot Password");
		lblNewLabel_1.setBounds(267, 47, 171, 46);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setBounds(246, 176, 97, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Gmail");
		lblNewLabel_3.setBounds(246, 252, 97, 14);
		contentPane.add(lblNewLabel_3);
	}

}

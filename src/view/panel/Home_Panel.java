package view.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class Home_Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Home_Panel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hello World");
		lblNewLabel.setBounds(191, 139, 120, 14);
		add(lblNewLabel);

	}
}

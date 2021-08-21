package home;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import assets.Icon;

@SuppressWarnings("serial")
public class Home extends JFrame{

	public void exitApplication() {
		System.out.println("Exited from Application..");
		System.exit(0);
	}

	public Home() {
		System.out.println("Home page active");
		setTitle("Employee and work management system");
		setLayout(new GridLayout(2,0));
	
		//Panel for header section
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		JLabel headerImagelabel = new JLabel(new ImageIcon(Icon.headerImage));
		JLabel headerTextLabel = new JLabel("Employee and work management system");
		headerTextLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,25));
		GridBagConstraints gbcHeader = new GridBagConstraints(); 
		gbcHeader.gridx = 2;
		headerPanel.add(headerImagelabel,gbcHeader);
		headerPanel.add(headerTextLabel,gbcHeader);
		getContentPane().add(headerPanel);
		//end panel of header section

		//Login ButtonPanel start
		JPanel LoginButtonPanel = new JPanel(); 
		LoginButtonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton adminLoginButton = new JButton("Admin"); 
		JButton employeeLoginButton = new JButton("Employee");
		JButton exitButton = new JButton(new ImageIcon(Icon.exitIcon));
		adminLoginButton.setFocusPainted(false);
		employeeLoginButton.setFocusPainted(false);
		exitButton.setFocusPainted(false);
		adminLoginButton.setToolTipText("Admin Login"); 
		employeeLoginButton.setToolTipText("Employee Login"); 
		exitButton.setToolTipText("Exit");
		c.ipadx = 50; c.ipady =20; 
		c.insets = new Insets(-150,0,0,0); 
		c.weightx = 0.5; 
		c.gridx = 0; c.gridy = 0;
		LoginButtonPanel.add(adminLoginButton,c);
		c.gridx = 1; c.gridy = 0;
		LoginButtonPanel.add(employeeLoginButton,c); 
		c.gridx = 0; c.gridy = 1;
		c.ipady = 5; c.ipadx = 0; c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0); 
		LoginButtonPanel.add(exitButton,c);
		getContentPane().add(LoginButtonPanel);
		//end of LoginButton panel
		
		//ActionListener for exitButton
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home();
				home.exitApplication();
			}
		});
		
		adminLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.LoginFrame login = new admin.LoginFrame();
				login.setVisible(true);
				setVisible(false);
			}
		});
		//end actionListener for exitButton
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 80, 800, 600);
	}

}

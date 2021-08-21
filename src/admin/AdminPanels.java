package admin;
import home.Home;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import assets.Icon;
@SuppressWarnings("serial")
public class AdminPanels extends JFrame{
	Dimension screenSize;
	Color lightBlue = new Color(91, 220, 235);
	Font adminLabelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 35);
	Font sidePanelButtonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
	JPanel topPanel;
	JPanel sidePanel;
	JPanel sideButtonPanel;
	JPanel contentPanel;
	JLabel adminLabelIcon;
	JLabel adminLabel;
	JButton manageButton;
	JButton scheduleButton;
	JButton reportButton;
	JButton logoutButton;
	GridBagConstraints gbc;
	
	//Constructor
	AdminPanels() {
		setTitle("Admin");
		
			
		//begin of top panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		topPanel.setPreferredSize(new Dimension(0,80));
		topPanel.setBackground(lightBlue);
		
		adminLabelIcon = new JLabel();
		adminLabelIcon.setIcon(new ImageIcon(Icon.adminImage));
		adminLabel = new JLabel("ADMIN");
		adminLabel.setBorder(new EmptyBorder(10,10,10,10));
		adminLabel.setFont(adminLabelFont);
		topPanel.add(adminLabelIcon);
		topPanel.add(adminLabel);
		add(topPanel, BorderLayout.NORTH);
		//end of top panel
		
		
		//begin of side panel
		sidePanel = new JPanel();
		sidePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		sidePanel.setPreferredSize(new Dimension(180,0));
		
		//begin of nested panel in side panel (sideButtonPanel)
		sideButtonPanel = new JPanel();
		sideButtonPanel.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(30,0,0,0);
		gbc.gridx = 0; gbc.gridy = 0; gbc.ipadx = 25; gbc.ipady = 10;
		
		//begin of nested side panel buttons 
		manageButton = new JButton();
		manageButton.setIcon(new ImageIcon(Icon.manageIcon));
		manageButton.setText("Manage");
		manageButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		manageButton.setFocusPainted(false);
		sideButtonPanel.add(manageButton,gbc);
		
		scheduleButton = new JButton();
		scheduleButton.setIcon(new ImageIcon(Icon.scheduleIcon));
		scheduleButton.setText("Schedule");
		scheduleButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		scheduleButton.setFocusPainted(false);
		gbc.insets = new Insets(40,0,0,0);
		gbc.gridx = 0; gbc.gridy = 1;
		sideButtonPanel.add(scheduleButton,gbc);
		
		reportButton = new JButton();
		reportButton.setIcon(new ImageIcon(Icon.reportIcon));
		reportButton.setText("Report");
		reportButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		reportButton.setFocusPainted(false);
		gbc.gridx = 0; gbc.gridy = 2;
		sideButtonPanel.add(reportButton,gbc);
		
		logoutButton = new JButton();
		logoutButton.setIcon(new ImageIcon(Icon.logoutIcon));
		logoutButton.setText("Logout");
		logoutButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
		logoutButton.setFocusPainted(false);
		gbc.gridx = 0; gbc.gridy = 3;
		sideButtonPanel.add(logoutButton,gbc);
		logoutButton.addActionListener(new ActionListener() {	//ActionListener for logout button
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		//end of nested side panel buttons
		
		sideButtonPanel.setBackground(lightBlue);
		//end of nested panel in side panel (sideButtonPanel)
		
		sidePanel.setBackground(lightBlue);
		sidePanel.add(sideButtonPanel);
		add(sidePanel, BorderLayout.WEST);
		//end of side panel
		
		
		
		//begin of content panel
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(0,15,0,0));
		JScrollPane contentPanelScroller = new JScrollPane(contentPanel);
		contentPanelScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPanelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(contentPanelScroller);
		//end of content panel
		
		
		//set properties for frame
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
//		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//end constructor
	
	
	//begin logout method
	public void logout() {
		int result = JOptionPane.showConfirmDialog(new JFrame(), "Sure? You want to logout?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			System.out.println("Logged out from admin");
			Home home = new Home();
			home.setVisible(true);
			setVisible(false);
		}
	}
	//end logout method
	
	
}

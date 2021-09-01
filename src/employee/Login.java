package employee;

import connection.DatabaseConnection;
import assets.Icon;
import home.Home;
import employee.Index;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.*;

@SuppressWarnings("serial")
public class Login extends JFrame {
	
	JPanel loginPanel = new JPanel();
	JLabel headerLabel = new JLabel();
	JLabel userNameLabel = new JLabel("Username");
	JTextField userNameTextField = new JTextField();
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordTextField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JButton backButton = new JButton();
	JFrame loginStatus = new JFrame();
	DatabaseConnection db = new DatabaseConnection();
	Connection conn;
	ResultSet rs;
	PreparedStatement pstmt;
	
	//constructor begin
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Login()  {
		System.out.println("Login form active");
		setResizable(false);
		setTitle("Login form");
		
		setLayout(new FlowLayout(FlowLayout.CENTER));

		//panel
		loginPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbcLoginPanel = new GridBagConstraints();
		gbcLoginPanel.gridx = 5 ; gbcLoginPanel.gridy = 2;
		//end panel
		
		//header label
		Font headerLabelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 35);
		headerLabel.setFont(headerLabelFont);
		headerLabel.setText("EMPLOYEE");
		Font font = headerLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		headerLabel.setFont(font.deriveFont(attributes));
		gbcLoginPanel.insets = new Insets(70,0,0,0);
		gbcLoginPanel.gridx = 0; gbcLoginPanel.gridy = 0; gbcLoginPanel.gridwidth = 2;
		gbcLoginPanel.anchor = GridBagConstraints.CENTER;
		loginPanel.add(headerLabel,gbcLoginPanel);
		//end header label
		
		//username label
		Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
		userNameLabel.setFont(textFont);
		gbcLoginPanel.insets = new Insets(50,0,0,0);
		gbcLoginPanel.gridx = 0; gbcLoginPanel.gridy = 1; gbcLoginPanel.gridwidth = 1;
		gbcLoginPanel.ipadx = 40; gbcLoginPanel.ipady = 15;
		gbcLoginPanel.anchor = GridBagConstraints.WEST;
		loginPanel.add(userNameLabel,gbcLoginPanel);
		//end username label
		
		//username textField
		gbcLoginPanel.gridx = 1; gbcLoginPanel.gridy = 1;
		gbcLoginPanel.ipadx = 150; gbcLoginPanel.ipady = 10;
		loginPanel.add(userNameTextField, gbcLoginPanel);
		//end username textfield
		
		//Password label
		gbcLoginPanel.insets = new Insets(20,0,0,0);
		passwordLabel.setFont(textFont);
		gbcLoginPanel.gridx = 0; gbcLoginPanel.gridy = 2; 
		gbcLoginPanel.ipadx = 40;
		loginPanel.add(passwordLabel, gbcLoginPanel);
		//end password label

		//password textField
		gbcLoginPanel.gridx = 1; gbcLoginPanel.gridy = 2;
		gbcLoginPanel.ipadx = 150; gbcLoginPanel.ipady = 10;
		loginPanel.add(passwordTextField, gbcLoginPanel);
		//end passwordField
		
		//begin login button
		loginButton.setFocusPainted(false);
		loginButton.setToolTipText("Employee Login");
		gbcLoginPanel.gridx = 0; gbcLoginPanel.gridy = 3;
		gbcLoginPanel.ipadx = 40;
		gbcLoginPanel.anchor = GridBagConstraints.CENTER;
		loginPanel.add(loginButton,gbcLoginPanel);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isLoginSuccess()) {
					Index index = new Index(userNameTextField.getText());
					index.setVisible(true);
					setVisible(false);
				}
			}
		});
		//end login button
		
		//begin reset button
		resetButton.setFocusPainted(false);
		resetButton.setToolTipText("Clear TextFields");
		gbcLoginPanel.gridx = 1; gbcLoginPanel.gridy = 3;
		loginPanel.add(resetButton,gbcLoginPanel);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTextField();
			}
		});
		//end reset button
		
		//backButton
		Image backIcon = new ImageIcon("images/back.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		backButton.setIcon(new ImageIcon(backIcon));
		backButton.setFocusPainted(false);
		backButton.setToolTipText("Back");
		gbcLoginPanel.gridx = 0; gbcLoginPanel.gridy = 4; gbcLoginPanel.gridwidth = 2;
		gbcLoginPanel.ipadx = 5; gbcLoginPanel.ipady = 5;
		gbcLoginPanel.insets = new Insets(30,0,0,0);
		loginPanel.add(backButton, gbcLoginPanel);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home = new Home();
				home.setVisible(true);
				setVisible(false);
			}
		});
		//end backButton
		
		add(loginPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450,120,500,500);
	}
	//constructor end
	

	//begin check Login
	public boolean isLoginSuccess() {
		int flag = 0;	//login false, flag = 1 =>login true
		conn = db.getConnection(conn);
		if (conn == null)
			return false;
		
		String username = userNameTextField.getText();
		String password = String.valueOf(passwordTextField.getPassword());
		String query = "select user_Id,password from employees where user_Id=? and password=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);				
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("Login Successfull");
				
				JOptionPane.showMessageDialog(loginStatus, "Login Successfull", "Login success",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(Icon.successIcon));
				flag = 1;
			} else {
				System.out.println("Username or Password incorrect!");
				JOptionPane.showMessageDialog(loginStatus, "Username or Password incorrect!", "Login failed!", JOptionPane.ERROR_MESSAGE);
				flag = 0;
			}
		} catch (SQLException e) {
			db.logMessage(e.getMessage());
			db.showErrorMessage("DatabaseError", "DatabaseError : " + e.getMessage());
			flag = 0;
		} catch (Exception e) {
			db.logMessage(e.getMessage());
			db.showErrorMessage("DatabaseError", "DatabaseConnectionError!");
			flag = 0;
		} finally {
			db.closeResultSet(rs);
			db.closePreparedStatement(pstmt);
			db.closeConnection(conn);
		}

		if (flag == 0) 
			return false;
		return true;
	}
	//end check login
	
	
	//reset textField
	public void resetTextField() {
		userNameTextField.setText("");
		passwordTextField.setText("");
	}
	//end reset textField
}
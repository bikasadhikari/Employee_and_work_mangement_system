package admin;
import javax.swing.*;
import java.util.Date;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import assets.Icon;


@SuppressWarnings("serial")
public class Manage extends AdminPanels {
	static int countClicks = 0;
	//instance variables
	Font buttonsPanelContentLabel = new Font(Font.SANS_SERIF, Font.PLAIN, 23);
	Font commandButtonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
	Color gray = new Color(228, 228, 228 );
	
	//first panel
	JPanel buttonsPanel;
	JPanel buttonsPanelContent;
	JPanel searchPanel;
	JLabel searchLabel;
	JPanel searchSrButtonPanel;
	JTextField searchTextField;
	String searchByOptions[] = {"ID","Name","Dept"};
	JComboBox<String> searchFilter;
	JPanel commandsPanel;
	JLabel commandsLabel;
	JPanel commandButtonsPanel;
	JButton addButton;
	JButton editButton;
	JButton deleteButton;
	JButton clearButton;
	JButton refreshButton;
	//end first panel
	
	//second panel
	JPanel textFieldPanel;
	JPanel textFieldContentPanel;
	JPanel textFieldContentPanelLayer1;
	GridBagConstraints labelTextFieldPanelGbc;
	//end second panel
	
	//third panel
	JPanel dataPanel;
	DefaultTableModel defaultTableModel;
	JTable table;
	JPopupMenu popupMenu;
	JMenuItem incrementSalaryMenuItem;
	static int popupMenuId;
	//third panel
	
	JTextField idTextField;
	JTextField fNameTextField;
	JTextField lNameTextField;
	JTextField jobTitleTextField;
	JTextField deptTextField;
	JTextField salaryTextField;
	JTextField dobTextField;
	JTextField phoneTextField;
	JTextField userIdTextField;
	JTextField passTextField;
	
	//Database Variables
	String id;
	String fName;
	String lName;
	String jobTitle;
	String dept;
	String dob;
	String salary;
	String phone;
	String userId;
	String password;
	//end database variables
	//end of instance variables
	
	
	//constructor
	public Manage() {
		super.contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.X_AXIS));
		createButtonsPanel();
		createTextFieldsPanel();
		createDataFieldPanel();
	}
	//end constructor
	
	
	
	//Begin of creating buttonsPanel
	public void createButtonsPanel() {
		buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(200,0));
		buttonsPanelContent = new JPanel();
		buttonsPanelContent.setLayout(new GridBagLayout());
		
		GridBagConstraints buttonsPanelContentGbc = new GridBagConstraints();
		buttonsPanelContentGbc.gridx = 0; buttonsPanelContentGbc.gridy = 0;
		buttonsPanelContentGbc.insets = new Insets(25,0,0,0);
		
		searchPanel = new JPanel(new GridBagLayout());
		searchPanel.setPreferredSize(new Dimension(220,100));
		GridBagConstraints searchPanelGbc = new GridBagConstraints();
		searchPanel.setBackground(gray);
		searchLabel = new JLabel("Search");
		searchLabel.setFont(buttonsPanelContentLabel);
		searchPanelGbc.insets = new Insets(0,0,10,0);
		searchPanelGbc.anchor = GridBagConstraints.CENTER;
		searchPanelGbc.gridx = 0; searchPanelGbc.gridy = 0; searchPanelGbc.gridwidth = 2;
		searchPanel.add(searchLabel, searchPanelGbc);
		
		searchSrButtonPanel = new JPanel(new FlowLayout());
		searchSrButtonPanel.setBackground(gray);
		searchTextField = new JTextField();
		searchTextField.setToolTipText("Search with ID or name or userID");
		searchTextField.setPreferredSize(new Dimension(110,30));
		searchSrButtonPanel.add(searchTextField);
		
		searchFilter = new JComboBox<String>(searchByOptions);
		searchFilter.setSelectedIndex(0);
		searchFilter.setPreferredSize(new Dimension(70, 29));
		searchSrButtonPanel.add(searchFilter);
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {	
				searchEmployees(searchStr());
			}
			public void removeUpdate(DocumentEvent e) {
				searchEmployees(searchStr());
			}
			public void insertUpdate(DocumentEvent e) {
				searchEmployees(searchStr());
			}
			
			public String searchStr() {
				String srcString = searchTextField.getText();
				return srcString;
			}
			
			public void searchEmployees(String srcString) {
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>((DefaultTableModel) table.getModel());
				table.setRowSorter(tr);
				int column = 0;
				if (searchFilter.getSelectedIndex() == 1) 
					column = 1;
				else if (searchFilter.getSelectedIndex() == 2)
					column = 4;
				tr.setRowFilter(RowFilter.regexFilter(srcString.trim(),column));
			}
		});
		
		searchPanelGbc.gridx = 0;searchPanelGbc.gridy = 1;
		searchPanelGbc.insets = new Insets(0,0,0,0);
		searchPanel.add(searchSrButtonPanel, searchPanelGbc);
		buttonsPanelContent.add(searchPanel,buttonsPanelContentGbc);
		
		commandsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints commandsPanelGbc = new GridBagConstraints();
		commandsPanel.setPreferredSize(new Dimension(220,400));
		buttonsPanelContentGbc.gridx = 0; buttonsPanelContentGbc.gridy = 1;
		commandsPanel.setBackground(gray);
		commandsLabel = new JLabel("Commands");
		commandsLabel.setFont(buttonsPanelContentLabel);
		commandsPanelGbc.gridx = 0; commandsPanelGbc.gridy = 0;
		commandsPanelGbc.insets = new Insets(-15,0,10,0);
		commandsPanel.add(commandsLabel, commandsPanelGbc);
		
		commandButtonsPanel = new JPanel();
		commandButtonsPanel.setLayout(new GridLayout(5,1,0,30));
		addButton = new JButton();
		addButton.setText("Add");
		addButton.setIcon(new ImageIcon(Icon.addIcon));

		editButton = new JButton();
		editButton.setText("Edit");
		editButton.setIcon(new ImageIcon(Icon.editIcon));
		deleteButton = new JButton();
		deleteButton.setText("Delete");
		deleteButton.setIcon(new ImageIcon(Icon.deleteIcon));
		clearButton = new JButton();
		clearButton.setText("Clear");
		clearButton.setIcon(new ImageIcon(Icon.clearIcon));
		refreshButton = new JButton();
		refreshButton.setToolTipText("Refresh data fields in table");
		refreshButton.setText("Refresh");
		refreshButton.setIcon(new ImageIcon(Icon.refreshIcon));
		
		addButton.setFont(commandButtonFont);
		editButton.setFont(commandButtonFont);
		deleteButton.setFont(commandButtonFont);
		clearButton.setFont(commandButtonFont);
		refreshButton.setFont(commandButtonFont);
		
		addButton.setFocusPainted(false);
		editButton.setFocusPainted(false);
		deleteButton.setFocusPainted(false);
		clearButton.setFocusPainted(false);
		refreshButton.setFocusPainted(false);
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isEmptyTextField()) {
					JOptionPane.showMessageDialog(new JOptionPane(), "TextFields are empty! Please enter all the details", "Alert!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (!isPhoneNumberValid()) {
					return;
				}
				if (isUserIdExists()) {
					JOptionPane.showMessageDialog(new JOptionPane(), "UserId exists! Please choose a different UserId", "Alert!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				addNewEmployee();
			}
		});
		
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JOptionPane(), "Select employee from the table to edit!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (isEmptyTextField()) {
					JOptionPane.showMessageDialog(new JOptionPane(), "TextFields are empty! Please enter all the details", "Alert!", JOptionPane.INFORMATION_MESSAGE);
					return;
				} 
				if (!isPhoneNumberValid()) {
					return;
				}
				int status = JOptionPane.showConfirmDialog(new JOptionPane(),"Do you want to change UserId ?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (status == JOptionPane.YES_OPTION) {
					if (isUserIdExists()) {
						JOptionPane.showMessageDialog(new JOptionPane(), "UserId exists! Please choose a different UserId", "Alert!", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				editEmployee();
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(idTextField.getText());
				int flag = JOptionPane.showConfirmDialog(new JOptionPane(), "Delete Employee of id : " + id + "\nAre you sure ?", "Confirm", JOptionPane.YES_NO_OPTION);
				if ( flag == JOptionPane.YES_OPTION ) {
					System.out.println("Confirmed for delete of ID : " + id);
					connection.DatabaseConnection db = new connection.DatabaseConnection();
					Connection conn = null;
					conn = db.getConnection(conn);
					String query = "Delete from employees where id=?";
					try {
						PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, id);
						pstmt.executeUpdate();
						clearAllTextFields();
						refreshButton.doClick();
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
						db.showErrorMessage("Alert!", "Something went wrong!\n" + e1.getLocalizedMessage());
					}
				}
			}
		});
		
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllTextFields();
				System.out.println("Cleared all textFields");
				table.getSelectionModel().clearSelection();
			}
		});
		
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Refresh button clicked");
				defaultTableModel.setRowCount(0);
				displayData();
				System.out.println("JTable data is refreshed..");
			}
		});
		
		commandButtonsPanel.add(addButton);
		commandButtonsPanel.add(editButton);
		commandButtonsPanel.add(deleteButton);
		commandButtonsPanel.add(clearButton);
		commandButtonsPanel.add(refreshButton);
		
		commandsPanelGbc.gridx = 0; commandsPanelGbc.gridy = 1;
		commandsPanelGbc.insets = new Insets(20,0,10,0);
		commandButtonsPanel.setBackground(gray);
		commandsPanel.add(commandButtonsPanel,commandsPanelGbc);
		buttonsPanelContent.add(commandsPanel,buttonsPanelContentGbc);
		
		buttonsPanel.add(buttonsPanelContent);
		contentPanel.add(buttonsPanel);
	}
	//End of creating buttonsPanel
	
	
	//Begin of creating textFieldPanel
	public void createTextFieldsPanel() {
		textFieldPanel = new JPanel();
		textFieldPanel.setPreferredSize(new Dimension(250,0));
		
		textFieldContentPanelLayer1 = new JPanel(new GridBagLayout());
		GridBagConstraints textFieldContentPanelLayer1Gbc = new GridBagConstraints();
		
		textFieldContentPanel = new JPanel(new GridBagLayout());
		textFieldContentPanel.setPreferredSize(new Dimension(250,580));
		textFieldContentPanel.setBackground(gray);
		
		GridBagConstraints textFieldContentPanelGbc = new GridBagConstraints();
		idTextField = new JTextField();
		idTextField.setEditable(false);
		JPanel idPanel = createLabelTextFieldPanel("Employee ID", idTextField, textFieldContentPanelGbc, 0);
		textFieldContentPanel.add(idPanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		fNameTextField = new JTextField();
		JPanel fNamePanel = createLabelTextFieldPanel("First Name", fNameTextField, textFieldContentPanelGbc, 1);
		textFieldContentPanel.add(fNamePanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		lNameTextField = new JTextField();
		JPanel lNamePanel = createLabelTextFieldPanel("Last Name", lNameTextField, textFieldContentPanelGbc, 2);
		textFieldContentPanel.add(lNamePanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		jobTitleTextField = new JTextField();
		JPanel jobTitlePanel = createLabelTextFieldPanel("Job Title", jobTitleTextField, textFieldContentPanelGbc, 3);
		textFieldContentPanel.add(jobTitlePanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		deptTextField = new JTextField();
		JPanel deptPanel = createLabelTextFieldPanel("Department", deptTextField, textFieldContentPanelGbc, 4);
		textFieldContentPanel.add(deptPanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		salaryTextField = new JTextField();
		JPanel salaryPanel = createLabelTextFieldPanel("Salary", salaryTextField, textFieldContentPanelGbc, 5);
		textFieldContentPanel.add(salaryPanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		dobTextField = new JTextField();
		JPanel dobPanel = createLabelTextFieldPanel("Date Of Birth", dobTextField, textFieldContentPanelGbc, 6);
		textFieldContentPanel.add(dobPanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		phoneTextField = new JTextField();
		JPanel phonePanel = createLabelTextFieldPanel("Phone", phoneTextField, textFieldContentPanelGbc, 7);
		textFieldContentPanel.add(phonePanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		userIdTextField = new JTextField();
		JPanel userIdPanel = createLabelTextFieldPanel("User ID", userIdTextField, textFieldContentPanelGbc, 8);
		textFieldContentPanel.add(userIdPanel,textFieldContentPanelGbc);
		
		textFieldContentPanelGbc = new GridBagConstraints();
		passTextField = new JTextField();
		JPanel passPanel = createLabelTextFieldPanel("Password", passTextField, textFieldContentPanelGbc, 9);
		textFieldContentPanel.add(passPanel,textFieldContentPanelGbc);
		
		textFieldContentPanelLayer1Gbc.insets = new Insets(25,0,0,0);
		textFieldContentPanelLayer1.add(textFieldContentPanel,textFieldContentPanelLayer1Gbc);
		textFieldPanel.add(textFieldContentPanelLayer1);
		contentPanel.add(textFieldPanel);
	}
	//End of creating textFieldPanel

	
	//method for creating data field panel
	public void createDataFieldPanel() {
		dataPanel = new JPanel(new FlowLayout());
		dataPanel.setBorder(new EmptyBorder(25,0,0,0));
	    defaultTableModel = new DefaultTableModel();
	    table = new JTable(defaultTableModel) {
	    	public boolean isCellEditable(int row, int column) {
	    		return false;
	    	}
	    };
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setRowSelectionAllowed(true);
	    table.setRowHeight(25);
	    table.setPreferredScrollableViewportSize(new Dimension(600, 500));
	    table.setFillsViewportHeight(false);
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		if (SwingUtilities.isLeftMouseButton(e) == true) {
	    			countClicks++;
	    			int rowSelected = table.rowAtPoint(e.getPoint());
	    			System.out.println("Left click selected row = " + rowSelected);
	    			int id = Integer.parseInt((table.getModel().getValueAt(rowSelected, 0)).toString());
	    			displayTextFields(id);
	    		}
	    		if (SwingUtilities.isRightMouseButton(e) == true) {
	    			countClicks++;
	    			if (countClicks == 1) {
	    				Robot r;
						try {
							r = new Robot();
							r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
							r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
						} catch (AWTException e1) {
							System.out.println(e1.getMessage());
						}
	    			}
	    			showJtablePopupMenu();
	    		}
	    	}
	    });
	    JScrollPane tableScroller = new JScrollPane(table);
	    tableScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    tableScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    dataPanel.add(tableScroller);
	    defaultTableModel.addColumn("ID");
	    defaultTableModel.addColumn("First Name");
	    defaultTableModel.addColumn("Last Name");
	    defaultTableModel.addColumn("Job Title");
	    defaultTableModel.addColumn("Department");
	    defaultTableModel.addColumn("Shift");
	    defaultTableModel.addColumn("Salary");
	    defaultTableModel.addColumn("Date Of Birth");
	    defaultTableModel.addColumn("Phone");
	    defaultTableModel.addColumn("Date of join");
	    defaultTableModel.addColumn("User ID");
	    defaultTableModel.addColumn("Password");
	    TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(80);
	    columnModel.getColumn(1).setPreferredWidth(100);
	    columnModel.getColumn(2).setPreferredWidth(100);
	    columnModel.getColumn(3).setPreferredWidth(150);
	    columnModel.getColumn(4).setPreferredWidth(130);
	    columnModel.getColumn(5).setPreferredWidth(100);
	    columnModel.getColumn(6).setPreferredWidth(100);
	    columnModel.getColumn(7).setPreferredWidth(130);
	    columnModel.getColumn(8).setPreferredWidth(100);
	    columnModel.getColumn(9).setPreferredWidth(100);
	    columnModel.getColumn(10).setPreferredWidth(100);
	    for (int i=0;i<=9;i++) 
	    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	        
	    displayData();
		contentPanel.add(dataPanel);
	}
	//end of method for creating data field panel
	
	
	//method for creating textFields in textFieldPanel
	private JPanel createLabelTextFieldPanel(String labelText, JTextField textField, GridBagConstraints textFieldContentPanelGbc, int textFieldContentPanelGbcRow) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(gray);
		JLabel label = new JLabel(labelText);
		JTextField tf = textField;
		textFieldContentPanelGbc.gridx = 0; textFieldContentPanelGbc.gridy = textFieldContentPanelGbcRow;
		textFieldContentPanelGbc.insets = new Insets(0,0,10,0);
		labelTextFieldPanelGbc = new GridBagConstraints();
		labelTextFieldPanelGbc.gridx = 0; labelTextFieldPanelGbc.gridy = 0;
		labelTextFieldPanelGbc.anchor = GridBagConstraints.WEST;
		panel.add(label,labelTextFieldPanelGbc);
		labelTextFieldPanelGbc.gridx = 0; labelTextFieldPanelGbc.gridy = 1;
		labelTextFieldPanelGbc.ipadx = 200; labelTextFieldPanelGbc.ipady = 10;
		panel.add(tf,labelTextFieldPanelGbc);
		return panel;
	}
	//end of method createLabelTextFieldPanel
	
	
	//method for displaying all employee records in JTable
	private void displayData() {
		connection.DatabaseConnection db = new connection.DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
			try {
				Statement statement = conn.createStatement();
				String query = "select * from employees order by id";
				ResultSet resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					String id = resultSet.getString("id");
			        String fname = resultSet.getString("fname");
			        String lname = resultSet.getString("lname");
			        String jobTitle = resultSet.getString("job_title");
			        String dept = resultSet.getString("dept");
			        String salary = resultSet.getString("salary");
			        String dob = resultSet.getString("dob");
			        String phone = resultSet.getString("phone");
			        String dateOfJoin = resultSet.getString("date_of_join");
			        String userId = resultSet.getString("user_id");
			        String password = resultSet.getString("password");
			        String shift = resultSet.getString("shift");
			        defaultTableModel.addRow(new Object[]{id, fname, lname, jobTitle, dept, shift, salary, dob, phone, dateOfJoin, userId, password});
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(new JOptionPane(), "Something went wrong!", "Alert", JOptionPane.ERROR_MESSAGE);
				System.out.println(e.getMessage());
			} finally {
				db.closeConnection(conn);
			}
	}
	//end method for displaying records in JTable
	
	
	//method for displaying pop up menu in JTable
	private void showJtablePopupMenu() {
		popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                        popupMenuId = Integer.parseInt((table.getModel().getValueAt(rowAtPoint, 0)).toString());
                        displayTextFields(popupMenuId);
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
		incrementSalaryMenuItem = new JMenuItem("Increment salary");
		incrementSalaryMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connection.DatabaseConnection db = new connection.DatabaseConnection();
				String popupName = "";
				String popupSalary = "";
				String query = "Select fname,salary from employees where id=?";
				Connection conn = null;
				conn = db.getConnection(conn);
				try {
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, popupMenuId);
					ResultSet resultSet = pstmt.executeQuery();
					if (resultSet.next()) {
						popupName = resultSet.getString("fname");
						popupSalary = String.valueOf(resultSet.getLong("salary"));
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(new JOptionPane(), "Something went wrong!", "Alert", JOptionPane.ERROR_MESSAGE);
					System.out.println(e1.getMessage());
				} finally {
					db.closeConnection(conn);
				}
				
				JFrame salaryIncrementFrame = new JFrame("Salary");
				salaryIncrementFrame.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				JLabel headingLabel = new JLabel("Increment salary");
				headingLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 23));
				JLabel idLabel = new JLabel("Employee ID");
				JTextField idValueLabel = new JTextField(Integer.toString(popupMenuId));
				idValueLabel.setPreferredSize(new Dimension(100,20));
				idValueLabel.setEditable(false);
				JLabel nameLabel = new JLabel("Name");
				JTextField nameValueLabel = new JTextField(popupName);
				nameValueLabel.setEditable(false);
				nameValueLabel.setPreferredSize(new Dimension(100,20));
				JLabel salaryLabel = new JLabel("Current Salary");
				JTextField salaryValueLabel = new JTextField(popupSalary);
				salaryValueLabel.setEditable(false);
				salaryValueLabel.setPreferredSize(new Dimension(100,20));
				JLabel incrementLabel = new JLabel("Increment (%)");
				JTextField incrementValueTextField = new JTextField();
				incrementValueTextField.setPreferredSize(new Dimension(100,20));
				JButton saveButton = new JButton("Save");
				Image saveIcon = new ImageIcon("images/save.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				saveButton.setIcon(new ImageIcon(saveIcon));
				saveButton.setFocusPainted(false);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveIncrementSalarySaved(salaryIncrementFrame,Integer.parseInt(idValueLabel.getText()),incrementValueTextField.getText(),salaryValueLabel.getText());
					}
				});
				gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth =2; gbc.insets = new Insets(0,0,20,0);
				salaryIncrementFrame.add(headingLabel,gbc);gbc.anchor = GridBagConstraints.CENTER;
				gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST; gbc.gridwidth = 1;
				salaryIncrementFrame.add(idLabel,gbc);
				gbc.gridx = 1; gbc.gridy = 1;gbc.insets = new Insets(0,10,20,0);
				salaryIncrementFrame.add(idValueLabel,gbc);
				gbc.gridx = 0; gbc.gridy = 2;gbc.insets = new Insets(0,0,20,0);
				salaryIncrementFrame.add(nameLabel,gbc);
				gbc.gridx = 1; gbc.gridy = 2;gbc.insets = new Insets(0,10,20,0);
				salaryIncrementFrame.add(nameValueLabel,gbc);
				gbc.gridx = 0; gbc.gridy = 3;gbc.insets = new Insets(0,0,20,0);
				salaryIncrementFrame.add(salaryLabel,gbc);
				gbc.gridx = 1; gbc.gridy = 3;gbc.insets = new Insets(0,10,20,0);
				salaryIncrementFrame.add(salaryValueLabel,gbc);
				gbc.gridx = 0; gbc.gridy = 4;gbc.insets = new Insets(0,0,20,0);
				salaryIncrementFrame.add(incrementLabel,gbc);
				gbc.gridx = 1; gbc.gridy = 4;gbc.insets = new Insets(0,10,20,0);
				salaryIncrementFrame.add(incrementValueTextField,gbc);
				gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0,0,20,0);
				salaryIncrementFrame.add(saveButton,gbc);
				salaryIncrementFrame.setLocation(table.getLocationOnScreen());
				salaryIncrementFrame.setSize(300,300);
				salaryIncrementFrame.setResizable(false);
				salaryIncrementFrame.setVisible(true);
			}
		});
		
		JMenuItem shiftMenuItem = new JMenuItem("Change Shift");
		shiftMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				connection.DatabaseConnection db = new connection.DatabaseConnection();
				Connection conn = null;
				conn = db.getConnection(conn);
				int shiftValue = 0;
				try {
					PreparedStatement pstmt = conn.prepareStatement("select shift from employees where id=?");
					pstmt.setInt(1,popupMenuId);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						shiftValue = rs.getInt("shift");
					}
				} catch(Exception e2) {
					e2.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				JFrame shiftFrame = new JFrame("Shift");
				shiftFrame.setLayout(new GridBagLayout());
				GridBagConstraints sgbc = new GridBagConstraints();
				sgbc.gridx=0;sgbc.gridy=0;sgbc.gridwidth=2;
				sgbc.insets = new Insets(0,0,20,0);
				JLabel headerLabel = new JLabel("Change Shift");
				headerLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,23));
				shiftFrame.add(headerLabel,sgbc);
				sgbc.gridx=0;sgbc.gridy=1;sgbc.gridwidth=1;
				sgbc.insets = new Insets(0,0,20,10);
				JLabel empLabel = new JLabel("Employee ID");
				shiftFrame.add(empLabel,sgbc);
				sgbc.gridx=1;sgbc.gridy=1;sgbc.gridwidth=1;
				JTextField empValue = new JTextField(Integer.toString(popupMenuId));
				empValue.setEditable(false);
				empValue.setPreferredSize(new Dimension(100,30));
				shiftFrame.add(empValue,sgbc);
				sgbc.gridx=0;sgbc.gridy=2;sgbc.gridwidth=1;
				JLabel slabel = new JLabel("Shift");
				shiftFrame.add(slabel,sgbc);
				sgbc.gridx=1;sgbc.gridy=2;sgbc.gridwidth=1;
				String shifts[] = {"1","2","3"};
				JComboBox<String> sbox = new JComboBox<String>(shifts);
				sbox.setSelectedItem(Integer.toString(shiftValue));
				sbox.setPreferredSize(new Dimension(100,30));
				shiftFrame.add(sbox,sgbc);
				sgbc.gridx=0;sgbc.gridy=3;sgbc.gridwidth=2;
				sgbc.insets = new Insets(0,0,20,0);
				JButton sbtn = new JButton("Save");
				sbtn.setPreferredSize(new Dimension(100,30));
				shiftFrame.add(sbtn,sgbc);
				
				sbtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int id = Integer.parseInt(empValue.getText());
						int shift = Integer.parseInt((String) sbox.getSelectedItem());
						connection.DatabaseConnection db = new connection.DatabaseConnection();
						Connection conn = null;
						conn = db.getConnection(conn);
						try {
							PreparedStatement pstmt = conn.prepareStatement("update employees set shift=? where id=?");
							pstmt.setInt(1, shift);
							pstmt.setInt(2, id);
							int flag = pstmt.executeUpdate();
							if (flag != 0) {
								shiftFrame.setVisible(false);
							}
						} catch (Exception e3) {
							e3.printStackTrace();
						} finally {
							try {
								conn.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							refreshButton.doClick();
						}
					}
					
				});
				
				shiftFrame.setSize(300,300);
				shiftFrame.setLocation(table.getLocationOnScreen());
				shiftFrame.setResizable(false);
				shiftFrame.setVisible(true);
			}
			
		});

		popupMenu.add(shiftMenuItem);
		
		popupMenu.add(incrementSalaryMenuItem);
		table.setComponentPopupMenu(popupMenu);
	}
	//End of popUp menu
	
	
	
	//Method for saving the salary increment from the JTable popUp menu
	private void saveIncrementSalarySaved(JFrame salaryIncrementFrame,int id, String salaryIncrementValue, String salary) {
		int incPercent = Integer.parseInt(salaryIncrementValue);
		long curSalary = Long.parseLong(salary);
		long newSalary = ((curSalary / 100) * incPercent) + curSalary;
		System.out.println(curSalary + " " + incPercent + " " + newSalary);
		connection.DatabaseConnection db = new connection.DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "update employees set salary=? where id=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, newSalary);
			pstmt.setInt(2, id);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				JOptionPane.showMessageDialog(new JOptionPane(), "Salary incremented of ID = " + id);
				refreshButton.doClick();
			}
		} catch (SQLException e) {
			db.showErrorMessage("Error!", "Something went wrong!");
		} finally {
			db.closeConnection(conn);
		}
		salaryIncrementFrame.setVisible(false);
	}
	//End of Method for saving the salary increment from the JTable popUp menu
	
	
	//method for displaying values in all textFields from database(employees)
	private void displayTextFields(int empId) {
		connection.DatabaseConnection db = new connection.DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		System.out.println("Fetching data for empId = " + empId);
		try {
			String query = "select * from employees where id=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				id = String.valueOf(rs.getInt("id"));		
				idTextField.setText(id);
				fName = rs.getString("fname");
				lName = rs.getString("lname");
				jobTitle = rs.getString("job_title");
				dept = rs.getString("dept");
				salary = String.valueOf(rs.getInt("salary"));
				dob = rs.getString("dob");
				phone = String.valueOf(rs.getLong("phone"));
				userId = rs.getString("user_id");
				password = rs.getString("password");
				setTextFields();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JOptionPane(), "Something went wrong!", "Alert", JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getMessage());
		} finally {
			db.closeConnection(conn);
		}
	}
	//end method for displaying values in all textFields from database(employees)

	
	//method for setting the values of the textFields
	private void setTextFields() {
		idTextField.setText(id);
		fNameTextField.setText(fName);
		lNameTextField.setText(lName);
		jobTitleTextField.setText(jobTitle);
		deptTextField.setText(dept);
		salaryTextField.setText(salary);
		dobTextField.setText(dob);
		phoneTextField.setText(phone);
		userIdTextField.setText(userId);
		passTextField.setText(password);
	}
	//end of method for setting the values of the textFields
	
	
	//method for clearing all the textFields
	private void clearAllTextFields() {
		searchFilter.setSelectedIndex(0);
		searchTextField.setText("");
		idTextField.setText("");
		fNameTextField.setText("");
		lNameTextField.setText("");
		jobTitleTextField.setText("");
		deptTextField.setText("");
		salaryTextField.setText("");
		dobTextField.setText("");
		phoneTextField.setText("");
		userIdTextField.setText("");
		passTextField.setText("");
	}
	//End of method for clearing all the textFields
	
	
	//check whether textFields are empty
	private boolean isEmptyTextField() {
		if (fNameTextField.getText().isEmpty() || lNameTextField.getText().isEmpty() || jobTitleTextField.getText().isEmpty() || deptTextField.getText().isEmpty() || salaryTextField.getText().isEmpty() || dobTextField.getText().isEmpty() || phoneTextField.getText().isEmpty() || userIdTextField.getText().isEmpty() || passTextField.getText().isEmpty())
			return true;
		return false;
	}
	//end of method isEmptyTextField
	
	
	private boolean isPhoneNumberValid() {
		int phone = phoneTextField.getText().length();
		if (phone != 10) {
			connection.DatabaseConnection db = new connection.DatabaseConnection();
			db.showErrorMessage("Alert!", "Phone number should be exact 10 digits!");
			return false;
		}
		return true;
	}
	
	
	//method for adding new employee
	private void addNewEmployee() {
		String fname = fNameTextField.getText();
		String lname = lNameTextField.getText();
		String jobTitle = jobTitleTextField.getText();
		String dept = deptTextField.getText();
		String dob = dobTextField.getText();
		long phone, salary;
		try {
			phone = Long.parseLong(phoneTextField.getText());
			salary = Long.parseLong(salaryTextField.getText());
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(new JOptionPane(), "Phone and salary should only be numeric!", "Alert!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String userId = userIdTextField.getText();
		String password = passTextField.getText();
		
		
		connection.DatabaseConnection db = new connection.DatabaseConnection();
		String joinDate = (new Date()).toString();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "insert into employees(fName,lName,job_title,dept,dob,phone,user_Id,password,salary,date_of_join) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, jobTitle);
			pstmt.setString(4, dept);
			pstmt.setString(5, dob);
			pstmt.setLong(6, phone);
			pstmt.setString(7, userId);
			pstmt.setString(8, password);
			pstmt.setLong(9, salary);
			pstmt.setString(10,joinDate);
			int flag = pstmt.executeUpdate();
			if (flag == 1) {
				JOptionPane.showMessageDialog(new JOptionPane(), "Data inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Icon.successIcon));
				clearAllTextFields();
				refreshButton.doClick();
				return;
			}
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			db.showErrorMessage("Alert!", "Something went wrong!\n" + e1.getLocalizedMessage());
		}
	}
	//end of method for adding new employee
	
	
	//method for editing employees
	private void editEmployee() {
		String fname = fNameTextField.getText();
		String lname = lNameTextField.getText();
		String jobTitle = jobTitleTextField.getText();
		String dept = deptTextField.getText();
		String dob = dobTextField.getText();
		long phone, salary;
		try {
			phone = Long.parseLong(phoneTextField.getText());
			salary = Long.parseLong(salaryTextField.getText());
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(new JOptionPane(), "Phone and salary should only be numeric!", "Alert!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String userId = userIdTextField.getText();
		String password = passTextField.getText();
		int id = Integer.parseInt(idTextField.getText());
		
		connection.DatabaseConnection db = new connection.DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "update employees set fName=?, lName=?, job_title=?, dept=?, dob=?, phone=?, user_Id=?, password=?, salary=? where id=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, jobTitle);
			pstmt.setString(4, dept);
			pstmt.setString(5, dob);
			pstmt.setLong(6, phone);
			pstmt.setString(7, userId);
			pstmt.setString(8, password);
			pstmt.setLong(9, salary);
			pstmt.setInt(10, id);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(new JOptionPane(), "Employee updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Icon.successIcon));
			clearAllTextFields();
			refreshButton.doClick();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			db.showErrorMessage("Alert!", "Something went wrong!\n" + e1.getLocalizedMessage());
		}
	}
	//end of method for editing employees
	
	
	private boolean isUserIdExists() {
		connection.DatabaseConnection db = new connection.DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "Select user_Id from employees where user_Id=?";
		int flag = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userIdTextField.getText());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = 1;
			} 
		} catch (Exception e) {
			db.showErrorMessage("Error!", "Something went wrong!");
			System.out.println(e.getLocalizedMessage());
			flag = 1;
		}
		if (flag == 0)
			return false;
		return true;
	}
}

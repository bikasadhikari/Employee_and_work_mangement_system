package admin;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import assets.Icon;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import connection.DatabaseConnection;

public class Projects extends AdminPanels{
	private static final long serialVersionUID = 1L;
	static int countClicks = 0;
	JPopupMenu popupMenu;
	JMenuItem changeMenu;
	static int popupMenuId;
	JTable table;
	Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
	DefaultTableModel dm;
	static int selId;
	JButton refreshBtn;
	
	public Projects() {
		super.contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
		createProjectField();
		displayProjects();
		createProjectTable();
	}
	
	public void createProjectField() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,10);
		
		JLabel projectNameLabel = new JLabel("Project Name");
		projectNameLabel.setFont(font1);
		panel.add(projectNameLabel,gbc);
		gbc.gridx=1;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,20);
		JTextField pNametf = new JTextField("");
		pNametf.setPreferredSize(new Dimension(110,30));
		panel.add(pNametf,gbc);
		
		gbc.gridx=2;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,10);
		JLabel manhrLabel = new JLabel("Man hours");
		manhrLabel.setFont(font1);
		panel.add(manhrLabel,gbc);
		gbc.gridx=3;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,20);
		JTextField hrTf = new JTextField("");
		hrTf.setPreferredSize(new Dimension(110,30));
		panel.add(hrTf,gbc);
		
		gbc.gridx=4;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,10);
		JLabel deptLabel = new JLabel("Department");
		deptLabel.setFont(font1);
		panel.add(deptLabel,gbc);
		gbc.gridx=5;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,20);
		JTextField deptTf = new JTextField("");
		deptTf.setPreferredSize(new Dimension(110,30));
		panel.add(deptTf,gbc);
		
		gbc.gridx=6;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,10);
		JLabel stLabel = new JLabel("Status");
		stLabel.setFont(font1);
		panel.add(stLabel,gbc);
		gbc.gridx=7;gbc.gridy=0;gbc.insets = new Insets(-60,0,0,20);
		String[] st = {"Open","Closed"};
		JComboBox<String> status = new JComboBox<String>(st);
		status.setPreferredSize(new Dimension(110,30));
		panel.add(status,gbc);
		
		gbc.gridx=0;gbc.gridy=1;gbc.gridwidth=8;gbc.insets = new Insets(0,0,-60,20);
		JButton saveButton = new JButton("Add");
		saveButton.setFocusPainted(false);
		gbc.ipadx = 20;gbc.ipady=10;
		panel.add(saveButton,gbc);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pname = "";
				int manHrs = 0;
				String dept = "";
				String statusValue = "";
				try {
				pname = pNametf.getText();
				dept = deptTf.getText();
				statusValue = (String) status.getSelectedItem();
				manHrs = Integer.parseInt(hrTf.getText());
				} catch (Exception e4) {
					System.out.println(e4.getLocalizedMessage());
				}
				if (pname.equals("") || dept.equals("") || statusValue.equals("") || Integer.toString(manHrs).equals("0")) {
					JOptionPane.showMessageDialog(new JFrame(), "All fields are mandatory");
					return;
				}
				
				DatabaseConnection db = new DatabaseConnection();
				Connection conn = null;
				conn = db.getConnection(conn);
				String query = "insert into projects (pname,manhrs,dept,status) values(?,?,?,?)";
				try {
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, pname);
					pstmt.setInt(2, manHrs);
					pstmt.setString(3, dept);
					pstmt.setString(4, statusValue);
					int flag = pstmt.executeUpdate();
					if (flag == 1) {
						JOptionPane.showMessageDialog(new JFrame(), "Project saved", "",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(Icon.successIcon));
						refreshBtn.doClick();
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		

		panel.setPreferredSize(new Dimension(0,200));
		contentPanel.add(panel);
	}
	
	public void displayProjects() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;gbc.gridy=0;gbc.insets = new Insets(0,0,0,15);
		JLabel searchLabel = new JLabel("Search");
		searchLabel.setFont(font1);
		panel.add(searchLabel,gbc);
		
		gbc.gridx = 1;gbc.gridy=0;
		JTextField sTf = new JTextField();
		sTf.setPreferredSize(new Dimension(150,30));
		panel.add(sTf,gbc);
		
		gbc.gridx = 2;gbc.gridy=0;
		JButton sBtn = new JButton("Search");
		sBtn.setFocusPainted(false);
		sBtn.setPreferredSize(new Dimension(110,30));
		panel.add(sBtn,gbc);
		
		gbc.gridx=3;gbc.gridy=0;
		refreshBtn = new JButton("Refresh");
		refreshBtn.setPreferredSize(new Dimension(110,30));
		refreshBtn.setFocusPainted(false);
		panel.add(refreshBtn,gbc);
		
		refreshBtn.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				dm.setRowCount(0);
				DatabaseConnection db = new DatabaseConnection();
				Connection conn = null;
				conn = db.getConnection(conn);
				String query = "Select * from projects";
				try {
					PreparedStatement pstmt = conn.prepareStatement(query);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						int id = rs.getInt("id");
						String pname = rs.getString("pname");
						int manHr = rs.getInt("manhrs");
						String dept = rs.getString("dept");
						String status = rs.getString("status");
						
						dm.addRow(new Object[] {id,pname,manHr,dept,status});
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
				}
			}
			
		});
		
		sBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dm.setRowCount(0);
				String srchValue = sTf.getText();
				System.out.println(srchValue);
				DatabaseConnection db = new DatabaseConnection();
				Connection conn = null;
				conn = db.getConnection(conn);
				try {
					String query = "Select * from projects where pname LIKE ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1,"%"+srchValue+"%");
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						System.out.println(rs.getString("pname"));
						dm.addRow(new Object[] {rs.getInt("id"),rs.getString("pname"),rs.getInt("manhrs"),rs.getString("dept"),rs.getString("status")});
					}
				} catch(Exception e4) {
					e4.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		

		panel.setPreferredSize(new Dimension(0,100));
		contentPanel.add(panel);
	}
	
	public void createProjectTable() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		dm = new DefaultTableModel();
		dm.addColumn("ID");
		dm.addColumn("Project Name");
		dm.addColumn("Man Hours");
		dm.addColumn("Department");
		dm.addColumn("Status");
		table = new JTable(dm) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rows,int columns) {
				return false;
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setRowSelectionAllowed(true);
	    table.setRowHeight(25);
	    table.setPreferredScrollableViewportSize(new Dimension(900, 200));
	    table.setFillsViewportHeight(false);
	    TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(300);
	    columnModel.getColumn(2).setPreferredWidth(120);
	    columnModel.getColumn(3).setPreferredWidth(250);
	    columnModel.getColumn(4).setPreferredWidth(150);
		JScrollPane sc = new JScrollPane(table);
		
		panel.add(sc);
		panel.setPreferredSize(new Dimension(0,300));
		contentPanel.add(panel);
		
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "Select * from projects";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String pname = rs.getString("pname");
				int manHr = rs.getInt("manhrs");
				String dept = rs.getString("dept");
				String status = rs.getString("status");
				
				dm.addRow(new Object[] {id,pname,manHr,dept,status});
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		if (SwingUtilities.isLeftMouseButton(e) == true) {
	    			countClicks++;
	    			int rowSelected = table.rowAtPoint(e.getPoint());
	    			System.out.println("Left click selected row = " + rowSelected);
//	    			int id = Integer.parseInt((table.getModel().getValueAt(rowSelected, 0)).toString());
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
		
	}
	
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
		JMenuItem changeMenu = new JMenuItem("Update");
		JMenuItem deleteBtn = new JMenuItem("Delete");
		changeMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame project = new JFrame();
				project.setTitle("Update Project");
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
				gbc.gridx=0;gbc.gridy=0;gbc.insets=new Insets(0,0,20,25);gbc.anchor=GridBagConstraints.WEST;
				JLabel idLabel = new JLabel("Project ID");
				idLabel.setPreferredSize(new Dimension(110,30));
				panel.add(idLabel,gbc);
				
				gbc.gridx=1;gbc.gridy=0;gbc.insets=new Insets(0,0,20,25);
				JTextField idTf = new JTextField(Integer.toString(popupMenuId));
				idTf.setEditable(false);
				idTf.setPreferredSize(new Dimension(110,30));
				panel.add(idTf,gbc);
				
				gbc.gridx=0;gbc.gridy=1;gbc.insets=new Insets(0,0,20,25);
				JLabel pNameLabel = new JLabel("Project Name");
				pNameLabel.setPreferredSize(new Dimension(110,30));
				panel.add(pNameLabel,gbc);
				
				gbc.gridx=1;gbc.gridy=1;gbc.insets=new Insets(0,0,20,25);
				JTextField pnameValue = new JTextField((String)table.getValueAt(table.getSelectedRow(), 1));
				pnameValue.setPreferredSize(new Dimension(110,30));
				panel.add(pnameValue,gbc);
				
				gbc.gridx=0;gbc.gridy=2;gbc.insets=new Insets(0,0,20,25);
				JLabel manHrLabel = new JLabel("Man Hours");
				idTf.setPreferredSize(new Dimension(110,30));
				panel.add(manHrLabel,gbc);
				
				gbc.gridx=1;gbc.gridy=2;gbc.insets=new Insets(0,0,20,25);
				JTextField manValue = new JTextField();
				int manVal = Integer.parseInt((table.getModel().getValueAt(table.getSelectedRow(), 2)).toString());
				manValue.setText((String)Integer.toString(manVal));
				manValue.setPreferredSize(new Dimension(110,30));
				panel.add(manValue,gbc);
				
				gbc.gridx=0;gbc.gridy=3;gbc.insets=new Insets(0,0,20,25);
				JLabel deptLabel = new JLabel("Department");
				deptLabel.setPreferredSize(new Dimension(110,30));
				panel.add(deptLabel,gbc);
				gbc.gridx=1;gbc.gridy=3;gbc.insets=new Insets(0,0,20,25);
				JTextField deptValue = new JTextField((String)table.getModel().getValueAt(table.getSelectedRow(), 3));
				deptValue.setPreferredSize(new Dimension(110,30));
				panel.add(deptValue,gbc);
				
				gbc.gridx=0;gbc.gridy=4;gbc.insets=new Insets(0,0,20,25);
				JLabel statLabel = new JLabel("Status");
				statLabel.setPreferredSize(new Dimension(110,30));
				panel.add(statLabel,gbc);
				
				gbc.gridx=1;gbc.gridy=4;gbc.insets=new Insets(0,0,20,25);
				String sValues[] = {"Open","Closed"};
				JComboBox<String> status = new JComboBox<String>(sValues);
				String stat = (String)table.getModel().getValueAt(table.getSelectedRow(), 4);
				if (stat.equals("Open"))
					status.setSelectedIndex(0);
				else
					status.setSelectedIndex(1);
				panel.add(status,gbc);
				
				gbc.gridx=0;gbc.gridy=5;gbc.gridwidth=2;gbc.insets=new Insets(0,0,20,25);gbc.anchor=GridBagConstraints.CENTER;
				JButton updateBtn = new JButton("Update");
				updateBtn.setPreferredSize(new Dimension(150,30));
				panel.add(updateBtn,gbc);
				
				updateBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						DatabaseConnection db = new DatabaseConnection();
						Connection conn = null;
						conn = db.getConnection(conn);
						String query = "update projects set pname=?,manhrs=?,dept=?,status=? where id=?";
						try {
							PreparedStatement pstmt = conn.prepareStatement(query);
							pstmt.setString(1, pnameValue.getText());
							pstmt.setInt(2, Integer.parseInt(manValue.getText()));
							pstmt.setString(3, deptValue.getText());
							pstmt.setString(4, (String) status.getSelectedItem());
							pstmt.setInt(5, Integer.parseInt(idTf.getText()));
							int flag = pstmt.executeUpdate();
							if (flag == 1) {
								JOptionPane.showMessageDialog(new JFrame(), "Update successfull","", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Icon.successIcon));
								project.setVisible(false);
								refreshBtn.doClick();
							} else {
								JOptionPane.showMessageDialog(new JFrame(), "Update failed!","",JOptionPane.ERROR_MESSAGE);
							}
						} catch(Exception e3) {
							JOptionPane.showMessageDialog(new JFrame(), e3.getLocalizedMessage(),"",JOptionPane.ERROR_MESSAGE);;
						}
					}
					
				});
				
				project.add(panel);
				project.setBounds(300,200,400,400);
				project.setResizable(false);
				project.setVisible(true);
			}
			
		});
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int status = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure that you want to delete the project with ID = " + popupMenuId + " ?");
				System.out.print(status);
				if (status == 0) {
					DatabaseConnection db = new DatabaseConnection();
					Connection conn = null;
					conn = db.getConnection(conn);
					String query = "Delete from projects where id=?";
					try {
						PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, popupMenuId);
						int flag = pstmt.executeUpdate();
						if (flag == 1) {
							JOptionPane.showMessageDialog(new JFrame(), "Project deleted");
							refreshBtn.doClick();
						} else {
							JOptionPane.showMessageDialog(new JFrame(), "Something went wrong!");
						}
					} catch (Exception e5) {
						JOptionPane.showMessageDialog(new JFrame(), e5.getLocalizedMessage());
					}
				}
			}
			
		});
		popupMenu.add(changeMenu);
		popupMenu.add(deleteBtn);
		table.setComponentPopupMenu(popupMenu);
	}
}

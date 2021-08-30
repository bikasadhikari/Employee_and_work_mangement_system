package admin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import assets.Icon;
import connection.DatabaseConnection;

@SuppressWarnings("serial")
public class Schedule extends AdminPanels {
	
	static int projectSelected;
	boolean isLeaderSelected;
	static int lid;
	static String lname;
	static String ldept;
	static String ldesig;
	static int ecount;
	static int memberId;
	static int[] eids = new int[15];
	
	public Schedule() {
		super.contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new BoxLayout(firstPanel,BoxLayout.X_AXIS));
		firstPanel.setPreferredSize(new Dimension(0,250));
		contentPanel.add(firstPanel);
		
		JPanel f1Panel = new JPanel();
		f1Panel.setPreferredSize(new Dimension(50,0));
		DefaultTableModel dm = new DefaultTableModel();
		dm.addColumn("ID");
		dm.addColumn("NAME");
		dm.addColumn("MAN HRS");
		dm.addColumn("DEPARTMENT");
		JTable table = new JTable(dm);
		TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(200);
	    columnModel.getColumn(2).setPreferredWidth(100);
	    columnModel.getColumn(3).setPreferredWidth(200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setRowSelectionAllowed(true);
	    table.setRowHeight(25);
	    table.setPreferredScrollableViewportSize(new Dimension(500, 200));
	    table.setFillsViewportHeight(false);
		JScrollPane scroll = new JScrollPane(table);
		f1Panel.add(scroll);
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "select * from projects where assign=0";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dm.addRow(new Object[] {rs.getInt("id"),rs.getString("pname"),rs.getInt("manhrs"),rs.getString("dept")});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getLocalizedMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	
		
		JLabel plabel = new JLabel("Project Selected : ");
		plabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));
		f1Panel.add(plabel);
		JTextField ptf = new JTextField("");
		ptf.setPreferredSize(new Dimension(200,30));
		ptf.setEditable(false);
		f1Panel.add(ptf);
		firstPanel.add(f1Panel);
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				projectSelected = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);
				ptf.setText((String)table.getModel().getValueAt(table.getSelectedRow(), 1));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		JPanel f2Panel = new JPanel();
		f2Panel.setPreferredSize(new Dimension(50,0));
		DefaultTableModel dm2 = new DefaultTableModel();
		dm2.addColumn("ID");
		dm2.addColumn("NAME");
		dm2.addColumn("DEPARTMENT");
		dm2.addColumn("DESIGNATION");
		JTable table2 = new JTable(dm2);
		TableColumnModel columnModel2 = table2.getColumnModel();
	    columnModel2.getColumn(0).setPreferredWidth(100);
	    columnModel2.getColumn(1).setPreferredWidth(200);
	    columnModel2.getColumn(2).setPreferredWidth(100);
	    columnModel2.getColumn(3).setPreferredWidth(200);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table2.setRowSelectionAllowed(true);
	    table2.setRowHeight(25);
	    table2.setPreferredScrollableViewportSize(new Dimension(500, 200));
	    table2.setFillsViewportHeight(false);
		JScrollPane scroll2 = new JScrollPane(table2);
		f2Panel.add(scroll2);
		
		table2.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				lid = (int) table2.getModel().getValueAt(table2.getSelectedRow(), 0);
				lname = (String) table2.getModel().getValueAt(table2.getSelectedRow(), 1);
				ldept = (String) table2.getModel().getValueAt(table2.getSelectedRow(), 2);
				ldesig = (String) table2.getModel().getValueAt(table2.getSelectedRow(), 3);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		conn = null;
		conn = db.getConnection(conn);
		query = "select * from employees where inproject=0";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dm2.addRow(new Object[] {rs.getInt("id"),rs.getString("fName"),rs.getString("dept"),rs.getString("job_title")});
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		JButton lbtn = new JButton("SELECT TEAM LEADER");
		lbtn.setPreferredSize(new Dimension(200,30));
		f2Panel.add(lbtn);
		JButton ebtn = new JButton("ADD TEAM MEMBER");
		ebtn.setPreferredSize(new Dimension(200,30));
		f2Panel.add(ebtn);
		firstPanel.add(f2Panel);
		
		
		JPanel secondPanel = new JPanel();
		
		DefaultTableModel dm3 = new DefaultTableModel();
		dm3.addColumn("ID");
		dm3.addColumn("NAME");
		dm3.addColumn("DEPARTMENT");
		dm3.addColumn("DESIGNATION");
		dm3.addColumn("ROLE");
		JTable table3 = new JTable(dm3);
		TableColumnModel columnModel3 = table3.getColumnModel();
	    columnModel3.getColumn(0).setPreferredWidth(100);
	    columnModel3.getColumn(1).setPreferredWidth(200);
	    columnModel3.getColumn(2).setPreferredWidth(200);
	    columnModel3.getColumn(3).setPreferredWidth(200);
	    columnModel3.getColumn(4).setPreferredWidth(200);
		table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table3.setRowSelectionAllowed(true);
	    table3.setRowHeight(25);
	    table3.setPreferredScrollableViewportSize(new Dimension(600, 250));
	    table3.setFillsViewportHeight(false);
		JScrollPane scroll3 = new JScrollPane(table3);
		secondPanel.add(scroll3);
		
		lbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ptf.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Select Project first!");
					return;
				}
				if (isLeaderSelected) {
					dm3.setValueAt(lid, 0, 0);
					dm3.setValueAt(lname, 0, 1);
					dm3.setValueAt(ldept, 0, 2);
					dm3.setValueAt(ldesig, 0, 3);
				} else {
					dm3.addRow(new Object[] {lid,lname,ldept,ldesig,"TEAM LEADER"});
					isLeaderSelected = true;
				}
			}
			
		});
		
		ebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isLeaderSelected) {
					JOptionPane.showMessageDialog(new JFrame(), "Select Team leader first!");
					return;
				}
				if (ecount > 15) {
					JOptionPane.showMessageDialog(new JFrame(), "Maximum limit reached for member");
					return;
				}
				int leaderId = (int) dm3.getValueAt(0, 0);
				if (lid == leaderId) {
					JOptionPane.showMessageDialog(new JFrame(), "Employee selected as team leader!");
					return;
				}
				for (int i=0;i<eids.length;i++) {
					if (lid == eids[i]) {
						JOptionPane.showMessageDialog(new JFrame(), "Employee already selected!");
						return;
					}
				}
				eids[ecount] = lid;
				dm3.addRow(new Object[] {lid,lname,ldept,ldesig,"MEMBER"});
				ecount++;
				
			}
			
		});
		
		table3.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				memberId = (int) table3.getModel().getValueAt(table3.getSelectedRow(), 0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JButton removeBtn = new JButton("REMOVE MEMBER");
		removeBtn.setPreferredSize(new Dimension(200,50));
		secondPanel.add(removeBtn);
		
		JButton saveBtn =  new JButton("SAVE");
		saveBtn.setPreferredSize(new Dimension(150,50));
		secondPanel.add(saveBtn);
		
		saveBtn.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				DatabaseConnection db = new DatabaseConnection();
				Connection conn = null;
				conn = db.getConnection(conn);
				String query = "update projects set status=?,assign=? where id=?";
				try {
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1,"In Progress");
					pstmt.setInt(2, 1);
					pstmt.setInt(3, projectSelected);
					int flag = pstmt.executeUpdate();
					if (flag == 1) {
						query = "update employees set inproject=? where id=?";
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, 1);
						pstmt.setInt(2, (int)dm3.getValueAt(0, 0));
						pstmt.executeUpdate();
						for (int i=0;i<ecount;i++) {
							query = "update employees set inproject=? where id=?";
							try {
								pstmt = conn.prepareStatement(query);
								pstmt.setInt(1, 1);
								pstmt.setInt(2, eids[i]);
								flag = pstmt.executeUpdate();
							} catch(Exception e4) {
								e4.printStackTrace();
							}
						}
					}
					query = "Insert into schedule (pid) values(?)";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, projectSelected);
					pstmt.executeUpdate();
					int sid = 0;
					query = "Select * from schedule where pid=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, projectSelected);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next())
						sid = rs.getInt("sid");	
					for (int i=0;i<dm3.getRowCount();i++) {
						int id = (int) dm3.getValueAt(i, 0);
						String role = (String) dm3.getValueAt(i, 4);
						query = "insert into assigned_projects (sid,eid,role) values(?,?,?)";
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, sid);
						pstmt.setInt(2, id);
						pstmt.setString(3, role);
						pstmt.executeUpdate();
					}
					JOptionPane.showMessageDialog(new JFrame(), "Employees assigned with project","",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(Icon.successIcon));
					scheduleButton.doClick();
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		removeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table3.getSelectedRow() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "Cannot Remove Team Leader!");
					return;
				}
				int id = (int) dm3.getValueAt(table3.getSelectedRow(), 0);
				dm3.removeRow(table3.getSelectedRow());
				for (int i=0;i<eids.length;i++) {
					if (id == eids[i]) {
						eids[i] = 0;
						ecount--;
					}
				}
			}
			
		});
		
		contentPanel.add(secondPanel);
	}
}

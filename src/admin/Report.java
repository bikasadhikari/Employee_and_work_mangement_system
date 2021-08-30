package admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import connection.DatabaseConnection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class Report extends AdminPanels {
	private static final long serialVersionUID = 1L;
	static int pid;
	
	public Report() {
		super.contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.X_AXIS));
		JPanel lpanel = new JPanel();
		lpanel.setLayout(new GridBagLayout());
		lpanel.setPreferredSize(new Dimension(400,0));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.insets=new Insets(0,0,0,0);
		JLabel plabel = new JLabel("PROJECTS");
		plabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
		lpanel.add(plabel,gbc);
		
		gbc.gridx=0;gbc.gridy=1;gbc.insets=new Insets(20,0,0,0);
		DefaultTableModel dm = new DefaultTableModel();
		dm.addColumn("ID");
		dm.addColumn("NAME");
		dm.addColumn("DEPARTMENT");
		dm.addColumn("STATUS");
		JTable table = new JTable(dm);
		TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(200);
	    columnModel.getColumn(2).setPreferredWidth(200);
	    columnModel.getColumn(3).setPreferredWidth(100);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setRowSelectionAllowed(true);
	    table.setRowHeight(25);
	    table.setPreferredScrollableViewportSize(new Dimension(500, 500));
	    table.setFillsViewportHeight(false);
		JScrollPane scroll = new JScrollPane(table);
		lpanel.add(scroll,gbc);
		contentPanel.add(lpanel);
		
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "select * from projects";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dm.addRow(new Object[] {rs.getInt("id"),rs.getString("pname"),rs.getString("dept"),rs.getString("status")});
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		
		
		JPanel rpanel = new JPanel();
		rpanel.setPreferredSize(new Dimension(300,0));
		rpanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.insets=new Insets(0,0,0,0);
		JLabel elabel = new JLabel("EMPLOYEE WORKED");
		elabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
		rpanel.add(elabel);
		gbc.gridx=0;gbc.gridy=1;gbc.insets=new Insets(17,0,0,0);
		DefaultTableModel dm2 = new DefaultTableModel();
		dm2.addColumn("ID");
		dm2.addColumn("NAME");
		dm2.addColumn("ROLE");
		dm2.addColumn("DEPARTMENT");
		dm2.addColumn("DESIGNATION");
		JTable table2 = new JTable(dm2);
		TableColumnModel columnModel2 = table2.getColumnModel();
	    columnModel2.getColumn(0).setPreferredWidth(100);
	    columnModel2.getColumn(1).setPreferredWidth(200);
	    columnModel2.getColumn(2).setPreferredWidth(200);
	    columnModel2.getColumn(3).setPreferredWidth(200);
	    columnModel2.getColumn(4).setPreferredWidth(150);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table2.setRowSelectionAllowed(true);
	    table2.setRowHeight(25);
	    table2.setPreferredScrollableViewportSize(new Dimension(500, 500));
	    table2.setFillsViewportHeight(false);
		JScrollPane scroll2 = new JScrollPane(table2);
		rpanel.add(scroll2,gbc);
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				dm2.setRowCount(0);
				pid = (int) table.getModel().getValueAt(table.getSelectedRow(), 0);
				System.out.println(pid);
				DatabaseConnection db = new DatabaseConnection();
				Connection conn = null;
				conn = db.getConnection(conn);
				String query = "select * from schedule where pid=?";
				try {
					int sid = 0;
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, pid);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						sid = rs.getInt("sid");
					}
					System.out.println(sid);
					query = "select * from assigned_projects where sid=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, sid);
					ResultSet rs2 = pstmt.executeQuery();
					while (rs2.next()) {
						int eid = rs2.getInt("eid");
						System.out.println(eid);
						String role = rs2.getString("role");
						query = "select * from employees where id=?";
						pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, eid);
						ResultSet rs1 = pstmt.executeQuery();
						if (rs1.next()) {
							dm2.addRow(new Object[] {rs1.getInt("id"),rs1.getString("fName"),role,rs1.getString("dept"),rs1.getString("job_title")});
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
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
		
		contentPanel.add(rpanel);
	}

}

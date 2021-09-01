package employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import connection.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Index extends JFrame {
	private static final long serialVersionUID = 1L;
	private String userid;
	
	public Index(String userid) {
		this.userid = userid;
		setTitle("EMPLOYEE");
		System.out.println(this.userid);
		setBounds(100,100,800,600);
		setLocationRelativeTo(null);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;
		JLabel headerLabel = new JLabel("WORK STATUS");
		headerLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,24));
		panel.add(headerLabel);
		
		gbc.gridx=0;gbc.gridy=1;gbc.insets = new Insets(40,0,0,0);
		DefaultTableModel dm = new DefaultTableModel();
		dm.addColumn("PROJECT ID");
		dm.addColumn("PROJECT NAME");
		dm.addColumn("ROLE");
		dm.addColumn("STATUS");
		JTable table = new JTable(dm);
		TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(0).setPreferredWidth(100);
	    columnModel.getColumn(1).setPreferredWidth(200);
	    columnModel.getColumn(2).setPreferredWidth(150);
	    columnModel.getColumn(3).setPreferredWidth(150);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setRowSelectionAllowed(true);
	    table.setRowHeight(25);
	    table.setPreferredScrollableViewportSize(new Dimension(500, 300));
	    table.setFillsViewportHeight(false);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll,gbc);
		
		DatabaseConnection db = new DatabaseConnection();
		Connection conn = null;
		conn = db.getConnection(conn);
		String query = "Select id from employees where user_Id=?";
		int eid = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				eid = rs.getInt("id");
			}
			System.out.println("Executed employees query");
			query = "Select * from assigned_projects where eid=?";
			PreparedStatement pstmt2 = conn.prepareStatement(query);
			pstmt2.setInt(1, eid);
			rs = pstmt2.executeQuery();
			while (rs.next()) {
				System.out.println("Executed projects query");
				String role = rs.getString("role");
				int sid = rs.getInt("sid");	
				query = "select pid from schedule where sid=?";
				PreparedStatement pstmt1 = conn.prepareStatement(query);
				pstmt1.setInt(1, sid);
				ResultSet rs1 = pstmt1.executeQuery();
				if (rs1.next()) {
					int pid = rs1.getInt("pid");
					query = "select * from projects where id=?";
					pstmt1 = conn.prepareStatement(query);
					pstmt1.setInt(1, pid);
					ResultSet rs2 = pstmt1.executeQuery();
					if (rs2.next()) {
						String pname = rs2.getString("pname");
						String status = rs2.getString("status");
						dm.addRow(new Object[] {pid,pname,role,status});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Something has went wrong!");
		} finally {
			db.closeConnection(conn);
		}
		
		gbc.gridx=0;gbc.gridy=2;gbc.insets = new Insets(20,0,0,0);
		JButton logoutButton = new JButton("LOGOUT");
		logoutButton.setFocusPainted(false);
		logoutButton.setPreferredSize(new Dimension(110,30));
		panel.add(logoutButton,gbc);
		
		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
				setVisible(false);
			}
			
		});
		
		add(panel);
	}
	
}

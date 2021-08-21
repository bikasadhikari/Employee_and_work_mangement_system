package assets;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Icon {
	public static Image headerImage = new ImageIcon("images/employeeManagement.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	public static Image exitIcon = new ImageIcon("images/shutdown.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image adminImage = new ImageIcon("images/admin.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	public static Image manageIcon = new ImageIcon("images/manage.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image scheduleIcon = new ImageIcon("images/schedule.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image reportIcon = new ImageIcon("images/report.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image logoutIcon = new ImageIcon("images/logout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image searchIcon = new ImageIcon("images/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image addIcon = new ImageIcon("images/add.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image editIcon = new ImageIcon("images/edit.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image deleteIcon = new ImageIcon("images/delete.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image clearIcon = new ImageIcon("images/clear.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image refreshIcon = new ImageIcon("images/refresh.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image saveIcon = new ImageIcon("images/save.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	public static Image successIcon = new ImageIcon("images/success.png").getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH);
}

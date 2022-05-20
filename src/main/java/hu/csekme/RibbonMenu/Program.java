package hu.csekme.RibbonMenu;
import java.awt.EventQueue;

public class Program {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				 	MainWindow frame = new MainWindow();
			    frame.setLocationRelativeTo(null);
				 	frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}

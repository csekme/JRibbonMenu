package hu.csekme.RibbonMenu;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Program {

	static final Logger logger = Logger.getLogger(Program.class.getName());
	
	public static void main(String[] args) {
		
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger.properties"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				 	MainWindow frame = new MainWindow();
				 	frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		logger.log(Level.INFO, "Start JRibbonBar test application");
	}

}

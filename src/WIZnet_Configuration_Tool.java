import java.awt.EventQueue;

import gnu.getopt.Getopt;

/**
 * 
 */

/**
 * @author Raphael
 *
 */
public class WIZnet_Configuration_Tool {

	/**
	 * @param args
	 */
	static final String PROGRAM_NAME = "wizconfig-tool";
	
	public static void main(final String[] args) throws InterruptedException {
		
		boolean CONSOLE_MODE = (args.length != 0)?true:false; 
		
		if(CONSOLE_MODE){
			Getopt g = new Getopt(PROGRAM_NAME, args, "bm:p:f:s:t:h");
			Console console = new Console(g, args);
			console.exec();
		}else{
			System.out.println("[INFO]Wiznet GUI application is running");
			EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
						e.printStackTrace();
					}
			}				
			});
		}
	}
}
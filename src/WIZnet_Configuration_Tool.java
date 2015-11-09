import java.awt.EventQueue;

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
	public static void main(String[] args) {
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

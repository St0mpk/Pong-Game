package pingpong;

import java.awt.EventQueue;

public class Start {

	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				GameFrame gameFrame = new GameFrame();
				
			}
		};
		
		EventQueue.invokeLater(runner);
	}

}

package pingpong;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	public GameFrame() {
		 
		initFrame();
	}
	
	//nastaví GameFrame
	private void initFrame() {
		
		add(new GameBoard());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("PongGame");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
}

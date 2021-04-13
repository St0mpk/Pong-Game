package pingpong;

import java.awt.event.KeyEvent;

public class Paddle extends Sprite{
	
	public final static int WIDTH = 20; 
	public final static int HEIGHT = GameBoard.HEIGHT / 5;
	private final int SPEED = 5;
	private String side;
	private int up; //kl�vesa pro pohyb nahoru
	private int down; //kl�vesa pro pohyb dolu
	private int dy; //rozd�l v pohybu


	public Paddle(String side) {
		super(WIDTH, HEIGHT);
		this.side = side;
		setSide();
		resetPos();		
	}
	
	private void setSide() { //nastav� stranu, kde je Paddle
		
		if(side.equals("right")) { //prav� strana
			
			up = KeyEvent.VK_UP;
			down = KeyEvent.VK_DOWN;
			x = GameBoard.WIDTH - WIDTH;
			
		} else if(side.equals("left")) { //lev� strana
			
			up = KeyEvent.VK_W;
			down = KeyEvent.VK_S;
			
			x = 0;
			
		} else { 
			System.out.println("tato strana neexistuje!");
		}
		
	}
	
	public boolean isKey(int keyCode) { //ov��� zda dan� kl�vesa posouv� Paddle
		
		return up == keyCode || down == keyCode; 
	}
	
	public void move() { //pohybuje s Paddlem
		if(checkHeightCol()) {
			dy = 0;
		
		}
		
		y+=dy;
			
	}
	
	private boolean checkHeightCol() {
		return dy + y < 0 || dy + y > GameBoard.HEIGHT - HEIGHT;
	}
	
	public void resetPos() {
		y = (GameBoard.HEIGHT - HEIGHT) / 2;
	}
	
	public void keyPressed(int keyCode) { //posouv� Paddle
		
		if(keyCode == up) {
			dy = -SPEED;
		} 
		if (keyCode == down) {
			dy = SPEED;
		}
		
	}
	
	public void keyReleased(int keyCode) { //zastavuje paddle
	
		if(keyCode == up) {
			dy = 0;
		}
		if (keyCode == down) {
			dy = 0;
		}
	}

}

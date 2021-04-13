package pingpong;

import java.util.Random;

public class Ball extends Sprite  {
	
	public static final int SIZE = 20;
	private int xSpeed;
	private int ySpeed;
	private final int MIN_SPEED = 4;
	private final int MAX_SPEED = 15;
	private int time = 0;	
	
	
	public Ball() {
		
		super(SIZE, SIZE);
		restart();
		
	}
	
	public void start() {
		
		generateRandomDirection();
		
	
	}
	
	private void generateRandomDirection() {
		
		Random r = new Random();
		double randomY = 0.3 + (0.7 - 0.3) * r.nextDouble();
		ySpeed = (int) Math.round(randomY * MIN_SPEED);
		double randomX = Math.sqrt(MIN_SPEED * MIN_SPEED - ySpeed * ySpeed);
		xSpeed = (int) Math.round(randomX);
		
		ySpeed *= (r.nextInt() % 2 == 0) ? 1 : -1;
		xSpeed *= (r.nextInt() % 2 == 0) ? 1 : -1;
	}
	
	public void restart() {
		xSpeed = 0;
		ySpeed = 0;
		x = (GameBoard.WIDTH - SIZE) / 2;
		y = (GameBoard.HEIGHT - SIZE) / 2;
		
		
				
	}
	
	public int move(boolean paddleCollision) {
		int state = 0;
	
		 if(checkHeightCollision()) {
			ySpeed *= -1;
			
		 }else if(paddleCollision) {
			 xSpeed *= -1;
			
		} else if(x + xSpeed < 0 - SIZE - MAX_SPEED) {
			
			restart();
			state = 1;
			
		} else if(x + xSpeed > GameBoard.WIDTH + MAX_SPEED) {
			
			restart();
			state = 2;
			
		}
		 
		 x+=xSpeed;
		 y+=ySpeed;
		 		 
		 if(checkSpeed()) {
				increaseSpeed();
				
			} else if(xSpeed == 0 && ySpeed == 0) {
				time = 0;
			}
		
		
			return state;
			}
			
	private boolean checkHeightCollision() {
		return y + ySpeed < 0  || y + ySpeed > GameBoard.HEIGHT - SIZE;
	}		
	
	private void increaseSpeed() {
	
	if(time != 0 && time % 1000 == 0) {
			
			if(xSpeed > 0) {
				xSpeed++;
			} else if(xSpeed < 0) {
				xSpeed--;
			}
			
			if(ySpeed > 0) {
				ySpeed++;
			} else if(ySpeed < 0) {
				ySpeed--;
			}
		}
		
		time++;
		
	}
	
	private boolean checkSpeed() {
		return xSpeed < MAX_SPEED && ySpeed < MAX_SPEED && xSpeed > -MAX_SPEED && ySpeed > -MAX_SPEED && xSpeed != 0 && ySpeed != 0 ;
	}
			
}

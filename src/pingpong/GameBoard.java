
package pingpong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GameBoard extends JPanel implements Runnable{
	
	public static final int WIDTH = 1080 ; //šíøka
	public static final int HEIGHT = 2 * WIDTH / 3; //výška
	private final int DELAY = 8;
	
	private int scoreL = 0;
	private int scoreR = 0;
	
	private boolean inGame = false;
	
	private Paddle rightPaddle, leftPaddle;
	private Ball ball;
	
	private Thread game;
	

	public GameBoard() { //konstruktor
		
		initGameBoard();
	}
	
	//nastaví GameBoard
	private void initGameBoard() {
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(new KAdapter());
		
		ball = new Ball();
		
		rightPaddle =  new Paddle("right");
		leftPaddle = new Paddle("left");
		
	}
	
	
	//vykresluje GameBoard
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(5.0f));
		g2d.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
		
		drawScore(g2d);
		drawBall(g2d);
		drawPaddle(g2d);
		
		
		if(!inGame) {
			drawPressSpace(g2d);
			}
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	//spuštìní vlákna
	public void addNotify() {
		super.addNotify();
		
		game = new Thread(this);
		game.start();
	}
	
	//nakreslí Pálky
	private void drawPaddle(Graphics2D g2d) {
	
			g2d.drawImage(rightPaddle.getImage(), rightPaddle.getX(), rightPaddle.getY(), this);
			g2d.drawImage(leftPaddle.getImage(), leftPaddle.getX(), leftPaddle.getY(), this);
		
	}
	
	//napíše na obrazovku: "Zmáèknìte libovolnou klávesu pro start hry"
	private void drawPressSpace(Graphics2D g2d) {
		
		String press = "Zmáèknìte mezerník pro start hry";
		Font font = new Font("Verdana", Font.BOLD , 20);
		FontMetrics fm = getFontMetrics(font);
		g2d.setFont(font);
		g2d.setColor(Color.red);
		g2d.drawString(press, (WIDTH - fm.stringWidth(press)) / 2, (HEIGHT - fm.getHeight()) / 2);	
	
	}
	
	private void drawScore(Graphics2D g2d) {

		Font font = new Font("Verdana", Font.PLAIN , 50);
		FontMetrics fm = getFontMetrics(font);
		g2d.setColor(Color.gray);
		g2d.setFont(font);
		g2d.drawString(""+scoreL, 50, fm.stringWidth(""+scoreL) + 40);	
		g2d.drawString(""+scoreR, WIDTH - fm.stringWidth(""+scoreR) - 50, fm.stringWidth(""+scoreR) + 40);
		
	}
	
	private void drawBall(Graphics2D g2d) {
		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), this);
	}
	
	
	public void run() {
		long beforeT, timeDiff, sleep;
		beforeT = System.currentTimeMillis();
		
		while (true) {
			
			if(inGame) {
				updateBall();
	
			} else {
				rightPaddle.resetPos();
				leftPaddle.resetPos();
				
			}
			
			updatePaddles();
			repaint();
			
			//vypoèítá èas uspání vlákna
			timeDiff = System.currentTimeMillis() - beforeT;
			sleep = DELAY - timeDiff;
			
			if (sleep < 2) {
				sleep = 2;
			}
			
			//uspí vlákno
			try {
				Thread.sleep(sleep);
				
			}catch(Exception e) {
				e.getStackTrace();
			}
			
			beforeT = System.currentTimeMillis();
			}
	}
	
	//aktualizuje polohu pálek
	private void updatePaddles() {
		
		rightPaddle.move();
		leftPaddle.move();
	}
	
	private void updateBall() {
		
		
		int ballState = ball.move(checkCollision());
		
		if(ballState != 0) {
			switch(ballState) {
				case 1: scoreR++; break;
				case 2: scoreL++; break; 
			}
			
			inGame = false;
			
			}
		
				
		
		
	}
	
	private boolean checkCollision() {
		
		boolean collision = false;
		Rectangle rb = ball.getBounds();
		Rectangle rp = rightPaddle.getBounds();
			
			if(rb.intersects(rp)) {
				collision = true;
			}
			
			rp = leftPaddle.getBounds();
			
			if(rb.intersects(rp)) {
				collision = true;
			}
			
			return collision;
		
	}
	
	private class KAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if(!inGame && keyCode == KeyEvent.VK_SPACE) { 
				inGame = true;
				ball.start();
				return;
			} 
			
		if(inGame) {
				
				if(rightPaddle.isKey(keyCode)) {
					rightPaddle.keyPressed(keyCode);
	
				} else if(leftPaddle.isKey(keyCode)) {
					leftPaddle.keyPressed(keyCode);
	
				}	
			}
		}
		
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if(rightPaddle.isKey(keyCode)) {
				rightPaddle.keyReleased(keyCode);

			} else if(leftPaddle.isKey(keyCode)) {
				leftPaddle.keyReleased(keyCode);

				}
			}
		}
	}
	
	


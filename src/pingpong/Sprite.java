package pingpong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Sprite {

	protected int x; //poloha na ose x
	protected int y; //poloha na ose y
	private int width;
	private int height;
	
	protected BufferedImage image;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		updateImage();
	}
	
	private void updateImage() { //nastavuje vzhled objektu
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);
		
	}
	
	public BufferedImage getImage() {
		return this.image;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
}

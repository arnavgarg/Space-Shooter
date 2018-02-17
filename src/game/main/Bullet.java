package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.handlers.SpriteSheet;

public class Bullet {
	
	private int x;
	private int y;
	
	private int vely;
	
	private BufferedImage bullet; 
	
	public Bullet(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		vely = -10;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		bullet = ss.getImage(0, 0, 32, 32);
	}
	
	public void update() {
		y += vely;
	}
	
	public void render(Graphics g) {
		g.drawImage(bullet, x, y, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

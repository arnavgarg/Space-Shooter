package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.handlers.SpriteSheet;

public class Enemy {
	
	private int x;
	private int y;
	
	private int vely;
	
	private BufferedImage enemy;
	
	public Enemy(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		vely = 2;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		enemy = ss.getImage(0, 78, 78, 44);
	}
	
	public void update() {
		y += vely;
	}
	
	public void render(Graphics g) {
		g.drawImage(enemy, x, y, null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}

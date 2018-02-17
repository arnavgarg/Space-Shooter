package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.handlers.SpriteSheet;

public class Player {

	private int x;
	private int y;
	
	private int velx;
	
	private BufferedImage player;

	public Player(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		velx = 0;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		player = ss.getImage(32, 0, 78, 78);
	}

	public void update() {
		x += velx;
		
		if (x <= 0) {
			x = 0;
		} else if (x >= Game.WIDTH - 78) {
			x = Game.WIDTH - 78;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(player, x, y, null);
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}

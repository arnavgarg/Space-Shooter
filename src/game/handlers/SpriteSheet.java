package game.handlers;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage ss; 
	
	public SpriteSheet(BufferedImage ss) {
		this.ss = ss;
	}
	
	public BufferedImage getImage(int x, int y, int width, int height) {
		return ss.getSubimage(x, y, width, height);
	}
	
}

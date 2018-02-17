package game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextController {

	Font bigtitle = new Font("Helvetica", Font.BOLD, 72);
	Font buttonpress = new Font("Helvetica", Font.PLAIN, 40);
	Font littletitle = new Font("Helvetica", Font.PLAIN, 20);
	Font score = new Font("Helvetica", Font.PLAIN, 18);
	
	public void render(Graphics g, char state, Game game) {
		g.setColor(Color.YELLOW);
		if (state == 's') {
			g.setFont(bigtitle);
			g.drawString("Space Shooter", 200, 200);
			
			g.setFont(buttonpress);
			g.drawString("Press Enter to Play", 285, 270);
			
			g.setFont(littletitle);
			g.drawString("Created by Arnav Garg", 340, 380);
		} else if (state == 'g') {
			g.setFont(score);
			g.drawString("High Score: " + game.getHighScore(), 600, 50);
			g.drawString("Score: " + game.getScore(), 750, 50);
		} else if (state == 'e') {
			g.setFont(bigtitle);
			g.drawString("Game Over", 260, 200);
			
			g.setFont(buttonpress);	
			g.drawString("Score: " + game.getScore(), 375, 270);
			g.drawString("High Score: " + game.getHighScore(), 335, 320);
			g.drawString("Press Enter to Reset", 265, 420);
		}
	}
	
}

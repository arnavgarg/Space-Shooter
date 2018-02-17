package game.handlers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.main.Game;

public class KeyInput extends KeyAdapter {

	private Game game = new Game();
	
	public KeyInput(Game game) {
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
	
}

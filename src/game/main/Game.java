package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import game.handlers.BufferedImageLoader;
import game.handlers.KeyInput;

public class Game extends Canvas implements Runnable {
	
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	public final String TITLE = "Space Shooter";
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage spriteSheet;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;
	private TextController text = new TextController();
	
	private boolean isShooting;
	private int round;
	private char state;
	private int score;
	private int highScore = 0;
	
	private ArrayList<Point> stars;
	
	private synchronized void start() {
		if (running)
			 return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if (!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	private void init() {
		BufferedImageLoader imgLoader = new BufferedImageLoader();
		try {
			spriteSheet = imgLoader.loadImage("/spritesheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		requestFocus();
		addKeyListener(new KeyInput(this));
		
		stars = new ArrayList<Point>();
		for (int i = 0; i < 200; i++)
			stars.add(new Point((int) (Math.random() * 900), (int) (Math.random() * 600)));
		
		reset();
	}
	
	private void reset() {
		round = 0;
		score = 0;
		player = new Player(411, 500, this);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		state = 's';
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("UPDATES: " + updates + ", FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();	
	}
	
	private void update() {
		if (state == 'g') {
			player.update();
			for (int i = 0; i < bullets.size(); i++)
				if (bullets.get(i).getY() < 0)
					bullets.remove(i);
				else
					bullets.get(i).update();

			checkIntersect();
			if (score > highScore)
				highScore = score;
			
			if (enemies.size() == 0)
				startNextRound();
		
			for (int i = 0; i < enemies.size(); i++) 
				if (enemies.get(i).getY() > HEIGHT - 44)
					state = 'e';
				else
					enemies.get(i).update();
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT); 
		
		g.setColor(Color.WHITE);
		for (int i = 0; i < 200; i++)
			g.fillOval(stars.get(i).x, stars.get(i).y, 2, 2);
		
		player.render(g);
		for (int i = 0; i < bullets.size(); i++)
			bullets.get(i).render(g);
		for (int i = 0; i < enemies.size(); i++)
			enemies.get(i).render(g);
		text.render(g, state, this);
		
		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT ) {
			player.setVelx(8);
		} else if (key == KeyEvent.VK_LEFT) {
			player.setVelx(-8);
		} else if (key == KeyEvent.VK_SPACE && !isShooting && state == 'g') {
			bullets.add(new Bullet(player.getX() + 19, player.getY(), this));
			isShooting = true;
		} else if (key == KeyEvent.VK_ENTER) {
			if (state == 's') {
				state = 'g';
			} else if (state == 'e') {
				reset();
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			player.setVelx(0);
		} else if (key == KeyEvent.VK_LEFT) {
			player.setVelx(0);
		} else if (key == KeyEvent.VK_SPACE) {
			isShooting = false;
		}
	}
	
	private void startNextRound() {
		round++;
		for (int i = 0; i < round; i++) {
			Random r = new Random();
			int x = r.nextInt(WIDTH - 78);
			enemies.add(new Enemy(x, 0, this));
		}
	}

	private void checkIntersect() {
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < bullets.size(); j++) {
				if (enemies.size() != i && bullets.size() != j && new Rectangle(enemies.get(i).getX(), enemies.get(i).getY(), 78, 44).intersects(new Rectangle(bullets.get(j).getX(), bullets.get(j).getY(), 32, 32))) {
					score++;
					enemies.remove(i); 
					bullets.remove(j); 
				}
			}
			if (enemies.size() != i && new Rectangle(enemies.get(i).getX(), enemies.get(i).getY(), 78, 44).intersects(new Rectangle(player.getX(), player.getY(), 78, 78))) {
				state = 'e';
			}
		}
		
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getHighScore() {
		return highScore;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
		frame.setResizable(false);
		
		game.start();	
	}
	
}

package de.ora.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
	public static final int WIDTH = 512;
	public static final int HEIGHT = 512;
	private static final Logger LOG = LoggerFactory.getLogger(Game.class);

	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;

	private BufferedImage level;
	private KeyInput keyInput;


	public Game() {
		new Window(WIDTH, HEIGHT, "Wizard", this);

		keyInput = new KeyInput();
		this.addKeyListener(keyInput);

		handler = new Handler();
		camera = new Camera(0, 0);

		ImageLoader imageLoader = new ImageLoader();
		level = imageLoader.load("levels/level1.png");
		loadLevel(level);

		camera.follow(handler.getPlayer());

		start();
	}

	public static void main(String[] args) {
		new Game();
	}

	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		for(int xx = 0; xx < w; xx++) {
			for(int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0x0ff;
				int green = (pixel >> 8) & 0x0ff;
				int blue = (pixel) & 0x0ff;

				Color color = new Color(red, green, blue);

				if(Color.BLACK.equals(color)) {
					continue;
				}

				if(ObjectId.BLOCK.matches(color)) {
					handler.add(new Block(xx * 32, yy * 32));
				}
				else if(ObjectId.PLAYER.matches(color)) {
					handler.add(new Player(xx * 32, yy * 32, keyInput));
				}
				else if(ObjectId.BOX.matches(color)) {
					handler.add(new Box(xx * 32, yy * 32));

				}
				else if(ObjectId.ENEMY.matches(color)) {
					handler.add(new Enemy(xx * 32, yy * 32));
				}

			}
		}
	}

	private void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop() {
		isRunning = false;
		try {
			thread.join();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while(delta >= 1) {
				tick();
				// updates++;
				delta--;
			}

			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				// updates=0;
			}
		}
		stop();
	}

	/**
	 * Renders all things in the game
	 * Gets updated 1000x/sec
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		///////
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g2d.translate(-camera.getX(), -camera.getY());
		handler.render(g);
		g2d.translate(camera.getX(), camera.getY());
		///////
		g.dispose();
		bs.show();
	}

	/**
	 * Updates all things in the game
	 * Gets updated 60x/sec
	 */
	private void tick() {
		camera.tick();
		handler.tick();
	}
}

package de.ora.game.engine;

import de.ora.game.ext.Renderer;
import de.ora.game.gos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameContainer implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(GameContainer.class);
	public static final int WIDTH = 512;
	public static final int HEIGHT = 512;

	private final Window window;
	private Thread thread;
	private final Renderer renderer;
	private final Handler handler;
	private final Camera camera;
	private final KeyInput keyInput;

	private float scale = 1.0f;
	private boolean isRunning = false;
	private BufferedImage level;
	private int frames;


	public GameContainer() {
		keyInput = new KeyInput();
		window = new Window(WIDTH, HEIGHT, scale, "Wizard", this);
		renderer = new Renderer(window);
		handler = new Handler();
		camera = new Camera(0, 0);

		ImageLoader imageLoader = new ImageLoader();
		level = imageLoader.load("levels/level1.png");
		loadLevel(level);

		camera.follow(handler.getPlayer());

		start();
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

				if(ObjectIdImpl.BLOCK.matches(color)) {
					handler.add(new Block(xx * 32, yy * 32));
				}
				else if(ObjectIdImpl.PLAYER.matches(color)) {
					handler.add(new Player(xx * 32, yy * 32, keyInput));
				}
				else if(ObjectIdImpl.BOX.matches(color)) {
					handler.add(new Box(xx * 32, yy * 32));

				}
				else if(ObjectIdImpl.ENEMY.matches(color)) {
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
	}

	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;

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
		renderer.clear();
		Graphics g = window.getImage().getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		/////
		g2d.translate(-camera.getX(), -camera.getY());
		handler.render(g);
		g2d.translate(camera.getX(), camera.getY());
		/////
		g.setColor(Color.WHITE);
		g.drawString(frames + " fps", 10, 20);
		window.render();

		g.dispose();
	}

	/**
	 * Updates all things in the game
	 * Gets updated 60x/sec
	 */
	private void tick() {
		camera.tick();
		handler.tick();
	}

	public void exit() {
		isRunning = false;
	}

	public Window getWindow() {
		return window;
	}

	public KeyInput getKeyInput() {
		return keyInput;
	}
}

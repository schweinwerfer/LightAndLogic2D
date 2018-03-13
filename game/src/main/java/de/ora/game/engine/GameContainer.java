package de.ora.game.engine;

import de.ora.game.engine.gfx.Resources;
import de.ora.game.ext.Renderer;
import de.ora.game.gos.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameContainer implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(GameContainer.class);
	public static final int WIDTH = 512;
	public static final int HEIGHT = 512;

	private final Window window;
	private final Resources resources;
	private Thread thread;
	private final Renderer renderer;
	private final Handler handler;
	private final Camera camera;
	private final KeyInput keyInput;
	private final AbstractGame game;

	private float scale = 1.0f;
	private boolean isRunning = false;
	private BufferedImage level;
	private int frames;


	public GameContainer(AbstractGame game) {
		this.game = game;
		keyInput = new KeyInput();
		window = new Window(WIDTH, HEIGHT, scale, "Wizard", this);
		renderer = new Renderer(window);
		handler = new Handler();
		camera = new Camera(0, 0);
		resources = new Resources(handler);
		final Player player = handler.getPlayer();
		player.addController(keyInput);
		camera.follow(player);
	}


	public void start() {
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
		final BufferedImage image = window.getImage();
		Graphics g = image.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		/////
		game.render(this, renderer);
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
		game.update(this, 0);
		camera.tick();
		handler.tick();
	}

	public void exit() {
		isRunning = false;
	}


	public KeyInput getKeyInput() {
		return keyInput;
	}

	public Camera getCamera() {
		return camera;
	}
}

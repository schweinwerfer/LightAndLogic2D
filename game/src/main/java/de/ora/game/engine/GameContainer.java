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
	private final double UPDATE_CAP = 1.0 / 60.0; // max 60 fps
	private final Renderer renderer;
	private final Handler handler;
	private final Camera camera;
	private final KeyInput keyInput;
	private final AbstractGame game;

	private float scale = 1.0f;
	private boolean isRunning = false;
	private BufferedImage level;
	private int fps;


	public GameContainer(AbstractGame game) {
		this.game = game;
		keyInput = new KeyInput();

		window = new Window(WIDTH, HEIGHT, scale, "GameOne", this);
		camera = new Camera(0, 0);
		renderer = new Renderer(window, camera);
		handler = new Handler();
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
		gameLoop2();
	}

	private void gameLoop2() {
		isRunning = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;
		fps = 0;

		while(isRunning) {
			render = false;

			// how much time has passed?
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while(unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				tick();
				render = true; // only render, if we have updated

				if(frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}

			if(render) {
				render();
				frames++;
			}
			else {
				try {
					Thread.sleep(1);
				}
				catch(InterruptedException e) {
					LOG.warn("Interrupted sleep", e);
				}
			}
		}

		stop();
	}

	private void gameLoop1() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		fps = 0;

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
			fps++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = 0;
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
		Graphics2D g2d = renderer.getG2d();
		/////
		renderer.translateA();
		game.render(this, renderer);
		handler.render(g2d, renderer);
		renderer.process();
		renderer.translateB();
		/////
		g2d.setColor(Color.WHITE);
		g2d.drawString(fps + " fps", 10, 20);
		window.render();

		renderer.disposeGraphics();
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

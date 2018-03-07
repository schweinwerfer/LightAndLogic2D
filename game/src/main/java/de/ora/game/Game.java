package de.ora.game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	private boolean isRunning = false;
	private Thread thread;


	public Game() {
		new Window(500, 500, "Wizard", this);
		start();
	}

	public static void main(String[] args) {
		new Game();
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
		///////
		g.setColor(Color.BLACK);
		///////
		g.dispose();
		bs.show();
	}

	/**
	 * Updates all things in the game
	 * Gets updated 60x/sec
	 */
	private void tick() {

	}
}

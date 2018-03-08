package de.ora.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isLeft() {
		return left;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch(key) {
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				this.up = true;
				break;

			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				this.left = true;
				break;

			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				this.right = true;
				break;

			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				this.down = true;
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch(key) {
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				this.up = false;
				break;

			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				this.left = false;
				break;

			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				this.right = false;
				break;

			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				this.down = false;
				break;
		}
	}
}

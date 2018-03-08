package de.ora.game;

import java.awt.*;

public class Player extends GameObject {
	private final KeyInput controller;
	private int speed = 3;

	public Player(int x, int y, KeyInput keyInput) {
		super(ObjectId.PLAYER, x, y);
		this.controller = keyInput;
	}

	protected void internalTick() {

		collision();

		if(controller.isUp()) {
			velY = -speed;
		}
		else if(!controller.isDown()) {
			velY = 0;
		}

		if(controller.isDown()) {
			velY = speed;
		}
		else if(!controller.isUp()) {
			velY = 0;
		}

		if(controller.isRight()) {
			velX = speed;
		}
		else if(!controller.isLeft()) {
			velX = 0;
		}

		if(controller.isLeft()) {
			velX = -speed;
		}
		else if(!controller.isRight()) {
			velX = 0;
		}
	}

	private void collision() {
		for(GameObject gameObject : getHandler().gameObjects) {
			if(gameObject.id == ObjectId.BLOCK) {
				if(getBounds().intersects(gameObject.getBounds())) {
					x += velX * -1;
					y += velY * -1;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 33, 33);
	}
}

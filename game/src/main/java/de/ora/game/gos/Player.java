package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.KeyInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Player extends GameObject {
	private static final Logger LOG = LoggerFactory.getLogger(Player.class);
	private KeyInput controller;
	private int speed = 3;

	private int bulletRechargeTimeout = 0;
	private PlayerOrientation orientation;

	public Player(int x, int y) {
		super(ObjectIdImpl.PLAYER, x, y);
		orientation = PlayerOrientation.RIGHT;
	}

	protected void internalTick() {

		collision();

		if(controller.isUp()) {
			velY = -speed;
			orientation = PlayerOrientation.UP;
		}
		else if(!controller.isDown()) {
			velY = 0;
		}

		if(controller.isDown()) {
			velY = speed;
			orientation = PlayerOrientation.DOWN;
		}
		else if(!controller.isUp()) {
			velY = 0;
		}

		if(controller.isRight()) {
			velX = speed;
			orientation = PlayerOrientation.RIGHT;
		}
		else if(!controller.isLeft()) {
			velX = 0;
		}

		if(controller.isLeft()) {
			velX = -speed;
			orientation = PlayerOrientation.LEFT;
		}
		else if(!controller.isRight()) {
			velX = 0;
		}

		if(controller.isShoot()) {
			if(bulletRechargeTimeout == 0) {

				int xAdd = 0, yAdd = 0, velX = 0, velY = 0;

				switch(orientation) {
					case UP:
						xAdd = 12;
						yAdd = 0;
						velY = -5;
						break;
					case DOWN:
						xAdd = 12;
						yAdd = 32;
						velY = 5;
						break;
					case LEFT:
						xAdd = 0;
						yAdd = 12;
						velX = -5;
						break;
					default:
						xAdd = 32;
						yAdd = 12;
						velX = 5;
						break;
				}
				final Bullet bullet = new Bullet(ObjectIdImpl.BULLET, x + xAdd, y + yAdd);
				bullet.setVelX(velX);
				bullet.setVelY(velY);
				getHandler().add(bullet);

				bulletRechargeTimeout = Bullet.BULLET_RECHARGE_TIME;
			}
		}

		if(bulletRechargeTimeout > 0) bulletRechargeTimeout--;
	}

	private void collision() {
		for(GameObject gameObject : getHandler().getGameObjects()) {
			if(gameObject.id == ObjectIdImpl.BLOCK) {
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

		g.setColor(Color.GRAY);

		switch(orientation) {
			case UP:
				g.fillRect(x + 8, y, 16, 8);
				break;
			case DOWN:
				g.fillRect(x + 8, y + 24, 16, 8);
				break;
			case LEFT:
				g.fillRect(x, y + 8, 8, 16);
				break;
			default:
			case RIGHT:
				g.fillRect(x + 24, y + 8, 8, 16);
				break;
		}
	}


	public Rectangle getBounds() {
		return new Rectangle(x, y, 33, 33);
	}

	public void addController(KeyInput keyInput) {
		this.controller = keyInput;
	}
}

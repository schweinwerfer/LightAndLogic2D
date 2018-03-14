package de.ora.game.engine;

import de.ora.game.engine.gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int x, y;
	protected float velX = 0, velY = 0;
	public ObjectId id;
	private Handler handler;
	private SpriteSheet spriteSheet;

	public GameObject(ObjectId id, int x, int y, SpriteSheet spriteSheet) {
		this(id, x, y);
		this.spriteSheet = spriteSheet;
	}

	public GameObject(ObjectId id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	/**
	 * Update routine
	 */
	public void tick() {
		internalTick();
		x += velX;
		y += velY;
	}

	protected abstract void internalTick();

	/**
	 * Render routine
	 *
	 * @param g
	 */
	public abstract void render(Graphics2D g);

	public abstract Rectangle getBounds();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ObjectId getId() {
		return id;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Handler getHandler() {
		return handler;
	}

	protected BufferedImage getImage(int col, int row) {
		return spriteSheet.get(col, row, 32, 32);
	}
}

package de.ora.game.engine;

import de.ora.game.engine.gfx.SpriteSheet;
import de.ora.game.ext.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int zBuff = 0;
	protected int x, y;
	protected float velX = 0, velY = 0;
	protected float accelerationX = 1, accelerationY = 1;
	public ObjectId id;
	private Handler handler;
	private SpriteSheet spriteSheet;

	public GameObject(ObjectId id, int x, int y, int zBuff, SpriteSheet spriteSheet) {
		this(id, x, y, zBuff);
		this.spriteSheet = spriteSheet;
	}

	public GameObject(ObjectId id, int x, int y, int zBuff) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.zBuff = zBuff;
	}

	/**
	 * Update routine
	 * @param passedTime
	 */
	public void update(double passedTime) {
		internalUpdate(passedTime);
		x += velX * accelerationX;
		y += velY * accelerationY;
	}

	protected abstract void internalUpdate(double passedTime);

	/**
	 * Render routine
	 *
	 * @param g
	 * @param renderer
	 */
	public abstract void render(Graphics2D g, Renderer renderer);

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

	public int getzBuff() {
		return zBuff;
	}
}

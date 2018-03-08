package de.ora.game;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet extends GameObject {
	public static final int BULLET_RECHARGE_TIME = 25;
	private int range = 200;
	private int damage = 10;
	private boolean penetrates = false;
	private Point2D origin;

	public Bullet(ObjectId id, int x, int y) {
		super(id, x, y);
		origin = new Point(x, y);
	}

	@Override
	protected void internalTick() {
		collision();

		if(origin.distance(x, y) > range) {
			getHandler().remove(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 5, 5);
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, 2, 2);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 5, 5);
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	private void collision() {
		for(GameObject gameObject : getHandler().getGameObjects()) {
			if(ObjectId.BLOCK == gameObject.id) {
				if(getBounds().intersects(gameObject.getBounds())) {
					getHandler().remove(this);
				}
			}
			if(ObjectId.ENEMY == gameObject.id && !penetrates) {
				if(getBounds().intersects(gameObject.getBounds())) {
					getHandler().remove(this);
				}
			}
		}
	}
}

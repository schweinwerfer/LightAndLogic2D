package de.ora.game.gos;

import de.ora.game.engine.GameObject;
import de.ora.game.engine.gfx.RadialLight;
import de.ora.game.ext.Renderer;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet extends GameObject {
	public static final int BULLET_RECHARGE_TIME = 25;
	private final RadialLight light;
	private int range = 250;
	private int damage = 100;
	private int radius = 5;
	private int lightRadius = 20;
	private boolean penetrates = true;
	private Point2D origin;

	public Bullet(ObjectIdImpl id, int x, int y) {
		super(id, x, y);
		origin = new Point(x, y);
		light = new RadialLight(20, Color.WHITE);
	}

	@Override
	protected void internalTick() {
		collision();

		if(origin.distance(x, y) > range) {
			getHandler().remove(this);
		}
	}

	@Override
	public void render(Graphics2D g, Renderer renderer) {
		renderer.renderLight(light, x, y);
		g.setColor(Color.WHITE);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
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

	public boolean isPenetrates() {
		return penetrates;
	}

	private void collision() {
		for(GameObject gameObject : getHandler().getGameObjects()) {
			if(ObjectIdImpl.BLOCK == gameObject.id) {
				if(getBounds().intersects(gameObject.getBounds())) {
					getHandler().remove(this);
				}
			}
		}
	}
}

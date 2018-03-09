package de.ora.game.gos;

import de.ora.game.engine.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet extends GameObject {
	public static final int BULLET_RECHARGE_TIME = 25;
	private int range = 250;
	private int damage = 20;
	private int radius = 5;
	private int lightRadius = 30;
	private boolean penetrates = false;
	private Point2D origin;

	public Bullet(ObjectIdImpl id, int x, int y) {
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
		Graphics2D g2d = (Graphics2D) g;
		final float diff = lightRadius * 0.0125f;
		Point2D center = new Point2D.Float(x + diff, y + diff);
		float[] dist = {0.4f, 1.0f};
		Color[] colors = {Color.YELLOW, Color.BLACK};
		RadialGradientPaint p = new RadialGradientPaint(center, lightRadius, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
		g2d.setPaint(p);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2d.fillOval(x - lightRadius, y - lightRadius, lightRadius * 2, lightRadius * 2);
		g.setColor(Color.YELLOW);
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

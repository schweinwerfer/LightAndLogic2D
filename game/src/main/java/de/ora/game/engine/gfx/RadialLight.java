package de.ora.game.engine.gfx;

import java.awt.*;
import java.awt.geom.Point2D;

public class RadialLight extends Light {
	private final float diff;
	private final int radius;
	private final int diameter;
	private final float[] dist = {0.5f, 1.0f};
	private Color[] colors;
	private Paint paint;

	public RadialLight(int radius, Color color) {
		super(radius);

		colors = new Color[]{color, Light.AMBIENT_COLOR};
		this.radius = radius;
		this.diameter = radius * 2;
		diff = this.radius * 0.0125f;
	}

	public void render(Graphics2D graphics2D, int x, int y) {
		Point2D center = new Point2D.Float(x + diff, y + diff);
		paint = new RadialGradientPaint(center, radius, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
		graphics2D.setPaint(paint);
		graphics2D.fillOval(x - this.radius, y - this.radius, this.diameter, this.diameter);
	}
}

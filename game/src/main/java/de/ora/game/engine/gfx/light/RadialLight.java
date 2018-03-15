package de.ora.game.engine.gfx.light;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RadialLight extends Light {
	private final int radius;
	private final int diameter;
	private final BufferedImage lightImg;
	private final int[] lm;

	public RadialLight(int radius, Color color) {
		super(radius);

		this.radius = radius;
		this.diameter = radius * 2;
		lightImg = new BufferedImage(this.diameter, this.diameter, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = lightImg.createGraphics();
		// clear to ambient color
		g2d.setPaint(AmbientLight.AMBIENT_COLOR);
		g2d.fillRect(0, 0, lightImg.getWidth(), lightImg.getHeight());

		// create radial halo
		float[] dist = {0.5f, 1.0f};
		Point2D center = new Point2D.Float(radius, radius);
		Color[] colors = new Color[]{color, AmbientLight.AMBIENT_COLOR};
		Paint paint = new RadialGradientPaint(center, radius, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
		g2d.setPaint(paint);
		g2d.fillOval(0, 0, this.diameter, this.diameter);

		g2d.dispose();

		lm = ((DataBufferInt) lightImg.getRaster().getDataBuffer()).getData();
	}

	public int[] getLm() {
		return lm;
	}
}

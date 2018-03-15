package de.ora.game.engine.gfx;

import java.awt.*;

public abstract class Light {
	public final static Color AMBIENT_COLOR = new Color(0x666666);
	private int radius;
	private int diameter;

	public Light(int radius) {
		this.radius = radius;
		this.diameter = radius * 2;
	}

	public int getRadius() {
		return radius;
	}

	public int getDiameter() {
		return diameter;
	}

	public abstract void render(Graphics2D graphics2D, int x, int y);
}

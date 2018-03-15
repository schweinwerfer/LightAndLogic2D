package de.ora.game.engine.gfx.light;

import java.awt.*;

public abstract class Light {
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

	public abstract int[] getLm();
}

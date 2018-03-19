package de.ora.game.engine.gfx.light;

public abstract class Light {
	private int radius;
	private int diameter;
	private int[] lm;

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

	public int[] getLm() {
		return lm;
	}

	public void setLm(int[] lm) {
		this.lm = lm;
	}

	public int getLightValue(int x, int y) {
		if(x < 0 || x >= diameter || y < 0 || y >= diameter) {
			return 0;
		}
		return lm[x + y * diameter];
	}
}

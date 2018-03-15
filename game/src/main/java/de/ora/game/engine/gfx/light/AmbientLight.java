package de.ora.game.engine.gfx.light;

import java.awt.*;

public class AmbientLight extends Light {
	public final static Color AMBIENT_COLOR = new Color(0x4D4D4D);

	public AmbientLight(int radius, Color color) {
		super(-1);
	}

	@Override
	public int[] getLm() {
		return new int[0];
	}
}

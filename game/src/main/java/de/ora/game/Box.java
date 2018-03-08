package de.ora.game;

import java.awt.*;

public class Box extends GameObject {

	public Box(int x, int y) {
		super(ObjectId.BLOCK, x, y);
	}

	public void internalTick() {

	}

	public void render(Graphics g) {
		g.setColor(new Color(0x804314));
		g.fillRect(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return null;
	}
}
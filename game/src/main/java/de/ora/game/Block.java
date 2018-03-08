package de.ora.game;

import java.awt.*;

public class Block extends GameObject {

	public Block(int x, int y) {
		super(ObjectId.BLOCK, x, y);
	}

	public void internalTick() {

	}

	public void render(Graphics g) {
		g.setColor(new Color(0xD4D4D4));
		g.fillRect(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
}

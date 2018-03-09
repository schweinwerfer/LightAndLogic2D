package de.ora.game.gos;

import de.ora.game.engine.GameObject;

import java.awt.*;

public class Block extends GameObject {

	public Block(int x, int y) {
		super(ObjectIdImpl.BLOCK, x, y);
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

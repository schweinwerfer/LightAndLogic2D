package de.ora.game.ext;

import de.ora.game.engine.Window;

import java.awt.*;
import java.awt.image.DataBufferInt;

public class Renderer {
	private int pW, pH;
	private int[] p;

	public Renderer(Window window) {
		final Dimension dimension = window.getDimension();
		pW = dimension.width;
		pH = dimension.height;
		p = ((DataBufferInt) window.getImage().getRaster().getDataBuffer()).getData();
	}

	public void clear() {
		for(int i = 0; i < p.length; i++) {
			p[i] = 0xff000000;
		}
	}
}

package de.ora.game.engine.gfx;

public class ImageTile extends Image {
	private final int tileW;
	private final int tileH;

	public ImageTile(String path, int tileW, int tileH) {
		super(path);
		this.tileW = tileW;
		this.tileH = tileH;
	}

	public int getTileW() {
		return tileW;
	}

	public int getTileH() {
		return tileH;
	}
}

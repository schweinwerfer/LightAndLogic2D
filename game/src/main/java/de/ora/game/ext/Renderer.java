package de.ora.game.ext;

import de.ora.game.engine.Camera;
import de.ora.game.engine.Window;
import de.ora.game.engine.gfx.Image;
import de.ora.game.engine.gfx.ImageTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {
	private final static Color INVISIBLE = new Color(0xffff00ff);
	private int pW, pH;
	private int[] p;
	private final BufferedImage screenImage;
	private Graphics2D g2d;

	public Renderer(Window window) {
		final Dimension dimension = window.getDimension();
		pW = dimension.width;
		pH = dimension.height;
		screenImage = window.getImage();
		g2d = screenImage.createGraphics();
		p = ((DataBufferInt) screenImage.getRaster().getDataBuffer()).getData();
	}

	public void clear() {
		for(int i = 0; i < p.length; i++) {
			p[i] = 0;//0xff000000;
		}
	}

	public void setPixel(int x, int y, int value) {
		if((x < 0 || x >= pW || y < 0 || y >= pH) || value == INVISIBLE.getRGB()) {
			return;
		}

		p[x + y * pW] = value;
	}

	public void draw(ImageTile image, int offX, int offY, int tileX, int tileY) {
		// Don't render images outside screen
		if(offX < -image.getW()) return;
		if(offY < -image.getH()) return;
		if(offX >= pW) return;
		if(offY >= pH) return;

		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();

		// Clipping code: cut image parts outside screen
		if(offX < 0) {newX -= offX;}
		if(offY < 0) {newY -= offY;}
		if(newWidth + offX >= pW) {newWidth -= newWidth + offX - pW;}
		if(newHeight + offY >= pH) {newHeight -= newHeight + offY - pH;}

		for(int y = newY; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
			}
		}
	}

	public void draw(Image image, int offX, int offY) {
		// Don't render images outside screen
		if(offX < -image.getW()) return;
		if(offY < -image.getH()) return;
		if(offX >= pW) return;
		if(offY >= pH) return;

		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();

		// Clipping code: cut image parts outside screen
		if(offX < 0) {newX -= offX;}
		if(offY < 0) {newY -= offY;}
		if(newWidth + offX >= pW) {newWidth -= newWidth + offX - pW;}
		if(newHeight + offY >= pH) {newHeight -= newHeight + offY - pH;}

		for(int y = newY; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
			}
		}
	}

	public void translateA(Camera camera) {
		getG2d().translate(-camera.getX(), -camera.getY());
	}

	public void translateB(Camera camera) {
		getG2d().translate(camera.getX(), camera.getY());
	}

	public void disposeGraphics() {
		if(g2d != null) {
			g2d.dispose();
			g2d = null;
		}
	}

	public Graphics2D getG2d() {
		if(g2d == null) {
			g2d = screenImage.createGraphics();
		}
		return g2d;
	}
}

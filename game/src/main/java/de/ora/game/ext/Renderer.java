package de.ora.game.ext;

import de.ora.game.engine.Camera;
import de.ora.game.engine.Window;
import de.ora.game.engine.gfx.Image;
import de.ora.game.engine.gfx.ImageTile;
import de.ora.game.engine.gfx.Light;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {
	private final static Color INVISIBLE = new Color(0xffff00ff);
	private final Camera camera;
	private int pW, pH;
	private int[] p;
	private final BufferedImage lightMapImage;
	private int[] lm; // lightmap
	private final BufferedImage screenImage;
	private int[] lb; // lightblock
	private Color ambientColor = Light.AMBIENT_COLOR;
	private Color backgroundColor = new Color(0x1B5983);
	private Graphics2D g2dLm;
	private Graphics2D g2d;


	public Renderer(Window window, Camera camera) {
		this.camera = camera;
		final Dimension dimension = window.getDimension();
		pW = dimension.width;
		pH = dimension.height;

		screenImage = window.getImage();
		g2d = screenImage.createGraphics();
		p = ((DataBufferInt) screenImage.getRaster().getDataBuffer()).getData();

		lightMapImage = new BufferedImage(screenImage.getWidth(), screenImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		lm = ((DataBufferInt) lightMapImage.getRaster().getDataBuffer()).getData();
		lb = new int[p.length];
	}

	public void clear() {

		for(int i = 0; i < p.length; i++) {
			p[i] = backgroundColor.getRGB();
			lm[i] = ambientColor.getRGB();
			lb[i] = 0;
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

	public void process() {
		for(int i = 0; i < p.length; i++) {
			float r = ((lm[i] >> 16) & 255) / 255f;
			float g = ((lm[i] >> 8) & 255) / 255f;
			float b = (lm[i] & 255) / 255f;

			p[i] =
				(int) (((p[i] >> 16) & 255) * r) << 16 |
					(int) (((p[i] >> 8) & 255) * g) << 8 |
					(int) ((p[i] & 255) * b);
		}
	}

	public void renderLight(Light light, int x, int y) {
//		for(int x = 0; x < light.getDiameter(); x++) {
//			for(int y = 0; y < light.getDiameter(); y++) {
//				setLightMap(x + xx, y + yy, light.getLm()[x + y * light.getDiameter()]);
//			}
//		}

		light.render(getG2dLm(), x, y);

//		try {
//
//			graphics.dispose();
//			ImageIO.write(lightMapImage, "png", new File("foo123456.png"));
//			System.exit(0);
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
	}

	public void setLightMap(int x, int y, int value) {
		if(x < 0 || x >= pW || y < 0 || y >= pH) {
			return;
		}

		int baseColor = lm[x + y * pW];

		int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
		int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
		int maxBlue = Math.max(baseColor & 0xff, value & 0xff);


		p[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue);
	}

	public void translateA() {
		getG2d().translate(-camera.getX(), -camera.getY());
		getG2dLm().translate(-camera.getX(), -camera.getY());
	}

	public void translateB() {
		getG2d().translate(camera.getX(), camera.getY());
		getG2dLm().translate(camera.getX(), camera.getY());
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

	public Graphics2D getG2dLm() {
		if(g2dLm == null) {
			g2dLm = lightMapImage.createGraphics();
		}
		return g2dLm;
	}
}

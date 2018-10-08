package de.ora.game.ext;

import de.ora.game.engine.Camera;
import de.ora.game.engine.GameObject;
import de.ora.game.engine.Window;
import de.ora.game.engine.gfx.Image;
import de.ora.game.engine.gfx.ImageTile;
import de.ora.game.engine.gfx.light.AmbientLight;
import de.ora.game.engine.gfx.light.Light;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

public class Renderer {
	private final static Color INVISIBLE = new Color(0xffff00ff);
	private final static Logger LOG = LoggerFactory.getLogger(Renderer.class);
	private final Camera camera;
	private int pW, pH;
	private int[] p;
	private final BufferedImage lightMapImage;
	private int[] lm; // lightmap
	private final BufferedImage screenImage;
	private int[] lb; // lightblock
	private Color ambientColor = AmbientLight.AMBIENT_COLOR;
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

	public void draw(ImageTile image, int offXIn, int offYIn, int tileX, int tileY) {
		// Don't render images outside screen
		int offX = (int) (offXIn - camera.getX());
		int offY = (int) (offYIn - camera.getY());
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

	public void draw(Image image, int offXIn, int offYIn) {
		// Don't render images outside screen
		int offX = (int) (offXIn - camera.getX());
		int offY = (int) (offYIn - camera.getY());
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
//			float r = ((lb[i] >> 16) & 255) / 255f;
//			float g = ((lb[i] >> 8) & 255) / 255f;
//			float b = (lb[i] & 255) / 255f;

			p[i] =
				(int) (((p[i] >> 16) & 255) * r) << 16 |
					(int) (((p[i] >> 8) & 255) * g) << 8 |
					(int) ((p[i] & 255) * b);
		}
	}

	public void renderLight(Light light, int xx, int yy) {
		int[] llm = light.getLm();
		int xNew = (int) (xx - camera.getX() - light.getRadius());
		int yNew = (int) (yy - camera.getY() - light.getRadius());

		drawLight(light, xNew + light.getRadius(), yNew + light.getRadius());

//		for(int x = 0; x < light.getDiameter(); x++) {
//			for(int y = 0; y < light.getDiameter(); y++) {
//
//				setLightMap((int) (x + xNew), (int) (y + yNew), llm[x + y * light.getDiameter()]);
//			}
//		}
	}

	public void renderLightBlock(double xIn, double yIn, double width, double height) {
		float xNew = (int) (xIn - camera.getX());
		float yNew = (int) (yIn - camera.getY());

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				setLightBlockMap((int) (x + xNew), (int) (y + yNew), 1);
			}
		}
	}

	public void drawLight(Light light, int offX, int offY) {
		for(int i = 0; i < light.getDiameter()+1; i++) {
			drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY);
			drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offX, offY);
			drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY);
			drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offX, offY);
		}
	}

	private void drawLightLine(Light light, int x0, int y0, int x1, int y1, int offX, int offY) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while(true) {

			final int radius = light.getRadius();
			int screenX = x0 - radius + offX;
			int screenY = y0 - radius + offY;

			if(screenX < 0 || screenX >= pW || screenY < 0 || screenY >= pH) {
				return;
			}

			int lightColor = light.getLightValue(x0, y0);
			if(lightColor == 0) {
				return;
			}

			if(lb[screenX + screenY * pW] == 1) {
				return;
			}

			setLightMap(screenX, screenY, lightColor);

			if(x0 == x1 && y0 == y1) {
				break;
			}

			e2 = 2 * err;

			if(e2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}

			if(e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}

	private void setLightMap(int x, int y, int value) {
		if(x < 0 || x >= pW || y < 0 || y >= pH) {
			return;
		}

		int baseColor = lm[x + y * pW];

		int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
		int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
		int maxBlue = Math.max(baseColor & 0xff, value & 0xff);


		lm[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue);
	}

	private void setLightBlockMap(int x, int y, int value) {
		if(x < 0 || x >= pW || y < 0 || y >= pH) {
			return;
		}
		lb[x + y * pW] = value;
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


	public void updateLightBlockMap(List<GameObject> gameObjects) {
		for(GameObject gameObject : gameObjects) {
			if(gameObject.isBlockLight()) {
				final Rectangle bounds = gameObject.getBlockBounds();
				renderLightBlock(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
			}
		}
	}
}

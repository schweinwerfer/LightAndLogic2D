package de.ora.game.engine.gfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	private int w, h;
	private int[] p;

	public Image(String path) {
		try {
			BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource(path));
			w = image.getWidth();
			h = image.getHeight();
			p = image.getRGB(0, 0, w, h, null, 0, w);
			image.flush();
		}
		catch(IOException e) {
			LOG.error("Could not load image: {}", path, e);
		}
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}
}

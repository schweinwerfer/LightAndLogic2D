package de.ora.game.engine.gfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	private BufferedImage image;

	public BufferedImage load(String path) {
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource(path));
			return image;
		}
		catch(IOException e) {
			LOG.error("Could not load image: {}", path, e);
			return null;
		}
	}
}

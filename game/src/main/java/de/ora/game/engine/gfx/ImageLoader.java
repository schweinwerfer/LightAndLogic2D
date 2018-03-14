package de.ora.game.engine.gfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
	private static Logger LOG = LoggerFactory.getLogger(ImageLoader.class);

	public static BufferedImage load(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getClassLoader().getResource(path));
		}
		catch(IOException e) {
			LOG.error("Could not load image: {}", path, e);
			return null;
		}
	}
}

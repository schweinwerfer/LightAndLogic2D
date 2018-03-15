package de.ora.game.engine.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class GfxUtil {

	public static BufferedImage getImageFromArray(int[] pixels, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		return image;
	}
}

package ch.aiko.pix.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage loadPNG(String path) {
		if (!path.startsWith("/")) path = "/" + path;
		try {
			return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage loadPNG(String path, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(loadPNG(path).getScaledInstance(w, h, BufferedImage.SCALE_AREA_AVERAGING), 0, 0, w, h, null);
		return img;
	}

}

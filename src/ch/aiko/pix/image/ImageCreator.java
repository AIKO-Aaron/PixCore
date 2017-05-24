package ch.aiko.pix.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class ImageCreator {

	public static final BufferedImage createStaticImage(int width, int height, int color) {
		BufferedImage img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, BufferedImage.BITMASK);
		Graphics g = img.getGraphics();
		g.setColor(new Color(color));
		g.fillRect(0, 0, width, height);
		return img;
	}
	
}

package ch.aiko.pix.image;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage img;

	public SpriteSheet(BufferedImage img) {
		this.img = img;
	}

	public SpriteSheet(String path) {
		try {
			this.img = (ImageIO.read(SpriteSheet.class.getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getSprite(int x, int y, int w, int h) {
		return img.getSubimage(x, y, w, h);
	}

	public BufferedImage getSprite(int x, int y, int w, int h, int ww, int hh) {
		return toBufferedImage(getSprite(x, y, w, h).getScaledInstance(ww, hh, BufferedImage.SCALE_FAST));
	}

	private BufferedImage toBufferedImage(Image sc) {
		if (sc instanceof BufferedImage) return (BufferedImage) sc;
		else {
			BufferedImage img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(sc.getWidth(null), sc.getHeight(null), BufferedImage.TRANSLUCENT);
			img.getGraphics().drawImage(sc, 0, 0, null);
			return img;
		}
	}

	public BufferedImage[] getSprites(int w, int h) {
		BufferedImage[] sprites = new BufferedImage[(img.getWidth() / w) * (img.getHeight() / h)];
		for (int x = 0; x < img.getWidth(); x += w) {
			for (int y = 0; y < img.getHeight(); y += h) {
				sprites[(x / w) + (y / h) * (img.getWidth() / w)] = img.getSubimage(x, y, w, h);
			}
		}
		return sprites;
	}

}

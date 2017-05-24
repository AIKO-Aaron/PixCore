package ch.aiko.pix.image;

import java.awt.image.BufferedImage;

public class AnimatedLoader {

	public static final int TYPE_PNG = 0x0;
	public static final int TYPE_GIF = 0x1;
	public static final int TYPE_PNG_EXTENDED = 0x2;

	public static BufferedImage[][] loadAnimations(String path, int type, int w, int h) {
		BufferedImage[] frames = null;
		switch (type) {
			case TYPE_PNG:
				frames = new BufferedImage[] { ImageLoader.loadPNG(path) };
				break;
			case TYPE_PNG_EXTENDED:
				frames = new SpriteSheet(ImageLoader.loadPNG(path)).getSprites(w, h);
				break;
			case TYPE_GIF:
				frames = GIFLoader.loadGif(path);
				break;
			default:
				System.err.println("Unknown format");
				return null;
		}
		BufferedImage[][] animSprites = new BufferedImage[frames.length][];

		for (int i = 0; i < animSprites.length; i++) {
			int sh = (int) Math.floor(frames[i].getWidth() / w);
			int sv = (int) Math.floor(frames[i].getHeight() / h);
			// System.out.println("Loaded " + sh * sv + " sprites in frame: " + i);
			animSprites[i] = new BufferedImage[sh * sv];
			for (int j = 0; j < animSprites[i].length; j++) {
				animSprites[i][j] = frames[i].getSubimage((j % sh) * w, (j / sh) * h, w, h);
			}
		}

		return animSprites;
	}

	/**
	 * Loads a single animation from a path. Requires correct file extensions. Only png and gif are supported
	 * 
	 * @param path The path to the png / gif
	 * @return The images loaded from the image
	 */
	public static BufferedImage[] loadAnimation(String path) {
		String type = path.split(".")[path.split(".").length - 1].toLowerCase(); // Last thing after a '.'
		switch (type) {
			case "png":
				return new BufferedImage[] { ImageLoader.loadPNG(path) };
			case "gif":
				return GIFLoader.loadGif(path);
			default:
				return new BufferedImage[] {};
		}
	}
	
	/**
	 * Loads a single animation from a path. Requires correct file extensions. Only png and gif are supported
	 * @param path The path to the gif/png
	 * @param w The width of the images
	 * @param h The height of the images
	 * @return The images
	 */
	public static BufferedImage[] loadAnimation(String path, int w, int h) {
		String type = path.split(".")[path.split(".").length - 1].toLowerCase(); // Last thing after a '.'
		switch (type) {
			case "png":
				return new BufferedImage[] { ImageLoader.loadPNG(path, w, h) };
			case "gif":
				return GIFLoader.loadGif(path, w, h);
			default:
				return new BufferedImage[] {};
		}
	}

}

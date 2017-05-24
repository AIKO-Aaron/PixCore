package ch.aiko.pix.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;

public class GIFLoader {

	public static BufferedImage[] loadGif(String path) {
		if(!path.startsWith("/")) path = "/" + path;
		ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();

		GIFImageReader reader = new GIFImageReader(new GIFImageReaderSpi());

		try {
			reader.setInput(ImageIO.createImageInputStream(GIFLoader.class.getResourceAsStream(path)));

			int mWidth = 0, mHeight = 0;
			for (int i = 0; i < reader.getNumImages(true); i++) {
				mWidth = Math.max(reader.getWidth(i), mWidth);
				mHeight = Math.max(reader.getHeight(i), mHeight);
			}

			for (int i = 0; i < reader.getNumImages(true); i++) {
				int w = reader.getWidth(i);
				int h = reader.getHeight(i);
				BufferedImage frame = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_ARGB);
				if(i > 0) frame.getGraphics().drawImage(frames.get(i - 1), 0, 0, null);
				frame.getGraphics().drawImage(reader.read(i), (mWidth - w) / 2, (mHeight - h) / 2, null);
				frames.add(frame);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return frames.toArray(new BufferedImage[frames.size()]);
	}
	
	public static BufferedImage[] loadGif(String path, int width, int height) {
		if(!path.startsWith("/")) path = "/" + path;
		ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();

		GIFImageReader reader = new GIFImageReader(new GIFImageReaderSpi());

		try {
			reader.setInput(ImageIO.createImageInputStream(GIFLoader.class.getResourceAsStream(path)));

			int mWidth = 0, mHeight = 0;
			for (int i = 0; i < reader.getNumImages(true); i++) {
				mWidth = Math.max(reader.getWidth(i), mWidth);
				mHeight = Math.max(reader.getHeight(i), mHeight);
			}
			
			for (int i = 0; i < reader.getNumImages(true); i++) {
				int w = reader.getWidth(i);
				int h = reader.getHeight(i);
				BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				if(i > 0) frame.getGraphics().drawImage(frames.get(i - 1), 0, 0, null);
				frame.getGraphics().drawImage(reader.read(i), (mWidth - w) / 2, (mHeight - h) / 2, width, height, null);
				frames.add(frame);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return frames.toArray(new BufferedImage[frames.size()]);
	}

}

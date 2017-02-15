package ch.aiko.pix.graphics.renderer;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * PixRenderer renders the image like the graphcisrenderer just the graphics object was replaces with a pixel array.
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixRenderer extends Renderer {

	public static final boolean ALPHA_ENABLED = false;
	
	private BufferedImage img;
	private int[] pixels;

	public PixRenderer(int w, int h) {
		super(w, h);
		img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(w, h, ALPHA_ENABLED ? Transparency.OPAQUE : Transparency.TRANSLUCENT);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	@Override
	public boolean supportsAlpha() {
		return ALPHA_ENABLED;
	}

	/**
	 * Fills a pixel on the screen with a given color. Counting starts in the upper left corner and goes right and downwards
	 * 
	 * @param index
	 *            The index the pixel is in (x + y * width)
	 * @param color
	 *            The color to draw the pixel
	 */
	public void drawPixel(int index, int color) {
		if (index < 0 || index >= pixels.length) return;
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		if (x < 0 || x >= width || y < 0 || y >= height) return; // Don't draw on the new line --> overflow protection
		pixels[x + y * width] = color;
	}

	public int readPixel(int index) {
		if (index < 0 || index >= pixels.length) return -1;
		return pixels[index];
	}

	public int readPixel(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return -1; // Don't draw on the new line --> overflow protection
		return pixels[x + y * width];
	}

	@Override
	public void clear(int color) {
		Arrays.fill(pixels, color);
	}

	@Override
	public void finishUp(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}

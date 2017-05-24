package ch.aiko.pix.graphics.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * PixRenderer renders the image like the graphcisrenderer just the graphics object was replaces with a pixel array.
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixRenderer extends Renderer {

	public static final boolean ALPHA_ENABLED = true;

	private BufferedImage img;
	private int[] pixels;

	public PixRenderer(int w, int h) {
		super(w, h);

		img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(w, h, ALPHA_ENABLED ? Transparency.OPAQUE : Transparency.BITMASK);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		drawText(0, 0, "Init", 0xFFFF00FF , new Font("Arial", 0, 25)); // Reduces time to wait for users first drawString call
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
		if (x < 0 || x >= width || y < 0 || y >= height || color >> 24 == 0) return; // Don't draw on the new line --> overflow protection
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
	public void drawImage(BufferedImage img, int x, int y) {
		int w = img.getWidth();
		int h = img.getHeight();
		DataBuffer db = img.getRaster().getDataBuffer();
		int[] pi = null;
		if (db instanceof DataBufferInt) {
			pi = ((DataBufferInt) db).getData();
		} else {
			// System.err.println("Unhandled type: " + db.getDataType());
			pi = img.getRGB(0, 0, w, h, pi, 0, w);
		}
		if (pi == null) return;
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				if (x + xx >= width || x + xx <= 0 || y + yy >= height || y + yy < 0 || (supportsAlpha() && ((pi[xx + yy * w] >> 24) & 0xFF) <= 0x10)) continue;
				pixels[x + xx + (y + yy) * width] = pi[xx + yy * w];
			}
		}
	}

	@Override
	public void drawImage(BufferedImage img, int x, int y, int x1, int y1, int w, int h) {
		int[] pi = new int[w * h];
		pi = img.getSubimage(x1, y1, w, h).getRGB(0, 0, w, h, pi, 0, w);
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				if (x + xx >= width || x + xx <= 0 || y + yy >= height || y + yy < 0 || supportsAlpha() && (pi[xx + x1 + (yy + y1) * w] >> 24 & 0xFF) <= 0) continue; // alpha = 0
				pixels[x + xx + (y + yy) * width] = pi[xx + x1 + (yy + y1) * w];
			}
		}
	}

	@Override
	public void finishUp(Graphics g, int w, int h) {
		g.drawImage(img, 0, 0, w, h, null);
	}

	public BufferedImage getImage() {
		return img;
	}

	@Override
	public void drawText(int x, int y, String text, int color, Font f) {
		if(text == null) return;
		Graphics g2d = img.getGraphics();
		FontMetrics fm = g2d.getFontMetrics(f);
		g2d.setFont(f);
		g2d.setColor(new Color(color));
		g2d.drawString(text, x, y + fm.getAscent());
	}
	
	public int getTextWidth(String t, Font f) {
		return (int)img.getGraphics().getFontMetrics(f).getStringBounds(t, img.getGraphics()).getWidth();
	}

}

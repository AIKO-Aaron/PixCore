package ch.aiko.pix.graphics.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

// Soon be gone
@Deprecated
public class GraphicsRenderer extends Renderer {

	private BufferedImage img;
	private Graphics g;

	public GraphicsRenderer(int w, int h) {
		super(w, h);
		img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(w, h, Transparency.OPAQUE);
		this.g = img.getGraphics();
	}

	@Override
	public boolean supportsAlpha() {
		return false;
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		g.setColor(new Color(color));
		g.drawRect(x, y, 1, 1);
	}

	@Override
	public void fillRect(int x, int y, int w, int h, int color) {
		g.setColor(new Color(color));
		g.fillRect(x, y, w, h);
	}

	@Override
	public void clear(int color) {
		// g.clearRect(0, 0, width, height);
		g.setColor(new Color(color));
		g.fillRect(0, 0, width, height);
	}

	public void finishUp(Graphics g, int w, int h) {
		g.drawImage(img, 0, 0, w, h, null);
	}

	@Override
	public void drawText(int x, int y, String text, int color, Font f) {
		g.setColor(new Color(color));
		g.setFont(f);
		g.drawString(text, x, y);
	}

	@Override
	public int getTextWidth(String s, Font f) {
		return (int)g.getFontMetrics(f).getStringBounds(s, g).getWidth();
	}

}

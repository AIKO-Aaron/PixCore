package ch.aiko.pix.graphics.renderer;

import java.awt.Color;
import java.awt.Graphics;

// Soon be gone
public class GraphicsRenderer extends BasicRenderer {

	private Graphics g;
	public GraphicsRenderer(Graphics g) {this.g = g;}
	
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

}

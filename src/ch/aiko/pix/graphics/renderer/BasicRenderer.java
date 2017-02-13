package ch.aiko.pix.graphics.renderer;

public abstract class BasicRenderer {

	public static final int ALPHA_MASK = 0xFF000000;

	public abstract boolean supportsAlpha();

	public abstract void drawPixel(int x, int y, int color);

	public void fillRect(int x, int y, int w, int h, int color) {
		if (!supportsAlpha()) color |= ALPHA_MASK;
		for (int yy = y; yy <= y + h; yy++) {
			for (int xx = x; xx <= x + w; xx++) {
				drawPixel(xx, yy, color);
			}
		}
	}

}

package ch.aiko.pix.graphics.renderer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Core class of all renderer classes. Contains most of the code for rendering complex stuff, in short everything that is not a single pixel This should be implemented by the child class and if it has a more efficient way to render something, the methods should be overridden
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public abstract class Renderer {

	/** If alpha isn't supported this mask will be added to every color, so it has full alpha */
	public static final int ALPHA_MASK = 0xFF000000;
	/** The color black with full alpha */
	public static final int BLACK = 0xFF000000;

	/**
	 * The width and height of this field
	 */
	protected int width, height;

	/**
	 * Creates the basic renderer
	 * 
	 * @param w
	 *            The width of the drawable field
	 * @param h
	 *            The height of the drawable field
	 */
	public Renderer(int w, int h) {
		this.width = w;
		this.height = h;
	}

	/**
	 * Whether or not alpha colors (translucency) are supported or not
	 * 
	 * @return True if alpha is supported false otherwise
	 */
	public abstract boolean supportsAlpha();

	/**
	 * Draws a single color on a single pixel in the panel. (0|0) is in the upper left corner and coordinates go up when the point is going to the lower-right corner.
	 * 
	 * @param x
	 *            The x coordinate to draw
	 * @param y
	 *            The y coordinate to draw
	 * @param color
	 *            The color to draw in
	 */
	public abstract void drawPixel(int x, int y, int color);

	/**
	 * Draws text to the screen! revolutionairy I know...
	 * 
	 * @param x
	 *            The x of the upper left corner
	 * @param y
	 *            The y of the upper left corner
	 * @param text
	 *            The text to draw
	 * @param color
	 *            The color to draw the text in
	 * @param font
	 *            The font to draw the text in
	 * @param mods
	 *            The modifiers for the font
	 * @param size
	 *            The size to draw the text in
	 */
	public abstract void drawText(int x, int y, String text, int color, Font f);

	/**
	 * Gets the width in pixels of a string in a font
	 * 
	 * @param s
	 *            The String
	 * @param f
	 *            The Font
	 * @return
	 */
	public abstract int getTextWidth(String s, Font f);

	/**
	 * Resets the whole screen to one color. Abstract because every renderer has their own way to do it. If there is no other way, you have to iterate over every pixel in the field and call drawPixel for it.
	 * 
	 * @param color
	 *            The color to clear to
	 */
	public abstract void clear(int color);

	/**
	 * Finishes the rendering process and draws the image to the graphics object given
	 * 
	 * @param g
	 *            The graphics of the panel to draw on
	 */
	public abstract void finishUp(Graphics g, int w, int h);

	/**
	 * Clears the screen to black
	 */
	public void clear() {
		clear(BLACK);
	}

	/**
	 * Fills a rectangle on the screen with the given color
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the rectangle
	 * @param y
	 *            The y coordinate of the upper left corner of the rectangle
	 * @param w
	 *            The width of the rectangle
	 * @param h
	 *            The height of the rectangle
	 * @param color
	 *            The color of the rectangle
	 */
	public void fillRect(int x, int y, int w, int h, int color) {
		if (!supportsAlpha()) color |= ALPHA_MASK;
		for (int yy = y; yy <= y + h; yy++) {
			for (int xx = x; xx <= x + w; xx++) {
				drawPixel(xx, yy, color);
			}
		}
	}

	/**
	 * 
	 * @param img
	 *            The Image to draw
	 * @param x
	 *            The upper left corner of the image
	 * @param y
	 *            The upper left corner of the image
	 */
	public void drawImage(Image img, int x, int y) {
		if (img instanceof BufferedImage) drawImage((BufferedImage) img, x, y);
		else {
			BufferedImage nImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			nImg.getGraphics().drawImage(img, x, y, null);
			drawImage(nImg, x, y);
		}
	}

	/**
	 * 
	 * @param img
	 *            The Image to draw
	 * @param x
	 *            The upper left corner of the image
	 * @param y
	 *            The upper left corner of the image
	 */
	public void drawImage(BufferedImage img, int x, int y) {
		int w = img.getWidth();
		int h = img.getHeight();
		int[] pi = new int[w * h];
		pi = img.getRGB(0, 0, w, h, pi, 0, w);
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				if (supportsAlpha() && (pi[xx + yy * w] >> 24 & 0xFF) <= 0) continue; // alpha = 0
				drawPixel(x + xx, y + yy, pi[xx + yy * w]);
			}
		}
	}

	/**
	 * 
	 * @param img
	 *            The Image to draw
	 * @param x
	 *            The upper left corner of the image
	 * @param y
	 *            The upper left corner of the image
	 */
	public void drawImage(BufferedImage img, int x, int y, int w, int h) {
		BufferedImage img2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		img2.getGraphics().drawImage(img, 0, 0, w, h, null);
		drawImage(img2, x, y);
	}

	/**
	 * Draws an image to the screen. Just draws the region specified
	 * 
	 * @param img
	 *            The image to draw
	 * @param x
	 *            The x-coordinate where to draw the image
	 * @param y
	 *            The y-coordinate where to draw the image
	 * @param x1
	 *            The x-coordinate of the upper-left corner of the bounds in the picture
	 * @param y1
	 *            The y-coordinate of the upper-left corner of the bounds in the picture
	 * @param w
	 *            The width of the bounds to draw
	 * @param h
	 *            The height of the bounds to draw
	 */
	public void drawImage(BufferedImage img, int x, int y, int x1, int y1, int w, int h) {
		int[] pi = img.getRGB(x1, y1, w, h, null, 0, w);
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				if (supportsAlpha() && (pi[xx + x1 + (yy + y1) * w] >> 24 & 0xFF) <= 0) continue; // alpha = 0
				drawPixel(x + xx, y + yy, pi[xx + x1 + (yy + y1) * w]);
			}
		}
	}

	/**
	 * Fills a rectangle on the screen with the given color
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the rectangle
	 * @param y
	 *            The y coordinate of the upper left corner of the rectangle
	 * @param w
	 *            The width of the rectangle
	 * @param h
	 *            The height of the rectangle
	 * @param color
	 *            The color of the rectangle
	 */
	public void fillRect(float x, float y, float w, float h, int color) {
		fillRect((int) x, (int) y, (int) w, (int) h, color);
	}

	/**
	 * Draws a rectangle on the screen with the given color
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the rectangle
	 * @param y
	 *            The y coordinate of the upper left corner of the rectangle
	 * @param w
	 *            The width of the rectangle
	 * @param h
	 *            The height of the rectangle
	 * @param color
	 *            The color of the rectangle
	 */
	public void drawRect(int x, int y, int w, int h, int color) {
		for (int i = y; i < y + h; i++) {
			drawPixel(x, i, color);
			drawPixel(x + w, i, color);
		}
		for (int i = x; i < x + w; i++) {
			drawPixel(i, y, color);
			drawPixel(i, y + h, color);
		}
	}

	/**
	 * Draws a horizontal line. It's faster because we don't need to calculate any numbers, its just straight to the right
	 * 
	 * @param x
	 *            The x coordinate of the beginning point
	 * @param y
	 *            The y coordinate of the beginning point
	 * @param w
	 *            The width to draw the line. Can't be negative
	 * @param thickness
	 *            The thickness of the line
	 * @param color
	 *            The color of the line
	 */
	public void fastHorizontalLine(int x, int y, int w, int thickness, int color) {
		if (!supportsAlpha()) color |= ALPHA_MASK;
		for (int l = -thickness / 2; l <= thickness / 2; l++) {
			for (int i = x; i < x + w; i++) {
				drawPixel(i, y + l, color);
			}
		}
	}

	/**
	 * Draws a vertical line. It's faster because we don't need to calculate any numbers, its just straight down
	 * 
	 * @param x
	 *            The x coordinate of the beginning point
	 * @param y
	 *            The y coordinate of the beginning point
	 * @param h
	 *            The height to draw the line. Can't be negative
	 * @param thickness
	 *            The thickness of the line
	 * @param color
	 *            The color of the line
	 */
	public void fastVerticalLine(int x, int y, int h, int thickness, int color) {
		if (!supportsAlpha()) color |= ALPHA_MASK;
		for (int l = -thickness / 2; l <= thickness / 2; l++) {
			for (int i = y; i < y + h; i++) {
				drawPixel(x, i + l, color);
			}
		}
	}

	/**
	 * Draws a line between the two points given and the specified color The thickness of the line will be 1
	 * 
	 * @param x1
	 *            The x coordinate of the first point
	 * @param y1
	 *            The y coordinate of the first point
	 * @param x2
	 *            The x coordinate of the second point
	 * @param y2
	 *            The y coordinate of the second point
	 * @param color
	 *            The color the line should be in
	 */
	public void drawLine(float x1, float y1, float x2, float y2, int color) {
		if (!supportsAlpha()) color |= ALPHA_MASK;
		float deltaX = (x2 - x1);
		float deltaY = (y2 - y1);
		float xMod = Math.signum(deltaX);
		float yMod = Math.signum(deltaY);

		if (deltaX == 0 && deltaY == 0) {
			drawPixel((int) x1, (int) y1, color);
			return;
		}

		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			yMod = deltaY / deltaX * Math.signum(deltaX);
		} else {
			xMod = deltaX / deltaY * Math.signum(deltaY);
		}

		for (float i = 0; i <= Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
			drawPixel((int) (x1 + xMod * i), (int) (y1 + yMod * i), color);
		}
	}

	/**
	 * Draws a line between the two points given and the specified color The thickness of the line will be 1
	 * 
	 * @param x1
	 *            The x coordinate of the first point
	 * @param y1
	 *            The y coordinate of the first point
	 * @param x2
	 *            The x coordinate of the second point
	 * @param y2
	 *            The y coordinate of the second point
	 * @param thickness
	 *            The thickness of the line
	 * @param color
	 *            The color the line should be in
	 */
	public void drawLine(float x1, float y1, float x2, float y2, int thickness, int color) {
		if (thickness == 1) {
			drawLine(x1, y1, x2, y2, color);
			return;
		}

		float deltaX = (x2 - x1);
		float deltaY = (y2 - y1);
		float xMod = Math.signum(deltaX);
		float yMod = Math.signum(deltaY);
		float l = -(float) Math.ceil(thickness / 2);

		if (Math.abs(deltaX) > Math.abs(deltaY)) yMod = deltaY / deltaX * Math.signum(deltaX);
		else xMod = deltaX / deltaY * Math.signum(deltaY);

		float xModI = yMod == 0 ? 0 : xMod == 0 ? yMod : 1 / xMod;
		float yModI = xMod == 0 ? 0 : yMod == 0 ? xMod : -1 / yMod;

		float t = (float) Math.sqrt(xModI * xModI + yModI * yModI);
		xModI /= t;
		yModI /= t;

		for (float i = 0; i <= Math.max(Math.abs(deltaX), Math.abs(deltaY)); i++) {
			drawLine((int) (x1 + xMod * i + xModI * l), (int) (y1 + yMod * i + yModI * l), (int) (x1 + xMod * i - xModI * l), (int) (y1 + yMod * i - yModI * l), color);
		}
	}

	/**
	 * Draws a polygon on the screen. This function won't connect the last point to the first, so if you want a closed polygon then you have to repeat the first point at the end of the arrays
	 * 
	 * @param xPoints
	 *            The x coordinates of the points
	 * @param yPoints
	 *            The y coordinates of the points
	 * @param thickness
	 *            The thickness of the lines drawn between the points
	 * @param color
	 *            The color of the polygon
	 */
	public void drawPolygon(int[] xPoints, int[] yPoints, int thickness, int color) {
		if (!supportsAlpha()) color |= ALPHA_MASK;
		if (xPoints.length == 0 || yPoints.length == 0) return;
		int ox = xPoints[0], oy = yPoints[0];
		for (int i = 1; i < Math.min(xPoints.length, yPoints.length); i++) {
			drawLine(ox, oy, xPoints[i], yPoints[i], color);
			ox = xPoints[i];
			oy = yPoints[i];
		}
	}

	/**
	 * Draws a polygon on the screen. This function won't connect the last point to the first, so if you want a closed polygon then you have to repeat the first point at the end of the arrays
	 * 
	 * @param xPoints
	 *            The x coordinates of the points
	 * @param yPoints
	 *            The y coordinates of the points
	 * @param color
	 *            The color of the polygon
	 */
	public void drawPolygon(int[] xPoints, int[] yPoints, int color) {
		drawPolygon(xPoints, yPoints, 1, color);
	}

	/**
	 * Renders text to the screen and cuts off after width
	 * 
	 * @param x
	 *            The x position of the upper left corner of the text
	 * @param y
	 *            The y position of the upper left corner of the text
	 * @param width
	 *            The maximum width of the text
	 * @param text
	 *            The text to render
	 * @param color
	 *            The color to render the text in
	 * @param f
	 *            The Font to use
	 * @return The amount of characters that had to be cut off
	 */
	public int drawTextLimit(int x, int y, int width, String text, int color, Font f) {
		int i = 0;
		while (getTextWidth(text, f) > width) {
			text = text.substring(0, text.length() - 1);
			i++;
		}
		drawText(x, y, text, color, f);
		return i;
	}

	/**
	 * Retrieves the width of this renderer
	 * 
	 * @return The width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Retrieves the height of this renderer
	 * 
	 * @return The height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param width
	 * @param height
	 * @param color
	 *            the color of the rect
	 */
	public void drawRoundedRect(int x, int y, int width, int height, int color) {
		double m = Math.min(width, height) / 2; // radius
		for (int yy = y; yy < y + height; yy++) {
			for (int xx = x; xx < x + width; xx++) {
				if (xx < x + m && yy < y + m) {
					if (Math.sqrt((xx - x - m) * (xx - x - m) + (yy - y - m) * (yy - y - m)) <= m) drawPixel(xx, yy, color);
				} else if (xx < x + m && yy > y + height - m) {
					if (Math.sqrt((xx - x - m) * (xx - x - m) + (yy - y - m) * (yy - y - m)) <= m) drawPixel(xx, yy, color);
				} else if (xx > x + width - m && yy > y + height - m) {
					if (Math.sqrt((xx - x - width + m) * (xx - x - width + m) + (yy - y - m) * (yy - y - m)) <= m) drawPixel(xx, yy, color);
				} else if (xx > x + width - m && yy < y + m) {
					if (Math.sqrt((xx - x - width + m) * (xx - x - width + m) + (yy - y - m) * (yy - y - m)) <= m) drawPixel(xx, yy, color);
				} else drawPixel(xx, yy, color);
			}
		}
	}

}

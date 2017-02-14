package ch.aiko.pix.graphics.renderer;

import java.awt.Graphics;

/**
 * Core class of all renderer classes Contains most of the code for rendering complex stuff, in short everything that is not a single pixel This should be implemented by the child class and if it has a more efficient way to render something, the methods should be overridden
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public abstract class BasicRenderer {

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
	public BasicRenderer(int w, int h) {
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
	 * Draws a single color on a single pixel in the panel.
	 * (0|0) is in the upper left corner and coordinates go up when the point is going to the lower-right corner.
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
	 * Resets the whole screen to one color. Abstract because every renderer has their own way to do it. If there is no other way, you have to iterate over every pixel in the field and call drawPixel for it.
	 * 
	 * @param color
	 *            The color to clear to
	 */
	public abstract void clear(int color);

	/**
	 * Finishes the rendering process and draws the image to the graphics object given
	 * 
	 * @param g The graphics of the panel to draw on
	 */
	public abstract void finishUp(Graphics g);

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

}

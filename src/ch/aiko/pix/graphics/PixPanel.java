package ch.aiko.pix.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import ch.aiko.pix.graphics.renderer.GraphicsRenderer;

/**
 * Root of every rendering operation. 
 * This can't do anything alone. Every PixPanel that should be rendered, need to be added to a {@link PixWindow Window}.
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixPanel {

	/** The second and last swing object created... */
	private Canvas canvas;
	
	public Renderable renderable;
	
	/**
	 * Creates a new {@link PixPanel} with the given width and height.
	 * 
	 * @param width The width of the drawable field
	 * @param height The height of the drawable field
	 */
	public PixPanel(int width, int height) {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Creates a new {@link PixPanel} with the given width and height.
	 * Additionally this adds the panel directly to the given screen
	 * 
	 * @param width The width of the drawable field
	 * @param height The height of the drawable field
	 * @param addTo The {@link PixWindow Window} this panel should be added to
	 */
	public PixPanel(int width, int height, PixWindow addTo) {
		this(width, height);
		addToWindow(addTo); // Alternatively addTo.setPanel(this);		
	}
	
	public final void preRender() {
		if(!canvas.isDisplayable()) return;
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		// TODO call children to draw
		renderable.render(new GraphicsRenderer(g));
		
		g.dispose();
		bs.show();
	}
	
	/**
	 * Sets the panel of a {@link ch.aiko.pix.graphics.PixWindow Window}.
	 * 
	 * @param window The {@link ch.aiko.pix.graphics.PixWindow Window} to set the panel of.
	 */
	public void addToWindow(PixWindow window) {
		window.setPanel(this);
	}
	
	/**
	 * This returns the foundation of the rendering process
	 * 
	 * @return The Canvas
	 */
	public Canvas getSwingCanvas() {
		return canvas;
	}

	/**
	 * Tries to set the width of the canvas
	 * 
	 * @param width The width this canvas should be set to
	 */
	public void setWidth(int width) {
		canvas.setSize(width, getHeight());
	}

	/**
	 * Tries to set the height of the canvas
	 * 
	 * @param height The height this canvas should be set to
	 */
	public void setHeight(int height) {
		canvas.setSize(getWidth(), height);
	}

	/**
	 * Gets the width of the canvas
	 * 
	 * @return The width of the canvas
	 */
	public int getWidth() {
		return canvas.getWidth();
	}

	/**
	 * Gets the height of the canvas
	 * 
	 * @return The heightof the canvas
	 */
	public int getHeight() {
		return canvas.getHeight();
	}
	
}

package ch.aiko.pix.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import ch.aiko.pix.graphics.renderer.Renderer;
import ch.aiko.pix.core.Renderable;
import ch.aiko.pix.core.Updatable;
import ch.aiko.pix.graphics.renderer.PixRenderer;

/**
 * Root of every rendering operation. This can't do anything alone. Every PixPanel that should be rendered, need to be added to a {@link PixWindow Window}.
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixPanel {

	/** The second and last swing object created... */
	private Canvas canvas;

	/**
	 * The renderer which draws the stuff to the screen
	 */
	public Renderer renderer;

	/*
	 * Temporary renderable to render tests
	 */
	public Renderable renderable;
	/**
	 * Temporary Updatable to update tests
	 */
	public Updatable updatable;

	/**
	 * If this panel is currently rendering
	 */
	public boolean rendering = false;
	/**
	 * If this panel is currently updating
	 */
	public boolean updating = false;

	/**
	 * The rendering thread --> unlimited fps
	 */
	protected Thread renderThread = new Thread(() -> renderLoop(), "render_loop");
	/**
	 * The updating thread --> 60 updates per second
	 */
	protected Thread updateThread = new Thread(() -> updateLoop(), "update_loop");

	/** Also limit fps? */
	public static final boolean LIMIT_FPS = true;
	/** One second in nanoseconds */
	public static final int SECOND = 1000000000;
	/** How many updates we want per second */
	public static final int DEST_UPS = 60;
	/** How long we need to wait each update (update included) to achieve the dest_ups */
	public static final int WAIT_TIME = SECOND / DEST_UPS;

	/**
	 * The last time we read the ups & fps (nanoseconds)
	 */
	private long last_time = System.nanoTime();

	/**
	 * ups and fps counting UPS and FPS from the the last second
	 */
	protected int ups, fps, lastUPS, lastFPS;

	/**
	 * Creates a new {@link PixPanel} with the given width and height.
	 * 
	 * @param width
	 *            The width of the drawable field
	 * @param height
	 *            The height of the drawable field
	 */
	public PixPanel(int width, int height) {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));

		renderer = new PixRenderer(width, height);
	}

	/**
	 * Creates a new {@link PixPanel} with the given width and height. Additionally this adds the panel directly to the given screen
	 * 
	 * @param width
	 *            The width of the drawable field
	 * @param height
	 *            The height of the drawable field
	 * @param addTo
	 *            The {@link PixWindow Window} this panel should be added to
	 */
	public PixPanel(int width, int height, PixWindow addTo) {
		this(width, height);
		addToWindow(addTo); // Alternatively addTo.setPanel(this);
	}

	/**
	 * Starts the rendering & updating processes
	 */
	public void start() {
		renderThread.start();
		updateThread.start();
	}

	/**
	 * Stops the rendering and updating processes
	 */
	public void stop() {
		rendering = false;
		updating = false;

		try {
			renderThread.join();
			updateThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * run function of the render thread
	 */
	protected void renderLoop() {
		rendering = true;
		while (rendering) {
			long start = System.nanoTime();
			preRender();
			++fps;
			long end = System.nanoTime();
			if (LIMIT_FPS && WAIT_TIME - end + start > 0) {
				try {
					Thread.sleep((WAIT_TIME - end + start) / 1000000, (int) (WAIT_TIME - end + start) % 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * run function of the update thread
	 */
	protected void updateLoop() {
		updating = true;
		while (updating) {
			long start = System.nanoTime();
			if (start > last_time + SECOND) {
				last_time = start;
				lastUPS = ups;
				lastFPS = fps;
				fps = 0;
				ups = 0;
			}
			preUpdate();
			++ups;
			long end = System.nanoTime();
			if (WAIT_TIME - end + start > 0) {
				try {
					Thread.sleep((WAIT_TIME - end + start) / 1000000, (int) (WAIT_TIME - end + start) % 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Creates a drawing space and invokes the render function of all children Then draws the image to the canvas
	 */
	public final void preRender() {
		if (!canvas.isDisplayable()) return;
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		if (renderer != null && renderable != null) renderable.render(renderer);
		// TODO call children to draw

		Graphics g = bs.getDrawGraphics();

		renderer.finishUp(g);

		g.dispose();
		bs.show();
	}

	/**
	 * Updates the panel and all of its children
	 */
	public void preUpdate() {
		// TODO updates
		if (updatable != null) updatable.update();
	}

	/**
	 * Sets the panel of a {@link ch.aiko.pix.graphics.PixWindow Window}.
	 * 
	 * @param window
	 *            The {@link ch.aiko.pix.graphics.PixWindow Window} to set the panel of.
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
	 * @param width
	 *            The width this canvas should be set to
	 */
	public void setWidth(int width) {
		canvas.setSize(width, getHeight());
	}

	/**
	 * Tries to set the height of the canvas
	 * 
	 * @param height
	 *            The height this canvas should be set to
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

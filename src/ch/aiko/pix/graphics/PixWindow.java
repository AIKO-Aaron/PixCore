package ch.aiko.pix.graphics;

import java.awt.Canvas;
import java.awt.Component;

import javax.swing.JFrame;

/**
 * The container for the {@link PixPanel}. Doesn't contain code for rendering nor has it anything to do with threads.
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixWindow {

	/**
	 * One of the onely swing objects created.
	 */
	private JFrame frame;
	/** The panel this window currently contains and draws and updates */
	private PixPanel panel = null;

	/**
	 * Creates a new window with a default {@link PixPanel Panel}.
	 * 
	 * @param title
	 *            The title of the window
	 * @param width
	 *            The width of the drawing canvas
	 * @param height
	 *            The height of the drawing canvas
	 */
	public PixWindow(String title, int width, int height) {
		frame = new JFrame(title);

		// creates and adds panel to window
		panel = new PixPanel(width, height, this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// TODO start rendering process

		frame.setVisible(true);
	}

	/**
	 * Creates a new window with the given {@link PixPanel Panel}.
	 * 
	 * @param title
	 *            The title of the window
	 * @param panel
	 *            The panel to add
	 */
	public PixWindow(String title, PixPanel panel) {
		frame = new JFrame(title);

		setPanel(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// TODO start rendering process

		frame.setVisible(true);
	}

	/**
	 * Sets the {@link PixPanel} of this window.
	 * 
	 * @param panel
	 *            The panel that should be rendered and updated from now.
	 */
	public void setPanel(PixPanel panel) {
		for(Component c : frame.getComponents()) if(c instanceof Canvas) frame.remove(c);
		this.panel = panel;
		frame.add(panel.getSwingCanvas());
		frame.pack();
	}

	/**
	 * Gets the current displayed {@link PixPanel Panel} of this screen
	 * 
	 * @return The {@link PixPanel Panel} of the screen
	 */
	public PixPanel getPanel() {
		return panel;
	}
	
	/**
	 * Sets the width of the window
	 * 
	 * @param width
	 *            The new width of the window
	 */
	public void setWidth(int width) {
		frame.setSize(width, getHeight());
	}

	/**
	 * Sets the height of the window
	 * 
	 * @param height
	 *            The new height of the window
	 */
	public void setHeight(int height) {
		frame.setSize(getWidth(), height);
	}

	/**
	 * Gets the width of the frame (not the actual canvas to draw on)
	 * 
	 * @return The width of the frame
	 */
	public int getWidth() {
		return frame.getWidth();
	}

	/**
	 * Gets the height of the frame (not the actual canvas to draw on)
	 * 
	 * @return The height of the frame
	 */
	public int getHeight() {
		return frame.getHeight();
	}

	/**
	 * Gets the width of the canvas to draw on.
	 * 
	 * @return The width of the canvas.
	 */
	public int getActualWidth() {
		return panel.getWidth();
	}

	/**
	 * Gets the height of the canvas to draw on.
	 * 
	 * @return The height of the canvas
	 */
	public int getActualHeight() {
		return panel.getHeight();
	}

}

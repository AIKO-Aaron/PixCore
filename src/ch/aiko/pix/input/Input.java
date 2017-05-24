package ch.aiko.pix.input;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.aiko.pix.graphics.Layer;

/**
 * Mouse & KeyBoard handling class
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class Input {

	/** If the computer this app runs on is a mac and the accent menu was enabled */
	private static int accentMenuEnabled = 0;

	/**
	 * Which layer is the parent of this inputs
	 */
	private Layer holder;

	/**
	 * Tracks which buttons are pressed and which are not
	 */
	protected int[] keys = new int[1024];
	/**
	 * Tracks the mouse
	 */
	protected int[] mouse = new int[4]; // for each button one
	private int frameX, frameY, frameWidth, frameHeight;
	protected int mouseX, mouseY;
	protected int deltaX, deltaY;
	protected int scrollWheel, deltaWheel;
	protected ArrayList<AWTEvent> lastEvents = new ArrayList<AWTEvent>();

	/**
	 * Creates a input object for a layer
	 * 
	 * @param g
	 *            The layer the input is for
	 */
	public Input(Layer g) {
		holder = g;
	}

	/**
	 * The mouse's x position on the screen
	 * 
	 * @return The x position of the mouse
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * The mouse's y position on the screen
	 * 
	 * @return The y position of the mouse
	 */
	public int getMouseY() {
		return mouseY;
	}

	/**
	 * Disables the accent menu on mac so you can have multiple keystrokes with just holding the button
	 */
	public static final void disableAccentMenuMac() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			try {
				Process p = Runtime.getRuntime().exec("defaults read -g ApplePressAndHoldEnabled");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				Runtime.getRuntime().exec("defaults write -g ApplePressAndHoldEnabled -bool false");
				accentMenuEnabled = Integer.parseInt(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Re-enables the accent menu on mac, if and only if it was enabled previously
	 */
	public static final void enableAccentMenuMac() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			try {
				Runtime.getRuntime().exec("defaults write -g ApplePressAndHoldEnabled -bool true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<AWTEvent> getLastEvents() {
		@SuppressWarnings("unchecked")
		ArrayList<AWTEvent> evts = (ArrayList<AWTEvent>) lastEvents.clone();
		lastEvents.clear();
		return evts;
	}

	/**
	 * Adds an event to the recently pushed events
	 */
	public void newEvent(AWTEvent e) {
		switch (e.paramString().split(",")[0].split(" ")[0]) {
			case "KEY_PRESSED":
				keyPressed(((KeyEvent) e).getKeyCode());
				break;
			case "KEY_RELEASED":
				keyReleased(((KeyEvent) e).getKeyCode());
				break;
			case "MOUSE_PRESSED":
				mousePressed(((MouseEvent) e).getButton());
				break;
			case "MOUSE_RELEASED":
				mouseReleased(((MouseEvent) e).getButton());
				break;
			case "MOUSE_MOVED":
			case "MOUSE_DRAGGED":
				mouseMoved(((MouseEvent) e).getX(), ((MouseEvent) e).getY());
				break;
			case "MOUSE_WHEEL":
				mouseWheelMoved(((MouseWheelEvent) e).getPreciseWheelRotation());
			case "WINDOW_CLOSING":
				if (accentMenuEnabled == 1) enableAccentMenuMac();
				break;
			case "COMPONENT_RESIZED":
			case "COMPONENT_MOVED":
				Component c = ((ComponentEvent) e).getComponent();
				setFrameX(c.getX());
				frameY = c.getY();
				frameWidth = c.getWidth();
				frameHeight = c.getHeight();
				break;
			case "MOUSE_CLICKED":
			case "MOUSE_EXITED":
			case "MOUSE_ENTERED":
			case "WINDOW_GAINED_FOCUS":
			case "WINDOW_ACTIVATED":
			case "WINDOW_OPENED":
			case "COMPONENT_SHOWN":
			case "KEY_TYPED":
			case "WINDOW_LOST_FOCUS":
			case "WINDOW_DEACTIVATED":
				// No action taken for these events
				break;
			default:
				System.out.println(e.paramString());
		}

		lastEvents.add(e);
		if (!holder.blocksEvents()) {
			for (int i = 0; i < holder.getChildren().size(); i++) {
				Layer l = holder.getChildren().get(i);
				if (l != null) l.getInput().newEvent(e);
			}
		}
	}

	/**
	 * Called when a key has been pressed
	 * 
	 * @param keyCode
	 *            The keycode
	 */
	public void keyPressed(int keyCode) {
		if (keyCode < keys.length) keys[keyCode] = 1;
	}

	/**
	 * Called when a key has been released
	 * 
	 * @param keyCode
	 *            The keycode
	 */
	public void keyReleased(int keyCode) {
		if (keyCode < keys.length) keys[keyCode] = 0;
	}

	/**
	 * Gets called for the mouseMoved as well as the mouseDragged events
	 * 
	 * @param x
	 *            the new x position of the mouse
	 * @param y
	 *            the new y position of the mouse
	 */
	public void mouseMoved(int x, int y) {
		deltaX = x - mouseX;
		deltaY = y - mouseY;
		mouseX = x;
		mouseY = y;
	}

	/**
	 * Called when a mouse button was released
	 * 
	 * @param key
	 *            The key (0-2)
	 */
	public void mousePressed(int key) {
		if (key < mouse.length) mouse[key] = 1;
	}

	/**
	 * Called when a mouse button has been released
	 * 
	 * @param key
	 *            The key (0-2)
	 */
	public void mouseReleased(int key) {
		if (key < mouse.length) mouse[key] = 0;
	}

	/**
	 * Called when the mousewheel has been moved
	 * 
	 * @param amount
	 *            The amount the scrollwheel has been moved
	 */
	public void mouseWheelMoved(double amount) {
		scrollWheel += amount;
		deltaWheel = (int) amount;
	}

	/**
	 * Returns true if the mouse button is pressed, false otherwise
	 * 
	 * @param key
	 *            The key (0-2)
	 * @return True if pressed
	 */
	public boolean isMousePressed(int key) {
		return key < mouse.length ? mouse[key] > 0 : false;
	}

	/**
	 * Returns true once the button has been pressed. Every time the mouse button has been pressed it can return true once again
	 * 
	 * @param key
	 *            The key (0-2)
	 * @return True if the key hasn't been popped yet and it is still pressed
	 */
	public boolean popMousePressed(int key) {
		boolean b = key < mouse.length ? mouse[key] == 1 : false;
		if (b && key < mouse.length) mouse[key] = 2;
		return b;
	}

	/**
	 * Detects if the key is currently pressed or not
	 * 
	 * @param keyCode
	 *            The keycode of the key to check
	 * @return If the key is pressed or not
	 */
	public boolean isKeyPressed(int keyCode) {
		return keyCode < keys.length ? keys[keyCode] > 0 : false;
	}

	/**
	 * Detects if the key is currently pressed or not. Only once is true. the is still pressed, but this function will return false.
	 * 
	 * @param keyCode
	 *            The keycode
	 * @return True if the key is pressed and popKeyPressed wasn't called for this key before (resets after release of key)
	 */
	public boolean popKeyPressed(int keyCode) {
		boolean b = keyCode < keys.length ? keys[keyCode] == 1 : false;
		if (b && keyCode < keys.length) keys[keyCode] = 2;
		return b;
	}

	/**
	 * Retrieve the amount the scrollwheel has been moved since last checked
	 * 
	 * @return The amount
	 */
	public int popScrollWheel() {
		int a = deltaWheel;
		deltaWheel = 0;
		return a;
	}

	/**
	 * Retrieve the amount of rotations the scrollwheel has made
	 * 
	 * @return The amount of rotations
	 */
	public int getScrollWheel() {
		return scrollWheel;
	}

	/**
	 * Sets the scrollwheel counter to 0
	 */
	public void resetScrollWheel() {
		scrollWheel = 0;
	}

	/**
	 * The current position of the frame
	 * 
	 * @return The x position of the frame
	 */
	public int getFrameX() {
		return frameX;
	}

	/**
	 * The current position of the frame
	 * 
	 * @return The x position of the frame
	 */
	public int getFrameY() {
		return frameY;
	}

	/**
	 * The current position of the frame
	 * 
	 * @return The x position of the frame
	 */
	public int getFrameWidth() {
		return frameWidth;
	}

	/**
	 * The current position of the frame
	 * 
	 * @return The x position of the frame
	 */
	public int getFrameHeight() {
		return frameHeight;
	}

	/**
	 * Doesn't actually resized the component
	 * 
	 * @param frameX
	 *            THe size the component should think it is
	 */
	public void setFrameX(int frameX) {
		this.frameX = frameX;
	}

	/**
	 * Doesn't actually resized the component
	 * 
	 * @param frameY
	 *            THe size the component should think it is
	 */
	public void setFrameY(int frameY) {
		this.frameY = frameY;
	}

	/**
	 * Doesn't actually resized the component
	 * 
	 * @param frameWidth
	 *            THe size the component should think it is
	 */
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	/**
	 * Doesn't actually resized the component
	 * 
	 * @param frameHeight
	 *            THe size the component should think it is
	 */
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	/**
	 * Get the width the renderer can draw to
	 * 
	 * @return The width of the canvas
	 */
	public int getRenderingWidth() {
		return holder.getPanel().getRenderingWidth();
	}

	/**
	 * Get the height the renderer can draw to
	 * 
	 * @return The height of the canvas
	 */
	public int getRenderingHeight() {
		return holder.getPanel().getRenderingHeight();
	}

}

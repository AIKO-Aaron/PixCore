package ch.aiko.pix.input;

import ch.aiko.pix.graphics.Layer;

/**
 * Mouse & KeyBoard handling class
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class Input {

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
	protected int[] mouse = new int[3 + 2 + 1]; // for each button one, for x|y positions, for scroll-wheel

	/**
	 * Creates a input object for a layer
	 * @param g The layer the input is for
	 */
	public Input(Layer g) {
		holder = g;
	}

	/**
	 * Called when a key has been pressed
	 * 
	 * @param keyCode The keycode
	 */
	public void keyPressed(int keyCode) {
		if (keyCode < keys.length) {
			keys[keyCode] = 1;
			if (!holder.blocksEvents()) {
				for (int i = 0; i < holder.getChildren().size(); i++) {
					Layer l = holder.getChildren().get(i);
					if (l != null) l.getInput().keyPressed(keyCode);
				}
			}
		}
	}

	/**
	 * Called when a key has been released
	 * 
	 * @param keyCode The keycode
	 */
	public void keyReleased(int keyCode) {
		if (keyCode < keys.length) {
			keys[keyCode] = 0;
			if (!holder.blocksEvents()) {
				for (int i = 0; i < holder.getChildren().size(); i++) {
					Layer l = holder.getChildren().get(i);
					if (l != null) l.getInput().keyReleased(keyCode);
				}
			}
		}
	}

	/**
	 * Detects if the key is currently pressed or not
	 * 
	 * @param keyCode The keycode of the key to check
	 * @return If the key is pressed or not
	 */
	public boolean isKeyPressed(int keyCode) {
		return keyCode < keys.length ? keys[keyCode] > 0 : false;
	}

	/**
	 * Detects if the key is currently pressed or not.
	 * Only once is true. the is still pressed, but this function will return false.
	 * 
	 * @param keyCode The keycode
	 * @return True if the key is pressed and popKeyPressed wasn't called for this key before (resets after release of key)
	 */
	public boolean popKeyPressed(int keyCode) {
		boolean b = keyCode < keys.length ? keys[keyCode] == 1 : false;
		if(b && keyCode < keys.length) keys[keyCode] = 2;
		return b;
	}

}

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

	public Input(Layer g) {
		holder = g;
	}

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

	public boolean isKeyPressed(int keyCode) {
		return keyCode < keys.length ? keys[keyCode] > 0 : false;
	}

	public boolean popKey(int keyCode) {
		boolean b = keyCode < keys.length ? keys[keyCode] == 1 : false;
		if(b && keyCode < keys.length) keys[keyCode] = 2;
		return b;
	}

}
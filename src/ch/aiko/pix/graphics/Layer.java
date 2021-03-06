package ch.aiko.pix.graphics;

import java.util.ArrayList;

import ch.aiko.pix.core.Renderable;
import ch.aiko.pix.core.Updatable;
import ch.aiko.pix.graphics.renderer.Renderer;
import ch.aiko.pix.input.Input;

/**
 * Layers are essentially what allows us to create the game. Everything is a layer. Well not everything, but everything that needs to get drawn is one....
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public abstract class Layer implements Updatable, Renderable {

	/**
	 * The children of this component
	 */
	protected ArrayList<Layer> children = new ArrayList<Layer>();
	/**
	 * The parent (if present) of this layer
	 */
	protected Layer parent = null;

	/**
	 * The Input handling object. Individual for each layer
	 */
	protected Input input = new Input(this);

	/**
	 * Sorts the children in ascending order (low level to high level) If you think the layers aren't in order you can call this function to sort them
	 */
	/**
	 * public void sortChildren() { children.sort(new Comparator<Layer>() { public int compare(Layer o1, Layer o2) { return o2.getLevel() - o1.getLevel(); // If o2 on top of o1 then 1 else -1 or 0 if equal } }); }
	 */

	/**
	 * Adds a child to the current layer
	 * 
	 * @param child
	 *            The child to add
	 */
	public void addChild(Layer child) {
		child.parent = this;
		child.input.setFrameX(input.getFrameX());
		child.input.setFrameY(input.getFrameX());
		child.input.setFrameWidth(input.getFrameWidth());
		child.input.setFrameHeight(input.getFrameHeight());
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getLevel() > child.getLevel()) {
				// System.out.println("Adding child @" + i + "." + child.getClass());
				children.add(i, child);
				return;
			}
		}
		children.add(children.size(), child);
	}

	/**
	 * Removes a layer from being rendered and updated.
	 * 
	 * @param child
	 *            The layer to remove
	 */
	public void removeChild(Layer child) {
		children.remove(child);
	}

	/**
	 * Gets a list with all the children in it. The list should be sorted in ascending order
	 * 
	 * @return The list with the children
	 */
	public ArrayList<Layer> getChildren() {
		return children;
	}

	/**
	 * Gets the input object of this layer
	 * 
	 * @return The input object for this layer
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * Calls the children's preRender, renderChildren, render functions in that order
	 * 
	 * @param renderer
	 *            The Renderer used to draw to the panel
	 */
	public final void renderChildren(Renderer renderer) {
		if (children.size() == 0) return;
		int i = children.size() - 1;
		for (; i >= 0; i--) {
			Layer c = children.get(i);
			if (c == null) continue;
			if (c.blocksRender()) break;
		}
		for (i = i < 0 ? 0 : i; i < children.size(); i++) {
			Layer c = children.get(i);
			if (c == null) continue;
			c.preRender(renderer);
			c.renderChildren(renderer);
			c.render(renderer);
		}
	}

	/**
	 * Calls the children's preUpdate, updateChildrem update functions in that order
	 */
	public final void updateChildren() {
		for (int i = children.size() - 1; i >= 0; i--) {
			Layer c = children.get(i);
			if (c == null) continue;
			c.preUpdate();
			c.updateChildren();
			if (c.update(c)) break;
		}
	}

	/**
	 * Retrieves the PixPanel, the topmost element in the frame
	 * 
	 * @returns The panel, or null if an error occurs
	 */
	public PixPanel getPanel() {
		if (parent == null && this instanceof PixPanel) return (PixPanel) this;
		else if (parent != null) return parent.getPanel();
		return null;
	}

	/**
	 * The level this layer is on. The higher the level, the higher is it.
	 * 
	 * @return
	 */
	public abstract int getLevel();

	/**
	 * Renders this layer. Unlike the render method this one gets called before renderChildren is called.
	 * 
	 * @param renderer
	 */
	public abstract void preRender(Renderer renderer);

	/**
	 * Updates this layer. Unlike the normal update method, this one gets called before any of the children are updated.
	 * 
	 * @param i
	 *            The Input Object
	 */
	public abstract void preUpdate();

	/**
	 * If this layer blocks events from passing to lower layers
	 * 
	 * @return If events are being blocked
	 */
	public abstract boolean blocksEvents();

	/**
	 * If this layer blocks the layers beneath it to be rendered
	 * 
	 * @return
	 */
	public abstract boolean blocksRender();
}

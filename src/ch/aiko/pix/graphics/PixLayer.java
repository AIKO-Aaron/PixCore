package ch.aiko.pix.graphics;

import ch.aiko.pix.core.Renderable;
import ch.aiko.pix.core.Updatable;
import ch.aiko.pix.graphics.renderer.Renderer;

/**
 * Basic Layer implementation with just the render and update method used.
 * preRender and preUpdate aren't used.
 * Every PixLayer doesn't block events from passing through
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixLayer extends Layer {

	private Renderable renderable;
	private Updatable updatable;
	private int level;
	
	public PixLayer(Renderable r, Updatable u, int level) {
		renderable = r;
		updatable = u;
		this.level = level;
	}
	
	@Override
	public boolean update() {
		return updatable.update();
	}

	@Override
	public boolean render(Renderer r) {
		return renderable.render(r);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void preRender(Renderer renderer) {}

	@Override
	public void preUpdate() {}

	@Override
	public boolean blocksEvents() {
		return false;
	}

}

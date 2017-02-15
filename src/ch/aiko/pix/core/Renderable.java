package ch.aiko.pix.core;

import ch.aiko.pix.graphics.renderer.Renderer;

/**
 * Renderable is a basic interface for use with lambdas
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public interface Renderable {

	/**
	 * Renders this Object and all of its children.
	 * Returns true if this object blocks the ones beneath from being rendered
	 * 
	 * @param r The renderer to do so
	 */
	public boolean render(Renderer r);
	
}

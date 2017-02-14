package ch.aiko.pix.graphics;

import ch.aiko.pix.graphics.renderer.BasicRenderer;

/**
 * Renderable is a basic interface for use with lambdas
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public interface Renderable {

	/**
	 * Renderes this Object and all of its children
	 * 
	 * @param r The renderer to do so
	 */
	public void render(BasicRenderer r);
	
}

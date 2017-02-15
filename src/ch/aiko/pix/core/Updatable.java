package ch.aiko.pix.core;

import ch.aiko.pix.graphics.Layer;

/**
 * A bsaic updatable for use with lambdas
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public interface Updatable {

	/**
	 * Updates this object and its children
	 * 
	 * @param l The layer this updatable is part of. Mostly useless except for lambdas, where you wouldn't have the input etc.
	 * 
	 */
	public boolean update(Layer l); 
	
}

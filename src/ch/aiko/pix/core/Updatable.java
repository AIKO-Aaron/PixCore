package ch.aiko.pix.core;

/**
 * A bsaic updatable for use with lambdas
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public interface Updatable {

	/**
	 * Updates this object and its children
	 */
	public boolean update(); 
	
}

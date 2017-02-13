package ch.aiko.pix;

import java.util.regex.Pattern;

/**
 * This library will use Java 8
 * 
 * Core class. Does nothing except the version handling...
 * 
 * @author AIKO (Aaron Hodel) 2017
 *
 */
public class PixCore {

	/** The major version number */
	public static final int MAJOR_VERSION = 1;
	/** The minor version number */
	public static final int MINOR_VERSION = 0;
	/** The patch version number */
	public static final int PATCH_NUMBER = 0;

	/**
	 * Puts the version numbers in a row, split by dots.
	 * 
	 * @return The version as a String
	 */
	public static final String getVersionString() {
		return MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_NUMBER;
	}

	/**
	 * Checks if the given version is supported by this version. All version less than this (should) be supported
	 * 
	 * @param version
	 *            The version to check
	 * @return Whether the version is supported or not
	 */
	public static final boolean isVersionSupported(String version) {
		int ma = Integer.parseInt(version.split(Pattern.quote("."))[0]);
		int mi = Integer.parseInt(version.split(Pattern.quote("."))[1]);
		int pa = Integer.parseInt(version.split(Pattern.quote("."))[2]);

		if (ma > MAJOR_VERSION || (mi >= MINOR_VERSION && ma == MAJOR_VERSION) || (pa >= PATCH_NUMBER && ma == MAJOR_VERSION && mi == MINOR_VERSION)) return false;
		return true;
	}

}

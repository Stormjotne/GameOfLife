package application;
/**
 * Custom exception for loading patterns from RLE files.
 * @author Ruby
 *
 */
public class PatternFormatException extends Exception {
	
	public PatternFormatException() {
		System.err.println("Pattern could not be loaded!");
	}
}

package application;
/** 
 * This class is used to import information about a Game of Life pattern from RLE files.
 * The PatternReader class invokes an object of Pattern which holds key values extracted from the file.
 * The pattern is represented by the board using the size and plot coordinates.
 * General information is displayed in a separate box.
 * */
public class GameOfLifePattern {
	private String patternName;
	private String patternOrigin;
	private String patternInformation;
	private int WIDTH;
	private int HEIGHT;
	private String lifeRules;
	private boolean notHighLife;
	char[] charPlotPatternArray;
	/** 
	 * The constructor of Pattern.
	 * It takes Strings and parsed strings from the PatternReader as arguments, and stores the values.
	 * Take note of the boolean variable notHighLife. If it returns true, the imported pattern is made for the normal version of Conway's Game of Life.
	 * */
	public GameOfLifePattern(String patternName, String patternOrigin, String patternInformation, int WIDTH, int HEIGHT, String lifeRules, char[] charPlotPatternArray) {
		this.patternName = patternName;
		this.patternOrigin = patternOrigin;
		this.patternInformation = patternInformation;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.lifeRules = lifeRules;
		this.notHighLife = (lifeRules == "6B3/S23");
		this.charPlotPatternArray = charPlotPatternArray;
	}
	
	public String toString() {
		String output = new String("Name: " + this.patternName + " | Origin: " + this.patternOrigin + " | Information: " + this.patternInformation + " | Width of Pattern: " + this.WIDTH + " | Height of Pattern: " + this.HEIGHT + " | Plot: ");
		return output;
	}
}

package application;

public class GameOfLifePattern {
	private String patternName;
	private String patternOrigin;
	private String patternInformation;
	private int WIDTH;
	private int HEIGHT;
	private boolean notHighLife;
	
	public GameOfLifePattern(String patternName, String patternOrigin, String patternInformation, int WIDTH, int HEIGHT) {
		this.patternName = patternName;
		this.patternOrigin = patternOrigin;
		this.patternInformation = patternInformation;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}
}

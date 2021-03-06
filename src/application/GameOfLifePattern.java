package application;
/** 
 * This class is used to import information about a Game of Life pattern from RLE files.
 * The PatternReader class invokes an object of Pattern which holds key values extracted from the file.
 * The pattern is represented by the board using the size and plot coordinates.
 * General information is displayed in a separate box.
 * @author Ruby
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
	GameOfLifeCell[][] patternBoard;
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
	
	GameOfLifeCell[][] constructPatternFromRLE() throws PatternFormatException, ArrayIndexOutOfBoundsException {
		  System.out.println("Pattern Width: " + this.WIDTH + " Pattern Height: " + this.HEIGHT);
		patternBoard = new GameOfLifeCell[WIDTH+2][HEIGHT+2];
		setCleanBoard(patternBoard.length, patternBoard[0].length);
		  System.out.println("Array Width: " + patternBoard.length + " Array Height: " + patternBoard[0].length);
		int counter = 0;
		//b = dead cell, o = alive cell, $ = end of line
		//i and j = 1 to avoid the edges.
		//j in the outer for-loop to iterate horizontally
		for (int j = 1; j < patternBoard[0].length; j++) {
			  for (int i = 1; i < patternBoard.length; i++) {
				  if(charPlotPatternArray[counter] == 'o'){
					  patternBoard[i][j] = new GameOfLifeCell(i, j, (byte) 1);
					  counter++;
				  }
				  else if(charPlotPatternArray[counter] == 'b'){
					  patternBoard[i][j] = new GameOfLifeCell(i, j, (byte) 0);
					  counter++;
				  }
				  else if(charPlotPatternArray[counter] == '$'){
					  i = patternBoard.length;
					  counter++;
				  }

				  else if(Character.isDigit(charPlotPatternArray[counter])){
					  int internalCounter = Character.getNumericValue(charPlotPatternArray[counter]);
					  counter++;
					  // Check if next char is also a digit.
					  // If it is, concatenate it to the first with general desimal arithmetic.
					  if(Character.isDigit(charPlotPatternArray[counter])){
						  int internalDoubleDigitCounter = (internalCounter * 10) + Character.getNumericValue(charPlotPatternArray[counter]);
						  counter++;
						// Check if next char is also a digit.
						  // If it is, concatenate it to the first two with general desimal arithmetic.
						  if(Character.isDigit(charPlotPatternArray[counter])){
							  int internalTripleDigitCounter = (internalDoubleDigitCounter * 100) + (internalCounter * 10) + Character.getNumericValue(charPlotPatternArray[counter]);
							  counter++;

							  // Check if next char is 'o', add as many live cells as the internalCounter states.
							  if(charPlotPatternArray[counter] == 'o'){
								  for (int k = 0; k < internalTripleDigitCounter; k++){
									  patternBoard[i+k][j] = new GameOfLifeCell(i, j, (byte) 1);
								  }
								  i+=internalTripleDigitCounter-2;
							  }
							  // Check if next char is 'b', add as many dead cells as the internalCounter states.
							  else if(charPlotPatternArray[counter] == 'b') {
								  for (int k = 0; k < internalTripleDigitCounter; k++){
									  patternBoard[i+k][j] = new GameOfLifeCell(i, j, (byte) 0);
								  }
								  i+=internalTripleDigitCounter-2;
							  }
							  // Check if next char is '$', skip as many lines as the internalCounter states.
							  else if(charPlotPatternArray[counter] == '$'){
								  j+=internalTripleDigitCounter-2;
								  i = patternBoard.length;
							  }

							  else {
								  counter++;
								  throw new PatternFormatException();
							  }
						  }

						  // Check if next char is 'o', add as many live cells as the internalCounter states.
						  if(charPlotPatternArray[counter] == 'o'){
							  for (int k = 0; k < internalDoubleDigitCounter; k++){
								  patternBoard[i+k][j] = new GameOfLifeCell(i, j, (byte) 1);
							  }
							  //i+=internalDoubleDigitCounter-(internalDoubleDigitCounter*2/3);
							  i+=internalDoubleDigitCounter-2;
						  }
						  // Check if next char is 'b', add as many dead cells as the internalCounter states.
						  else if(charPlotPatternArray[counter] == 'b') {
							  for (int k = 0; k < internalDoubleDigitCounter; k++){
								  patternBoard[i+k][j] = new GameOfLifeCell(i, j, (byte) 0);
							  }
							  //i+=internalDoubleDigitCounter-(internalDoubleDigitCounter*2/3);
							  i+=internalDoubleDigitCounter-2;
						  }
						  // Check if next char is '$', skip as many lines as the internalCounter states.
						  else if(charPlotPatternArray[counter] == '$'){
							  j+=internalDoubleDigitCounter-2;
							  i = patternBoard.length;
						  }

						  else {
							  counter++;
							  throw new PatternFormatException();
						  }
					  }
					  // Check if next char is 'o', add as many live cells as the internalCounter states.
					  else if(charPlotPatternArray[counter] == 'o'){
						  for (int k = 0; k < internalCounter; k++){
							  patternBoard[i+k][j] = new GameOfLifeCell(i, j, (byte) 1);
						  }
						  //i+=internalCounter-(internalCounter*2/3);
						  i+=internalCounter-2;
					  }
					  // Check if next char is 'b', add as many dead cells as the internalCounter states.
					  else if (charPlotPatternArray[counter] == 'b'){
						  for (int k = 0; k < internalCounter; k++){
							  patternBoard[i+k][j] = new GameOfLifeCell(i, j, (byte) 0);
						  }
						  i+=internalCounter-2;
					  }
					  // Check if next char is '$', skip as many lines as the internalCounter states.
					  else if(charPlotPatternArray[counter] == '$'){
						  j+=internalCounter-2;
						  i = patternBoard.length;
					  }

					  else {
						  counter++;
						  throw new PatternFormatException();
					  }
				  }
				  // Check if next char is '!', set the for-loop variables to conclude the loop.
				  else if(charPlotPatternArray[counter] == '!'){
					  j = patternBoard[0].length;
					  i = patternBoard.length;
				  }
				  
				  else {
					  counter++;
					  throw new PatternFormatException();
				  }
			  }
		}
		return patternBoard;
	}
	private void setCleanBoard(int x, int y){
		GameOfLifeCell[][] cleanBoard = new GameOfLifeCell[x][y];
		for (int i = 0; i < x; i++) {
			  for (int j = 0; j < y; j++) {
				  cleanBoard[i][j] = new GameOfLifeCell(i, j, (byte) 0);
			  }
		}
		patternBoard = cleanBoard;
	}
	
	private void printArray(GameOfLifeCell[][] x){
		for (int i = 0; i < (x.length); i++) {
			  for (int j = 0; j < (x[0].length); j++) {
				  System.out.print(x[i][j]);
			  }
		}
	}
	
	@Override
	public String toString() {
		String output = "Pattern Width: " + this.WIDTH + "Pattern Height: " + this.HEIGHT;
		
		return output;
	}
}

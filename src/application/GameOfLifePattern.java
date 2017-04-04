package application;
/** 
 * This class is used to import information about a Game of Life pattern from RLE files.
 * The PatternReader class invokes an object of Pattern which holds key values extracted from the file.
 * The pattern is represented by the board using the size and plot coordinates.
 * General information is displayed in a separate box.
 * */
public class GameOfLifePattern {
	public GameOfLifeModel game;
	private String patternName;
	private String patternOrigin;
	private String patternInformation;
	private int WIDTH;
	private int HEIGHT;
	private String lifeRules;
	private boolean notHighLife;
	char[] charPlotPatternArray;
	byte[][] patternBoard;
	/** 
	 * The constructor of Pattern.
	 * It takes Strings and parsed strings from the PatternReader as arguments, and stores the values.
	 * Take note of the boolean variable notHighLife. If it returns true, the imported pattern is made for the normal version of Conway's Game of Life.
	 * */
	public GameOfLifePattern(GameOfLifeModel game, String patternName, String patternOrigin, String patternInformation, int WIDTH, int HEIGHT, String lifeRules, char[] charPlotPatternArray) {
		this.patternName = patternName;
		this.patternOrigin = patternOrigin;
		this.patternInformation = patternInformation;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.lifeRules = lifeRules;
		this.notHighLife = (lifeRules == "6B3/S23");
		this.charPlotPatternArray = charPlotPatternArray;
	}
	
	public byte[][] constructPatternFromRLE() {
		patternBoard = new byte[WIDTH+2][HEIGHT+2];
		int counter = 0;
		//b = dead cell, o = alive cell, $ = end of line
		//i and j = 1 to avoid the edges.
		//j in the outer for-loop to iterate horizontally
		for (int j = 1; j < patternBoard[0].length; j++) {
			  for (int i = 1; i < patternBoard.length; i++) {
				  if(charPlotPatternArray[counter] == 'b'){
					  patternBoard[i][j] = 0;
					  counter++;
				  }
				  else if(charPlotPatternArray[counter] == 'o'){
					  patternBoard[i][j] = 1;
					  counter++;
				  }
				  else if(charPlotPatternArray[counter] == '$'){
					  counter++;
				  }
				  
				  else if(charPlotPatternArray[counter] == '2'){
					  counter++;
					  if(charPlotPatternArray[counter] == 'o'){
						  patternBoard[i][j] = 1;
						  i++;
						  patternBoard[i][j] = 1;
						  counter++;
					  }
					  else {
						  patternBoard[i][j] = 0;
						  i++;
						  patternBoard[i][j] = 0;
						  counter++;
					  }
				  }
				  else if(charPlotPatternArray[counter] == '3'){
					  counter++;
					  if(charPlotPatternArray[counter] == 'o'){
						  patternBoard[i][j] = 1;
						  i++;
						  patternBoard[i][j] = 1;
						  i++;
						  patternBoard[i][j] = 1;
						  //Outofbounds patternBoard[i][j=j+2] = 1;
						  counter++;
					  }
					  else {
						  patternBoard[i][j] = 0;
						  i++;
						  patternBoard[i][j] = 0;
						  i++;
						  patternBoard[i][j] = 0;
						  counter++;
					  }
				  }
				  else if(charPlotPatternArray[counter] == '!'){
					  i = patternBoard[0].length-1;
					  j = patternBoard.length-1;
				  }
				  
				  /*else if(Character.isDigit(charPlotPatternArray[counter])){
					  int internalCounter = Character.getNumericValue(charPlotPatternArray[counter]);
					  counter++;
					  if(charPlotPatternArray[counter] == 'o'){
						  for (int k = 1; k < internalCounter; k++){
							  patternBoard[i+k][j] = 1;
						 }
						 i+=internalCounter;
					 }
					  else {
						  for (int k = 1; k < internalCounter; k++){
							  patternBoard[i+k][j] = 0;
						  }
						  i+=internalCounter;
					  }
				  }*/
				  
				 
			  }
		}
		//game.setBoard(patternBoard);
		return patternBoard;
	}
	
	public void printArray(byte[][] x){
		for (int i = 0; i < (x.length); i++) {
			  for (int j = 0; j < (x[0].length); j++) {
				  System.out.println(x[i][j]);
			  }
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		for(int i = 0; i < patternBoard.length; i++){
			for(int j = 0; j < patternBoard[i].length; j++){
				output.append(patternBoard[j][i]);
			}
		}
		return output.toString();
	}
}

package application;

import java.util.Random;
/**
 * 
 * This class contains data about the game board (arrays) and cells.
 * Class should be split into Board and Cell before we start working on extra assignments. 
 * @author Ruby, Håkon & Julia
 */
public class GameOfLifeStatic extends GameOfLife {
	public static int k = 260, m = 120;
	public static GameOfLifeCell[][] board = new GameOfLifeCell[k][m];
	public GameOfLifeCell[][] first = new GameOfLifeCell[k][m];
	int neighbors;
	int previous;
	int state;
		
	public GameOfLifeStatic(){
		board = setCleanBoard(k,m);
		first = board;
		System.out.println("New Static Board!");
	}
	/**
	 * Counts neighbors around a x,y coordinate and returns an integer.
	 * @param  
	 * @author Ruby, Håkon & Julia
	 * */
	public int countNeighbors(int x, int y){ 
		neighbors = 0;
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				// Sums the neighbors' states, which are 0 or 1.
				// Executes a modulus operation to achieve wrap-around, 
				// i.e. the cells on the top count as neighbors for the bottom cells, and the cells to the left are neighbors to the rightmost cells.
			    neighbors += getBoard()[(x+i+k)%k][(y+j+m)%m].getPreviousState();
			   }
			}
		neighbors -= getBoard()[x][y].getPreviousState(); //Trekker fra cellens egen verdi: 0, eller 1.
		return neighbors;
	}
	
	/** 
	 * Resets the game board.
	 * */
	public void firstGeneration(){
		board = first;
	}

	/**
	 * Initializes an empty array to be represented by the game board.
	 * @param x : the width of the board.
	 * @param y : the height of the board.
	 * */
	public GameOfLifeCell[][] setCleanBoard(int x, int y){
		GameOfLifeCell[][] cleanBoard = new GameOfLifeCell[x][y];
		for (int i = 0; i < (board.length); i++) {
			  for (int j = 0; j < (board[0].length); j++) {
				  cleanBoard[i][j] = new GameOfLifeCell(i, j, (byte) 0);
			  }
		}
		return cleanBoard;
	}

	/**
	 * Initializes a random array to be represented by the game board.
	 * @param x : the width of the board.
	 * @param y : the height of the board.
	 * */	
	public GameOfLifeCell[][] setRandomBoard(int x, int y){
		GameOfLifeCell[][] randomBoard = new GameOfLifeCell[x][y];
		Random ranNum = new Random();
		for (int i = 0; i < (board.length); i++) {
			  for (int j = 0; j < (board[0].length); j++) {
				byte n = (byte)ranNum.nextInt(2);
				randomBoard[i][j] = new GameOfLifeCell(i, j, n);
			}
		}
		return randomBoard;
	}

	/**
	 * Initializes a array which incorporates any lesser user-defined arrays.
	 * Useful for importing existing GoL patterns.
	 * Used in the PatternReader class to modify the game board with information parsed from RLE files.
	 * @param boardArray : any two-dimensional array lesser than the current board's width and height.
	 * Pattern.constructPatternFromRLE() returns such an array from RLE files.
	 */
	public void setPatternBoard(GameOfLifeCell[][] boardArray) {
		GameOfLifeCell[][] temporaryBoard = setCleanBoard(k, m);
		
		for (int i = 0; i < (boardArray.length); i++) {
			  for (int j = 0; j < (boardArray[0].length); j++) {
				  temporaryBoard[i][j] = boardArray[i][j];
			  }
		}
		board = temporaryBoard;
	}
	
	public void setBoard(GameOfLifeCell[][] boardArray) {
		GameOfLifeCell[][] temporaryBoard = new GameOfLifeCell[k][m];
		for (int i = 0; i < k; i++) {
			  for (int j = 0; j < m; j++) {
				  temporaryBoard[i][j] = boardArray[i][j];
			  }
		}
		board = temporaryBoard;
	}
	
	public void changeSingleBoardValueToOne(int x, int y){
		board[x][y] = new GameOfLifeCell(x, y, (byte) 1);
	}
	
	public void changeSingleBoardValueToZero(int x, int y){
		board[x][y] = new GameOfLifeCell(x, y, (byte) 0);
	}
	
	public byte getSingleValue(int x, int y){
		return board[x][y].getCellState();
	}
	
	public GameOfLifeCell[][] getBoard(){
		return board;
	}
	
	public void setBoardWidth(int WIDTH){
		k = WIDTH;
	}
	
	public void setBoardheight(int HEIGHT){
		m = HEIGHT;
	}
	
	public int getBoardWidth(){
		return k;
	}
	
	public int getBoardHeight(){
		return m;
	}
	
	
	public String toString() {
		StringBuffer output = new StringBuffer();
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				output.append(board[j][i]);
			}
		}
		return output.toString();
	}
}

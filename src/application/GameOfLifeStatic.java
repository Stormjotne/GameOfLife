package application;

import java.util.Random;
/**
 * 
 * This class contains data about the static board.
 * Inherits from GameOfLife.
 * @author Ruby, Håkon & Julia
 */
public class GameOfLifeStatic extends GameOfLife {
	public static GameOfLifeCell[][] board = new GameOfLifeCell[k][m];
	public GameOfLifeCell[][] first = new GameOfLifeCell[k][m];
		
	public GameOfLifeStatic(){
		setCleanBoard();
		first = board;
		System.out.println("New Static Board!");
	}
	/**
	 * Gets GameOfLifeCell object from static board coordinates.
	 * Sums states of neighbouring cells' in a 3x3 grid, including given object.
	 * Subtracts state of given object.
	 * Uses modulus to get cells from opposite "side" of the board, in case given cell's coordinates are at either extreme.
	 * If 250 is the width of the board, such extreme coordinates might be:
	 * (0,1) , (250, 100) , (45, 0) , (0, 250)
	 * Cell objects keep track of previous and current number of neighbours. Those variables are updated in this loop.
	 * If a cell has 0 neighbours, or if previous and current number of neighbours is the same, the cell will be deactivated.
	 * @param x
	 * @param y
	 */
	int countNeighbors(int x, int y){ 
		int neighbors = 0;
		currentCell = getCell(x,y);
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				// Sums the neighbors' states, which are 0 or 1.
				// Executes a modulus operation to achieve wrap-around, 
				// i.e. the cells on the top count as neighbors for the bottom cells, and the cells to the left are neighbors to the rightmost cells.
			    neighbors += getPreviousCellState((x+i+k)%k,(y+j+m)%m);
			  }
			}
		neighbors -= currentCell.getPreviousState(); //Trekker fra cellens egen verdi: 0, eller 1.
		currentCell.currentNeighbors(neighbors);
		//	Unsuccessful implementation of tracking active cells.
		/*if (neighbors <= 0 || currentCell.getCurrentNeighbors() == currentCell.getPreviousNeigbors()) {
			currentCell.deActivate();
		}*/
		return neighbors;
	}
	/**
	 * returns GameOfLifeCell object from static board coordinates.
	 */
	GameOfLifeCell getCell(int x, int y) {
		if (x > getWidth() || y > getHeight()) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return board[x][y];
	}
	/**
	 * Change state of the cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @param nstate
	 */
	void setCellState(int x, int y, byte nstate) {
		if (x > getWidth() || y > getHeight()) {
			throw new ArrayIndexOutOfBoundsException();
		}
		board[x][y].newCellState(nstate);
	}
	/**
	 * Returns byte 0 or 1 from cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @return
	 */
	byte getCellState(int x, int y) {
		if (x > getWidth() || y > getHeight()) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return board[x][y].getCellState();
	}
	/**
	 * Returns byte 0 or 1 from cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @return
	 */
	byte getPreviousCellState(int x, int y) {
		return board[x][y].getPreviousState();
	}
	/**
	 * Sets all neighbouring cells' active status to true (boolean).
	 */
	void activateNeighborCells(int x, int y) {
		for (int c = -1; c <= 1; c++) {
			for (int d = -1; d <= 1; d++) {
				board[(x+c+k)%k][(y+d+m)%m].activate();
			}
		}
	}
	/** 
	 * Resets the game board.
	 * */
	void firstGeneration(){
		board = first;
	}
	/**
	 * Initializes an empty array to be represented by the game board.
	 * */
	void setCleanBoard(){
		GameOfLifeCell[][] cleanBoard = new GameOfLifeCell[k][m];
		for (int i = 0; i < k; i++) {
			  for (int j = 0; j < m; j++) {
				  cleanBoard[i][j] = new GameOfLifeCell(i, j, (byte) 0);
			  }
		}
		board = cleanBoard;
	}
	/**
	 * Initializes a random array to be represented by the game board.
	 * */	
	void setRandomBoard(){
		GameOfLifeCell[][] randomBoard = new GameOfLifeCell[k][m];
		Random ranNum = new Random();
		for (int i = 0; i < GameOfLife.k; i++) {
			  for (int j = 0; j < GameOfLife.m; j++) {
				byte n = (byte)ranNum.nextInt(2);
				randomBoard[i][j] = new GameOfLifeCell(i, j, n);
			}
		}
		board = randomBoard;
	}
	
	public void setFirstBoard(){
		board = first;
	}

	/**
	 * Initializes a array which incorporates any lesser user-defined arrays.
	 * Useful for importing existing GoL patterns.
	 * Used in the PatternReader class to modify the game board with information parsed from RLE files.
	 * @param boardArray : any two-dimensional array lesser than the current board's width and height.
	 * Pattern.constructPatternFromRLE() returns such an array from RLE files.
	 */
	void setPatternBoard(GameOfLifeCell[][] boardArray) {
		int importedboardwidth = boardArray.length;
		int importedboardheight = boardArray[0].length;
		int widthStart = Math.round(k/2)-(importedboardwidth/2);
		int heightStart = Math.round(m/2)-(importedboardheight/2);
		GameOfLifeCell[][] temporaryBoard = new GameOfLifeCell[k][m];
		for (int i = 0; i < k; i++) {
			  for (int j = 0; j < m; j++) {
				  temporaryBoard[i][j] = new GameOfLifeCell(i, j, (byte) 0);
			  }
		}
		for (int i = 0, c = widthStart; i < importedboardwidth; c++, i++) {
			  for (int j = 0, d = heightStart; j < importedboardheight; d++, j++) {
				  temporaryBoard[c][d] = boardArray[i][j];
			  }
		}
		board = temporaryBoard;
	}
	/**
	 * Sets the static board to be in part equal to the input array.
	 * @param boardArray
	 */
	void setBoard(GameOfLifeCell[][] boardArray) {
		GameOfLifeCell[][] temporaryBoard = new GameOfLifeCell[k][m];
		for (int i = 0; i < GameOfLife.k; i++) {
			  for (int j = 0; j < GameOfLife.m; j++) {
				  temporaryBoard[i][j] = boardArray[i][j];
			  }
		}
		board = temporaryBoard;
	}
	/**
	 * An array-print used for testing arrays of smaller sizes.
	 */
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

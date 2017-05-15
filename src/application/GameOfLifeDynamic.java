package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * This class contains data about the dynamic board.
 * @author Håkon & Ruby
 *
 */
public class GameOfLifeDynamic extends GameOfLife {

	private List<List<GameOfLifeCell>> board;
	private List<List<GameOfLifeCell>> first;
	
	public GameOfLifeDynamic() {
		setCleanBoard();
		first = board;
		System.out.println("New Dynamic Board!");
	}
	/**
	 * Gets GameOfLifeCell object from static board coordinates.
	 * Sums states of neighbouring cells' in a 3x3 grid, including given object.
	 * Subtracts state of given object.
	 * Uses modulus to get cells from opposite "side" of the board, in case given cell's coordinates are at either extreme.
	 * If 250 is the width of the board, such extreme coordinates might be:
	 * (0,1) , (249, 100) , (45, 0) , (0, 249)
	 * Cell objects keep track of previous and current number of neighbours. Those variables are updated in this loop.
	 * If a cell has 0 neighbours, or if previous and current number of neighbours is the same, the cell will be deactivated.
	 * @param x
	 * @param y
	 */
	int countNeighbors(int x, int y) {
		int neighbors = 0;
		// Not fully expanded in either direction. Cells at Left and Top edges die.
        if ((getWidth() < maxk && getHeight() < maxm) && (x == 0 || y == 0 || x == getWidth()-1 || y == getHeight()-1)) {
            return neighbors;
        }
        // Fully expanded at width, while not expanded at height. Cells at Top die.
        else if ((getWidth() == maxk && getHeight() < maxm) && (y == 0 || y == getWidth()-1)) {
        	return neighbors;
        }
        // Fully expanded at height, while not expanded at width. Cells at Left die.
        else if ((getWidth() < maxk && getHeight() == maxm) && (x == 0 || x == getHeight()-1)) {
        	return neighbors;
        }
        // When board is fully expanded, cells at the edges wrap around.
        // Cells not on the edges always go to the following else.
        else
		currentCell = getCell(x,y);
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				// Sums the neighbors' states, which are 0 or 1.
				// Executes a modulus operation to achieve wrap-around, 
				// i.e. the cells on the top count as neighbors for the bottom cells, and the cells to the left are neighbors to the rightmost cells.
				  neighbors += getPreviousCellState((x+i+k)%k,(y+j+m)%m);
				//  neighbors += getPreviousCellState(x+i,y+j);
			  }
			}
		neighbors -= currentCell.getPreviousState();
		currentCell.currentNeighbors(neighbors);
		//	Unsuccessful implementation of tracking active cells.
		/*if (neighbors == 0) {
			currentCell.deActivate();
		}*/
		return neighbors;
	}
	/**
	 * returns GameOfLifeCell object from static board coordinates.
	 */
	GameOfLifeCell getCell(int x, int y) {
		// Only attempts to extend when board isn't fully expanded.
		if (getWidth() < maxk || getHeight() < maxm) {
			if ((x == getWidth()-2 || y == getHeight()-2) && board.get(x).get(y).getCellState() == 1) {
				extendBoard(getWidth()+16, getHeight()+8);
			}
			else if ((x == 1 || y == 1) && board.get(x).get(y).getCellState() == 1) {
				extendBoard(0-16, 0-8);
			}
		}
			return board.get(x).get(y);
	}
	/**
	 * Change state of the cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @param nstate
	 */
	void setCellState(int x, int y, byte nstate) {
		if (x > getWidth() || y > getHeight()) {
			extendBoard(x, y);
		}
		board.get(x).get(y).newCellState(nstate);
	}
	/**
	 * Returns byte 0 or 1 from cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @return
	 */
	byte getCellState(int x, int y) {
		return board.get(x).get(y).getCellState();
	}
	/**
	 * Returns byte 0 or 1 from cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @return
	 */
	byte getPreviousCellState(int x, int y) {
		return board.get(x).get(y).getPreviousState();
	}
	/**
	 * Sets all neighbouring cells' active status to true (boolean).
	 */
	public void activateNeighborCells(int x, int y) {
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				board.get((x+i+getWidth())%getWidth()).get((y+j+getHeight())%getHeight()).activate();
			}
		}
	}
	/**
	 * Initializes an empty array to be represented by the game board.
	 * */
	void setCleanBoard() {
		 List<List<GameOfLifeCell>> cleanBoard = new ArrayList<List<GameOfLifeCell>>();
	        for(int i = 0; i < GameOfLife.k; i++){
	            List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
	            for(int j = 0; j < GameOfLife.m; j++){
	                innerBoard.add(new GameOfLifeCell(i, j, (byte) 0));
	            }
	            cleanBoard.add(innerBoard);
	        }
			board = cleanBoard;
	}
	/**
	 * Initializes a random array to be represented by the game board.
	 * */	
	
	void setRandomBoard(){
		List<List<GameOfLifeCell>> randomBoard = new ArrayList<List<GameOfLifeCell>>();
		Random ranNum = new Random();
		for(int i = 0; i < GameOfLife.k; i++){
            List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
            for(int j = 0; j < GameOfLife.m; j++){
				byte n = (byte)ranNum.nextInt(2);
                innerBoard.add(new GameOfLifeCell(i, j, n));
			}
            randomBoard.add(innerBoard);
		}
		board = randomBoard;
	}
	
	void setFirstBoard(){
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
		if (k < importedboardwidth || m < importedboardheight) {
		extendBoard(importedboardwidth, importedboardheight);
		}
		byte currentCellState;
		int widthStart = Math.round((k/2)-(importedboardwidth/2));
		int heightStart = Math.round((m/2)-(importedboardheight/2));
		List<List<GameOfLifeCell>> temporaryBoard = new ArrayList<List<GameOfLifeCell>>();
		 for(int i = 0; i < GameOfLife.k; i++){
	            List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
	            for(int j = 0; j < GameOfLife.m; j++){
	                innerBoard.add(new GameOfLifeCell(i, j, (byte) 0));
	            }
	            temporaryBoard.add(innerBoard);
	        }
		
		for (int i = 0, c = widthStart; i < boardArray.length; c++, i++) {
			  for (int j = 0, d = heightStart; j < boardArray[0].length; d++, j++) {
				 currentCellState = boardArray[i][j].getCellState();
				 temporaryBoard.get(c).get(d).newCellState(currentCellState);
			  }
		}
		board = temporaryBoard;
	}
	/**
	 * Extends board horizontally from current max-width to the specified x value.
	 * Extends board vertically from min-height to current max-height.
     * Extends board horizontally from min-width to the newly specified max-width (x value).
     * Extends board vertically from current max-height to the the specified y value.
	 * @param x
	 * @param y
	 */
	private void extendBoard(int x, int y){
        int width = getWidth();
		int height = getHeight();
        List<List<GameOfLifeCell>> tempBoard = new ArrayList<List<GameOfLifeCell>>();
        // Should trigger at Bottom or Right side.
        if (x > width-2 || y > height-2) {
            tempBoard.addAll(board);
        	for(int i = width; i <= (x); i++){
        		List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
        		for(int j = 0; j < height; j++){
        			innerBoard.add(new GameOfLifeCell(i, j, (byte) 0));
        		}
        		tempBoard.add(innerBoard);
        		increaseWidth();
        	}
        	width = getWidth();
            for(int i = 0; i < width; i++){
                for(int j = height; j <= (y); j++){
                    tempBoard.get(i).add(new GameOfLifeCell(i, j, (byte) 0));
                    if(y >= m) {
                    	increaseHeight();
                    }
                }
            }
            board = tempBoard;
        }
        // Unsuccessful implementation of leftward and upward expansion.
        // Should trigger at Top or Left side. 
        /*else if (x < 1 || y < 1) {
        	tempBoard.addAll(board);
        	for(int i = width; i <= width+Math.abs(x); i++){
        		List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
        		for(int j = 0; j < height; j++){
        			innerBoard.add(new GameOfLifeCell(i, j, (byte) 0));
        		}
        		tempBoard.add(innerBoard);
        		increaseWidth();
        	}
        	width = getWidth();
            for(int i = 0; i < width; i++){
                for(int j = height; j <= height+Math.abs(y); j++){
                    tempBoard.get(i).add(new GameOfLifeCell(i, j, (byte) 0));
                    if(height+Math.abs(y) >= m) {
                    	increaseHeight();
                    }
                }
            }
            tempBoard.addAll(board);
            board = tempBoard;
        }*/
    }
}

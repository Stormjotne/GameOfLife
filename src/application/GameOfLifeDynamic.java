package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	 * (0,1) , (250, 100) , (45, 0) , (0, 250)
	 * Cell objects keep track of previous and current number of neighbours. Those variables are updated in this loop.
	 * If a cell has 0 neighbours, or if previous and current number of neighbours is the same, the cell will be deactivated.
	 * @param x
	 * @param y
	 */
	public int countNeighbors(int x, int y) {
		neighbors = 0;
        if (x == 0 || y == 0 || x == getWidth()-1 || y == getHeight()-1) {
            return neighbors;
        }
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
		/*if (neighbors == 0 || currentCell.getCurrentNeighbors() == currentCell.getPreviousNeigbors()) {
			currentCell.deActivate();
		}*/
		currentCell.previousNeighbors(neighbors);
		return neighbors;
	}
	/**
	 * returns GameOfLifeCell object from static board coordinates.
	 */
	public GameOfLifeCell getCell(int x, int y) {
		if ((x == getWidth()-2 && y == getHeight()-2) && board.get(x).get(y).getCellState() == 1) {
			extendBoard(x+3,y+3);
		}
		else if ((x == getWidth()-2) && board.get(x).get(y).getCellState() == 1) {
			extendBoard(x+3,getHeight());
		}
		else if ((y == getHeight()-2) && board.get(x).get(y).getCellState() == 1) {
			extendBoard(getHeight(),y+3);
		}
		else if ((x <= 1 && y <= 1)&& board.get(x).get(y).getCellState() == 1) {
			extendBoard(x, y);
		}
		else if ((x <= 1) && board.get(x).get(y).getCellState() == 1) {
			extendBoard(x,getHeight());
		}
		else if ((y <= 1) && board.get(x).get(y).getCellState() == 1) {
			extendBoard(getHeight(),y);
		}
		
		return board.get(x).get(y);
	}
	/**
	 * Change state of the cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @param nstate
	 */
	public void setCellState(int x, int y, byte nstate) {
		board.get(x).get(y).newCellState(nstate);
	}
	/**
	 * Returns byte 0 or 1 from cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @return
	 */
	public byte getCellState(int x, int y) {
		return board.get(x).get(y).getCellState();
	}
	/**
	 * Returns byte 0 or 1 from cell at coordinates x, y.
	 * @param x
	 * @param y
	 * @return
	 */
	public byte getPreviousCellState(int x, int y) {
		return board.get(x).get(y).getPreviousState();
	}
	/**
	 * Sets all neighbouring cells' active status to true (boolean).
	 */
	public void activateNeighborCells(int x, int y) {
		for (int c = -1; c <= 1; c++) {
			for (int d = -1; d <= 1; d++) {
				board.get((x+c+k)%k).get((y+d+m)%m).activate();
			}
		}
	}
	/**
	 * Initializes an empty array to be represented by the game board.
	 * */
	public void setCleanBoard() {
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
	
	public void setRandomBoard(){
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
	public void setPatternBoard(GameOfLifeCell[][] boardArray) {
		byte currentCellState;
		int widthStart = Math.round(k/2)-boardArray.length;
		int heightStart = Math.round(m/2)-boardArray[0].length;
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
	public void extendBoard(int x, int y){
        int width = getWidth();
		int height = getHeight();
        List<List<GameOfLifeCell>> tempBoard = new ArrayList<List<GameOfLifeCell>>();
        tempBoard.addAll(board);
        // Should trigger at Bottom, Right corner.
        if (x > width && y > height) {
        	for(int i = width; i <= x; i++){
        		List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
        		for(int j = 0; j < height; j++){
        			innerBoard.add(new GameOfLifeCell(i, j, (byte) 0));
        		}
        		tempBoard.add(innerBoard);
        		increaseWidth();
        	}
        	width = getWidth();
            for(int i = 0; i < width; i++){
                for(int j = height; j <= y; j++){
                    tempBoard.get(i).add(new GameOfLifeCell(i, j, (byte) 0));
                    if(y >= m) {
                    	increaseHeight();
                    }
                    else {
                    	
                    }
                }
            }
        }
        // Should trigger at the Right side.
        else if (x > width) {
        	for(int i = width; i <= x; i++){
        		List<GameOfLifeCell> innerBoard = new ArrayList<GameOfLifeCell>();
        		for(int j = 0; j < height; j++){
        			innerBoard.add(new GameOfLifeCell(i, j, (byte) 0));
        		}
        		tempBoard.add(innerBoard);
        		increaseWidth();
        	}
        }
        // Should trigger at the Bottom.
        else if (y > height) {
        	for(int i = 0; i < width; i++){
        		for(int j = height; j <= y; j++){
        			tempBoard.get(i).add(new GameOfLifeCell(i, j, (byte) 0));
        			if(y >= m) {
        				increaseHeight();
        			}
        			else {
                	
        			}
        		}
        	}
        }
        // Should trigger at Top, Left corner. 
        else if (x <= 1 && y <= 1) {
        	
        }
        // Should trigger at the Left side. 
        else if (x <= 1) {
        	
        }
        // Should trigger at the Top. 
        else if (y <= 1) {
        	
        }
        board = tempBoard;
    }
}

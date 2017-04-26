package application;

import java.util.Collections;

/**
 * This class contains all logical calculations for game of life.
 * @author Ruby, Håkon
 *
 */
public class GameOfLifeRules {
	int neighbors;
	int changedCells;
	
	
	public GameOfLifeRules(){
	}
	
	public void setCurrentRules() {
		
	}
	
	/**
	 * Applies the standard GoL rules and updates the board.
	 * */
	public void conwayLife(GameOfLife game, GameOfLifeStatic board){
		for (int x = 0; x < (board.getBoardWidth()); x++) {
			  for (int y = 0; y < (board.getBoardHeight()); y++) {
					GameOfLifeCell currentCell = board.getBoard()[x][y];
				  if (currentCell.isActive()) {
				  	neighbors = board.countNeighbors(x,y);
					if ((currentCell.getCellState() == (byte) 1)&&(neighbors < 2)){
						currentCell.newCellState((byte)0);
						//board.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 1)&&(neighbors > 3)){
						currentCell.newCellState((byte)0);
						//board.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3)){
						currentCell.newCellState((byte)1);
						//board.activateNeighborCells(x,y);
					}
					/*else {
						board.getBoard()[x][y] = board.getBoard()[x][y];
					}*/
				  }
			  }
		}
	}
	/**
	 * Applies the no-deaths rules and updates the board.
	 * */
	public void noDeathsLife(GameOfLife game, GameOfLifeStatic board){
		for (int x = 0; x < (board.getBoardWidth()); x++) {
			  for (int y = 0; y < (board.getBoardHeight()); y++) {
				  	int neighbors = board.countNeighbors(x,y);
					
				  	if ((board.getBoard()[x][y].getCellState() == (byte) 0)&&(neighbors == 3)){
				  		board.getBoard()[x][y].newCellState((byte)1);
					}
					/*else {
						nextBoard[x][y] = game.getBoard()[x][y];
					}*/
			  }
		}
	}
}

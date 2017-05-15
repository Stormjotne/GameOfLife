package application;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all logical calculations for game of life.
 * @author Ruby, Håkon
 *
 */
public class GameOfLifeRules {
	int neighbors;
	int changedCells;
	static Object LOCK = new Object();
	private int minIndex;
	private int maxIndex;
	static List<Thread> threads = new ArrayList<Thread>();
	
	/**
	 * Simple constructor.
	 */
	public GameOfLifeRules(){
	}
	
	/**
	 * Applies the standard GoL rules and updates the board.
	 * @param game
	 * */
	protected void conwayLife(GameOfLife game){
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (game.getHeight()); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				if (currentCell.wasActive()) {
				  	neighbors = game.countNeighbors(x,y);
				  	if ((currentCell.getCellState() == (byte) 1)&&(neighbors < 2)){
						game.setCellState(x, y, (byte)0);
						game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 1)&&(neighbors > 3)){
						game.setCellState(x, y, (byte)0);
						game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3)){
						game.setCellState(x, y, (byte)1);
						game.activateNeighborCells(x,y);
					}
				}
			  }
		}
	}
	/**
	 * Used for performance testing.
	 * @param game
	 */
	protected void conwayLifePerformance(GameOfLife game){
		long start = System.currentTimeMillis();
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (game.getHeight()); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  if (currentCell.wasActive()) {
				  	neighbors = game.countNeighbors(x,y);
					if ((currentCell.getCellState() == (byte) 1)&&(neighbors < 2)){
						game.setCellState(x, y, (byte)0);
						game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 1)&&(neighbors > 3)){
						game.setCellState(x, y, (byte)0);
						game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3)){
						game.setCellState(x, y, (byte)1);
						game.activateNeighborCells(x,y);
					}
				  }
			  }
		}
	  	long elapsed = System.currentTimeMillis()-start;
	  	System.out.println("Logic (ms):" + elapsed);
	}
	/**
	 * Resets the value of minIndex and maxIndex.
     * @param increment
     */
    void resetIndex(int increment){
        minIndex = 0 - increment;
        maxIndex = 0;
    }
    
    /**
     * Applies the standard GoL rules and updates the board using threads.
     * Currently only works when the variable 'neighbors' is in a synchronized lock,
     * which in turn defeats the purpose of threads in GoL.
     * Without the lock around the countNeighbors method call, there are some errors with neighborcounting that i can't find.
     * 
     */
    @Deprecated
    protected void conwayLifeConcurrent(GameOfLife game, int increment){
        int minIndex;
        int maxIndex;
        synchronized (LOCK){
            this.minIndex += increment;
            this.maxIndex += increment;
            minIndex = this.minIndex;
            maxIndex = this.maxIndex;
            
        }
        for (int x = minIndex; x < maxIndex; x++) {
              for (int y = 0; y < (GameOfLife.m); y++) {
                    int neighbors;
                    GameOfLifeCell currentCell = game.getCell(x,y);
                 if (currentCell.wasActive()) {
                    synchronized(LOCK){
                     neighbors = game.countNeighbors(x,y);
                    }
                    if ((currentCell.getCellState() == (byte) 1)&&(neighbors < 2)){
                        game.setCellState(x, y, (byte)0);
                        //game.activateNeighborCells(x,y);
                    }
                    else if ((currentCell.getCellState() == (byte) 1)&&(neighbors > 3)){
                        game.setCellState(x, y, (byte)0);
                        //game.activateNeighborCells(x,y);
                    }
                    else if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3)){
                        game.setCellState(x, y, (byte)1);
                        //game.activateNeighborCells(x,y);
                    }
                 }
              }
        }
    }

	
	protected void conwayLifeConcurrentPerformance(GameOfLife game) throws InterruptedException {
		
	}
	/**
	 * Applies the no-deaths rules and updates the board.
	 * */
	protected void noDeathsLife(GameOfLife game){
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (game.getHeight()); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
					  if (currentCell.wasActive()) {
						  int neighbors = game.countNeighbors(x,y);
						  if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3)){
							  game.setCellState(x, y, (byte)1);
							  game.activateNeighborCells(x,y);
						  }
					}
			  }
		}
	}
	/**
	 * Applies the High Life rules and updates the board.
	 * */
	protected void highLife(GameOfLife game){
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (game.getHeight()); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  if (currentCell.isActive()) {
				  	neighbors = game.countNeighbors(x,y);
					if ((currentCell.getCellState() == (byte) 1)&&(neighbors < 2)){
						currentCell.newCellState((byte)0);
						game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 1)&&(neighbors > 3)){
						currentCell.newCellState((byte)0);
						game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3 || neighbors == 6)){
						currentCell.newCellState((byte)1);
						game.activateNeighborCells(x,y);
					}
				  }
			  }
		}
	}
}

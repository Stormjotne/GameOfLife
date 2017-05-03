package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains all logical calculations for game of life.
 * @author Ruby, Håkon
 *
 */
public class GameOfLifeRules {
	int neighbors;
	int changedCells;
	static List<Thread> threads = new ArrayList<Thread>();
	
	
	public GameOfLifeRules(){
	}
	
	/**
	 * Applies the standard GoL rules and updates the board.
	 * */
	public void conwayLife(GameOfLife game){
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (GameOfLife.m); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  if (currentCell.isActive()) {
				  	neighbors = game.countNeighbors(x,y);
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
	
	public void conwayLifePerformance(GameOfLife game){
		long start = System.currentTimeMillis();
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (GameOfLife.m); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  if (currentCell.isActive()) {
				  	neighbors = game.countNeighbors(x,y);
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
	  	long elapsed = System.currentTimeMillis()-start;
	  	System.out.println("Logic (ms):" + elapsed);
	}
	
	public void conwayLifeConcurrent(GameOfLife game) throws InterruptedException {
		int numThreads = Runtime.getRuntime().availableProcessors();
		int widthPerThread = game.getWidth()/numThreads;
		
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (GameOfLife.m); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  if (currentCell.isActive()) {
				  	neighbors = game.countNeighbors(x,y);
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
	
	public void conwayLifeConcurrentPerformance(GameOfLife game) throws InterruptedException {
		
	}
	/**
	 * Applies the no-deaths rules and updates the board.
	 * */
	public void noDeathsLife(GameOfLife game){
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (GameOfLife.m); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  	int neighbors = game.countNeighbors(x,y);
					
				  	if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3)){
				  		game.setCellState(x, y, (byte)1);
						//game.activateNeighborCells(x,y);
					}
			  }
		}
	}
	/**
	 * Applies the High Life rules and updates the board.
	 * */
	public void highLife(GameOfLife game){
		for (int x = 0; x < (game.getWidth()); x++) {
			  for (int y = 0; y < (GameOfLife.m); y++) {
					GameOfLifeCell currentCell = game.getCell(x,y);
				  if (currentCell.isActive()) {
				  	neighbors = game.countNeighbors(x,y);
					if ((currentCell.getCellState() == (byte) 1)&&(neighbors < 2)){
						currentCell.newCellState((byte)0);
						//game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 1)&&(neighbors > 3)){
						currentCell.newCellState((byte)0);
						//game.activateNeighborCells(x,y);
					}
					else if ((currentCell.getCellState() == (byte) 0)&&(neighbors == 3 || neighbors == 6)){
						currentCell.newCellState((byte)1);
						//game.activateNeighborCells(x,y);
					}
				  }
			  }
		}
	}
}

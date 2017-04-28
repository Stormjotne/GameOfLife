package application;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;
/**
 *	Game of Life Object.
 *	GameOfLifeStatic and GameOfLifeDynamic both inherit from this class.
 *	
 * @author Ruby
 */
public abstract class GameOfLife {

	public static int k = 250, m = 250;
	public GameOfLifeCell currentCell;
	int neighbors;
	
	public GameOfLife() {
		
	}
	
	public abstract GameOfLifeCell getCell(int x, int y);
	public abstract int countNeighbors(int x, int y);
	public abstract void setCleanBoard();
	public abstract void setRandomBoard();
	public abstract void setPatternBoard(GameOfLifeCell[][] boardArray);
	public abstract void activateNeighborCells(int x, int y);
	
	public void changeSingleBoardValueToOne(int x, int y){
		getCell(x, y).newCellState((byte) 1);
	}
	
	public void changeSingleBoardValueToZero(int x, int y){
		getCell(x, y).newCellState((byte) 0);
	}
	
	public byte getSingleValue(int x, int y){
		return getCell(x,y).getCellState();
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
	
	
}

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

	public static int k = 100, m = 50;
	public static int maxk = 500, maxm = 250;
	public GameOfLifeCell currentCell;
	int neighbors;
	
	public GameOfLife() {
		
	}
	
	public abstract GameOfLifeCell getCell(int x, int y);
	public abstract void setCellState(int x, int y, byte nstate);
	public abstract byte getCellState(int x, int y);
	public abstract byte getPreviousCellState(int x, int y);
	public abstract int countNeighbors(int x, int y);
	public abstract void setCleanBoard();
	public abstract void setRandomBoard();
	public abstract void setFirstBoard();
	public abstract void setPatternBoard(GameOfLifeCell[][] boardArray);
	public abstract void activateNeighborCells(int x, int y);
	
	public void setWidth(int WIDTH){
		if (WIDTH < 0 || WIDTH > maxk) {
			throw new IllegalArgumentException();
		}
		else
			GameOfLife.k = WIDTH;
	}
	
	public void setHeight(int HEIGHT){
		if (HEIGHT < 0 || HEIGHT > maxm) {
			throw new IllegalArgumentException();
		}
		else
		GameOfLife.m = HEIGHT;
	}
	public void increaseWidth(){
		if (k < maxk) {
			GameOfLife.k++;
		}
	}
	
	public void increaseHeight(){
		if (m < maxm) {
			GameOfLife.m++;
		}
	}
	
	public int getWidth(){
		return GameOfLife.k;
	}
	
	public int getHeight(){
		return GameOfLife.m;
	}
	
	
}

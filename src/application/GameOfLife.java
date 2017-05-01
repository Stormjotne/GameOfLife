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

	public static int k = 200, m = 100;
	public GameOfLifeCell currentCell;
	int neighbors;
	
	public GameOfLife() {
		
	}
	
	public abstract GameOfLifeCell getCell(int x, int y);
	public abstract void setCellState(int x, int y, byte nstate);
	public abstract byte getCellState(int x, int y);
	public abstract int countNeighbors(int x, int y);
	public abstract void setCleanBoard();
	public abstract void setRandomBoard();
	public abstract void setPatternBoard(GameOfLifeCell[][] boardArray);
	public abstract void activateNeighborCells(int x, int y);
	
	public void setWidth(int WIDTH){
		if (WIDTH < 0 || WIDTH > 500) {
			throw new IllegalArgumentException();
		}
		else
		k = WIDTH;
	}
	
	public void setHeight(int HEIGHT){
		if (HEIGHT < 0 || HEIGHT > 500) {
			throw new IllegalArgumentException();
		}
		else
		m = HEIGHT;
	}
	public void increaseWidth(){
		if (k < 500) {
			k++;
		}
		else
		throw new IllegalArgumentException();
	}
	
	public void increaseHeight(){
		if (m < 500) {
			m++;
		}
		else
		throw new IllegalArgumentException();
	}
	
	public int getWidth(){
		return k;
	}
	
	public int getHeight(){
		return m;
	}
	
	
}

package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOfLifeCell {

	private String cellName;
	private byte cellState;
	private byte prevCellState;
	private byte firstCellState;
	public int currentNeighbors;
	public int previousNeighbors;
	public boolean isActive; 
	public boolean wasActive;
	int x, y;
	int z;
	protected int cellSize;
	/**
	 * 
	 */
	public GameOfLifeCell() {
	}
	/**
	 * 
	 * @param cellSize
	 */
	public GameOfLifeCell(int cellSize) {
		this.cellSize = cellSize;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param nstate
	 */
	public GameOfLifeCell(int x, int y, byte nstate) {
		//Coordinates
		this.x = x;
		this.y = y;
		cellName = ("(" + x + "," + y + ")");
		//States
		cellState = nstate;
		prevCellState = cellState;
		firstCellState = cellState;
		previousNeighbors = 0;
		isActive = true;
		wasActive = isActive;
	}
	/**
	 * 
	 */
	public void activate() {
		this.isActive = true;
	}
	/**
	 * 
	 */
	public void deActivate() {
		this.isActive = false;
	}
	/**
	 * 
	 */
	public void savePreviousState() {
		this.prevCellState = cellState;
	}
	/**
	 * 
	 * @param nstate
	 */
	public void newCellState(byte nstate) {
		this.cellState = nstate;
	}
	/**
	 * 
	 * @param cllsz
	 */
	public void setCellSize(int cllsz) {
		this.cellSize = cllsz;
	}
	/**
	 * 
	 * @param neighbors
	 */
	public void currentNeighbors(int neighbors) {
		this.currentNeighbors = neighbors;
	}
	/**
	 * 
	 * @param neighbors
	 */
	public void previousNeighbors(int neighbors) {
		this.previousNeighbors = neighbors;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * 
	 * @return
	 */
	public byte getCellState() {
		return cellState;
	}
	/**
	 * 
	 * @return
	 */
	public byte getPreviousState() {
		return prevCellState;
	}
	/**
	 * 
	 * @return
	 */
	public byte getFirstState() {
		return firstCellState;
	}
	/**
	 * 
	 * @return
	 */
	public int getCellSize() {
		return cellSize;
	}
	/**
	 * 
	 * @return
	 */
	public int getCurrentNeighbors() {
		return currentNeighbors;
	}
	/**
	 * 
	 * @return
	 */
	public int getPreviousNeigbors() {
		return previousNeighbors;
	}
	/**
	 * 
	 * @param cell
	 * @return
	 */
	public byte drawCell(GameOfLifeCell cell) {
		if(cell.getCellState() == 1){
			return 1;
		}
		else  {
			return 0;
		}
	}
	/**
	 * 
	 * @param cell
	 * @return
	 */
	public byte drawCellHistory(GameOfLifeCell cell) {
		if(cell.getPreviousState() == 0 && cell.getCellState() == 1){
			return 2;
		}
		else if(cell.getPreviousState() == 1 && cell.getCellState() == 0){
			return 3;
		}
		else if(cell.getPreviousState() == 1 && cell.getCellState() == 1){
			return 1;
		}
		else  {
			return 0;
		}
	}
	/**
	 * 
	 * @param cell
	 * @return
	 */
	public byte drawCellRandomColor(GameOfLifeCell cell) {
		Random ranNum = new Random();
		byte n = (byte)ranNum.nextInt(7);
		return n;
	}
	/**
	 * Draw method for all cells.
	 * Takes byte value currentState, which is returned from drawCell methods above.
	 * Based on combinations on different variables in cell objects, colour representations may vary greatly.
	 * @param gc
	 * @param c
	 * @param x
	 * @param y
	 * @param cellSize
	 * @param currentState
	 */
	public void drawBox(GraphicsContext gc, Color c, int x, int y, int cellSize, byte currentState) {
		if (currentState == 0) {
			c = Color.WHITE;
		}
		else if (currentState == 2) {
			c = Color.BLUE;
		}
		else if (currentState == 3) {
			c = Color.RED;
		}
		else if (currentState == 4) {
			c = Color.GREEN;
		}
		else if (currentState == 5) {
			c = Color.PURPLE;
		}
		else if (currentState == 6) {
			c = Color.YELLOW;
		}
		else if (currentState == 7) {
			c = Color.CYAN;
		}
		else if (currentState == 8) {
			c = Color.ORANGE;
		}
		gc.setFill(c);
		gc.fillRect(x*cellSize, y*cellSize, cellSize-cellSize*0.2, cellSize-cellSize*0.2);
	}
	
}

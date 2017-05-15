package application;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * This class contains information about the cells.
 * Each GameOfLifeCell object stores values about its current and previous state, neighbors and activity.
 * @author Ruby
 *
 */
public class GameOfLifeCell {

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
	 * Empty constructor.
	 */
	public GameOfLifeCell() {
	}
	/**
	 * Simple constructor used for initializing the current cellSize.
	 * @param cellSize
	 */
	public GameOfLifeCell(int cellSize) {
		this.cellSize = cellSize;
	}
	/**
	 * Constructor used in arrays and arraylists to create cells with a state.
	 * @param x
	 * @param y
	 * @param nstate
	 */
	public GameOfLifeCell(int x, int y, byte nstate) {
		//Coordinates
		this.x = x;
		this.y = y;
		//States
		cellState = nstate;
		prevCellState = cellState;
		firstCellState = cellState;
		currentNeighbors = 0;
		previousNeighbors = currentNeighbors;
		isActive = true;
		wasActive = isActive;
	}
	/**
	 * Activate a cell.
	 */
	void activate() {
		this.isActive = true;
	}
	/**
	 * Deactive a cell.
	 */
	void deActivate() {
		this.isActive = false;
	}
	/**
	 * Save previous activity of cell.
	 */
	void saveActivity() {
		this.wasActive = isActive;
	}
	/**
	 * Save previous state of cell.
	 */
	void savePreviousState() {
		this.prevCellState = cellState;
	}
	/**
	 * Save previous neighbor count of cell.
	 */
	void savePreviousNeighbors() {
		this.previousNeighbors = currentNeighbors;
	}
	/**
	 * Set new state of cell.
	 * @param nstate
	 */
	void newCellState(byte nstate) {
		this.cellState = nstate;
	}
	/**
	 * Set new cellSize.
	 * @param cllsz
	 */
	void setCellSize(int cllsz) {
		this.cellSize = cllsz;
	}
	/**
	 * Save current neighbor count of cell.
	 * @param neighbors
	 */
	void currentNeighbors(int neighbors) {
		this.currentNeighbors = neighbors;
	}
	/**
	 * Save previous neighbor count of cell.
	 * @param neighbors
	 */
	void previousNeighbors(int neighbors) {
		this.previousNeighbors = neighbors;
	}
	/**
	 * Return current activity.
	 * @return
	 */
	boolean isActive() {
		return isActive;
	}
	/**
	 * Return previous activity.
	 * @return
	 */
	boolean wasActive() {
		return wasActive;
	}
	/**
	 * Return state of a cell.
	 * @return
	 */
	byte getCellState() {
		return cellState;
	}
	/**
	 * Return previous cell state.
	 * @return
	 */
	byte getPreviousState() {
		return prevCellState;
	}
	/**
	 * Return first state of cell.
	 * @return
	 */
	byte getFirstState() {
		return firstCellState;
	}
	/**
	 * Return cellSize.
	 * @return
	 */
	int getCellSize() {
		return cellSize;
	}
	/**
	 * Return current neighbor count of cell.
	 * @return
	 */
	int getCurrentNeighbors() {
		return currentNeighbors;
	}
	/**
	 * Return previous neighbor count of cell.
	 * @return
	 */
	int getPreviousNeigbors() {
		return previousNeighbors;
	}
	/**
	 * Basic drawing return method.
	 * Returns 0 or 1.
	 * @param cell
	 * @return
	 */
	byte drawCell(GameOfLifeCell cell) {
		if(cell.getCellState() == 1){
			return 1;
		}
		else  {
			return 0;
		}
	}
	/**
	 * Historic drawing return method.
	 * Returns 0, 1, 2, or 3, based on a combination of historic factors of the cell.
	 * @param cell
	 * @return
	 */
	byte drawCellHistory(GameOfLifeCell cell) {
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
	void drawBox(GraphicsContext gc, Color c, int x, int y, int cellSize, byte currentState, double widthRatio, double heightRatio) {
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
		gc.fillRect((x*cellSize)*widthRatio, (y*cellSize)*heightRatio, (cellSize-cellSize*0.2)*widthRatio, (cellSize-cellSize*0.2)*heightRatio);
	}
	
}

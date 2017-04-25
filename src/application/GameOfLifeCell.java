package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOfLifeCell extends GameOfLife {

	private byte cellState;
	private byte prevCellState;
	private byte firstCellState;
	public int currentNeighbors;
	public int previousNeighbors;
	int x, y;
	int z;

	protected int cellSize;
	
	public GameOfLifeCell() {
	}
	
	public GameOfLifeCell(int cellSize) {
		this.cellSize = cellSize;
	}
	
	public GameOfLifeCell(int x, int y, byte nstate) {
		//Coordinates
		this.x = x;
		this.y = y;
		//States
		cellState = nstate;
		prevCellState = cellState;
		firstCellState = cellState;
		previousNeighbors = 0;
	}
	
	public void savePreviousState() {
		this.prevCellState = cellState;
	}
	
	public void newCellState(byte nstate) {
		this.cellState = nstate;
	}
	
	public void setCellSize(int cllsz) {
		this.cellSize = cllsz;
	}
	
	public void currentNeighbors(int neighbors) {
		this.currentNeighbors = neighbors;
	}
	
	public void previousNeighbors(int neighbors) {
		this.previousNeighbors = neighbors;
	}
	
	public byte getCellState() {
		return cellState;
	}
	public byte getPreviousState() {
		return prevCellState;
	}
	public byte getFirstState() {
		return firstCellState;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public int getCurrentNeighbors() {
		return currentNeighbors;
	}
	
	public int getPreviousNeigbors() {
		return previousNeighbors;
	}
	
	public byte drawCell(GameOfLifeCell cell) {
		if(cell.getCellState() == 1){
			return 1;
		}
		else  {
			return 0;
		}
	}
	
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
	
	public byte drawCellRandomColor(GameOfLifeCell cell) {
		Random ranNum = new Random();
		byte n = (byte)ranNum.nextInt(7);
		return n;
	}
	
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

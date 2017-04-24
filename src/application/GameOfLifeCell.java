package application;

public class GameOfLifeCell extends GameOfLife {

	private byte cellState;
	private byte prevCellState;
	private byte firstCellState;
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
	}
	
	public void savePreviousState() {
		this.prevCellState = cellState;
	}
	
	public void newCellState(byte nstate) {
		this.cellState = nstate;
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
	
	public void setCellSize(int cllsz) {
		cellSize = cllsz;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
}

package application;

public class GameOfLifeModel {
	private byte[][] board = new byte[][] {
			{1, 0, 0, 1},
			{0, 0, 1, 1},
			{1, 0, 1, 0},
			{0, 1, 0, 0}};
	
	public GameOfLifeModel(){
		System.out.println("Game made!");
	}
	
	public byte[][] getBoard(){
		return board;
	}
	
	private int cellSize;
	
	protected void setSize(int clsz) {
		cellSize = clsz;
	}
	
	public int getSize() {
		return cellSize;
	}
	
}

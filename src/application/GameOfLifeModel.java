package application;

public class GameOfLifeModel {
	private int k = 35, m = 60;
	public byte[][] board = new byte[k][m];
	/*
	private byte[][] board = new byte[][] {
			{1, 0, 0, 1},
			{0, 0, 1, 1},
			{1, 0, 1, 0},
			{0, 1, 0, 0}};
	*/
	
	public GameOfLifeModel(){
		Random ranNum = new Random();
		
		for(int i = 0; i < board.length; i++){
			
			for(int j = 0; j < board[i].length; j++){
				byte n = (byte)ranNum.nextInt(2);
				board[i][j] = n;
				
			}
		}
		
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

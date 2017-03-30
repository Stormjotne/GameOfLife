package application;

import java.util.Random;

public class GameOfLifeModel {
	public static int k = 80, m = 40;
	public static byte[][] board = new byte[k][m];
	public byte[][] first = new byte[k][m];
	private int cellSize;
	int previous;
	int state;
		
	public GameOfLifeModel(){
		board = setCleanBoard(k,m);
		first = board; // Arrayen 'first' er n� lik den f�rste instansen av spillebrettet.
		System.out.println("Game made!");
	}

	/** 
	 * Resets the game board.
	 * */
	public void firstGeneration(){
		board = first;
	}

	/**
	 * Initializes an empty array to be represented by the game board.
	 * */
	public byte[][] setCleanBoard(int x, int y){
		byte[][] cleanBoard = new byte[x][y];
		for (int i = 0; i < (board.length); i++) {
			  for (int j = 0; j < (board[0].length); j++) {
				  cleanBoard[i][j] = 0;
			  }
		}
		return cleanBoard;
	}

	/**
	 * Initializes a random array to be represented by the game board.
	 * */	
	public byte[][] setRandomBoard(int x, int y){
		byte[][] randomBoard = new byte[x][y];
		Random ranNum = new Random();
		for (int i = 1; i < (board.length-1); i++) {
			  for (int j = 1; j < (board[0].length-1); j++) {
				byte n = (byte)ranNum.nextInt(2);
				randomBoard[i][j] = n;
			}
		}
		return randomBoard;
	}

	
	public void setBoard(byte[][] boardArray) {
		board = boardArray;
	}
	
	public void changeSingleBoardValueToOne(int x, int y){
		board[x][y] = 1;
	}
	
	public void changeSingleBoardValueToZero(int x, int y){
		board[x][y] = 0;
	}
	
	public byte getSingleValue(int x, int y){
		return board[x][y];
	}
	
	public byte[][] getBoard(){
		return board;
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
	
	protected void setCellSize(int cllsz) {
		cellSize = cllsz;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public String toString() {
		StringBuffer output = new StringBuffer();
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				output.append(board[j][i]);
			}
		}
		return output.toString();
	}
}

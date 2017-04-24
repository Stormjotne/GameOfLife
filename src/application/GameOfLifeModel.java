package application;

import java.util.Random;
/**
 * 
 * This class contains data about the game board (arrays) and cells.
 * Class should be split into Board and Cell before we start working on extra assignments. 
 * @author Ruby, Håkon & Julia
 */
public class GameOfLifeModel {
	public static int k = 260, m = 120;
	public static byte[][] board = new byte[k][m];
	public byte[][] first = new byte[k][m];
	private int cellSize;
	int previous;
	int state;
		
	public GameOfLifeModel(){
		board = setCleanBoard(k,m);
		first = board;
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
	 * @param x : the width of the board.
	 * @param y : the height of the board.
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
	 * @param x : the width of the board.
	 * @param y : the height of the board.
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

	/**
	 * Initializes a array which incorporates any lesser user-defined arrays.
	 * Useful for importing existing GoL patterns.
	 * Used in the PatternReader class to modify the game board with information parsed from RLE files.
	 * @param boardArray : any two-dimensional array lesser than the current board's width and height.
	 * Pattern.constructPatternFromRLE() returns such an array from RLE files.
	 */
	public void setPatternBoard(byte[][] boardArray) {
		byte[][] temporaryBoard = new byte[k][m];
		for (int i = 0; i < (board.length); i++) {
			  for (int j = 0; j < (board[0].length); j++) {
				  temporaryBoard[i][j] = 0;
			  }
		}
		for (int i = 0; i < (boardArray.length); i++) {
			  for (int j = 0; j < (boardArray[0].length); j++) {
				  temporaryBoard[i][j] = boardArray[i][j];
			  }
		}
		board = temporaryBoard;
	}
	
	public void setBoard(byte[][] boardArray) {
		byte[][] temporaryBoard = new byte[k][m];
		for (int i = 0; i < k; i++) {
			  for (int j = 0; j < m; j++) {
				  temporaryBoard[i][j] = boardArray[i][j];
			  }
		}
		board = temporaryBoard;
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

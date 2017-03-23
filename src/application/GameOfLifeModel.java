package application;

import java.util.Random;

public class GameOfLifeModel {
	public static int k = 80, m = 40;
	public static byte[][] board = new byte[k][m];
	public byte[][] next = new byte[k][m];
	public byte[][] first = new byte[k][m];
	private int cellSize;
	
	int previous;
	int state;
		
	public GameOfLifeModel(){
		setCleanBoard(k,m);
		first = board; // Arrayen 'first' er n� lik den f�rste instansen av spillebrettet.
		System.out.println("Game made!");
		System.err.println("\n" + "Null pointer exeption:");
	}
	
	public void firstGeneration(){
		/** Used to reset the game board. **/
		board = first;
	}
	
	public void nextGeneration(){
		/** Iterates through the board and updates the state of all cells using countNeighbors(). **/
		next = new byte[board.length][board[0].length];
		for (int x = 1; x < (board.length-1); x++) {
			  for (int y = 1; y < (board[x].length-1); y++) {
				  	int neighbors = countNeighbors(x,y);
				  	
					
					if ((board[x][y] == 1)&&(neighbors < 2)){
						next[x][y] = 0;
					}
					else if ((board[x][y] == 1)&&(neighbors > 3)){
						next[x][y] = 0;
					}
					else if ((board[x][y] == 0)&&(neighbors == 3)){
						next[x][y] = 1;
					}
					//Kan bytte ut else-utsagnet med dette regelrette utsagnet:
					/*else if ((board[x][y] == 1)&&((neighbors == 2)||(neighbors == 3))){
						next[x][y] = 1;
					}*/
					else {
						next[x][y] = board[x][y];
					}
			  }
		}
		/*Oppdaterer spillebrettet.*/
		board = next;
	}
	
	public int countNeighbors(int x, int y){ 
		/** Returns the number of live neighbors of the cell at location (x, y). **/
		int neighbors = 0;
		
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
			    neighbors += board[(x+i)][(y+j)]; //Legger sammen naboenes tilstander: 0, eller 1.
			   }
			}
		neighbors -= board[x][y]; //Trekker fra cellens egen verdi: 0, eller 1.
		return neighbors;
	}
	
	public byte[][] setCleanBoard(int x, int y){
	/** Initializes an empty array to be represented by the game board. **/
		byte[][] cleanBoard = new byte[x][y];
		for (int i = 1; x < (board.length); x++) {
			  for (int j = 1; y < (board[x].length); y++) {
				  cleanBoard[i][j] = 0;
			  }
		}
		return cleanBoard;
	}
		
	public byte[][] setRandomBoard(int x, int y){
	/** Initializes a random array to be represented by the game board. **/	
		byte[][] randomBoard = new byte[x][y];
		Random ranNum = new Random();
		for (int i = 1; x < (board.length); x++) {
			  for (int j = 1; y < (board[x].length); y++) {
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

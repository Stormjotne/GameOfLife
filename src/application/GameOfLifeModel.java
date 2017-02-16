package application; /*         MY VERSION          */

import java.util.Random;

public class GameOfLifeModel {
	public static int k = 60, m = 35;
	public static byte[][] board = new byte[k][m];
	public byte[][] next = new byte[k][m];
	
	int previous;
	int state;
		
	public GameOfLifeModel(){
		/** RANDOM ARRAY **/
		Random ranNum = new Random();
		for(int i = 0; i < board.length; i++){
			
			for(int j = 0; j < board[i].length; j++){
				byte n = (byte)ranNum.nextInt(2);
				board[i][j] = n;
			}
		}
		/*** We need random in our lives ***/
		
		System.out.println("Game made!");
		System.err.println("\n" + "Null pointer exeption:");
	}
	
	/**************** The meaning of life *****************/
	public void rules (){
		
		for (int x = 1; x < next.length-1; x++) {
		  for (int y = 1; y < next[x].length-1; y++) {
			  	int neighbors = 0;
				for (int c = -1; c <= 1; c++) {
					  for (int d = -1; d <= 1; d++) {
					    neighbors += board[x+c][y+d]; //Add up all the neighbors' states.
					   }
					}
				neighbors -= board[x][y];
				
				if ((board[x][y] == 1) && (neighbors < 2)){
					next[x][y] = 0;
				}
				else if ((board[x][y] == 1)&&(neighbors > 3)){
					next[x][y] = 0;
				}
				else if ((board[x][y] == 0)&&(neighbors == 3)){
					next[x][y] = 1;
				}
				else{ next[x][y] = board[x][y];}
		  }
		}
		board = next;
		
	}
	/****************************************/
	
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

package application; /*         MY VERSION          */

import java.util.Random;

public class GameOfLifeModel {
	public static int k = 100, m = 100;
	public static byte[][] board = new byte[k][m];
	public byte[][] next = new byte[k][m];
	private int cellSize;
	
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
	
	
	public int countNeighbors(int x, int y){ 
		/*Returnerer antall naboer til punktet (x,y)*/
		int neighbors = 0;
		
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
			    neighbors += board[x+i][y+j]; //Legger sammen naboenes tilstander: 0, eller 1.
			   }
			}
		neighbors -= board[x][y]; //Trekker fra cellens egen verdi: 0, eller 1.
		return neighbors;
	}
	
	
	public void applyRules(){
		/*Itererer gjennom board og bruker metoden countNeighbors() til å oppdatere en celles tilstand.*/
		for (int x = 1; x < k-1; x++) {
			  for (int y = 1; y < m-1; y++) {
				  	int neighbors = countNeighbors(x,y);
					
					if ((board[x][y] == 1) && (neighbors < 2)){
						next[x][y] = 0;
					}
					else if ((board[x][y] == 1)&&(neighbors > 3)){
						next[x][y] = 0;
					}
					else if ((board[x][y] == 0)&&(neighbors == 3)){
						next[x][y] = 1;
					}
					else {
						next[x][y] = board[x][y];
					}
			  }
		}
	}
	
	public void nextGeneration(){
		/*Tegner brettet til skjerm.*/
		board = next;
	}
	
	public byte[][] getBoard(){
		return board;
	}
	
	protected void setSize(int clsz) {
		cellSize = clsz;
	}
	
	public int getSize() {
		return cellSize;
	}
}

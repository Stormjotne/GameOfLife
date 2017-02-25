package application; /*         MY VERSION          */

import java.util.Random;

public class GameOfLifeModel {
	public static int k = 5, m = 5;
	public static byte[][] board = new byte[k][m];
	public byte[][] next = new byte[k][m];
	public byte[][] first = new byte[k][m];
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
		first = board; // Arrayen 'first' er nå lik den først instansen av spillebrettet.
		System.out.println("Game made!");
		System.err.println("\n" + "Null pointer exeption:");
	}
	
	public void firstGeneration(){
		/*Brukt for å tilbakestille spillebrettet.*/
		board = first;
	}
	
	public void nextGeneration(){
		/*Tegner brettet til skjerm.*/
		applyRules();
		board = next;
	}
	
	public void applyRules(){
		/*Itererer gjennom board og bruker metoden countNeighbors() til å oppdatere en celles tilstand.*/
		for (int x = 1; x < k-1; x++) {
			  for (int y = 1; y < m-1; y++) {
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
	
	public void setBoard(byte[][] b) {
		board = b;
	}
	
	public byte[][] getBoard(){
		return board;
	}
	
	public void setBoardSize(int Area){
		k = Area;
		m = k;
	}
	
	public int getBoardSize(){
		return k;
		/*return k*m;*/
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
				output.append(board[i][j]);
			}
		}
		return output.toString();
	}
}

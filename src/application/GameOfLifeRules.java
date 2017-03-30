package application;

public class GameOfLifeRules extends GameOfLifeModel {
	public byte[][] nextBoard;
	int neighbors;
	
	public GameOfLifeRules(){
	}
	
	/**
	 * Counts neighbors around a x,y coordinate and returns an integer.
	 * */
	public int countNeighbors(int x, int y){ 
		neighbors = 0;
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
			    neighbors += super.getBoard()[(x+i)][(y+j)]; //Legger sammen naboenes tilstander: 0, eller 1.
			   }
			}
		neighbors -= super.getBoard()[x][y]; //Trekker fra cellens egen verdi: 0, eller 1.
		return neighbors;
	}
	
	/**
	 * Applies the rules and updates the board.
	 * */
	public void nextGeneration(){
		nextBoard = new byte[board.length][board[0].length];
		for (int x = 1; x < (super.getBoard().length-1); x++) {
			  for (int y = 1; y < (super.getBoard()[x].length-1); y++) {
				  	int neighbors2 = countNeighbors(x,y);
					
					if ((super.getBoard()[x][y] == 1)&&(neighbors2 < 2)){
						nextBoard[x][y] = 0;
					}
					else if ((super.getBoard()[x][y] == 1)&&(neighbors2 > 3)){
						nextBoard[x][y] = 0;
					}
					else if ((super.getBoard()[x][y] == 0)&&(neighbors2 == 3)){
						nextBoard[x][y] = 1;
					}
					else {
						nextBoard[x][y] = super.getBoard()[x][y];
					}
			  }
		}
		//Oppdaterer spillebrettet.
		super.setBoard(nextBoard);
	}
}

package application;

public class GameOfLifeRules extends GameOfLifeModel {
	public byte[][] nextBoard;
	int neighbors;
	
	public GameOfLifeRules(){
	}
	
	/**
	 * Counts neighbors around a x,y coordinate and returns an integer.
	 * @param 
	 * @author Ruby, Håkon & Julia
	 * */
	public int countNeighbors(int x, int y){ 
		neighbors = 0;
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				// Sums the neighbors' states, which are 0 or 1.
				// Executes a modulus operation to achieve wrap-around, 
				// i.e. the cells on the top count as neighbors for the bottom cells, and the cells to the left are neighbors to the rightmost cells.
			    neighbors += super.getBoard()[(x+i+(super.getBoard().length))%(super.getBoard().length)][(y+j+(super.getBoard()[x].length))%(super.getBoard()[x].length)];
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
		for (int x = 0; x < (super.getBoard().length); x++) {
			  for (int y = 0; y < (super.getBoard()[x].length); y++) {
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

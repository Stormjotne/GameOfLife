package application;
/**
 * This class contains all logical calculations for game of life.
 * @author Ruby
 *
 */
public class GameOfLifeRules {
	int neighbors;
	
	public GameOfLifeRules(){
	}
	/**
	 * Applies the standard GoL rules and updates the board.
	 * */
	public void conwayLife(GameOfLifeStatic game){
		for (int x = 0; x < (game.getBoardWidth()); x++) {
			  for (int y = 0; y < (game.getBoardHeight()); y++) {
				  	int neighbors = game.countNeighbors(x,y);
					
					if ((game.getBoard()[x][y].getCellState() == (byte) 1)&&(neighbors < 2)){
						game.getBoard()[x][y].newCellState((byte)0);
					}
					else if ((game.getBoard()[x][y].getCellState() == (byte) 1)&&(neighbors > 3)){
						game.getBoard()[x][y].newCellState((byte)0);
					}
					else if ((game.getBoard()[x][y].getCellState() == (byte) 0)&&(neighbors == 3)){
						game.getBoard()[x][y].newCellState((byte)1);
					}
					/*else {
						game.getBoard()[x][y] = game.getBoard()[x][y];
					}*/
			  }
		}
	}
	/**
	 * Applies the no-deaths rules and updates the board.
	 * */
	public void noDeathsLife(GameOfLifeStatic game){
		for (int x = 0; x < (game.getBoardWidth()); x++) {
			  for (int y = 0; y < (game.getBoardHeight()); y++) {
				  	int neighbors = game.countNeighbors(x,y);
					
				  	if ((game.getBoard()[x][y].getCellState() == (byte) 0)&&(neighbors == 3)){
						game.getBoard()[x][y].newCellState((byte)1);
					}
					/*else {
						nextBoard[x][y] = game.getBoard()[x][y];
					}*/
			  }
		}
	}
}

package application;
/**
 * This class contains all logical calculations for game of life.
 * @author Ruby
 *
 */
public class GameOfLifeRules {
	int neighbors;
	int changedCells;
	
	
	public GameOfLifeRules(){
	}
	
	public void setCurrentRules() {
		
	}
	
	/**
	 * Applies the standard GoL rules and updates the board.
	 * */
	public void conwayLife(GameOfLife game, GameOfLifeStatic board){
		for (int x = 0; x < (board.getBoardWidth()); x++) {
			  for (int y = 0; y < (board.getBoardHeight()); y++) {
				  //if (game.activityMap.get("(" + x + "," + y + ")") == 1) {
				  //if (game.activityList.contains("(" + x + "," + y + ")")) {
				  	neighbors = board.countNeighbors(x,y);
					if ((board.getBoard()[x][y].getCellState() == (byte) 1)&&(neighbors < 2)){
						board.getBoard()[x][y].newCellState((byte)0);
						//game.updateActivityMapNeighbors(x,y);
						//game.updateActivityListNeighbors(x,y);
					}
					else if ((board.getBoard()[x][y].getCellState() == (byte) 1)&&(neighbors > 3)){
						board.getBoard()[x][y].newCellState((byte)0);
						//board.updateActivityMapNeighbors(x,y);
						//game.updateActivityListNeighbors(x,y);
					}
					else if ((board.getBoard()[x][y].getCellState() == (byte) 0)&&(neighbors == 3)){
						board.getBoard()[x][y].newCellState((byte)1);
						//game.updateActivityMapNeighbors(x,y);
						//game.updateActivityListNeighbors(x,y);
					}
					/*else {
						board.getBoard()[x][y] = board.getBoard()[x][y];
					}*/
				  }
			  //}
		}
	}
	/**
	 * Applies the no-deaths rules and updates the board.
	 * */
	public void noDeathsLife(GameOfLife game, GameOfLifeStatic board){
		for (int x = 0; x < (board.getBoardWidth()); x++) {
			  for (int y = 0; y < (board.getBoardHeight()); y++) {
				  	int neighbors = board.countNeighbors(x,y);
					
				  	if ((board.getBoard()[x][y].getCellState() == (byte) 0)&&(neighbors == 3)){
				  		board.getBoard()[x][y].newCellState((byte)1);
					}
					/*else {
						nextBoard[x][y] = game.getBoard()[x][y];
					}*/
			  }
		}
	}
}

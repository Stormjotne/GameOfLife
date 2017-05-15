package application;
/**
 *	Game of Life Object.
 *	GameOfLifeStatic and GameOfLifeDynamic both inherit from this class.
 *	
 * @author Ruby
 */
public abstract class GameOfLife {

	public static int k = 240, m = 120;
	public static int maxk = 480, maxm = 240;
	public GameOfLifeCell currentCell;
	
	public GameOfLife() {
		
	}
	
	abstract GameOfLifeCell getCell(int x, int y);
	abstract void setCellState(int x, int y, byte nstate);
	abstract byte getCellState(int x, int y);
	abstract byte getPreviousCellState(int x, int y);
	abstract int countNeighbors(int x, int y);
	abstract void setCleanBoard();
	abstract void setRandomBoard();
	abstract void setFirstBoard();
	abstract void setPatternBoard(GameOfLifeCell[][] boardArray);
	abstract void activateNeighborCells(int x, int y);
	/**
	 * Set board width to desried value.
	 * @param WIDTH
	 */
	public void setWidth(int WIDTH){
		if (WIDTH < 0 || WIDTH > maxk) {
			throw new IllegalArgumentException();
		}
		else
			GameOfLife.k = WIDTH;
	}
	/**
	 * Set board height to desired value.
	 * @param HEIGHT
	 */
	public void setHeight(int HEIGHT){
		if (HEIGHT < 0 || HEIGHT > maxm) {
			throw new IllegalArgumentException();
		}
		else
		GameOfLife.m = HEIGHT;
	}
	/**
	 * Increase width by 1.
	 */
	public void increaseWidth(){
		if (k < maxk) {
			GameOfLife.k++;
		}
	}
	/**
	 * Increase height by 1.
	 */
	public void increaseHeight(){
		if (m < maxm) {
			GameOfLife.m++;
		}
	}
	/**
	 * Get current width.
	 * @return
	 */
	public int getWidth(){
		return GameOfLife.k;
	}
	/**
	 * Get current height.
	 * @return
	 */
	public int getHeight(){
		return GameOfLife.m;
	}
	
	
}

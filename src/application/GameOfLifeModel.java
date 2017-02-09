package application;

public class GameOfLifeModel {
	private byte[][] board = {
			{1, 0, 0, 1},
			{0, 0, 1, 1},
			{1, 0, 1, 0},
			{0, 1, 0, 0}};
	
	public GameOfLifeModel(){
		System.out.println("Game made!");
	}
	
}

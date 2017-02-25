package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import application.GameOfLifeModel;

public class LogicTesting {
	

	private GameOfLifeModel game;

	@Test
	public void testNextGeneration() {
		byte[][] b = {
				{ 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0 },
				{ 0, 0, 1, 0, 0 },
				{ 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0 }
				};
			game = new GameOfLifeModel();
			//game.setBoardSize(5);
			game.setBoard(b);
			game.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000000011100000000000");
		}
}
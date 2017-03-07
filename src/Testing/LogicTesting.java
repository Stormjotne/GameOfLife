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
				{ 0, 0, 0, 0, 0 },
				{ 0, 1, 1, 1, 0 },
				{ 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 }
				};
			game = new GameOfLifeModel();
			//game.setBoardSize(5);
			game.setBoard(b);
			org.junit.Assert.assertEquals(game.toString(),"0000000100001000010000000");
			System.out.println(game.toString());
			game.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000000011100000000000");
			System.out.println(game.toString());
			game.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000100001000010000000");
			System.out.println(game.toString());
		}
}
package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import application.GameOfLifeModel;
import application.GameOfLifeRules;

public class LogicTesting {
	

	private GameOfLifeModel game;
	private GameOfLifeRules rules;

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
			rules = new GameOfLifeRules();
			//game.setBoardSize(5);
			game.setBoard(b);
			org.junit.Assert.assertEquals(game.toString(),"0000000100001000010000000");
			System.out.println(game.toString());
			rules.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000000011100000000000");
			System.out.println(game.toString());
			rules.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000100001000010000000");
			System.out.println(game.toString());
		}
}
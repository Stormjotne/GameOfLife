package Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import application.GameOfLifeStatic;
import application.GameOfLifeRules;

public class LogicTesting {
	

	private GameOfLifeStatic game;
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
			game = new GameOfLifeStatic();
			rules = new GameOfLifeRules();
			//game.setBoardSize(5);
			//game.setBoard(b);
			org.junit.Assert.assertEquals(game.toString(),"0000000100001000010000000");
			System.out.println(game.toString());
			//rules.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000000011100000000000");
			System.out.println(game.toString());
			//rules.nextGeneration();
			org.junit.Assert.assertEquals(game.toString(),"0000000100001000010000000");
			System.out.println(game.toString());
		}
}
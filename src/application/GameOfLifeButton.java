package application;

import java.io.IOException;
/**
 * 
 * @author Ruby
 *	Intended to hold all functions called upon by buttons, but the back-and-forth calling seems redundant atm.
 */
public class GameOfLifeButton {
	public void playButton(){
		animation.play();
	}
	
	public void pauseButton(){
		animation.pause();
	}
	
	public void stopButton(){
		animation.stop();
	}
	
	public void randomButton(){
		game.setRandomBoard(game.getBoardWidth(), game.getBoardHeight());
	}
	
	public void cleanButton(){
		game.setCleanBoard(game.getBoardWidth(), game.getBoardHeight());
	}
	
	public void fileChooserButton(){
		URLReader.setPatternURL("http://www.conwaylife.com/patterns/glider.rle");
		try {
			URLReader.downloadPattern();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void fileByURLButton(){
		URLReader.setPatternURL("http://www.conwaylife.com/patterns/glider.rle");
		try {
			URLReader.readFileFromURL();
		} catch (IOException e) {
			  System.err.printf ("Failed to read from url: " + URLReader.getPatternURL());
			  e.printStackTrace ();
		}
	}
}

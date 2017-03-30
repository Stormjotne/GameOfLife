package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

/**
 * 
 * @author Ruby
 *	Intended to hold all functions called upon by buttons, but the back-and-forth calling seems redundant atm.
 */
public class GameOfLifeButton extends GameOfLifeController {
	
	@FXML private Button playButton;
	@FXML private Button pauseButton;
	@FXML private Button stopButton;
	@FXML private Button randomButton;
	@FXML private Button cleanButton;
	@FXML private Button fileChooserButton;
	@FXML private Button fileByURLButton;
	
	//Tried taking these out of initialize, but can't get them to work in another class...
	public void mouseEvents(){
	playButton.setOnAction((event) -> {
		playButton();
	});
	pauseButton.setOnAction((event) -> {
		pauseButton();
	});
	stopButton.setOnAction((event) -> {
		stopButton();
	});
	randomButton.setOnAction((event) -> {
		randomButton();
	});
	cleanButton.setOnAction((event) -> {
		cleanButton();
	});
	fileChooserButton.setOnAction((event) -> {
		fileChooserButton();
	});
	fileByURLButton.setOnAction((event) -> {
		fileByURLButton();
	});
	}
	
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
		game.setBoard(game.setRandomBoard(game.getBoardWidth(), game.getBoardHeight()));
	}
	
	public void cleanButton(){
		game.setBoard(game.setCleanBoard(game.getBoardWidth(), game.getBoardHeight()));
	}
	
	/*public void fileChooserButton(){
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
	}*/
}

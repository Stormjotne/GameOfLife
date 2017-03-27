package application; /*       MY VERSION        */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;


public class GameOfLifeController extends Application implements javafx.fxml.Initializable {
	
	@FXML private Canvas grid;
	@FXML private Button playButton;
	@FXML private Button pauseButton;
	@FXML private Button stopButton;
	@FXML private Button randomButton;
	@FXML private Button cleanButton;
	@FXML private Button fileChooserButton;
	@FXML private Button fileByURLButton;
	
	@FXML public ColorPicker colorPicker;
	private GraphicsContext gc;
	private GameOfLifeModel game;
	private GameOfLifePatternReader URLReader;
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), e -> run()));
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = new GameOfLifeModel();
		URLReader = new GameOfLifePatternReader();
		gc = grid.getGraphicsContext2D();
		colorPicker.setValue(Color.BLACK);
		game.setCellSize(5);
		draw(gc);
		timeLine();
		game.nextGeneration();
		
		/***** Mouse onClick logic ******
		  Changes the location in the array on mouseclick and draws a new box
		 */
		grid.addEventHandler(MouseEvent.MOUSE_PRESSED,(MouseEvent e) ->{
			int x = (int)(e.getX()/game.getCellSize());
			int y = (int)(e.getY()/game.getCellSize());
			if(game.getSingleValue(x,y)==1){
				game.changeSingleBoardValueToZero(x,y);
				drawBox(x,y,Color.WHITE);
			}
			else { 
				game.changeSingleBoardValueToOne(x,y);
				drawBox(x,y,colorPicker.getValue());
			}
		});
		/********************************/
		
		assert playButton != null : "fx:id=\"playButton\" No Play Button Found.";
		assert pauseButton != null : "fx:id=\"pauseButton\" No Pause Button Found.";
		assert stopButton != null : "fx:id=\"stopButton\" No Stop Button Found.";
		assert randomButton != null : "fx:id=\"randomButton\" No Random Button Found.";
		assert cleanButton != null : "fx:id=\"cleanButton\" No Clean Button Found.";
		assert fileChooserButton != null : "fx:id=\"fileChooserButton\" No Pattern From Drive Button Found.";
		assert fileByURLButton != null : "fx:id=\"fileByURLButton\" No Pattern By URL Button Found.";
		/*Button Logic*/
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
			stopButton();
		});
		fileChooserButton.setOnAction((event) -> {
			fileChooserButton();
		});
		fileByURLButton.setOnAction((event) -> {
			fileByURLButton();
		});
	}
	
	/*****************************/
	public void timeLine(){
		animation.setAutoReverse(false);
        animation.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void run(){
		draw(gc);
		game.nextGeneration();
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
			OutputStream yourOutputStream = null;
			e.printStackTrace(new PrintStream(yourOutputStream));
		}
	}
	/*****************************/
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("GameOfLifeFXML.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Game Of Life");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML private void draw(GraphicsContext gc) {
		for (int i = 0; i < game.getBoard().length; i++) {
			for (int j = 0; j < game.getBoard()[i].length; j++) {
				if(game.getBoard()[i][j] == 1){
					drawBox(i, j, colorPicker.getValue());
				}
				else  {
					drawBox(i, j, Color.WHITE);
				}
			}
		}
    }
	private void drawBox(int x, int y, Color c){
		gc.setFill(c);
		gc.fillRect(x*game.getCellSize(), y*game.getCellSize(), game.getCellSize()-1, game.getCellSize()-1);
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
		
	}
}
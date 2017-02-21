package application; /*       MY VERSION        */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOfLifeController extends Application implements javafx.fxml.Initializable {
	
	@FXML private Canvas grid;
	@FXML private Button playButton;
	@FXML private Button pauseButton;
	@FXML private Button stopButton;
	private GraphicsContext gc;
	private GameOfLifeModel game;
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(1200), e -> run()));
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = new GameOfLifeModel(); // Lager en random array 

		gc = grid.getGraphicsContext2D();
		draw(gc);
		timeLine();
		game.applyRules();
		game.nextGeneration();
		
		assert playButton != null : "fx:id=\"playButton\" No Play Button Found.";
		assert pauseButton != null : "fx:id=\"pauseButton\" No Pause Button Found.";
		assert stopButton != null : "fx:id=\"stopButton\" No Stop Button Found.";
		/*Button Logic*/
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
                play();
            }
        });
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
                pause();
            }
        });
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
                stop();
            }
        });
	}
	
	/*****************************/
	public void timeLine(){
		animation.setAutoReverse(false);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
	}
	
	public void run(){
		game.applyRules();
		draw(gc);
	}
	
	public void play(){
		animation.play();
	}
	
	public void pause(){
		animation.pause();
	}
	
	public void stop(){
		animation.stop();
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
					drawBox(i, j, Color.RED);
				}
				else  {
					drawBox(i, j, Color.WHITE);
				}
				game.setSize(10);
			}
		}
    }
	private void drawBox(int x, int y, Color c){
		gc.setFill(c);
		gc.fillRect(x*game.getSize(), y*game.getSize(), game.getSize()-1, game.getSize()-1);
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}
}
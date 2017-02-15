package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private GraphicsContext gc;
	private GameOfLifeModel game;
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(120), e -> run()));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = new GameOfLifeModel(); // Lager en random array 
		
		/***  added this  ***/ 
		game.rules(); // something wrong with the rules
		/** Calls the rules on our array **/
		
		gc = grid.getGraphicsContext2D();
		draw(gc);
		timeLine();
	}
	/*****************************/
	public void timeLine(){
		animation.setAutoReverse(false);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
	}
	
	public void run(){
		game.rules();
		draw(gc);
	}
	/*****************************/
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
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
				int l = game.getSize();
				//gc.fillRect(j*l, i*l, l, l); Denne gjorde så den tegnet to arrayer oppå hverandre ( tror jeg )
				
			}
		}
    }
	/* Delte opp tegne metoden. (Lagde en metode som tegner og
	 *  en som sjekker hvilke elementer som skal farges.) */
	private void drawBox(int x, int y, Color c){ 
		gc.setFill(c);
		gc.fillRect(x*10, y*10, 9, 9);
		
	}


	public static void main(String[] args) {
		launch(args);
		
	}
}
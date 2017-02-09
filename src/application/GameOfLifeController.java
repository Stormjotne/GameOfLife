package application;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		draw(gc);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Game Of Life");
		Parent root = FXMLLoader.load(getClass().getResource("GameOfLifeFXML.fxml"));
		Scene scene = new Scene(root);
		
        
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML private void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
    }

	public static void main(String[] args) {
		launch(args);
		GameOfLifeModel game = new GameOfLifeModel();
		
	}
}
package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * The controller class for the Game of Life application.
 * @author Ruby, Håkon & Julia
 * */
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
	@FXML private Slider speed;
	
	private GraphicsContext gc;
	public GameOfLifeModel game;
	public GameOfLifeRules rules;
	private GameOfLifePatternReader PatternReader;
	final FileChooser fileChooser = new FileChooser();
	FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("RLE files (*.rle)", "*.rle");
	File defaultDirectory = new File("patterns/");
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(40), e -> run()));
	
	int timing = 120;
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> run())); 
	    
	public void timerlistener(){
        speed.valueProperty().addListener((ObservableValue<? extends Number> timerlistener, Number oldtime, Number newtime) -> {
            timing = newtime.intValue();
            animation.setRate(timing);
        });
    }

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = new GameOfLifeModel();
		rules = new GameOfLifeRules();
		PatternReader = new GameOfLifePatternReader();
		gc = grid.getGraphicsContext2D();
		colorPicker.setValue(Color.BLACK);
		game.setCellSize(10);
		draw(gc);
		timeLine();
		rules.nextGeneration();
		
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
		
		grid.addEventHandler(MouseEvent.MOUSE_DRAGGED,(MouseEvent e) ->{
			int x = (int)(e.getX()/game.getCellSize());
			int y = (int)(e.getY()/game.getCellSize());<?xml version="1.0" encoding="UTF-8"?>

<?import java.lang.*?>
<?import javafx.geometry.*?>
<?import javafx.scene.canvas.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<BorderPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="1080.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="application.GameOfLifeController">
   <center>
      <Canvas fx:id="grid" height="400.0" width="800.0" BorderPane.alignment="CENTER" />
   </center>
   <bottom>
      <GridPane prefHeight="52.0" prefWidth="1030.0" BorderPane.alignment="CENTER">
        <columnConstraints>
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="210.66668701171875" minWidth="0.0" prefWidth="63.666656494140625" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="432.0" minWidth="0.0" prefWidth="68.33334350585938" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="619.0" minWidth="10.0" prefWidth="70.66668701171875" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="619.0" minWidth="10.0" prefWidth="85.33331298828125" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="474.66668701171875" minWidth="10.0" prefWidth="68.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="474.66668701171875" minWidth="10.0" prefWidth="156.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="474.66668701171875" minWidth="10.0" prefWidth="118.0" />
          <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="818.0000305175781" minWidth="10.0" prefWidth="123.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="818.0000305175781" minWidth="10.0" prefWidth="154.0" />
          <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" maxWidth="383.0" minWidth="10.0" prefWidth="204.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Button fx:id="playButton" defaultButton="true" mnemonicParsing="false" text="Play">
               <padding>
                  <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
               </padding></Button>
            <Button fx:id="pauseButton" mnemonicParsing="false" text="Pause" GridPane.columnIndex="1">
               <padding>
                  <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
               </padding>
            </Button>
            <Button fx:id="stopButton" mnemonicParsing="false" text="Reset" GridPane.columnIndex="2">
               <padding>
                  <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
               </padding>
            </Button>
            <Button fx:id="randomButton" mnemonicParsing="false" text="Random" GridPane.columnIndex="3" />
            <Button fx:id="cleanButton" mnemonicParsing="false" text="Clean" GridPane.columnIndex="4" />
            <Button fx:id="fileChooserButton" mnemonicParsing="false" text="Pattern From Drive" GridPane.columnIndex="5" />
            <GridPane GridPane.columnIndex="9">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <Label text="Cell size:" GridPane.halignment="RIGHT" />
                  <TextField text="10" GridPane.columnIndex="1">
                     <GridPane.margin>
                        <Insets left="10.0" right="10.0" />
                     </GridPane.margin>
                  </TextField>
               </children>
            </GridPane>
            <Button fx:id="fileByURLButton" mnemonicParsing="false" text="Pattern From URL" GridPane.columnIndex="6" />
            <ColorPicker fx:id="colorPicker" prefHeight="25.0" prefWidth="87.0" GridPane.columnIndex="7">
               <GridPane.margin>
                  <Insets left="10.0" right="10.0" />
               </GridPane.margin>
            </ColorPicker>
            <GridPane GridPane.columnIndex="8">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="109.0" minWidth="10.0" prefWidth="31.0" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="194.0" minWidth="10.0" prefWidth="120.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <Slider blockIncrement="0.0" majorTickUnit="2.0" max="20.0" min="1.0" minorTickCount="0" showTickLabels="true" showTickMarks="true" snapToTicks="true" value="1.0" GridPane.columnIndex="1" />
                  <Label text="FPS:" />
               </children>
            </GridPane>
         </children>
      </GridPane>
   </bottom>
   
</BorderPane>

			
				game.changeSingleBoardValueToOne(x,y);
				drawBox(x,y,colorPicker.getValue());
			
		});
		
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
			cleanButton();
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
		timerlistener();
		rules.nextGeneration();
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
		draw(gc);
	}
	
	public void cleanButton(){
		game.setBoard(game.setCleanBoard(game.getBoardWidth(), game.getBoardHeight()));
		draw(gc);
	}
	
	public void fileChooserButton(){
		try {
			fileChooser.setInitialDirectory(defaultDirectory);
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				//Reads file and stores object.
				PatternReader.parseFileToPatternObject(game, file);
			}
		} catch (IOException e) {
			  System.err.printf ("Failed to read from local storage.");
			  e.printStackTrace ();
		}
		draw(gc);
	}
	
	public void fileByURLButton(){
		TextInputDialog defaultURLInput = new TextInputDialog("http://www.conwaylife.com/patterns/glider.rle");
		defaultURLInput.setTitle("URL Input Dialog");
		defaultURLInput.setHeaderText("Load your favorite GoL Pattern.");
		defaultURLInput.setContentText("Enter URL:");
		/*defaultURLInput.showAndWait();
		.ifPresent(urlinput -> {
			if (urlinput == ButtonType.OK) {
			PatternReader.setPatternURL(urlinput);
			}
		});*/
		final Button cancel = (Button) defaultURLInput.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
            System.out.println("Cancel was definitely pressed")
        );

        Optional<String> result = defaultURLInput.showAndWait();
        
		
		
			try {
				if (result.isPresent()) {
		            System.out.println("Result present => OK was pressed");
		            System.out.println("Result: " + result.get());
		            PatternReader.setPatternURL(result.get());
		            PatternReader.parseURLToPatternObject(game);
		        } else {
		            System.out.println("Result not present => Cancel might have been pressed");
		        }    
				//Reads file and stores object.
				//PatternReader.parseFileToPatternObject(game);
			} catch (IOException e) {
				System.err.printf ("Failed to read from url: " + PatternReader.getPatternURL());
				e.printStackTrace ();
			}
			draw(gc);
	}
	
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
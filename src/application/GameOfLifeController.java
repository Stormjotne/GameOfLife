package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.tk.Toolkit;

import java.lang.reflect.*;
import java.lang.reflect.Method;

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
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
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
	@FXML private MenuButton cellButton;
	@FXML private MenuItem cellConway;
	@FXML private MenuItem cellHistory;
	@FXML private MenuButton rulesButton;
	@FXML private MenuItem rulesConway;
	@FXML private MenuItem rulesNoDeaths;
	@FXML private MenuButton boardButton;
	@FXML private MenuItem boardDynamic;
	@FXML private MenuItem boardStatic;
	@FXML public ColorPicker colorPicker;
	@FXML private Slider speedSlider;
	
	private GraphicsContext gc;
	public GameOfLife game;
	public GameOfLifeCell cell;
	public GameOfLifeRules rules;
	Method ruleMethod = null;
	Method cellMethod = null;
	private GameOfLifePatternReader PatternReader;
	final FileChooser fileChooser = new FileChooser();
	FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("RLE files (*.rle)", "*.rle");
	File defaultDirectory = new File("patterns/");
	Alert alertGameOfLifeController = new Alert(AlertType.ERROR);
	int timing = 10;
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> run()));
	
	    
	public void timerlistener(){
        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> timerlistener, Number oldtime, Number newtime) -> {
            timing = newtime.intValue();
            animation.setRate(timing);
        });
    }
	public Method setRules(String ruletype) {
		try {
		ruleMethod = rules.getClass().getDeclaredMethod(ruletype, GameOfLife.class);
		} catch (SecurityException e) 
		{  }
		  catch (NoSuchMethodException e)
		{  }
		return ruleMethod;
	}
	
	public Method setCellRules(String ruletype) {
		try {
		cellMethod = cell.getClass().getDeclaredMethod(ruletype, GameOfLifeCell.class);
		} catch (SecurityException e) 
		{  }
		  catch (NoSuchMethodException e)
		{  }
		return cellMethod;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = new GameOfLifeDynamic();
		rules = new GameOfLifeRules();
		ruleMethod = setRules("conwayLife");
		cell = new GameOfLifeCell(3);
		cellMethod = setCellRules("drawCell");
		PatternReader = new GameOfLifePatternReader();
		gc = grid.getGraphicsContext2D();
		colorPicker.setValue(Color.BLACK);
		draw(gc);
		timeLine();
	    animation.setRate(timing);
	    //removed rules from initialize
		
		/***** Mouse onClick logic ******
		  Changes the location in the array on mouseclick and draws a new box
		 */
		grid.addEventHandler(MouseEvent.MOUSE_PRESSED,(MouseEvent e) ->{
			try {
			int x = (int)(e.getX()/cell.getCellSize());
			int y = (int)(e.getY()/cell.getCellSize());
			if(game.getCellState(x,y)==1){
				game.setCellState(x,y,(byte)0);
				draw(gc);
			}
			else { 
				game.setCellState(x,y,(byte)1);
				draw(gc);
			}
			} catch(ArrayIndexOutOfBoundsException ex) {
				System.err.println("Action could not be performed. Out of Bounds.");
			} catch(IndexOutOfBoundsException ex) {
				System.err.println("Action could not be performed. Out of Bounds.");
			}
		});
		
		grid.addEventHandler(MouseEvent.MOUSE_DRAGGED,(MouseEvent e) ->{
			try {
			int x = (int)(e.getX()/cell.getCellSize());
			int y = (int)(e.getY()/cell.getCellSize());
			//Only brings cells to life.
			game.setCellState(x,y,(byte)1);
				draw(gc);
		} catch(ArrayIndexOutOfBoundsException ex) {
			System.err.println("Action could not be performed. Out of Bounds.");
		} catch(IndexOutOfBoundsException ex) {
			System.err.println("Action could not be performed. Out of Bounds.");
		}
		});
		/*Assertion of GUI control elements.*/
		assert playButton != null : "fx:id=\"playButton\" No Play Button Found.";
		assert pauseButton != null : "fx:id=\"pauseButton\" No Pause Button Found.";
		assert stopButton != null : "fx:id=\"stopButton\" No Stop Button Found.";
		assert randomButton != null : "fx:id=\"randomButton\" No Random Button Found.";
		assert cleanButton != null : "fx:id=\"cleanButton\" No Clean Button Found.";
		assert fileChooserButton != null : "fx:id=\"fileChooserButton\" No Pattern From Drive Button Found.";
		assert fileByURLButton != null : "fx:id=\"fileByURLButton\" No Pattern By URL Button Found.";
		assert cellButton != null : "fx:id=\"cellButton\" No Cell Button Found.";
		assert rulesButton != null : "fx:id=\"rulesButton\" No Rules Button Found.";
		assert boardButton != null : "fx:id=\"boardButton\" No Board Button Found.";
		assert colorPicker != null : "fx:id=\"colorPicker\" No Color Picker Found.";
		assert speedSlider != null : "fx:id=\"speedSlider\" No Speed Slider Found.";
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
		cellConway.setOnAction((event) -> {
			cellConwayButton();
		});
		cellHistory.setOnAction((event) -> {
			cellHistoryButton();
		});
		rulesConway.setOnAction((event) -> {
			rulesConwayButton();
		});
		rulesNoDeaths.setOnAction((event) -> {
			rulesNoDeathsButton();
		});
		boardDynamic.setOnAction((event) -> {
			boardDynamicButton();
		});
		boardStatic.setOnAction((event) -> {
			boardStaticButton();
		});
	}
	/*Button Functions*/
	public void playButton() {
		animation.play();
	}
	
	public void pauseButton() {
		animation.pause();
	}
	
	public void stopButton() {
		animation.stop();
	}
	
	public void randomButton() {
		game.setRandomBoard();
		draw(gc);
	}
	
	public void cleanButton() {
		game.setCleanBoard();
		draw(gc);
	}
	
	public void fileChooserButton()  {
		try {
			fileChooser.setInitialDirectory(defaultDirectory);
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				//Reads file and stores object.
				PatternReader.parseFileToPatternObject(file);
				// Create temporary object of Pattern
				GameOfLifePattern tempObj = new GameOfLifePattern(PatternReader.tempName, PatternReader.tempOrigin, PatternReader.tempInformation, PatternReader.tempWIDTH, PatternReader.tempHEIGHT, PatternReader.tempLifeRules, PatternReader.charPlotPatternArray);
				// Constructs a new board / array with information from the Pattern Object.
				try {
					game.setPatternBoard(tempObj.constructPatternFromRLE());
				} catch(PatternFormatException e) {
					alertGameOfLifeController.setTitle("Error");
					alertGameOfLifeController.setHeaderText("File could not be parsed.");
					alertGameOfLifeController.setContentText("The program was unable to parse the specified file. Please make sure it doesn't contain any illegal letters, and that the pattern is not too big.");
					alertGameOfLifeController.showAndWait();
				} catch(ArrayIndexOutOfBoundsException e) {
					alertGameOfLifeController.setTitle("Error");
					alertGameOfLifeController.setHeaderText("Pattern too big.");
					alertGameOfLifeController.setContentText("The program was unable to draw the specified pattern because it was too big.");
					alertGameOfLifeController.showAndWait();
				}
			}
		} catch (IOException e) {
			  System.err.printf ("Failed to read from local storage.");
			  e.printStackTrace ();
		}
		draw(gc);
	}
	
	public void fileByURLButton() {
		TextInputDialog defaultURLInput = new TextInputDialog("http://www.conwaylife.com/patterns/glider.rle");
		defaultURLInput.setTitle("URL Input Dialog");
		defaultURLInput.setHeaderText("Load your favorite GoL Pattern.");
		defaultURLInput.setContentText("Enter URL:");
		final Button cancel = (Button) defaultURLInput.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
            System.out.println("Cancel was definitely pressed")
        );

        Optional<String> result = defaultURLInput.showAndWait();
        	try {
        		//Get url from user input
				if (result.isPresent() /*&& result.get().endsWith(".rle)")*/) {
		            System.out.println("Result present => OK was pressed");
		            System.out.println("Result: " + result.get());
		            PatternReader.setPatternURL(result.get());
		            //Reads file and stores object.
		            PatternReader.parseURLToPatternObject();
		            // Create temporary object of Pattern
		            GameOfLifePattern tempObj = new GameOfLifePattern(PatternReader.tempName, PatternReader.tempOrigin, PatternReader.tempInformation, PatternReader.tempWIDTH, PatternReader.tempHEIGHT, PatternReader.tempLifeRules, PatternReader.charPlotPatternArray);
		            // Constructs a new board / array with information from the Pattern Object.
					try {
						game.setPatternBoard(tempObj.constructPatternFromRLE());
					} catch(PatternFormatException e) {
						alertGameOfLifeController.setTitle("Error");
						alertGameOfLifeController.setHeaderText("File could not be parsed.");
						alertGameOfLifeController.setContentText("The program was unable to parse the specified file. Please make sure it doesn't contain any illegal letters, and that the pattern is not too big.");
						alertGameOfLifeController.showAndWait();
					} catch(ArrayIndexOutOfBoundsException e) {
						alertGameOfLifeController.setTitle("Error");
						alertGameOfLifeController.setHeaderText("Pattern too big.");
						alertGameOfLifeController.setContentText("The program was unable to draw the specified pattern because it was too big.");
						alertGameOfLifeController.showAndWait();
					}
		        } else {
		            System.out.println("Result was invalid. URL was malformed, or dialog cancelled by user.");
		        }    
				//Reads file and stores object.
				//PatternReader.parseFileToPatternObject(game);
			} catch (IOException e) {
				alertGameOfLifeController.setTitle("Error");
				alertGameOfLifeController.setHeaderText("Path not found.");
				alertGameOfLifeController.setContentText("The URL you specified did not contain a .rle file");
				alertGameOfLifeController.showAndWait();
				System.err.printf ("Failed to read from url: " + PatternReader.getPatternURL());
			}
			draw(gc);
	}
	
	public void cellConwayButton() {
		cellMethod = setCellRules("drawCell");
	}
	public void cellHistoryButton() {
		cellMethod = setCellRules("drawCellHistory");
	}
	public void rulesConwayButton() {
		ruleMethod = setRules("conwayLife");
	}
	public void rulesNoDeathsButton() {
		ruleMethod = setRules("noDeathsLife");
	}
	public void boardDynamicButton() {
		game = new GameOfLifeDynamic();
	}
	public void boardStaticButton() {
		game = new GameOfLifeStatic();
	}

	public void timeLine() {
		animation.setAutoReverse(false);
        animation.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void run(){
		cellHistory();
		timerlistener();
		//Use reflection to call Rules,
		//That way it's easy to change them at runtime.
		try {
			ruleMethod.invoke(rules, game);
			} catch (IllegalArgumentException e) {}
			  catch (IllegalAccessException e) {}
			  catch (InvocationTargetException e) {}
		draw(gc);
	}
	
	public void cellHistory() {
		for (int i = 0;i < GameOfLife.k;i++) {
			for (int j = 0;j < GameOfLife.m;j++) {
				game.getCell(i,j).savePreviousState();
			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("GameOfLifeFXML.fxml"));
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Game Of Life");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML private void draw(GraphicsContext gc) {
		gc.clearRect(0, 0, grid.getWidth(), grid.getHeight());
		int currentSize = cell.getCellSize();
		byte currentState = 0;
		for (int i = 0; i < GameOfLife.k; i++) {
			for (int j = 0; j < GameOfLife.m; j++) {
				GameOfLifeCell currentCell = game.getCell(i,j);
				//Return combination of previous and current cell states:
				//Use reflection to call cell presentation,
				//different methods return different states
				try {
					currentState = (byte) cellMethod.invoke(currentCell, currentCell);
					} catch (IllegalArgumentException e) {}
					  catch (IllegalAccessException e) {}
					  catch (InvocationTargetException e) {}
				//currentState = currentCell.drawCell(currentCell);
				//currentState = currentCell.drawCellHistory(currentCell);
				currentCell.drawBox(gc, colorPicker.getValue(), i, j, currentSize, currentState);
			}
		}
    }

	public static void main(String[] args) {
		
		launch(args);
		
		
	}
}
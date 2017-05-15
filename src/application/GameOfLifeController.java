package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;


/**
 * The controller class for the Game of Life application.
 * @author Ruby, Håkon & Julia
 * */
public class GameOfLifeController extends Application implements javafx.fxml.Initializable {
	/* Inject GUI Elements via FXML*/
	@FXML private Canvas grid;
	@FXML private Button playButton;
	@FXML private Button pauseButton;
	@FXML private Button resetButton;
	@FXML private Button GIFButton;
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
	@FXML private MenuItem rulesHighLife;
	@FXML private MenuItem rulesConwayPerformance;
	@FXML private MenuButton boardButton;
	@FXML private MenuItem boardDynamic;
	@FXML private MenuItem boardStatic;
	@FXML private ColorPicker colorPicker;
	@FXML private Slider speedSlider;
	
	private GraphicsContext gc;
	private GameOfLife game;
	private GameOfLifeCell cell;
	private GameOfLifeRules rules;
	private GameOfLifeGIF gif = null;
	private Method ruleMethod = null;
	private Method cellMethod = null;
	static List<Thread> workers = new ArrayList<Thread>();
	private GameOfLifePatternReader PatternReader;
	private final FileChooser fileChooser = new FileChooser();
	File defaultDirectory;
	File defaultGIFDirectory;
	private FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("RLE files (*.rle)", "*.rle");
	private Alert alertGameOfLifeController = new Alert(AlertType.ERROR);
	private int timing = 10;
	private int cellSize = 5;
	
	Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> run()));
	
    
    void defaultDirectory() {
    	defaultDirectory = new File(System.getProperty("user.dir") + "/patterns/");
    	if (! defaultDirectory.exists()){
    		defaultDirectory.mkdir();
    	}
    	defaultGIFDirectory = new File(System.getProperty("user.dir") + "/gifs/");
    	if (! defaultGIFDirectory.exists()){
    		defaultGIFDirectory.mkdir();
    	}
    }

	    
	private void timerListener(){
        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> timerlistener, Number oldtime, Number newtime) -> {
            timing = newtime.intValue();
            animation.setRate(timing);
        });
    }
	private Method setRules(String ruletype) {
		try {
		ruleMethod = rules.getClass().getDeclaredMethod(ruletype, GameOfLife.class);
		} catch (SecurityException e) 
		{  }
		  catch (NoSuchMethodException e)
		{  }
		return ruleMethod;
	}
	
	private Method setCellRules(String ruletype) {
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
		cell = new GameOfLifeCell(cellSize);
		cellMethod = setCellRules("drawCell");
		PatternReader = new GameOfLifePatternReader();
		defaultDirectory();
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
			double currentWidthRatio = grid.getWidth()/(cellSize*game.getWidth());
			double currentHeightRatio = grid.getHeight()/(cellSize*game.getHeight());
			try {
				int x = (int)((e.getX()/cell.getCellSize())/currentWidthRatio);
				int y = (int)((e.getY()/cell.getCellSize())/currentHeightRatio);
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
			double currentWidthRatio = grid.getWidth()/(cellSize*game.getWidth());
			double currentHeightRatio = grid.getHeight()/(cellSize*game.getHeight());
			try {
				int x = (int)((e.getX()/cell.getCellSize())/currentWidthRatio);
				int y = (int)((e.getY()/cell.getCellSize())/currentHeightRatio);
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
		assert resetButton != null : "fx:id=\"stopButton\" No Stop Button Found.";
		assert GIFButton != null : "fx:id=\"GIFButton\" No GIF Button Found.";
		assert randomButton != null : "fx:id=\"randomButton\" No Random Button Found.";
		assert cleanButton != null : "fx:id=\"cleanButton\" No Clean Button Found.";
		assert fileChooserButton != null : "fx:id=\"fileChooserButton\" No Pattern From Drive Button Found.";
		assert fileByURLButton != null : "fx:id=\"fileByURLButton\" No Pattern By URL Button Found.";
		assert cellButton != null : "fx:id=\"cellButton\" No Cell Button Found.";
		assert rulesButton != null : "fx:id=\"rulesButton\" No Rules Button Found.";
		assert boardButton != null : "fx:id=\"boardButton\" No Board Button Found.";
		assert colorPicker != null : "fx:id=\"colorPicker\" No Color Picker Found.";
		assert speedSlider != null : "fx:id=\"speedSlider\" No Speed Slider Found.";
		/* Button Logic */
		playButton.setOnAction((event) -> {
			playButton();
		});
		pauseButton.setOnAction((event) -> {
			pauseButton();
		});
		resetButton.setOnAction((event) -> {
			resetButton();
		});
		GIFButton.setOnAction((event) -> {
			try {
			GIFButton();
			}
			catch (Exception e) {
				System.err.println("Something terrible has happened to you. I think it was:" + e);
			}
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
		rulesHighLife.setOnAction((event) -> {
			rulesHighLifeButton();
		});
		rulesConwayPerformance.setOnAction((event) -> {
			rulesConwayPerformanceButton();
		});
		boardDynamic.setOnAction((event) -> {
			boardDynamicButton();
		});
		boardStatic.setOnAction((event) -> {
			boardStaticButton();
		});
	}
	/*Button Functions*/
	private void playButton() {
		animation.play();
		draw(gc);
	}
	
	private void pauseButton() {
		animation.pause();
		draw(gc);
	}
	
	private void resetButton() {
		animation.stop();
		game.setWidth(240);
		game.setHeight(120);
		game.setCleanBoard();
		draw(gc);
	}
	
	private void GIFButton() throws Exception {
		int frameCount = 0;
		String GIFSavePath = null;
		TextInputDialog GIFFrames = new TextInputDialog("10");
		GIFFrames.setTitle("GIF Creator");
		GIFFrames.setHeaderText("Export a GIF of your favorite life-cycle!");
		GIFFrames.setContentText("Frame count:");
		final Button cancel = (Button) GIFFrames.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
            System.out.println("Cancel was definitely pressed")
        );
        Optional<String> result = GIFFrames.showAndWait();
    		//Get url from user input
			if (result.isPresent() && result.get().matches("[0-9]+")) {
	            System.out.println("Result present => OK was pressed");
	            System.out.println("Result: " + result.get());
	            frameCount = Integer.parseInt(result.get());
	            
	            TextInputDialog GIFPath = new TextInputDialog("GIF01.gif");
	    		GIFPath.setTitle("GIF Creator");
	    		GIFPath.setHeaderText("Export a GIF of your favorite life-cycle!");
	    		GIFPath.setContentText("File Name:");
	    		final Button cancel2 = (Button) GIFPath.getDialogPane().lookupButton(ButtonType.CANCEL);
	            cancel2.addEventFilter(ActionEvent.ACTION, event ->
	                System.out.println("Cancel was definitely pressed")
	            );
	            Optional<String> result2 = GIFPath.showAndWait();
	        		//Get url from user input
	    			if (result2.isPresent()) {
	    	            System.out.println("Result present => OK was pressed");
	    	            System.out.println("Result: " + result2.get());
	    	            GIFSavePath = result2.get();
	    			 } else {
	    				 System.out.println("Result was invalid. ");
	    			 }
			 } else {
				System.out.println("Result was invalid. ");
			 }
			//Input code for GIF-generator here.
            // makes a GameOfLifeGIF object and pass it the necessary parameters.
            gif = new GameOfLifeGIF(frameCount, (defaultGIFDirectory + "/" + GIFSavePath), game, rules);
            // starts the process of making the gif.
            gif.makeGIF();
        //Use variables frameCount and GIFSavePath.
	}
	
	private void randomButton() {
		game.setRandomBoard();
		draw(gc);
	}
	
	private void cleanButton() {
		game.setCleanBoard();
		draw(gc);
	}
	
	private void fileChooserButton()  {
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
	
	private void fileByURLButton() {
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
				if (result.isPresent()) {
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
	
	private void cellConwayButton() {
		cellMethod = setCellRules("drawCell");
	}
	private void cellHistoryButton() {
		cellMethod = setCellRules("drawCellHistory");
	}
	private void rulesConwayButton() {
		ruleMethod = setRules("conwayLife");
	}
	private void rulesNoDeathsButton() {
		ruleMethod = setRules("noDeathsLife");
	}
	private void rulesHighLifeButton() {
		ruleMethod = setRules("highLife");
	}
	private void rulesConwayPerformanceButton() {
		ruleMethod = setRules("conwayLifePerformance");
	}
	private void boardDynamicButton() {
		game = new GameOfLifeDynamic();
		draw(gc);
	}
	private void boardStaticButton() {
		game = new GameOfLifeStatic();
		draw(gc);
	}

	private void timeLine() {
		animation.setAutoReverse(false);
        animation.setCycleCount(Timeline.INDEFINITE);
	}
	
	private void run(){
		timerListener();
		cellHistory();
		//Use reflection to call Rules.
		//Ruleset can be changed by the press of a button.
		try {
			ruleMethod.invoke(rules, game);
			} catch (IllegalArgumentException e) {
				System.err.println("Either arguments of this method could be wrong.");
			}
			  catch (IllegalAccessException e) {
				  System.err.println("Could not access this method.");
			  }
			  catch (InvocationTargetException e) {
				  System.err.println("Method could not be invoked on object.");
			  }
		// Comment out above, and uncomment below to use threads.
		/*try {
            createWorkers();
            runWorkers();
            workers.clear();
        } catch (InterruptedException e1) {
            //e1.printStackTrace();
        }*/
		draw(gc);
		
	}
	
	private void cellHistory() {
		for (int i = 0;i < GameOfLife.k;i++) {
			for (int j = 0;j < GameOfLife.m;j++) {
				GameOfLifeCell currentCell = game.getCell(i,j);
				currentCell.savePreviousState();
				currentCell.saveActivity();
				currentCell.savePreviousNeighbors();
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
		double currentWidthRatio = grid.getWidth()/(cellSize*game.getWidth());
		double currentHeightRatio = grid.getHeight()/(cellSize*game.getHeight());
		for (int i = 0; i < game.getWidth(); i++) {
			for (int j = 0; j < game.getHeight(); j++) {
				GameOfLifeCell currentCell = game.getCell(i,j);
				//Return combination of previous and current cell states:
				//Use reflection to call cell presentation,
				//different methods return different states
				try {
					currentState = (byte) cellMethod.invoke(currentCell, currentCell);
				} catch (IllegalArgumentException e) {
					System.err.println("Either arguments of this method could be wrong.");
					currentState = currentCell.drawCell(currentCell);
				}
				  catch (IllegalAccessException e) {
					  System.err.println("Could not access this method.");
						currentState = currentCell.drawCell(currentCell);
				  }
				  catch (InvocationTargetException e) {
					  System.err.println("Method could not be invoked on object.");
						currentState = currentCell.drawCell(currentCell);
				  }
				currentCell.drawBox(gc, colorPicker.getValue(), i, j, currentSize, currentState, currentWidthRatio, currentHeightRatio);
			}
		}
    } 
	/**
     * Makes threads by the number of processors Threads implement conwayLifeConcurrent.
     * @throws InterruptedException 
     */
    public void createWorkers() throws InterruptedException{
        int numWorkers = Runtime.getRuntime().availableProcessors();
        int increment = GameOfLife.k/numWorkers;
        rules.resetIndex(increment);
        for(int i = 0; i < numWorkers; i++){
            workers.add(new Thread(() -> {
                rules.conwayLifeConcurrent(game, increment);
                }));
        }
    }
    
    /**
     * Runs the threads and then calls updateNeighbors() after all the threads have finished running.
     * @throws InterruptedException
     */
    public void runWorkers() throws InterruptedException {
        for(Thread t : workers) {
            t.start();
        }
        for(Thread t : workers) {
            t.join();
        }
    }
	public static void main(String[] args) {
		
		launch(args);
		
		
	}
}
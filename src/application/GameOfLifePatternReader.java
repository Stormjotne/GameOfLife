package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
import java.net.*;
/**
 * 
 * @author Ruby
 *
 */
public class GameOfLifePatternReader {
	
	String userPatternURL;
	String userPatternDirectory;
	String userPatternName;

	String tempName;
	String tempOrigin;
	String tempInformation;
	int tempWIDTH;
	int tempHEIGHT;
	String tempLifeRules;
	StringBuffer tempPlotPattern = new StringBuffer();
	char[] charPlotPatternArray;
	Alert alertGameOfLifePatternReader = new Alert(AlertType.ERROR);

	public GameOfLifePatternReader() {
		
	}
	
	/**
	 * Reads a single RLE file and parses its contents into useful information about a Game of Life pattern.
	 * The goal is for this method to be called by both remote and local file functions and successfully parse contents into Pattern objects.
	 * Takes a user-selected file as its arguments.
	 * @author Ruby
	 * */
	void parseFileToPatternObject(File rle) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(rle.getAbsolutePath()));
			String fileRead = br.readLine();
			while (fileRead != null) {
				//Name of Pattern
				if (fileRead.startsWith("#N")) {
					tempName = fileRead.substring(3);
					//System.out.println(tempName);
				}
				//Name of Creator
				else if (fileRead.startsWith("#O")) {
					tempOrigin = fileRead.substring(3);
					//System.out.println(tempOrigin);
				}
				//Comments, trivia about the pattern
				else if (fileRead.startsWith("#C")) {
					tempInformation = fileRead.substring(3);
					//System.out.println(tempInformation);
				}
				else if (fileRead.startsWith("#P")) {
					
				}
				else if (fileRead.startsWith("#R")) {
					
				}
				else if (fileRead.startsWith("#r")) {
					
				}
				//Size of pattern, and rules (Life or HighLife)
				else if (fileRead.startsWith("x")) {
				
				String[] tokenize = fileRead.split(", ");
				tempWIDTH = Integer.parseInt(tokenize[0].substring(4));
				tempHEIGHT = Integer.parseInt(tokenize[1].substring(4));
				tempLifeRules = tokenize[2].substring(7);
				//System.out.println(tempWIDTH + tempHEIGHT + tempLifeRules);
				}
				else {
				//Append lines with RLE pattern coordinates to a StringBuffer
					tempPlotPattern.append(fileRead);
					
				}
                fileRead = br.readLine();
			}
			//The StringBuffer is converted to a String and then to an array of Characters
			//The last Character should always be '!'
			charPlotPatternArray = tempPlotPattern.toString().toCharArray();
            br.close();
			//Reset StringBuffer for the coordinate plot.
			tempPlotPattern.setLength(0);
		}
		catch (FileNotFoundException fnfe)
        {
			alertGameOfLifePatternReader.setTitle("Error");
			alertGameOfLifePatternReader.setHeaderText("File not found");
			alertGameOfLifePatternReader.setContentText("The path you specified did not contain a .rle file");
			alertGameOfLifePatternReader.showAndWait();
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
	}
	/**
	 * Reads a RLE-file from user-specified URL and parses its contents into useful information about a Game of Life pattern.
	 * The goal is for this method to be called by both remote and local file functions and successfully parse contents into Pattern objects.
	 * Takes no argument.
	 * */
	void parseURLToPatternObject() throws IOException {
		try {
			URL patternURL = new URL(userPatternURL);
			BufferedReader RLEReader = new BufferedReader(
					new InputStreamReader(patternURL.openStream()));
			String fileRead = RLEReader.readLine();
			while (fileRead != null) {
				//Name of Pattern
				if (fileRead.startsWith("#N")) {
					tempName = fileRead.substring(3);
					//System.out.println(tempName);
				}
				//Name of Creator
				else if (fileRead.startsWith("#O")) {
					tempOrigin = fileRead.substring(3);
					//System.out.println(tempOrigin);
				}
				//Comments, trivia about the pattern
				else if (fileRead.startsWith("#C")) {
					tempInformation = fileRead.substring(3);
					//System.out.println(tempInformation);
				}
				else if (fileRead.startsWith("#P")) {
					
				}
				else if (fileRead.startsWith("#R")) {
					
				}
				else if (fileRead.startsWith("#r")) {
					
				}
				//Size of pattern, and rules (Life or HighLife)
				else if (fileRead.startsWith("x")) {
			
					String[] tokenize = fileRead.split(", ");
					tempWIDTH = Integer.parseInt(tokenize[0].substring(4));
					tempHEIGHT = Integer.parseInt(tokenize[1].substring(4));
					tempLifeRules = tokenize[2].substring(7);
					//System.out.println(tempWIDTH + tempHEIGHT + tempLifeRules);
				}
				else {
					//Append lines with RLE pattern coordinates to a StringBuffer
					tempPlotPattern.append(fileRead);
				
				}
				fileRead = RLEReader.readLine();
			}
			//The StringBuffer is converted to a String and then to an array of Characters
			//The last Character should always be '!'
			charPlotPatternArray = tempPlotPattern.toString().toCharArray();
			System.out.println(charPlotPatternArray);
			RLEReader.close();
			//Reset StringBuffer for the coordinate plot.
			tempPlotPattern.setLength(0);
		}
		catch (FileNotFoundException fnfe)
        {
			alertGameOfLifePatternReader.setTitle("Error");
			alertGameOfLifePatternReader.setHeaderText("File not found");
			alertGameOfLifePatternReader.setContentText("The path you specified did not contain a .rle file");
			alertGameOfLifePatternReader.showAndWait();
            System.out.println("file not found");
        }
	}
	/**
	 * Downloads a single rle-file from user-specified URL and saves it to the specified directory.
	 * */
	void downloadPattern() throws IOException {
		URL patternURL = new URL(userPatternURL);
		ReadableByteChannel rbc = Channels.newChannel(patternURL.openStream());
		File directory = new File(String.valueOf(userPatternDirectory));
			if (! directory.exists()){
				directory.mkdir();
			}
		FileOutputStream fos = new FileOutputStream(userPatternDirectory + userPatternName + ".rle");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        System.out.println("Pattern with filename: " + patternURL.getFile() + " has been copied to the local patterns folder.");
        fos.close();
	}
	/** 
	 *  Sets the online location of the pattern. 
	 *  Takes user input.
	 * */
	void setPatternURL(String patternURL) {
		userPatternURL = patternURL;
	}
	/** 
	 * Gets the online location of the pattern.
	 * Given by the user.
	 * */
	String getPatternURL() {
		String currentPatternURL = userPatternURL;
		return currentPatternURL;
	}
	/** 
	 *  Sets the local location of the pattern directory. 
	 *  Takes user input.
	 * */
	void setPatternDirectory(String patternDirectory) {
		userPatternDirectory = patternDirectory;
	}
	/** 
	 * Gets the local location of the pattern directory.
	 * Given by the user.
	 * */
	String getPatternDirectory() {
		String currentPatternDirectory = userPatternDirectory;
		return currentPatternDirectory;
	}
	/** 
	 *  Sets the name of the pattern chosen by the user.
	 *  Takes user input.
	 * */
	void setPatternName(String patternName) {
		userPatternName = patternName;
	}
	/** 
	 * Gets the name of the user-defined pattern.
	 * Given by the user.
	 * */
	String getPatternName() {
		String currentpatternName = userPatternName;
		return currentpatternName;
	}
}
package application;

import java.nio.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifePatternReader {
	
	public String userPatternURL;
	public String userPatternDirectory;
	public String userPatternName;

	String tempName;
	String tempOrigin;
	String tempInformation;
	int tempWIDTH;
	int tempHEIGHT;
	String tempLifeRules;
	String tempPlotPattern;
	char[] charPlotPatternArray;

	public void GameOfLifePatternReader() {
		
	}
	
	/**
	 * Reads a single RLE file and parses its contents into useful information about a Game of Life pattern.
	 * The goal is for this method to be called by both remote and local file functions and successfully parse contents into Pattern objects.
	 * Takes an instance of the Model class as its argument.
	 * */
	public void parseFileToPatternObject(GameOfLifeModel game) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(userPatternDirectory + "glider.rle"));
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
				//make a string with dead and alive cells to be transformed into an array
				//b = dead cell, o = alive cell, $ = end of line
					charPlotPatternArray = fileRead.toCharArray();
					//System.out.println(charPlotPatternArray);
					
				}
                fileRead = br.readLine();
			}
            br.close();
			// Create temporary object of Pattern
			GameOfLifePattern tempObj = new GameOfLifePattern(game, tempName, tempOrigin, tempInformation, tempWIDTH, tempHEIGHT, tempLifeRules, charPlotPatternArray);
			tempObj.toString();
		}
		catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
	}
	
	/**
	 * Downloads a single file from user-specified URL and saves it to the specified directory.
	 * */
	public void downloadPattern() throws IOException {
		URL patternURL = new URL(userPatternURL);
		ReadableByteChannel rbc = Channels.newChannel(patternURL.openStream());
		File directory = new File(String.valueOf(userPatternDirectory));
			if (! directory.exists()){
				directory.mkdir();
			}
		FileOutputStream fos = new FileOutputStream(userPatternDirectory + "glider.rle");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        System.out.println("Pattern with filename: " + patternURL.getFile() + " has been copied to the local patterns folder.");
        fos.close();
	}

	/**
	 * Reads a file from user-specified URL and prints it to the console line-by-line.
	 * */
	public void readFileFromURL() throws IOException {
		URL patternURL = new URL(userPatternURL);
		BufferedReader RLEReader = new BufferedReader(
		new InputStreamReader(patternURL.openStream()));
		String inputLine;
        while ((inputLine = RLEReader.readLine()) != null) {
            System.out.println(inputLine);
        }
        RLEReader.close();
	}

	/** 
	 *  Sets the online location of the pattern. 
	 *  Takes user input.
	 * */
	public void setPatternURL(String patternURL) {
		userPatternURL = patternURL;
	}

	/** 
	 * Gets the online location of the pattern.
	 * Given by the user.
	 * */
	public String getPatternURL() {
		String currentPatternURL = userPatternURL;
		return currentPatternURL;
	}
	
	/** 
	 *  Sets the local location of the pattern directory. 
	 *  Takes user input.
	 * */
	public void setPatternDirectory(String patternDirectory) {
		userPatternDirectory = patternDirectory;
	}

	/** 
	 * Gets the local location of the pattern driectory.
	 * Given by the user.
	 * */
	public String getPatternDirectory() {
		String currentPatternDirectory = userPatternDirectory;
		return currentPatternDirectory;
	}
}
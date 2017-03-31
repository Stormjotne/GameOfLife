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

	public void GameOfLifePatternReader() {
		
	}
	
	/**
	 * Reads a single RLE file from local patterns folder.
	 * Needs a lot of work.
	 * */
	public void readLocalFile() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("patterns/glider.rle"));
			String fileRead = br.readLine();
			while (fileRead != null) {
				String tempName;
				String tempOrigin;
				String tempInformation;
				int tempWIDTH;
				int tempHEIGHT;
				String tempNotHighLife;
				if (fileRead.startsWith("#N")) {
					tempName = fileRead.substring(3);
					System.out.println(tempName);
				}
				else if (fileRead.startsWith("#O")) {
					tempOrigin = fileRead.substring(3);
					System.out.println(tempOrigin);
				}
				else if (fileRead.startsWith("#C")) {
					tempInformation = fileRead.substring(3);
					System.out.println(tempInformation);
				}
				else if (fileRead.startsWith("#P")) {
					
				}
				else if (fileRead.startsWith("#R")) {
					
				}
				else if (fileRead.startsWith("#r")) {
					
				}
				else if (fileRead.startsWith("x")) {
				
				String[] tokenize = fileRead.split(", ");
				tempWIDTH = Integer.parseInt(tokenize[0].substring(4));
				tempHEIGHT = Integer.parseInt(tokenize[1].substring(4));
				tempNotHighLife = tokenize[2].substring(7);
				System.out.println(tempWIDTH + tempHEIGHT + tempNotHighLife);
				}
				else {
				//b = dead cell, o = alive cell, $ = end of line
					
				}
				/* Create temporary object of Pattern */
				//GameOfLifePattern tempObj = new GameOfLifePattern(tempName, tempOrigin, tempInformation, tempWIDTH, tempHEIGHT);
                fileRead = br.readLine();
			}
            br.close();	
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
		File directory = new File(String.valueOf("patterns/"));
			if (! directory.exists()){
				directory.mkdir();
			}
		FileOutputStream fos = new FileOutputStream("patterns/glider.rle");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        System.out.println("Pattern with filename: " + patternURL.getFile() + " has been copied to the local patterns folder.");
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
	 * */
	public void setPatternURL(String patternURL) {
		userPatternURL = patternURL;
	}

	/** 
	 * */
	public String getPatternURL() {
		String currentPatternURL = userPatternURL;
		return currentPatternURL;
	}
}
package application;

import java.nio.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameOfLifePatternReader {
	
	public String userPatternURL;
	
	public void downloadPattern() throws IOException {
		URL patternURL = new URL(userPatternURL);
		ReadableByteChannel rbc = Channels.newChannel(patternURL.openStream());
		FileOutputStream fos = new FileOutputStream("patterns/glider.rle");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        System.out.println("Pattern with filename: " + patternURL.getFile() + " is now being copied to the local patterns folder.");
	}
		
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
	
	public void setPatternURL(String patternURL) {
		userPatternURL = patternURL;
	}
	
	public String getPatternURL() {
		String currentPatternURL = userPatternURL;
		return currentPatternURL;
	}
}
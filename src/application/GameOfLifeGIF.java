package application;

import lieng.GIFWriter;

import java.awt.Color;
import java.io.IOException;

//import javafx.scene.paint.Color;

/*
* A demonstration of the GIFWriter class.
* This program makes a GIF image file with a simple animation.
*/
public class GameOfLifeGIF {
	
		// Declaration of objects
		lieng.GIFWriter gWriter;
		GameOfLifeController GameC;
		GameOfLife game = new GameOfLifeDynamic();
		GameOfLife next;
		GameOfLifeRules rules;
		
		// data related to the GIF image file
		private int cellSize;
		// In pixels
		private int width = game.getWidth()*10;
		private int height = game.getHeight()*10;
		// The number of frames to be written to gif
		private int frames;
		// The time between each image 
		private int timePerMilliSecond = 500; // 1 second
		String path = "Test.gif";
		
		
		public GameOfLifeGIF(int frames, String path, GameOfLife board, GameOfLifeRules rules){
			this.frames = frames;
			this.path = path;
			this.game = board;
			this.rules = rules;
		}
		
		
		/** Method that converts javafx.color to awt.color
		 * Could not get the javafx.Color color from colorPicker and send it here, 
		 * there for this method is currently redundant
		 * */
		public java.awt.Color convertFxColor(javafx.scene.paint.Color fx){
			java.awt.Color awtColor = new java.awt.Color((float) fx.getRed(),(float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity());
			return awtColor;
			}
		
		// Write game of life sequence to gif
		private void writeGIF(GIFWriter gWriter, GameOfLife game, int frames) throws IOException {
			//game.setRandomBoard();
		       if (frames != 0) {
		    	   
		    	   	// The size of the cells drawn to the gif
		    	   	// based on the image size provided
		    	   	cellSize = (int) getSize(height, width, game.getWidth(), game.getHeight());
		        	
		            int x = 0;
		            int x1 = cellSize;
		            int y = 0;
		            int y1 = cellSize;
		            
		            System.out.println(cellSize);
		            gWriter.createNextImage();
		            // The for-loop draws each cell to the image/gif
		            for (int row = 0; row < game.getWidth() - 1; row++) {
		                for (int col = 0; col < game.getHeight() - 1; col++) {
		                    if (game.getCellState(row,col) == 1) {
		                    	//convertFxColor(GameC.getColor());
		                    	
		                    	/* Because fault in getting the javafx.Color and converting it,
		                    	   the cells are always drawn in black.
		                    	   */
		                    	gWriter.fillRect(x, x1, y, y1, Color.BLACK);
		                    }
		                    
		                    //displace x with the cellSize to draw cell next to each other
		                    x += cellSize;
		                    x1 += cellSize;
		                }
		                // reset x and x1
		                x = 0;
		                x1 = cellSize;
		                // Displace y to draw under the former line
		                y += cellSize;
		                y1 += cellSize;
		            }
		            // sends current image to GifWriter
		            gWriter.insertCurrentImage();
		            
		            /* Calls rules on the game object to change it for the next image.
		             * This does currently not work, and i see no reasons why.
		             * The game object is a reference to the game object drawn in the GUI,
		             * there for it will also change the board in the GUI once the gif is made. :(
		             *  */
		            rules(game);
		            
		            gWriter.createNextImage();
		         
		            // tail-recursion
		            writeGIF(gWriter, game, --frames);
		        }
		    }
		
		public void rules(GameOfLife game){
			// try and catch because some of the rule methods throws exceptions
            try{
            	rules.conwayLife(game);
            	}
            catch(Exception e){
            	e.toString();
            	}
			}
		
		private double getSize(double availableHeight, double availableWidth, int rows, int columns) {
			/**
			 * availableHeight is the provided height for the gif set by height at the top
			 * availableWith is the provided width
			 * rows is given by: game.getWidth()
			 * columns is given by: game.getHeight()
			 *  */
	        double sizeHeight = availableHeight / rows;
	        double sizeWidth = availableWidth / columns;
	        /* The method will calculate, based on provided space
	         * the height and width of cell drawn to the gif.
	         * It returns the smaller of the two, to ensure there is space to draw the whole board in.
	         */
	        return Math.min(sizeWidth, sizeHeight);
	    }
		
		public boolean makeGIF() throws Exception {
			// create the GIFWriter object
			gWriter = new lieng.GIFWriter(width,height,path,timePerMilliSecond);
			
			// The method who will make the images and add it to the gif
			writeGIF(gWriter, game, frames);
			
			// close the GIF stream.
			gWriter.close();
			
			System.out.println("done!");
			return true;
		}
}

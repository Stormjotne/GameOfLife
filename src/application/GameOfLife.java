package application;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;
/**
 * 
 *	Game object.
 * @author Ruby
 */
public class GameOfLife {

	public static int k = 250, m = 180;
	public HashMap<String, Byte> activityMap = new HashMap<String, Byte>();
	public List<String> activityList = new ArrayList<String>();
	
	/**
	 * This method initializes the activityMap, all cells start as active.
	 */
	public void initActivityMap() {
		for (int x = 0; x < k; x++){
			for (int y = 0; y < m; y++) {
				activityMap.put("(" + x + "," + y + ")", (byte)1);
			}
		}
		System.out.println(activityMap);
	}
	/**
	 * This method updates activity for one cell.
	 * The only method needed is to set a value of zero.
	 */
	public void updateActivityMapZero (int x, int y) {
		activityMap.put("(" + x + "," + y + ")", (byte)0);
	}
	/**
	 * This method updates activity for all eight neighbors of one cell.
	 * The only method needed is to set a value of one.
	 */
	public void updateActivityMapNeighbors (int x, int y) {
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				  activityMap.put("(" + (x+i) + "," + (y+j) + ")", (byte)1);
			  }
		}
	}
	/**
	 * This method initializes the activityList, all cells start as active.
	 */
	public void initActivityList() {
		for (int x = 0; x <= k; x++){
			for (int y = 0; y <= m; y++) {
				activityList.add("(" + x + "," + y + ")");
			}
		}
		System.out.println(activityList);
	}
	/**
	 * This method deletes one cell from activityList.
	 */
	public void updateActivityListDelete (int x, int y) {
		activityList.remove("(" + x + "," + y + ")");
	}
	/**
	 * This method adds all eight neighbors of one cell to activityList.
	 */
	public void updateActivityListNeighbors (int x, int y) {
		for (int i = -1; i <= 1; i++) {
			  for (int j = -1; j <= 1; j++) {
				  if (!activityList.contains("(" + (x+i) + "," + (y+j) + ")")) {
				  activityList.add("(" + (x+i) + "," + (y+j) + ")");
				  }
			  }
		}
	}
}

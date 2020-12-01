package uk.ac.ed.inf.powergrab;

/**
 * This class defines the PowerGrab playing area. All points
 * on every map are within this area. Note that the drone must  
 * not fly out of this area.
 * 
 * @author s1891130
 * @since 1.0
*/

public class PlayArea {
	
	/*
	 * The borders are given by latitudes and longitudes
	 *  based on a real world map. 
	 */
	
    static public double leftBorder = -3.192473;
    static public double rightBorder = -3.184319;
    static public double topBorder = 55.946233;
    static public double downBorder = 55.942617;
}

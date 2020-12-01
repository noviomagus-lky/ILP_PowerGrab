package uk.ac.ed.inf.powergrab;

import java.util.List;

/**
 * This class consists public attributes and methods that 
 * can examine or move the position of an object on the map.
 * 
 * @author s1891130
 * @since 1.0
 */

public class Position {

	//the position is determined by a coordinate(longitude, latitude). 
	public double latitude;
	public double longitude;
	
	/**
	 * The drone travels a unit distance every time, which is
	 * 0.0003 degrees. Note that due to considering small changes
	 * if longitude or latitude, we approximate the earth's surface
	 * as plane.
	 */
	static private double moveUnitDistance = 0.0003;
	
	/**
	 * Constructor, the initial location is always required.
	 * 
	 */
    Position(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
    
    Position(List<Double> coordinates)
    {
    	this.longitude = coordinates.get(0);
    	this.latitude = coordinates.get(1);
    }
	
    /**
     * Finds the next position of the drone according to the 
     * current position and the specified direction.
     * 
     * @param direction the direction that the drone flies in.
     * @return the next position of the drone.
     * @see Direction
     */
	public Position nextPosition(Direction direction)
	{
		double pi = Math.PI;
	    double angle = 0;
	    
	    //convert the direction to angle
		switch(direction) {
		case E : angle= 0.0;break;
		case ENE : angle= pi/8;break;
		case NE : angle= pi/4;break;
		case NNE : angle= 3*pi/8;break;
		case N : angle= pi/2;break;
		case NNW : angle= 5*pi/8;break;
		case NW : angle= 3*pi/4;break;
		case WNW : angle= 7*pi/8;break;
		case W : angle= pi;break;
		case WSW : angle= -7*pi/8;break;
		case SW : angle= -3*pi/4;break;
		case SSW : angle= -5*pi/8;break;
		case S : angle= -pi/2;break;
		case SSE : angle= -3*pi/8;break;
		case SE : angle= -pi/4;break;
		case ESE : angle= -pi/8;break;
		default: angle = 0.0;
		}
		
		//calculate the next position
		double nextLatitude = latitude + moveUnitDistance*Math.sin(angle);
	    double nextLongitude = longitude + moveUnitDistance*Math.cos(angle);
	    return new Position(nextLatitude,nextLongitude);
	}
	
	/**
	 * Checks if the object is in the play area.
	 * 
	 * @return returns true only if the object is in the play area. Note
	 *         that the method will return false if the object is right
	 *         on the border.     
	 * @see PlayArea
	 */
	public boolean inPlayArea()
	{
		if(latitude >= PlayArea.topBorder || latitude <= PlayArea.downBorder)
			return false;
		else if (longitude >= PlayArea.rightBorder || longitude <= PlayArea.leftBorder)
			return false;
		else 
			return true;
	}
	

	public static Double dist(Position pos1, Position pos2)
	{
		Double x = pos2.longitude - pos1.longitude;
		Double y = pos2.latitude - pos1.latitude;
		return Math.sqrt(x*x + y*y);
	}
	
	public Double dist(Position pos)
	{
		Double x = longitude - pos.longitude;
		Double y = latitude - pos.latitude;
		return Math.sqrt(x*x + y*y);
	}
	
	public boolean equals(Object o) {
		
		if(!(o instanceof Position))
			return false;
		
		Position pos2 = (Position)o; 
		if(pos2.longitude == longitude && pos2.latitude == latitude)
			return true;
			
		return false;
		
	}
	
	public String toString()
	{
		return new Double(latitude).toString() + "," + new Double(longitude).toString();
	}
		
	

}

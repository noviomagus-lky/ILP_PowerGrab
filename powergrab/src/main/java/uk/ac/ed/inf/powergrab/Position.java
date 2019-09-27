package uk.ac.ed.inf.powergrab;

public class Position {

	public double latitude;
	public double longitude;
	static private double moveUnitDistance = 0.0003;
	//static private double precision = 0.000001;
	//static private int inversePrecision = 1000000;
    Position(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
	
	public Position nextPosition(double direction)
	{
		double nextLatitude = latitude + moveUnitDistance*Math.sin(direction);
	    double nextLongitude = longitude + moveUnitDistance*Math.cos(direction);
	    return new Position(nextLatitude,nextLongitude);
	}
	
	public boolean inPlayArea()
	{
		if(latitude >= PlayArea.topBorder || latitude <= PlayArea.downBorder)
			return false;
		else if (longitude >= PlayArea.rightBorder || longitude <= PlayArea.leftBorder)
			return false;
		else 
			return true;
	}
	
	/*private double setPrecision (double num)
	{
		System.out.println(num);
		double setNumPrecision = Math.round(num/precision);
		System.out.println(setNumPrecision*precision);
		return setNumPrecision/inversePrecision;
	
		return num;
	}
	*/
}

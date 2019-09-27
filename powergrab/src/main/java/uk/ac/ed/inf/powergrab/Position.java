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
	
	public Position nextPosition(Direction direction)
	{
		double pi = Math.PI;
	    double angle = 0;
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
		double nextLatitude = latitude + moveUnitDistance*Math.sin(angle);
	    double nextLongitude = longitude + moveUnitDistance*Math.cos(angle);
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

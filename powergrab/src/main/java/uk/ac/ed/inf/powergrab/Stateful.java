package uk.ac.ed.inf.powergrab;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Stateful extends Stateless{
	
	public Stateful(Position position, Integer seed, GameMap theMap)
	{
		super(position, seed, theMap);
	}
	
	public static Station findNearestGreen(List<Station> stations,Position dronePos)
	{
		
		
		double minDist = Double.MAX_VALUE;
		Station result = null;
		
		if(stations.size() == 0)
			return null;
		
		for(int i = 0;i < stations.size();i++)
		{
			Station temp = stations.get(i);
			if(temp.getCoins() > 0 && dronePos.dist(temp.getPosition()) < minDist)
			{
				minDist = dronePos.dist(temp.getPosition());
				result = temp;
			}
		}
		
		if(minDist == Double.MAX_VALUE)
			return null;
		
		return result;
	}
	
	public boolean isGreenLeft(List<Station> stations)
	{
		if(stations.size() == 0)
			return false;
		
		for(int i = 0;i < stations.size();i++)
		{
			if(stations.get(i).getCoins() > 0)
				return true;
		}
		
		return false;
	}
	
	public void gotoStation(Station nextStation)
	{
		int moves = 0;
		int maxMoves = 10;
		int randMoves = 10;
		
		while(nextStation.getCoins() != 0 && drone.canMove())
		{
			Direction bestDirection = directions[0];
			Position nextPos;
			double minDist = Double.MAX_VALUE;
			
			for(int i = 0;i < directions.length;i++)
			{
				nextPos = drone.getPosition().nextPosition(directions[i]);
			    double dist = nextPos.dist(nextStation.getPosition());
			    
			    if(isValidPosition(nextPos, stations) && dist < minDist)
			    {
			    	minDist = dist;
			    	bestDirection = directions[i];
			    }
			}
			
			if(moves >= 10)
			{
				for(int i = 0;i < randMoves;i++)
					nextMove();
				moves = 0;
			}
				
			else
			{
				moves++;
				drone.move(bestDirection);
				if(drone.getPosition().dist(nextStation.getPosition()) <= chargeRange)
					{drone.collect(nextStation);}
			}
		}
	}
	
	private boolean isValidPosition(Position pos, List<Station> stations)
	{
		if(!pos.inPlayArea())
			return false;
		
		Station linkedStation = getNearestStation(pos, stations);
		if(linkedStation != null && linkedStation.getCoins() < 0)
			return false;
		
		List<Position> track = drone.getTrack();
		if(track.size() >= 2 && pos.equals(track.get(track.size() - 2)))
			{return false;}
		return true;
	}
	
	public void run()
	{	
		Station nextStation;
		
		while(isGreenLeft(stations) && drone.canMove())
		{
			nextStation = findNearestGreen(stations, drone.getPosition());
			gotoStation(nextStation);
			
		}
		
		//super.run();
	}
	
	public void saveResult() throws IOException
	{
		String fileString = "stateful-" + theMap.getDate();
		FileWriter fw = new FileWriter(fileString + ".geojson");
		fw.write(theMap.toString());
		fw.close();
		fw = new FileWriter(fileString + ".txt");
		fw.write(drone.getLog());
		fw.close();
	}
	

}

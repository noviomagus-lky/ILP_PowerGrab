package uk.ac.ed.inf.powergrab;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Stateless {

	protected Drone drone;
	protected Random rnd;
	protected GameMap theMap;
	protected List<Station> stations;
	
	protected static double chargeRange = 0.00025;
	
	protected static Direction[] directions = Direction.values();
	
	
	public Stateless(Position position, Integer seed, GameMap theMap)
	{
		this.drone = new Drone(position);
		rnd = new Random(seed);
		this.theMap = theMap;
		this.stations = theMap.getStations();
	}
	
	public void run()
	{	
		while(drone.canMove())
		{
			nextMove();
		}
		
		theMap.addLines(drone.getTrack());
	}
	
	public void nextMove()
	{
		List <Station> nearbyStations = getNearbyStations(drone.getPosition(), stations);
		List <Direction> bestDirection = new LinkedList();
		Double maxCoins = Double.NEGATIVE_INFINITY;
		
		for(int i = 0;i < directions.length;i++)
		{
			Position next = drone.position.nextPosition(directions[i]);
			Station nearestStation = null;
			Double coins = 0.0;
			
		    if(isValidPosition(next, nearbyStations))
		    {
				nearestStation = getNearestStation(next, nearbyStations);
				
				if(nearestStation != null)
					coins = nearestStation.getCoins();
				
				if(coins > maxCoins)
				{
					bestDirection.clear();
					bestDirection.add(directions[i]);
					maxCoins = coins;
				}
				else if(coins.equals(maxCoins))
				{
					bestDirection.add(directions[i]);
				}
		    }
			
		}

		int random = rnd.nextInt(bestDirection.size());
		
		drone.move(bestDirection.get(random));			
		
		Station linkedStation = getNearestStation(drone.getPosition(), nearbyStations);
		if(linkedStation != null)
			drone.collect(linkedStation);
	}
	
	private boolean isValidPosition(Position pos, List<Station> stations)
	{
		if(!pos.inPlayArea())
			return false;
		
		Station linkedStation = getNearestStation(pos, stations);
		if(linkedStation != null && linkedStation.getCoins() < 0)
			return false;

		return true;
	}
	
	public void printResult()
	{
		//theMap.print();
		
		System.out.printf("coins rate: %f \n", drone.getCoins()/theMap.getTotalCoins()*100);
		System.out.printf("steps: %d \n", drone.getTrack().size() - 1);
	}
	
	public Station getNearestStation(Position currentPos, List<Station> stations)
	{
	    Double min = Double.MAX_VALUE;
	    Station result = null;
	    if(stations.size() == 0)
	    	return result;
	    
		for(int i = 0;i < stations.size();i++)
		{
			double dist = currentPos.dist(stations.get(i).getPosition());
			if(dist < min)
			{
				min = dist;
				result = stations.get(i);
			}
		}
		if(min > 0.00025)
			return null;
		return result;
		
	}
	
	public 	List <Station> getNearbyStations(Position position, List <Station> stations)
	{
		List <Station> nearbyStations = new LinkedList();
		for(int i = 0;i < stations.size();i++)
		{
			if(Position.dist(stations.get(i).getPosition(), drone.getPosition()) < 0.00055)
				nearbyStations.add(stations.get(i));
		}
		
		return nearbyStations;
	}
	
	public void saveResult() throws IOException
	{
		String fileString = "stateless-" + theMap.getDate();
		FileWriter fw = new FileWriter(fileString + ".geojson");
		fw.write(theMap.toString());
		fw.close();
		fw = new FileWriter(fileString + ".txt");
		fw.write(drone.getLog());
		fw.close();
	}
}

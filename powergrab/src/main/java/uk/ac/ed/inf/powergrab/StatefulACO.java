package uk.ac.ed.inf.powergrab;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class StatefulACO extends Stateful{
	
	private List<Station> greenStations;
	
	public StatefulACO(Position position, Integer seed, GameMap theMap)
	{
		super(position, seed, theMap);
		this.greenStations = getGreenStations(stations);
	}
	
	public void run()
	{	
		double[][] graph = buildACOGraph(stations);
				
		AntColonyOptimization ACO = new AntColonyOptimization(graph, rnd);
		
		int[] stationOrder = ACO.solve();
		
		for(int i = 1;i < stationOrder.length;i++)
		{
			Station nextStation = greenStations.get(stationOrder[i] - 1);
			gotoStation(nextStation);
			
			if(!drone.canMove())
				break;
		}
		
		//super.run();
	}
	
	private List<Station> getGreenStations(List<Station> stations)
	{
		List<Station> greenStations = new LinkedList();
		for(int i = 0;i < stations.size();i++)
		{
			if(stations.get(i).getCoins() > 0)
				greenStations.add(stations.get(i));
		}
		return greenStations;
	}
	
	private double[][] buildACOGraph(List<Station> stations)
	{		
		int length = greenStations.size() + 1;
		double[][] graph = new double[length][length];
		
		List<Position> positions = new LinkedList();
		positions.add(0, drone.getPosition());
		for(int i = 0;i < greenStations.size();i++)
		{
			positions.add(greenStations.get(i).getPosition());
		}
		
		for(int i = 0;i < length;i++)
			for(int j = 0;j < length;j++)
			{
				graph[i][j] = positions.get(i).dist(positions.get(j));
			}
		
		return graph;
	}
}

package uk.ac.ed.inf.powergrab;

import java.util.LinkedList;
import java.util.List;

public class Drone {

	private Double coins;
	private Double power;
	private Integer moves;
	public Position position;
	private List<Double> currentCoins;
	private List<Double> currentPower;
	private List<Position> track;
	private String log = null;
	
	private static Double moveUnitPower = 1.25; 
	
	public Drone(Position position)
	{
		this.coins = 0.0;
		this.power = 250.0;
		this.moves = 0;
		this.position = position;
		
		currentCoins = new LinkedList();
		currentPower = new LinkedList();
		
		track = new LinkedList();
		track.add(position);
		log = position.toString();
	}
	
	public void collect(Station station)
	{
		power += station.getPower();
		coins += station.getCoins();
		station.consume();
	}
	
	public boolean canMove()
	{
		if(moves >= 250)
			return false;
		if(power < moveUnitPower)
			return false;
		return true;
	}
	
	public void move(Direction direction)
	{
		if(!log.equals(position.toString()))
		  log += position.toString();
		log += "," + direction.toString();
		
		position = position.nextPosition(direction);
		moves++;
		power -= moveUnitPower;
		
		currentCoins.add(coins);
		currentPower.add(power);
		track.add(position);
		
		log += "," + position.toString();
		log += "," + coins.toString() + "," + power.toString() + "\n";
		
	}
	
	public List<Position>  getTrack()
	{
		return track;
	}
	
	public double getCoins()
	{
		return coins;
	}
	
	public void reset()
	{
		currentCoins = new LinkedList();
		currentPower = new LinkedList();
		
		position = track.get(0);
		track = new LinkedList();
		track.add(position);
		
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public String getLog()
	{
		return log;
	}
}

package uk.ac.ed.inf.powergrab;

public class Station {
	
	private Double coins;
	private Double power;
	private Position position;

	public Station(Double coins, Double power, Position position) 
	{
		this.coins = coins;
		this.power = power;
		this.position = position;
	}
	
	public Double getCoins()
	{
		return coins;
	}
	
	public Double getPower()
	{
		return power;
	}
	
	public Position getPosition()
	{
		return new Position(position.latitude, position.longitude);
	}
	
	public void consume()
	{
		coins = 0.0;
		power = 0.0;
	}
}

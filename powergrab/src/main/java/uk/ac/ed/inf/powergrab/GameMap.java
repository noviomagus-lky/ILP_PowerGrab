package uk.ac.ed.inf.powergrab;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.io.IOException;
import com.mapbox.geojson.*;

public class GameMap {

	private String mapSource;
	private FeatureCollection fc;
	private List<Feature> features;
	private List<Station> stations;
	private String dd;
	private String mm;
	private String yyyy;
	private String mapString;
	
	public  GameMap(String dd, String mm, String yyyy) throws MalformedURLException, IOException
	{
		//String mapString = "http://homepages.inf.ed.ac.uk/stg/powergrab/2019/01/01/powergrabmap.geojson";
		this.dd = dd;
		this.mm = mm;
		this.yyyy = yyyy;
		this.mapString = getMapString(dd, mm, yyyy);
		URL mapUrl = new URL(mapString);
		HttpURLConnection conn  = (HttpURLConnection) mapUrl.openConnection();
		
		conn.setConnectTimeout(15000);
		conn.setReadTimeout(10000);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		
		Scanner sc = new Scanner(conn.getInputStream());
		
		while(sc.hasNext())
		{
			if(mapSource == null)
				mapSource = sc.next();
			else
			mapSource += sc.next();
		}
		
		fc = FeatureCollection.fromJson(mapSource);
		features = fc.features();
		
		sc.close();
		
		this.stations = new LinkedList<Station>();
		for(int i = 0;i < features.size();i++)
		{
			Double coins = features.get(i).getProperty("coins").getAsDouble();
			Double power = features.get(i).getProperty("power").getAsDouble();
			Point p = (Point)features.get(i).geometry();
			Position position = new Position(p.coordinates());
			stations.add(new Station(coins, power, position));
		}
	}
	


	public void addLines(List<Position> lines)
	{
		List<Point> list = new LinkedList();
	  if(lines.size() > 0)
		for(int i = 0;i < lines.size();i++)
		{		
		
			//Point p1 = Point.fromLngLat(lines.get(i)[0].longitude, lines.get(i)[0].latitude);
			//Point p2 = Point.fromLngLat(lines.get(i)[1].longitude, lines.get(i)[1].latitude);
			
			Point point = Point.fromLngLat(lines.get(i).longitude, lines.get(i).latitude);
			list.add(point);
			
		}
	    LineString line = LineString.fromLngLats(list);
		Feature flightpath = Feature.fromGeometry(line);
		features.add(flightpath);
		fc = FeatureCollection.fromFeatures(features);
	}
	
	public List<Station> getStations(){
		
		List<Station> temp= new LinkedList<Station>();
		for(int i = 0;i < features.size();i++)
		{
			Double coins = features.get(i).getProperty("coins").getAsDouble();
			Double power = features.get(i).getProperty("power").getAsDouble();
			Point p = (Point)features.get(i).geometry();
			Position position = new Position(p.coordinates());
			temp.add(new Station(coins, power, position));
		}
		
		return temp;
	}
	
	public void print()
	{
		System.out.println(fc.toJson());
	}
	
	public double getTotalCoins()
	{
		double totalCoins = 0;
		
		for(int i = 0;i < stations.size();i++)
		{
			double stationCoins = stations.get(i).getCoins();
			if(stationCoins > 0)
			totalCoins += stationCoins;	
		}
		
		return totalCoins;
	}
	
	public String getMapString(String dd, String mm, String yyyy)
	{
		String mapString = "http://homepages.inf.ed.ac.uk/stg/powergrab/"+
							yyyy + "/" + mm + "/" + dd + "/powergrabmap.geojson";
		return mapString;
	}
	public String getMapString()
	{
		return mapString;
	}
	public String getDate()
	{
		return dd + "-" + mm + "-" + yyyy;
	}
	public String toString()
	{
		return fc.toJson();
	}
}




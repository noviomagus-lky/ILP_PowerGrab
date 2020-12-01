package uk.ac.ed.inf.powergrab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args ) throws IOException
    {
    	
    	String dd = args[0];
    	String mm = args[1];
    	String yyyy = args[2];
    	GameMap map1 = new GameMap(dd, mm, yyyy);
    	
    	Scanner sc = new Scanner(args[3] + " " + args[4] + " " + args[5]);
    	double latitude = sc.nextDouble();
    	double longitude = sc.nextDouble();
    	
    	int rndSeed = sc.nextInt();
    	
    	Stateless test1 = new Stateless(new Position(latitude, longitude), rndSeed, map1);
    	StatefulACO test2 = new StatefulACO(new Position(latitude, longitude), rndSeed, map1);
    	Stateful test3 = new Stateful(new Position(latitude, longitude), rndSeed, map1);

    	if(args[6].equals("stateless"))
    		{
    			test1.run();test1.saveResult();test1.printResult();
    		}
    	else
    		{
    			test2.run();test2.saveResult();test2.printResult();
    			test3.run();test3.saveResult();test3.printResult();
    		}
    	
    	
    	
    }
}

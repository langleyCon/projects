package cs452.hw1;
/*Connor Langley
 * AStar
 * 9/29/2019
 * */
import java.util.*;
import java.util.Optional;
import java.io.*;
import java.util.regex.*;
/*This is the unoptimized version of my AStar implementation
 * With the optimizations commented out.
 * */
public class AStar extends Pathfinder{
	
	HashMap<String, City> cities = new HashMap<>();
	List<String> used = new ArrayList<>();
	Comparator<City> cityCom = new Comparator<City>() {
		public int compare(City city1, City city2) {
			if(city1.h < city2.h) {
				return -1;
			}
			else if(city1.h > city2.h) {
				return 1;
			}
			return 0;
		}
	};
	PriorityQueue<City> frontier = new PriorityQueue<>(cityCom);
	/*Honestly a fairly messy main method. 
	 * Allows for continued use of the program
	 * Essentially just calls AStar every time the user gives the program
	 * two cities to find a path between*/
	public static void main(String[] args) {
		while(true) {
			AStar a = new AStar(args[0]);
			Scanner scan = new Scanner(System.in);
			String start, end;
			System.out.println("Type \"end\" to exit the program");
			System.out.println("Enter Start City");
			start = scan.nextLine();
			if(start.equals("end")) {
				break;
			}
			System.out.println("Enter End City");
			end = scan.nextLine();
			if(end.equals("end")) {
				break;
			}
			Path route = (Path) a.getPathInfo(start, end);
			String r = start + "";
			for(String s : route.path) {
				r = r +"->"+ s;
			}
			System.out.println(r);
			System.out.println("Total Nodes Added to Frontier: " + route.totalN);
			System.out.println("Total Nodes Not Expanded in Frontier: " + route.totalON);
			System.out.println("");
		}
		

	}
	/*Starting point for the Program
	 * Parses through the given file and 
	 * essentially sets up the tree for the search.*/
	public AStar(String f) {
		File file = new File(f);
		boolean state = true;
		
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				String tmp = scan.nextLine();
				if(tmp.equals("# name latitude longitude")) {
					continue;
				}
				else if(tmp.equals("# distances")) {
					state = false;
					continue;
				}
				if(state) {
					firstParse(tmp);
				}
				else {
					secondParse(tmp);
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("No Such File Found");
		}
		
	}
	/*Gets the direct distance from one city to another used for generating
	 * a heuristic.*/
	public double getDistance(String start, String end) {
		City city1 = cities.get(start);
		City city2 = cities.get(end);
		double r = 3958.8;
		double lon = city2.lon - city1.lon;
		double lat = city2.lat - city1.lat;
		double a = Math.pow(Math.sin(Math.toRadians(lat)/2), 2)
				   + Math.cos(Math.toRadians(city1.lat))
				   * Math.acos(Math.toRadians(city2.lat))
				   * Math.pow(Math.sin(Math.toRadians(lon)/2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = r * c;
		return d;
	}
	/*This parse gets all the direct connections between the different cities from
	 * the input file.
	 * Each unique city has a list of connections, which this parse populates.*/
	private void secondParse(String nextLine) {
		Pattern p = Pattern.compile("(.*)[,]\\s(.*)[:]\\s(\\d+[.]\\d+)");
		Matcher m = p.matcher(nextLine);
		m.find();
		City c = cities.get(m.group(1));
		City c2 = cities.get(m.group(2));
		City newCon = new City(c2.name, c2.lat, c2.lon);
		City newCon2 = new City(c.name, c.lat, c.lon);
		newCon.h = Double.parseDouble(m.group(3));
		newCon2.h = newCon.h;
		newCon2.connections = c.connections;
		newCon.connections = c2.connections;
		newCon.parent = c;
		newCon2.parent = c2;
		c.connections.add(newCon);
		c2.connections.add(newCon2);

	}
	/*This parse generates all the unique city nodes with all their needed information
	 * from the given input file.*/
	public void firstParse(String line) {
		Pattern p = Pattern.compile("(.*)\\s([-]?\\d+[.]\\d+)\\s([-]?\\d+[.]\\d+)");
		Matcher m = p.matcher(line);
		m.find();
		City newCity = new City(m.group(1), Double.parseDouble(m.group(2)), Double.parseDouble(m.group(3)));
		cities.put(m.group(1), newCity);


	}

	@Override
	public void setVerbose(int level) {
		
	}
	/*Essentially the main body of the program. This along with all its helper methods
	 * walks through the tree to find the best path to the destination city from the 
	 * given start city.*/
	public FoundPath getPathInfo(String startCity, String endCity) {
		Path p = new Path();
		City start = cities.get(startCity);
		City end = cities.get(endCity);
		City currentCity = start;
		
		for(int i = 0; i < start.connections.size(); i++) {
			if(end.name.equals(start.connections.get(i).name)) {
							
				break;
			}
		}
		//used.add(currentCity.name);
		while(!currentCity.name.equals(end.name)) {//THINK THE ISSUE IS HERE WITH NAMES REPEATING
			for(City c : currentCity.connections) {
				if(!checkUsed(c)) {
					calculateHeur(c, end);
					p.totalN++;
					p.totalON++;
					frontier.add(c);
				}
			}
			//used.add(currentCity.name);
			currentCity = frontier.remove();
			/*for(String s : used) {
				if(currentCity.name.equals(s)){
					currentCity = frontier.remove();
				}
			}*/
			p.totalON--;
			p.path.add(currentCity.name);
		}
		
		return p;
	}
	/*Part of the optimization of my AStar implementation this checks a list of used cities
	 * to insure there is no backtracking.*/
	private boolean checkUsed(City c) {
		for(int i = 0; i < used.size(); i++) {
			if(c.name.equals(used.get(i))) {
				return true;
			}
		}
		return false;
	}
	/*Simply calculates the heuristic for the AStar search, by adding the actual cost
	 * from one city to another with direct distance from a city to another.*/
	private void calculateHeur(City c, City e) {
		double direct = getDistance(c.name, e.name);
		c.h = c.h + direct;
		
	}

	@Override
	public Optional<Double> getDirectDistance(String startCity, String endCity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCities() {
		
		
		
		return null;
	}

}







/*CREATED BY PROFESSOR*/


package cs452.hw1;
import java.util.*;

	class City {
	String name;
	double lat, lon, h;
	//HashMap<String, City> connections = new HashMap<>();
	List<City> connections;
	City parent = null;


	City(String n, double la, double lo) {
		name = n;
		lat = la;
		lon = lo;
		h = 0;
		connections = new ArrayList<>();

	}
}

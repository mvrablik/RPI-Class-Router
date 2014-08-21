package main;

import org.json.*;

import java.util.ArrayList;
import java.awt.Point;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class Building {
	private Location coordinates;
	
	public static final Location[] BldgCoords = new Location[] {
		new Location(42.729551,-73.678576),
		new Location(42.731017,-73.683374),
		new Location(42.728922,-73.677016),
		new Location(42.729669,-73.679651),
		new Location(42.729281,-73.6822),
		new Location(42.729882,-73.680869),
		new Location(42.728896,-73.680208),
		new Location(42.730768,-73.681657),
		new Location(42.730942,-73.680579),
		new Location(42.73082,-73.67979),
		new Location(42.731541,-73.682827),
		new Location(42.730085,-73.682599),
		new Location(42.729999,-73.681926),
		new Location(42.730689,-73.682462),
		new Location(42.730393,-73.683004),
		new Location(42.729455,-73.67845)
	};
	public static final String[] BldgNames = new String[] {
		"DARRIN",
		"PITTS",
		"ARMORY",
		"JONSSN",
		"VORHES",
		"GREENE",
		"J-ROWL",
		"SAGE",
		"TROY",
		"RCKTTS",
		"WEST",
		"EATON",
		"LALLY",
		"WALKER",
		"CARNEG",
		"LOW"
	};

	
	public Location getLocation() {return coordinates;}
	
	public Building(Location coordinates)
	{
		this.coordinates = coordinates;
	}
	public Building(String name)
	{
		for (int i = 0; i< BldgNames.length; i++)
		{
			if (name.equals(BldgNames[i]))
			{
				this.coordinates = BldgCoords[i];
			}
		}
	}
	public Path route(Building other) throws MalformedURLException, JSONException, IOException, URISyntaxException
	{
		URI uri = new URI("http://maps.googleapis.com/maps/api/directions/json?origin="+coordinates.toString()+"&destination="+other.getLocation().toString()+"&mode=walking");
		JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
		JSONObject root = new JSONObject(tokener);
		JSONArray routes = root.getJSONArray("routes");
		JSONObject route = (JSONObject) routes.get(0);
		JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
		String polyline = overviewPolyline.getString("points");
		ArrayList<Location> path = PolylineDecoder.decodePoly(polyline);
		Point[] points = new Point[path.size()];
		int i = 0;
		for (Location point:path)
		{
			points[i] = (Location.trilaterate(point));
			i++;
		}
		return new Path(points);
	}
}

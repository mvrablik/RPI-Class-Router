package main;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URISyntaxException;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

import org.json.JSONException;

public class Location {
	public static final Location[] GPSCoords = new Location[] {new Location(42.728838,-73.683018),new Location(42.729187,-73.679842),new Location(42.729845,-73.678721)};
	public static final Point[] Points = new Point[] {new Point(506,1673),new Point(823,1557),new Point(919,1435)};
	
	private double latitude;

	private double longitude;
	public static double getScale()
	{
		double pDiff = Points[0].distance(Points[1]);
		double cDiff = GPSCoords[0].distance(GPSCoords[1]);
		return cDiff/pDiff;
	}
	
	public static Point2D.Double subtract(Point p1, Point p2)
	{
		return new Point2D.Double((p1.getX()-p2.getX()),(p1.getY()-p2.getY()));
	}
	
	public static Point2D.Double subtract(Point2D p1, Point2D p2)
	{
		return new Point2D.Double((p1.getX()-p2.getX()),(p1.getY()-p2.getY()));
	}
	
	public static Point2D.Double divide(Point2D.Double p,double denominator)
	{
		return new Point2D.Double(p.getX()/denominator,p.getY()/denominator);
	}
	
	public static Point2D.Double multiply(Point2D.Double p, double factor)
	{
		return new Point2D.Double(p.getX()*factor,p.getY()*factor);
	}
	
	public static double magnitude(Point2D.Double p)
	{
		return Math.sqrt(p.getX()*p.getX()+p.getY()*p.getY());
	}
	
	public static double dot(Point2D.Double p1, Point2D.Double p2)
	{
		return p1.getX()*p2.getX()+p1.getY()*p2.getY();
	}
	
	public static Point trilaterate(Location coord)
	{
		double scale = getScale();
		double coordDist1 = coord.distance(GPSCoords[0]);
		double coordDist2 = coord.distance(GPSCoords[1]);
		double coordDist3 = coord.distance(GPSCoords[2]);
		double d1 = coordDist1/scale;
		double d2 = coordDist2/scale;
		double d3 = coordDist3/scale;
		Point P1 = Points[0];
		Point P2 = Points[1];
		Point P3 = Points[2];
		double i1=P1.getX(),i2=P2.getX(),i3=P3.getX();
        double j1=P1.getY(),j2=P2.getY(),j3=P3.getY();
        double x,y;
        
        x = (((2*j3-2*j2)*((d1*d1-d2*d2)+(i2*i2-i1*i1)+(j2*j2-j1*j1)) - (2*j2-2*j1)*((d2*d2-d3*d3)+(i3*i3-i2*i2)+(j3*j3-j2*j2)))/
                ((2*i2-2*i3)*(2*j2-2*j1)-(2*i1-2*i2)*(2*j3-2*j2)));
        y = ((d1*d1-d2*d2)+(i2*i2-i1*i1)+(j2*j2-j1*j1)+x*(2*i1-2*i2))/(2*j2-2*j1);
		/*Point2D.Double ex = (subtract(P2,P1));
		ex = divide(ex,magnitude(ex));
		double i = dot(ex,subtract(P3,P1));
		Point2D.Double ey = subtract(subtract(P3,P1),multiply(ex,i));
		ey = divide(ey,magnitude(ey));
		double j = dot (ey,subtract(P3,P1));
		double d = magnitude(subtract(P2,P1));
		double x = (dist1*dist1-dist2*dist2+d*d)/(2*d);
		double y = (dist1*dist1-dist3*dist3+i*i+j*j)/(2*j)-i*x/j;*/
		return new Point((int)x,(int)y);
	}
	
	public Location() {
		
	}

	public Location(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
 	}
	
	 /**
	  * @return the latitude
	  */
	public double getLatitude() {
		return latitude;
	}

	 /**
	  * @return the longitude
	  */
	public double getLongitude() {
		return longitude;
	}
	
	public double distance(Location l2)
	{
		double lat1 = latitude, lat2 = l2.getLatitude();
		double lon1 = longitude, lon2 = l2.getLongitude();
		GeodesicData g = Geodesic.WGS84.Inverse(lat1, lon1, lat2, lon2);
		return g.s12;
	}
	 
	public String toString()
	{
		return latitude+","+longitude;
	}
}

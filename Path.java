package main;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Path {
	private Point[] points;
	public Point[] getPoints()
	{
		return points.clone();
	}
	public Path(Point[] newPoints)
	{
		points = newPoints;
	}
	public void paintOn(Graphics2D g)
	{
		for (int i = 0; i<points.length-1; i++)
		{
			Point p = points[i];
			Point p2 = points[i+1];
			g.setStroke(new BasicStroke(10));
			g.drawLine((int)p.getX(), (int)p.getY()-10, (int)p2.getX(), (int)p2.getY()-10);
		}
	}
}

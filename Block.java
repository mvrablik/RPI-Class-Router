package main;

import java.sql.Time;
import java.util.Calendar;

public class Block {
	public Calendar time;
	public Building location;
	public String day;
	
	public Block(String beg_time, String end_time, String n_location, String n_day)
	{
		String[] parts = beg_time.split(":");
		int hour = Integer.parseInt(parts[0]);
		int minute = Integer.parseInt(parts[1]);
		time = Calendar.getInstance();
		time.set(Calendar.MINUTE,minute);
		time.set(Calendar.HOUR, hour);
		location = new Building(n_location);
		day = n_day;
	}
	public Calendar getStartTime()
	{
		return time;
	}
	public Building getBuilding()
	{
		return location;
	}

}

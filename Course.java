package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

	public class Course {
		ArrayList<Block> timeblocks;
		final String [] DAYS = {"", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
		int crn;
		public Course (int newcrn) throws Exception{
			timeblocks = new ArrayList<Block>();
			crn = newcrn;
			String url = "http://yacs.me/api/4/sections/?crn=" + crn;
			URL oracle = new URL(url);  
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			   	String inputLine;
			    	JSONObject course = new JSONObject(new JSONTokener(yc.getInputStream()));
			    	JSONArray block = (JSONArray)(course.get("result"));
			    	JSONObject other = block.getJSONObject(0);
			    	JSONArray sections = (JSONArray)(other.get("section_times"));
				    for(int i = 0; i < sections.length(); i++)
				    {
				    	JSONObject sectionTime = sections.getJSONObject(i);
					   	String startTime = (String)(sectionTime.get("start"));
					   	String endTime = (String)(sectionTime.get("end"));
					   	String location = (String)(sectionTime.get("location"));
					   	String[] parts = location.split(" ");
					   	String part1 = parts[0];
					   	String part2 = parts[1];
					   	JSONArray days = (JSONArray)(sectionTime.get("days_of_the_week"));
					   	for (int k = 0; k < days.length(); k++)
					   	{
					   		days.get(k);
					   		timeblocks.add(new Block(startTime, endTime, part1, days.getString(k)));
					    }
				    }
			    	in.close();
			}
		public ArrayList<Block> getBlocksOnDay(int day)
		{
			ArrayList<Block> onDay = new ArrayList<Block>();
			for (int j = 0; j < timeblocks.size(); j++)
			{
				if (timeblocks.get(j).day.equalsIgnoreCase(DAYS[day]))
				onDay.add(timeblocks.get(j));
			}
			return onDay;
		
		}
		
		public static boolean isValidCRN(int crn)
		{
			try {
				Course c = new Course(crn);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

}

package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONException;

public class Schedule {
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	private ArrayList<Course> courses;
	public Schedule(ArrayList<Integer> crns)
	{
		courses = new ArrayList<Course>();
		for (int crn:crns)
		{
			if (Course.isValidCRN(crn))
			{
				try {
					courses.add(new Course(crn));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public ArrayList<Block> getBlocks(int day)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		for (Course course:courses)
		{
			for (Block block:course.getBlocksOnDay(day))
			{
				blocks.add(block);
			}
		}
		Collections.sort(blocks,new Comparator<Block>() {
	        @Override
	        public int compare(Block b1, Block b2)
	        {
	    		return b1.getStartTime().compareTo(b2.getStartTime());
	        }
	    });
		return blocks;
	}
	
	public ArrayList<Path> getPaths(int day)
	{
		ArrayList<Block> blocks = getBlocks(day);
		ArrayList<Path> paths = new ArrayList<Path>();
		for (int i = 0; i<blocks.size()-1; i++)
		{
			try {
				paths.add(blocks.get(i).getBuilding().route(blocks.get(i+1).getBuilding()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return paths;
	}
}

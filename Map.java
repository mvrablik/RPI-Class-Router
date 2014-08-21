package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel
{
	private String fileName = "RPI_Map.jpg";
	
	private int picWidth = 400;
    private int picHeight = 400;
    private Color pathColor = Color.red;
    
    private BufferedImage buffImg;
    private Graphics2D buffG2D;
    
    private int panelWidth;
    private int panelHeight;
    private double scale;
    private int drawWidth;
    private int drawHeight;
    private int edgeWidth;
    private int edgeHeight;
    
	public Map()
	{
		init(new ArrayList<main.Path>());
	}
	
	public Map(ArrayList<main.Path> pathList)
	{
		init(pathList);
	}

	private void init(ArrayList<main.Path> pathList)
	{
		try {
			buffImg = ImageIO.read(getClass().getClassLoader().getResource(fileName));
            picWidth = buffImg.getWidth();
            picHeight = buffImg.getHeight();
            buffG2D = (Graphics2D)buffImg.getGraphics();
            
            buffG2D.setColor(pathColor);
            for (int i = 0; i < pathList.size(); i ++)
            {
            	pathList.get(i).paintOn(buffG2D);
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	@Override
    protected void paintComponent(Graphics g)
    {
		if (buffImg == null)
		{
			super.paintComponent(g);
			return;
		}
		
        panelWidth = this.getParent().getWidth();
        panelHeight = this.getParent().getHeight();
        scale = Math.min((double)panelWidth / picWidth, (double)panelHeight / picHeight);
        drawWidth = (int)Math.round(picWidth * scale);
        drawHeight = (int)Math.round(picHeight * scale);
        edgeWidth = (panelWidth - drawWidth) / 2;
        edgeHeight = (panelHeight - drawHeight) / 2;
        
        setSize(panelWidth, panelHeight);
        
        super.paintComponent(g);

        g.drawImage(buffImg, edgeWidth, edgeHeight, drawWidth, drawHeight, null);
    }
}

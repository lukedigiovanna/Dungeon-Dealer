package mapping.gridComponents;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import java.util.*;

public abstract class GridComponent {
	private ArrayList<GridComponentExtra> extras = new ArrayList<GridComponentExtra>();
	protected double lightValue = .2;
	//superclass for gridcomponents that may be a path, wall, void etc.

	public GridComponent() {
		
	}
	
	public void setLightValue(double v) {
		this.lightValue = v;
	}
	
	public Image getImage() {
		return display.ImageProcessor.darken(getRawImage(),1-lightValue);
	}
	
	public abstract BufferedImage getRawImage();
	
	public void paintExtras(Graphics g) {
		for (GridComponentExtra e : extras) {
			g.drawImage(e.getImage(), (int)(e.getX()*32), (int)(e.getY()*32), (int)(e.getWidth()*32), (int)(e.getHeight()*32), null);
		}
	}
	
	public void addExtra(GridComponentExtra e) {
		extras.add(e);
	}
	
	public static GridComponent parseChar(char a) {
		switch (a) {
		case '0': case ' ':
			return new Void();
		case 'w':
			return new CobblestoneWall();
		case 'p':
			return new Path();
		case 'l':
			return new PlanksPath();
		}
		return null;
	}
	
	public ArrayList<GridComponentExtra> getExtras() {
		return extras;
	}
}

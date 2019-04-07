package mapping.gridComponents;

import java.awt.Image;

public abstract class GridComponentExtra {
	private Image img;
	private double x = 0, y = 0, width = 1, height = 1;
	
	public GridComponentExtra() {
		setImage();
	}
	
	public GridComponentExtra(double x, double y, double width, double height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		setImage();
	}
	
	public abstract void setImage();
	
	public void setImage(Image img) {
		this.img = img;
	}
	
	public Image getImage() {
		return img;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}

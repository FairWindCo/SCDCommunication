package ua.pp.fairwind.javafx.guiElements.menu;

public class PrefferedSize {
	double x=0;
	double y=0;
	double height;
	double width;
	boolean center=true;
	public PrefferedSize(double x, double y, double height, double width) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		center=false;
	}
	public PrefferedSize(double height, double width) {
		super();
		this.height = height;
		this.width = width;
		
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		center=false;
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		center=false;
		this.y = y;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public boolean isCenter() {
		return center;
	}
	public void setCenter(boolean center) {
		this.center = center;
	}
	
	
	
	
}

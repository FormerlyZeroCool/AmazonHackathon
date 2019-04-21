package com.andrewrs.calendar;

import java.awt.Color;
import java.awt.Graphics;

public abstract class CalendarPart {
	protected int x,y,width,height;
	protected final Color white=new Color(255,255,255),black=new Color(0,0,0);
	

	public CalendarPart(int x, int y,int width,int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public void setDim(int x,int y, int width,int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}

	public void draw(Graphics g) {}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

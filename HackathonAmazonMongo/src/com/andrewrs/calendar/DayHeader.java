package com.andrewrs.calendar;

import java.awt.Color;
import java.awt.Graphics;

public class DayHeader extends CalendarPart
{

	private int dayOfWeek;
	public DayHeader(int x,int y,int width, int height,int dayOfWeek)
	{
		super(x,y,width,height);
		this.dayOfWeek=dayOfWeek;
	}
	@Override
	public void draw(Graphics g)
	{
		
		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		int j=2;
		for(int i=2;i<10;i+=j)
		{
			g.drawLine(x, y+j, x+width-j, y+j);
			g.drawLine(x, y+height-j, x+width-j, y+height-j);
			//g.drawLine(x+width-j, y, x+width-j, y+height-j);
			j++;
		}
		int sy=y+20;
		int sx=x+5;
		switch(dayOfWeek)
		{
		case 0:
			g.drawString("Sunday",sx, sy);
			break;
		case 1:
			g.drawString("Monday",sx, sy);
			break;
		case 2:
			g.drawString("Tuesday",sx, sy);
			break;
		case 3:
			g.drawString("Wednesday",sx, sy);
			break;
		case 4:
			g.drawString("Thursday",sx, sy);
			break;
		case 5:
			g.drawString("Friday",sx, sy);
			break;
		case 6:
			g.drawString("Saturday",sx, sy);
			break;
		}
		
	}
	
}

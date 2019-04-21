package com.andrewrs.user;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DayOfWeekSelector extends Canvas
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<DayOfWeek> days;
	public DayOfWeekSelector(int x,int y,int width, int height)
	{
		days=new ArrayList<DayOfWeek>();
		this.setBounds(x, y, width, height);
		{//rolled out for loop for window builder parsing
			int i=0;
			int dWidth=width/7;
			y=0;
			
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
			i++;
			days.add(new DayOfWeek(x+i*dWidth,y,dWidth,height,i));
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				for(int i=0;i<days.size();i++)
				{
					days.get(i).onClick(x, y);
				}
			}});
	}
	@Override
	public void paint(Graphics g)
	{
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		for(DayOfWeek d:days)
		{
			d.draw(g);
		}
	}
	public void clear()
	{
		for(DayOfWeek d:days)
		{
			d.setSelected(false);
		}
	}
	public String getDayName(int i)
	{
		return days.get(i).getDayName();
	}
	public boolean isSelectedByIndex(int i)
	{
		return days.get(i).isSelected();
	}
}

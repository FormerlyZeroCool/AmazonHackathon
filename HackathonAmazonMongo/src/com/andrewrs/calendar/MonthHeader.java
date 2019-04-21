package com.andrewrs.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

public class MonthHeader extends CalendarPart
{

	private LocalDate date=LocalDate.now();
	public final ChangeMonthButton right,left;
	private Calendar cal;
	public MonthHeader(int x,int y,int width, int height,Calendar parentCal)
	{
		super(x,y,width,height);
		right=new ChangeMonthButton(this,true);
		left= new ChangeMonthButton(this,false);
		cal=parentCal;
	}
	public void refreshAppts()
	{
		cal.clearAppts();
	}
	public void setDate(LocalDate newDate)
	{
		date=newDate;
	}
	public void setDate(String yyyymm)
	{
		DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		date=LocalDate.parse(yyyymm+"-01",format);
	}
	public LocalDate getDate()
	{
		return date;
	}
	public String getMonthString()
	{
		return date.getMonth().toString();
	}
	public void changeMonth(int deltaMonth)
	{
		if(deltaMonth<0)
			date=date.minusMonths(-deltaMonth);
		else
			date=date.plusMonths(deltaMonth);
	}
	@Override
	public void draw(Graphics g)
	{

		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawString(
				date.getMonth().toString().substring(0,1)+
				date.getMonth().toString().substring(
						1, date.getMonth().toString().length()).toLowerCase(),
						x+(width/2)-date.getMonth().toString().length()*3,
						y+height/2-3);
		String subtext=date.getDayOfWeek().toString().substring(0, 1)
				+date.getDayOfWeek().toString().substring(1,date.getDayOfWeek().toString().length()).toLowerCase();
		subtext=subtext+" "+date.getDayOfMonth();
		g.drawString(
				subtext,
				x+(width/2)-subtext.length()*4,
				y+height/2+12);
		g.drawRect(x, y, width, height);
		right.setDim();
		right.draw(g);
		left.setDim();
		left.draw(g);
		
	}
}

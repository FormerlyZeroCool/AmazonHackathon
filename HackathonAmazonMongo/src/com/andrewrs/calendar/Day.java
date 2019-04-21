package com.andrewrs.calendar;
import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDate;
import java.util.ArrayList;
public class Day extends CalendarPart{
	public LocalDate date;
	private boolean isToday=false;
	private 
	int bigDayX=(int)(width*2.5),
	bigDayTextX=bigDayX+10,
	bigDayWidth=width*2;
	public boolean getIsToday()
	{
		return isToday;
	}
	public int getBigDayX() {
		return bigDayX;
	}
	public void setBigDayX(int bigDayX) {
		this.bigDayX = bigDayX;
	}
	public int getBigDayTextX() {
		return bigDayTextX;
	}
	public void setBigDayTextX(int bigDayTextX) {
		this.bigDayTextX = bigDayTextX;
	}
	public int getBigDayWidth() {
		return bigDayWidth;
	}
	public void setBigDayWidth(int bigDayWidth) {
		this.bigDayWidth = bigDayWidth;
	}
	private boolean isBig=false;
	public ArrayList<Appointment> apptsLocal;
	private static final Color BLACK=new Color(0,0,0),WHITE=new Color(255,255,255);
	public Day(int x,int y,int width,int height,LocalDate date)
	{

		super(x,y,width,height);
		this.date=date;
		this.apptsLocal=new ArrayList<Appointment>();
		if(this.date.equals(LocalDate.now()))
		{
			isToday=true;
		}
		else
		{
			isToday=false;
		}
	}
	public void addAppt(Appointment a)
	{
		boolean exists=false;
		for(int i=0;i<apptsLocal.size();i++)
		{
			if(apptsLocal.get(i).compare(a))
				exists=true;
		}
		//System.out.println(exists);
		if(!exists)
		{
			a.setAssociatedDay(this);
			a.rePosition();
			apptsLocal.add(a);
		}
	}
	public void clearAllAppt()
	{
		apptsLocal.clear();
	}
	public void setDate(LocalDate d)
	{
		date=d;
	}
	public void draw(Graphics g)
	{
		
		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		g.drawRect(x+2, y+2, width-4, height-4);
		g.drawString(date.getDayOfMonth()+"", x+5, y+15);

		if(this.date.equals(LocalDate.now()))
		{
			isToday=true;
			g.setColor(black);
			g.drawRect(x+1, y+1, width-2, height-2);
		}
		else
		{
			isToday=false;
		}
		for(int i=0;i<apptsLocal.size();i++)
		{
			apptsLocal.get(i).draw(g);
			//if(apptsLocal.get(i).getDesc().length()<lineLen)
			//g.drawString(apptsLocal.get(i).getDesc(), x+4, y+30+(i*20));
			//else
			//	g.drawString(apptsLocal.get(i).getDesc().substring(0,lineLen), x+4, y+30+(i*20));
		}
		if(isBig)
		{
			bigDayX=(int)(width*2.5);
			bigDayTextX=bigDayX+5;
			bigDayWidth=width*2;
			g.setColor(white);
			g.fillRect(bigDayX, 100, bigDayWidth, 300);
			g.setColor(black);
			g.drawRect(bigDayX,100,bigDayWidth,30);
			g.drawRect(bigDayX,100,bigDayWidth,300);
			String dayOfWeek=date.getDayOfWeek().toString().substring(0, 1)+
					date.getDayOfWeek().toString().substring(1, date.getDayOfWeek().toString().length()).toLowerCase();
			g.drawString(date.getDayOfMonth()+" "+dayOfWeek, bigDayTextX, 115);
			
			for(int i=0;i<apptsLocal.size();i++)
			{
				apptsLocal.get(i).drawBig(g);

			}
		}

	}
	public int getApptIndex(Appointment a)
	{
		return apptsLocal.indexOf(a);
	}
	public void rePositionAllText()
	{
		this.width=this.getWidth();
		for(int i=0;i<apptsLocal.size();i++)
		{
			apptsLocal.get(i).rePosition();
		}
	}
	public int getDayInt()
	{
		return date.getDayOfMonth();
	}
	public int getNextApptLinePos(int upToIndex)
	{
		int sum=0;
		for(int i=0;i<upToIndex;i++)
		{
			sum+=apptsLocal.get(i).getHeight();
		}
		return sum;
	}

	public int getNextApptLinePos(Appointment upTo)
	{
		int sum=0;
		int upToIndex=apptsLocal.indexOf(upTo);
		for(int i=0;i<upToIndex;i++)
		{
			sum+=apptsLocal.get(i).getBigHeight();
		}
		return sum+20*upToIndex;
	}
	public int getLastApptY()
	{
		return y+apptsLocal.size()*Appointment.ROWSPACE+height/10;
	}
	public void setBig(boolean isBig)
	{
		this.isBig=isBig;
	}
	public boolean isBig()
	{
		return isBig;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
}

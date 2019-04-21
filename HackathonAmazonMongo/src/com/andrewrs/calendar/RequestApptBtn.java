package com.andrewrs.calendar;

import java.awt.Graphics;
public class RequestApptBtn extends CalendarPart{

	private boolean addApptReq=false;
	public RequestApptBtn(int x, int y)
	{
		super(x,y,125,30);
	}
	@Override
	public void draw(Graphics g)
	{
		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		g.drawRect(x+2, y+2, width-4, height-4);
		g.drawString("Request an",x+10, y+14);
		g.drawString("Appointment",x+10, y+25);
	}
	public boolean isAddApptReq() 
	{
		return addApptReq;
	}

	public void setAddApptReq(boolean addApptReq) 
	{
		this.addApptReq = addApptReq;
	}
	
	public void onClick(int x, int y) 
	{
		
		if(this.x<=x && this.y<=y && this.x+this.width>=x && this.y+this.height>=y)
		{
			System.out.println(addApptReq);
			addApptReq=!addApptReq;
		}
	}
}


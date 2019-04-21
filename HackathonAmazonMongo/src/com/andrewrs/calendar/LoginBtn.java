package com.andrewrs.calendar;

import java.awt.Graphics;

public class LoginBtn extends CalendarPart
{
	private boolean loginNow=false;
	private String text;
	public LoginBtn(int x, int y,String text)
	{
		super(x,y,125,30);
		this.text=text;
	}
	@Override
	public void draw(Graphics g)
	{
		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		g.drawRect(x+2, y+2, width-4, height-4);
		g.drawString(text,x+10, y+20);
	}
	public boolean isLoginNow() 
	{
		return loginNow;
	}

	public void setLoginNow(boolean addAppt) 
	{
		this.loginNow = addAppt;
	}
	
	public void onClick(int x, int y) 
	{
		
		if(this.x<=x && this.y<=y && this.x+this.width>=x && this.y+this.height>=y)
		{
			System.out.println(loginNow);
			loginNow=!loginNow;
		}
	}
}

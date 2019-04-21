package com.andrewrs.calendar;

import java.awt.Graphics;

public class ChangeMonthButton extends CalendarPart
{

	private boolean isRight;
	private MonthHeader parentComp;

	public ChangeMonthButton(MonthHeader parentComp,boolean isRight)
	{
		super(2+parentComp.getX()+parentComp.getWidth(),parentComp.getY(),parentComp.getWidth()/4,parentComp.getHeight());
		if(!isRight)
		{
			x=parentComp.getX()-(parentComp.getWidth()/4);
			y=parentComp.getY();
			width=parentComp.getWidth()/4;
			height=parentComp.getHeight();
		}
		this.isRight=isRight;
		this.parentComp=parentComp;
	}
	public void setDim()
	{
		
		if(!isRight)
		{
			x=parentComp.getX()-(parentComp.getWidth()/4);
			y=parentComp.getY();
			width=parentComp.getWidth()/4;
			height=parentComp.getHeight();
		}
		else
		{
			x=2+parentComp.getX()+parentComp.getWidth();
			y=parentComp.getY();
			width=parentComp.getWidth()/4;
			height=parentComp.getHeight();
		}
	}
	public boolean isRight() 
	{
		return isRight;
	}

	public void onClick(int x,int y)
	{
		if(this.x<=x && this.y<=y && this.x+this.width>=x && this.y+this.height>=y)
		{
			if(isRight)
			{
				parentComp.changeMonth(1);
			}
			else
			{
				parentComp.changeMonth(-1);
			}
			parentComp.refreshAppts();
		}
		
	}
	public void setRight(boolean isRight) 
	{
		this.isRight = isRight;
	}
	@Override
	public void draw(Graphics g)
	{
		g.setColor(white);
		g.fillRect(x, y, width, height);
		g.setColor(black);
		g.drawRect(x, y, width, height);
		if(isRight)
		{
			int xPos[][]= {{x+width/10,x+width-(width/10),x+width/10},{x+width/5,x+width-(width/4),x+width/5}};
			int yPos[][]= {{y+height/10,y+height/2,y+height-height/10},{y+height/4,y+height/2,y+height-height/4}};
			g.drawPolygon(xPos[0],yPos[0],3);
			g.drawPolygon(xPos[1],yPos[1],3);
			
		}
		else
		{

			int xPos[][]= {{x+width-width/10,x+(width/10),x+width-width/10},
					{x+width-width/5,x+(width/4),x+width-width/5}};
			int yPos[][]= {{y+height/10,y+height/2,y+height-height/10},
					{y+height/4,y+height/2,y+height-height/4}};
			g.drawPolygon(xPos[0],yPos[0],3);
			g.drawPolygon(xPos[1],yPos[1],3);
		}
	}
}

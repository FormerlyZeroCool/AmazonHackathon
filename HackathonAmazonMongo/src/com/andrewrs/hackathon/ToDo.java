package com.andrewrs.hackathon;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class ToDo extends Canvas{

	private static final long serialVersionUID = 1L;
	private ArrayList<String> items;
	private Color black=new Color(0,0,0),white=new Color(255,255,255);
	public ToDo(int x,int y,int width,int height)
	{
		this.setBounds(x, y, width, height);
		items=new ArrayList<String>();
		refreshList();
	}
	private int wrapCharCount=27;
	@Override
	public void paint(Graphics g)
	{//27 is the longest before line must wrap
		g.setColor(white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(black);
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		g.drawRect(2,2, this.getWidth()-5, this.getHeight()-5);
		g.drawRect(0, 0, this.getWidth()-1, 25);
		g.drawString("To Do List", this.getWidth()/2-25, 15);
		int cellHeight=this.getHeight()/(items.size()+1);
		for(int i=0;i<items.size();i++)
		{
			if(items.get(i).length()<wrapCharCount)
			{

				g.drawString(items.get(i), 5, 40+i*(cellHeight));
				g.drawLine(2, 50+i*(cellHeight),getWidth()-4, 50+i*(cellHeight));
			}
			else
			{
				if(items.get(i).length()<wrapCharCount)
				{

					g.drawLine(2, 50+i*(cellHeight),getWidth()-4, 50+i*(cellHeight));
					g.drawString(items.get(i), 5, 40+i*(cellHeight));
				}
				else 
				{
					String listItem[]=items.get(i).split("(?<=\\G.{26})");
					int x=0;
					while(x<listItem.length)
					{
						g.drawString(listItem[x], 5,x*15+ 40+i*(cellHeight));
						x++;
					}
					g.drawLine(2,x*15+ 50+i*(cellHeight),getWidth()-4,x*15+ 50+i*(cellHeight));
				}
				
			}
		}
	}
	public void refreshList()
	{

	}
}

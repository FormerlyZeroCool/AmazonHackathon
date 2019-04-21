package com.andrewrs.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

import com.andrewrs.ws.StringKeyPairs;

public class Appointment extends CalendarPart {

	private String desc;
	private LocalDate startDate,endDate;
	private LocalTime startTime,endTime;
	private Day associatedDay;
	private String classCode;
	private String tutorEmail;
	public static int ROWSPACE=17;
	public Appointment(int x,int y,String desc,LocalDate startDate,LocalDate endDate, Day associatedDay)
	{
		super(0,0,0,0);
		this.desc=desc;
		this.startDate=startDate;
		this.endDate=endDate;
	}
	public Appointment(int x,int y, String desc,LocalDate startDate, Day associatedDay)
	{
		super(x,y,associatedDay.getWidth()-x,desc.length()/((associatedDay.getWidth()*2)/10));
		this.desc=desc;
		this.startDate=startDate;
		this.associatedDay = associatedDay;
	}
	public Appointment(int x,int y,String classCode, String tutorEmail,LocalDate date,LocalTime startTime,LocalTime endTime)
	{
		super(x,y,x,y);
		this.desc=classCode +" "+ tutorEmail.split(Pattern.quote("."))[0];
		this.tutorEmail=tutorEmail;
		this.classCode=classCode;
		this.startDate=date;
		this.endDate=endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		
		
	}
	@Override
	public String toString()
	{
		return "classCode:"+classCode+" startDate:"+startDateToString()+
				" startTime:"+startTimeToString()+" endTime:"+endTimeToString()+
				" tutorName:"+getTutorEmail();
	}
	public void setAssociatedDay(Day d)
	{
		this.associatedDay=d;
	}
	public boolean compare(Appointment comp)
	{
		boolean equal=true;
		if(!comp.startDate.equals(startDate))
		{
			equal=false;
		}
		if(!comp.startTime.equals(startTime))
		{
			equal=false;
		}
		if(!comp.endTime.equals(endTime))
		{
			equal=false;
		}
		if(!comp.classCode.equals(classCode))
		{
			equal=false;
		}
		if(!comp.tutorEmail.equals(tutorEmail))
		{
			equal=false;
		}
		return equal;
	}
	public void rePosition()
	{
		this.x=associatedDay.getX()+5;
		this.y=associatedDay.getY()+(ROWSPACE+2)*associatedDay.getApptIndex(this)+27;
	}
	public int getHeight(int rowNum)
	{
		//int bigDayX=(int)(associatedDay.getWidth()*2.5);
		//int bigDayTextX=bigDayX+10;

		int bigDayWidth=associatedDay.getWidth()*2;
		int lineLen=bigDayWidth/10;
		String desc[]=getDesc().toString().split("(?<=\\G.{"+lineLen+"})");
		if(desc.length>=rowNum)
			return desc.length*ROWSPACE;
		else
			return -1;
			
	}
	public int getBigHeight()
	{
		//int bigDayX=(int)(associatedDay.getWidth()*2.5);
		//int bigDayTextX=bigDayX+10;

		int bigDayWidth=associatedDay.getWidth()*2;
		int lineLen=bigDayWidth/10;
		String desc[]=getDesc().toString().split("(?<=\\G.{"+lineLen+"})");
		
		return desc.length*20;
			
	}
	@Override
	public void draw(Graphics g)
	{
		g.setColor(super.black);
		if(y<associatedDay.getY()+associatedDay.getHeight()-5)
		{
			int lineLen=(int)(associatedDay.getWidth()/9);
			if(getDesc().length()<lineLen)
				g.drawString(getDesc(), x, y);
			else
				g.drawString(getDesc().substring(0,lineLen), x, y);
			
		}
		
	}
	public void drawBig(Graphics g)
	{

		g.setColor(super.black);
		int bigDayX=(int)(associatedDay.getWidth()*2.5)+10;
		int bigDayWidth=associatedDay.getWidth()*2;
		int lineLen=(int)(bigDayWidth/9);
		String descArr[]=desc.toString().split("(?<=\\G.{"+lineLen+"})");
		int j=0;
		g.drawString(getFormattedTimeRange(), bigDayX, 165+associatedDay.getNextApptLinePos(this)-20);
		while(j<descArr.length)
		{
			g.drawString(descArr[j], bigDayX,j*15+ 165+associatedDay.getNextApptLinePos(this));
			//g.drawString(appts.get(i).getDesc(), 160, x*15+165+i*20);
			j++;
		}
		g.setColor(new Color(140,140,140));
		g.drawRect(bigDayX+bigDayWidth/20, (descArr.length-1)*14+ 171+associatedDay.getNextApptLinePos(this), bigDayWidth-bigDayWidth/5, 2);
		
	}
	public String getFormattedTimeRange()
	{
		StringBuilder times=new StringBuilder();
		if(startTime.getHour()>12)
			times.append(startTime.getHour()-12);
		else
			times.append(startTime.getHour());
		times.append(":");
		if(endTime.getMinute()<10)
			times.append("0");
		times.append(endTime.getMinute());
		if(startTime.getHour()>11)
			times.append("p");
		else
			times.append("a");
		times.append("-");
		if(endTime.getHour()>12)
			times.append(endTime.getHour()-12);
		else
			times.append(endTime.getHour());
		times.append(":");
		if(endTime.getMinute()<10)
			times.append("0");
		times.append(endTime.getMinute());
		if(endTime.getHour()>11)
			times.append("p");
		else
			times.append("a");
		return times.toString();
	}
	@Override
	public int getHeight()
	{
		return ROWSPACE;
	}
	public LocalDate getStartDate()
	{
		return startDate;
	}
	public String startDateToString()
	{
		return startDate.getYear()+"-"+
				(startDate.getMonth().getValue()<10?"0":"")+startDate.getMonth().getValue()+"-"+
				((startDate.getDayOfMonth()<10?"0":"")+startDate.getDayOfMonth());
	}
	public String getDesc()
	{
		return getFormattedTimeRange().split("-")[0]+" "+desc;
	}
	public void setStartDate(LocalDate d)
	{
		startDate=d;
	}
	public String getClassCode() 
	{
		return classCode;
	}
	public void setClassCode(String classCode) 
	{
		this.classCode = classCode;
	}
	public LocalTime getStartTime() 
	{
		return startTime;
	}
	public String startTimeToString()
	{
		return startTime.getHour()+":"+startTime.getMinute();
	}
	public void setStartTime(LocalTime startTime) 
	{
		this.startTime = startTime;
	}
	public LocalTime getEndTime() 
	{
		return endTime;
	}
	public void setEndTime(LocalTime endTime) 
	{
		this.endTime = endTime;
	}
	public String endTimeToString()
	{
		return endTime.getHour()+":"+endTime.getMinute();
	}
	public String getTutorEmail() 
	{
		return tutorEmail;
	}
	public String getTutorName() 
	{
		return tutorEmail.split("W+[.]")[0];
	}
	public void getTutorEmail(String tutorEmail) 
	{
		this.tutorEmail = tutorEmail;
	}
}

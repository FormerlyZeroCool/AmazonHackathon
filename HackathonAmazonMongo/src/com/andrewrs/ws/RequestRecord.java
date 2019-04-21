package com.andrewrs.ws;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RequestRecord 
{
	private String classCode;
	private String studentEmail;
	private LocalTime startTime;
	private LocalTime endTime;
	public ArrayList<String> weekDays=new ArrayList<String>();

	public RequestRecord(String classCode, String studentEmail,LocalTime startTime,LocalTime endTime)
	{
		this.studentEmail=studentEmail;
		this.classCode=classCode;
		this.startTime=startTime;
		this.endTime=endTime;
		
	}
	@Override
	public String toString()
	{
		StringBuilder days=new StringBuilder();
		days.append("[");
		for(String day:weekDays)
		{
			days.append(day);
			days.append(",");
		}
		days.append("]");
		return "classCode:"+classCode+
				" startTime:"+startTimeToString()+" endTime:"+endTimeToString()+
				" tutorName:"+studentEmail+" days:"+days;
	}
	public void addWeekDay(String day)
	{
		weekDays.add(day);
	}
	public String startTimeToString()
	{
		String min;
		if(startTime.getMinute()<10)
			min="0"+startTime.getMinute();
		else
			min=startTime.getMinute()+"";
		return startTime.getHour()+":"+min;
	}

	public String endTimeToString()
	{
		String min;
		if(endTime.getMinute()<10)
			min="0"+endTime.getMinute();
		else
			min=endTime.getMinute()+"";
		return endTime.getHour()+":"+min;
	}

	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public String weekDaysToString() {
		StringBuilder days=new StringBuilder();
		days.append("[");
		for(String day:weekDays)
		{
			days.append(day);
			days.append(",");
		}
		days.append("]");
		return days.toString();
	}
}

package com.andrewrs.ws;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.andrewrs.calendar.Appointment;

public class ApptRecord 
{
	private LocalDate startDate,endDate;
	private LocalTime startTime,endTime;
	private String classCode;
	private String tutorEmail;
	private String id;
	public ArrayList<String> weekDays=new ArrayList<String>();
	public ApptRecord(String id,String classCode, String tutorEmail,LocalDate startDate, LocalDate endDate,LocalTime startTime,LocalTime endTime)
	{
		this.setId(id);
		this.tutorEmail=tutorEmail;
		this.classCode=classCode;
		this.startDate=startDate;
		this.endDate=endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		
	}
	public ApptRecord(String classCode, String tutorEmail,LocalDate startDate, LocalDate endDate,LocalTime startTime,LocalTime endTime)
	{
		this.tutorEmail=tutorEmail;
		this.classCode=classCode;
		this.startDate=startDate;
		this.endDate=endDate;
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
		return "classCode:"+classCode+" startDate:"+startDateToString()+" endDate:"+endDateToString()+
				" startTime:"+startTimeToString()+" endTime:"+endTimeToString()+
				" tutorName:"+getTutorEmail()+" Days:"+days.toString();
	}
	public void printAllAppts()
	{
		ArrayList<Appointment> appts=getAppts();
		for(Appointment a:appts)
			System.out.println(a.toString());
	}
	public ArrayList<Appointment> getAppts()
	{
		startDate=startDate.minusDays(startDate.getDayOfWeek().getValue()-1);
		for(int i=0;i<weekDays.size();i++)
		{
			weekDays.set(i, weekDays.get(i).toLowerCase());
		}
		ArrayList<Appointment> appts=new ArrayList<Appointment>();
		for(int i=0;i<=startDate.until(endDate, ChronoUnit.WEEKS);i++)
		{
			if(weekDays.contains("monday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(i*7),startTime,endTime));
			}
			if(weekDays.contains("tuesday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(1+i*7),startTime,endTime));
			}
			if(weekDays.contains("wednesday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(2+i*7),startTime,endTime));
			}
			if(weekDays.contains("thursday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(3+i*7),startTime,endTime));
			}
			if(weekDays.contains("friday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(4+i*7),startTime,endTime));
			}
			if(weekDays.contains("saturday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(5+i*7),startTime,endTime));
			}
			if(weekDays.contains("sunday"))
			{
				appts.add(new Appointment(0,0,classCode,tutorEmail,startDate.plusDays(6+i*7),startTime,endTime));
			}
		}
		
		return appts;
	}
	public String getTutorEmail() {
		return tutorEmail;
	}
	public void setTutorEmail(String tutorEmail) {
		this.tutorEmail = tutorEmail;
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
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String startDateToString()
	{
		return startDate.getYear()+"-"+
				(startDate.getMonth().getValue()<10?"0":"")+startDate.getMonth().getValue()+"-"+
				((startDate.getDayOfMonth()<10?"0":"")+startDate.getDayOfMonth());
	}

	public String endDateToString()
	{
		return endDate.getYear()+"-"+
				(endDate.getMonth().getValue()<10?"0":"")+endDate.getMonth().getValue()+"-"+
				((endDate.getDayOfMonth()<10?"0":"")+endDate.getDayOfMonth());
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

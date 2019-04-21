package com.andrewrs.calendar;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import com.andrewrs.hackathon.HackathonMain;
import com.andrewrs.ws.ApptRecord;
import com.andrewrs.ws.ApptService;

public class Calendar extends Canvas{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public ArrayList<Day> days=new ArrayList<Day>();
	public ArrayList<CalendarPart> components=new ArrayList<CalendarPart>();
	private ArrayList<DayHeader> dowHeader=new ArrayList<DayHeader>();
	private Day bigDay;
	private Day today;
	private MonthHeader header;
	private LoginBtn loginBtn;
	private RequestApptBtn requestApptBtn;
	public Calendar(int x,int y,int width,int height)
	{
		
		this.setBounds(x, y, width, height);
		header=new MonthHeader((int)(width/2.5),0,width/5,30,this);
		dowHeader.clear();
		for(int i=0;i<7;i++)
		{
			dowHeader.add(new DayHeader(i*(width/7),30,width/7,30,i));
			components.add(dowHeader.get(i));
		}
		LocalDate date=header.getDate().withDayOfMonth(1);
		int dow=date.getDayOfWeek().getValue();
		LocalDate fDom=date.minusDays((long)(dow));
		this.width=this.getWidth();
		for(int j=0;j<5;j++)
		{
			for(int k=0;k<7;k++)
			{
				
				days.add(new Day(
						k*(this.getWidth()/7),
						j*((this.getHeight()-offset)/5)+offset,
						this.getWidth()/7,
						(this.getHeight()-offset)/5,
						fDom)
						);
				components.add(days.get(k+j*7));
				fDom=fDom.minusDays(-1);
			}
		}
		components.add(header);
		loginBtn=new LoginBtn(0,0,"Login");
		
		components.add(loginBtn);
		requestApptBtn=new RequestApptBtn(this.width-130,0);
		components.add(requestApptBtn);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				requestApptBtn.onClick(x, y);
				changeMonthClick(x,y);
				loginBtn.onClick(x,y);
				if(getBigDay()!=null)
				{
					getBigDay().setBig(false);
					setBigDay(null);
				}
				else
				{
					boolean found=false;
					for(int i=0;i<days.size() && !found;i++)
					{
						if(days.get(i).getX()<=x && days.get(i).getY()<=y && days.get(i).getHeight()+days.get(i).getY()>=y && days.get(i).getWidth()+days.get(i).getX()>=x )
						{
							found=true;
							setBigDay(getDays().get(i));
							getBigDay().setBig(true);
						}
					}
					
				}

			}
			
		});
		resizeDays();
	}
	public boolean createAppt()
	{
		return loginBtn.isLoginNow();
	}
	public boolean createApptRequest()
	{
		return requestApptBtn.isAddApptReq();
	}
	public void closeCreateApptRequest()
	{
		requestApptBtn.setAddApptReq(false);
	}
	public void refreshCalendar()
	{
		if(HackathonMain.apptWs!=null && HackathonMain.apptWs.appts!=null)
		{
			components.clear();
			dowHeader.clear();
			clearAppts();
			days.clear();
			for(int i=0;i<7;i++)
			{
				dowHeader.add(new DayHeader(i*(width/7),30,width/7,30,i));
				components.add(dowHeader.get(i));
			}
			LocalDate date=header.getDate().withDayOfMonth(1);
			int dow=date.getDayOfWeek().getValue(); 
			LocalDate fDom=date.minusDays((long)(dow));
			this.width=this.getWidth();
			for(int j=0;j<5;j++)
			{
				for(int k=0;k<7;k++)
				{
					
					days.add(new Day(
							k*(this.getWidth()/7),
							j*((this.getHeight()-offset)/5)+offset,
							this.getWidth()/7,
							(this.getHeight()-offset)/5,
							fDom)
							);
					components.add(days.get(k+j*7));
					fDom=fDom.minusDays(-1);
				}
			}

			requestApptBtn=new RequestApptBtn(this.width-130,0);
			components.add(requestApptBtn);
			components.add(header);
			setDaysAppts();
			setToday();
		}
		
		resizeDays();
	}
	private int offset=60;
	private int width;
	public void resizeDays()
	{
		try {
		for(int i=0;i<7;i++)
		{
			dowHeader.get(i).setDim(i*(width/7),30,width/7,30);
		}
		LocalDate date=header.getDate().withDayOfMonth(1);
		//System.out.println(todayToString[0]);
		int dow=date.getDayOfWeek().getValue();
		LocalDate fDom=date.minusDays((long)(dow));
		width=this.getWidth();
		for(int j=0;j<5;j++)
		{
			for(int k=0;k<7;k++)
			{
				
				days.get(k+j*7).setDim(k*(this.getWidth()/7),
						j*((this.getHeight()-offset)/5)+offset,
						this.getWidth()/7,
						(this.getHeight()-offset)/5);
				days.get(k+j*7).setDate(fDom);
				days.get(k+j*7).rePositionAllText();//re-positions text in form
				fDom=fDom.minusDays(-1);
			}
		}
		}catch(Exception e)
		{
			
		}
		requestApptBtn.setX(this.width-130);
		header.setDim((int)(width/2.5),0,width/5,30);
		
		
	} 
	
	@Override
	public void paint(Graphics g)
	{
		for(int i=0;i<components.size();i++)
			try 
			{
				if(!components.get(i).equals(getBigDay()))
					components.get(i).draw(g);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		if(getBigDay()!=null)
			getBigDay().draw(g);
		
	}
	
	public void changeMonthClick(int x, int y)
	{
		header.right.onClick(x,y);
		header.left.onClick(x, y);
		
	}
	private boolean setToday()//returns false if today is not on calendar
	{
		today=findDayByDate(LocalDate.now());
		
		if(today==null)
			return false;
		else
		{
			return true;
		}
	}
	public void setBigDay(Day d)
	{
			this.bigDay=d;
	}
	public Day getBigDay()
	{
		return bigDay;
	}
	public ArrayList<Day> getDays()
	{
		return days;
	}
	public void clearAppts()
	{
		for(Day d:days)
			d.clearAllAppt();
		setDaysAppts();
	}
	public void setDaysAppts()
	{
			if(HackathonMain.apptWs!=null)
				try {
			for(int i=0;i<HackathonMain.apptWs.appts.size();i++)
			{
				findDayByDate(HackathonMain.apptWs.appts.get(i).getStartDate()).addAppt(HackathonMain.apptWs.appts.get(i));
			}
				}
			catch(IndexOutOfBoundsException e)
			{
				//e.printStackTrace();
			}
			
	}
	public Day findDayByDate(LocalDate d)
	{
		//int max=days.size();
		//int min=0;
		//int counter=0;
		//boolean found=false;
		/*while(min<max && !found )
		{
			//System.out.println("finDayByDate " +min+" "+max+" "+((min+max)/2));
			if(days.get((max+min)/2).date.toEpochDay()<d.toEpochDay())
			{
				min=Math.round((max+min)/2.0f);
				//System.out.println("case 1");
			}
			else if(days.get((max+min)/2).date.toEpochDay()!=d.toEpochDay())
			{
				max=Math.round((max+min)/2.0f);
				//System.out.println("case 2");
			}
			else if(days.get((max+min)/2).date.toEpochDay()==d.toEpochDay())
			{
				found=true;
				//System.out.println("case 3");
			}
			counter++;
		}*/
		for(int i=0;i<days.size();i++)
		{
			if(days.get(i).date.toEpochDay()==d.toEpochDay())
				return days.get(i);
		}
			return new Day(0,0,0,0,LocalDate.now());
	}
	public void login(boolean b) 
	{
		loginBtn.setLoginNow(b);
	}
	
}

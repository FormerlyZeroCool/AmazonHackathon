package com.andrewrs.user;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import javax.swing.SwingConstants;

import com.andrewrs.hackathon.HackathonMain;
import com.andrewrs.user.LoginForm;
import com.andrewrs.user.UserData;
import com.andrewrs.ws.ApptRecord;
import com.andrewrs.ws.RequestRecord;

public class AddApptForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnl1,pnl2,pnl3,pnl4,pnl5,pnl6;
	private JComboBox<String> classCombo,repeatCombo;
	private JComboBox<LocalDate> startWeek;
	private JComboBox<String> endTime,startTime;
	private JCheckBox entireSemesterChk,showNameInApptChk;
	private JTextField TBEmail;
	private DayOfWeekSelector daySelector;
	private JButton saveAndNew,save;
	private boolean isRunning=true;
	private UserData user;
	private AdminMenu adminMenuBar;
	private int respCode=0;
	public AddApptForm(UserData user)
	{
		
		this.setSize(600, 300);
		getContentPane().setLayout(new GridLayout(6, 1, 0, 0));
		this.user=user;
		
		pnl1 = new JPanel();
		getContentPane().add(pnl1);
		pnl1.setLayout(new GridLayout(1, 4, 0, 0));
		
	
		pnl2 = new JPanel();
		getContentPane().add(pnl2);
		pnl2.setLayout(new GridLayout(1, 4, 0, 0));
		
		pnl3 = new JPanel();
		getContentPane().add(pnl3);
		pnl3.setLayout(new GridLayout(1, 1, 0, 0));
		pnl4 = new JPanel();
		getContentPane().add(pnl4);
		pnl4.setLayout(new GridLayout(1, 4, 0, 0));

		pnl5 = new JPanel();
		getContentPane().add(pnl5);
		
		pnl6 = new JPanel();
		getContentPane().add(pnl6);
		pnl6.setLayout(new GridLayout(1, 4, 0, 0));
		
		daySelector=new DayOfWeekSelector(pnl3.getX()+this.getWidth()/180,0,this.getWidth()-this.getWidth()/120,(int)(this.getHeight()/6.7));
		pnl3.add(daySelector);
		daySelector.repaint();
		classCombo=new JComboBox<String>();
		repeatCombo=new JComboBox<String>();
		entireSemesterChk=new JCheckBox();
		classCombo.addItem("CSC101");
		classCombo.addItem("CSC110");
		classCombo.addItem("CSC111");
		classCombo.addItem("CSC211");
		classCombo.addItem("CSC215");
		classCombo.addItem("CSC231");
		classCombo.addItem("CSC331");
		classCombo.addItem("CSC431");
		TBEmail=new JTextField();
		if(user.isAdmin())
		{
			adminMenuBar = new AdminMenu();
			adminMenuBar.refreshFields();
			this.setJMenuBar(adminMenuBar);
			
		}
		if(!user.isLoggedIn())
		{
			JLabel lblEmail = new JLabel("Name:");
			lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
			JPanel pnlEmail=new JPanel();
			pnl1.add(lblEmail);
			//pnlEmail.add(lblEmail);
			JLabel lblClassCode = new JLabel("Class Code:");
			lblClassCode.setHorizontalAlignment(SwingConstants.LEFT);
			pnl1.add(lblClassCode);
			pnl2.add(pnlEmail);
			TBEmail.setColumns(11);
			pnlEmail.add(TBEmail);
			pnl2.add(classCombo);
			JLabel label_1 = new JLabel("Repeat for Semester");
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			pnl1.add(label_1);
			pnl1.add(new JLabel("Repeat for Weeks:"));
			pnl4.add(new JLabel("Start time:"));
			pnl4.add(new JLabel("End time:"));
			pnl4.add(new JLabel(""));
			pnl4.add(new JLabel(""));
		}
		else
		{
			
			JLabel lblClassCode = new JLabel("Class Code:");
			lblClassCode.setHorizontalAlignment(SwingConstants.LEFT);
			pnl1.add(lblClassCode);
			pnl1.add(new JLabel(" "));
			pnl2.add(classCombo);
			pnl2.add(new JLabel(" "));
			JLabel label_1 = new JLabel("Repeat for Semester");
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			pnl1.add(label_1);
			pnl1.add(new JLabel("Repeat for Weeks:"));
			pnl4.add(new JLabel("Starting Week:"));
			pnl4.add(new JLabel(""));
			pnl4.add(new JLabel("Start time:"));
			pnl4.add(new JLabel("End time:"));
			startWeek=new JComboBox<LocalDate>();
			pnl5.add(startWeek);
			LocalDate date=LocalDate.now();
			date=date.minusDays(date.getDayOfWeek().getValue()-1);
			pnl5.add(new JLabel(""));
			for(int i=0;i<50;i++)
			{
				startWeek.addItem(date.plusWeeks(i));
				
			}
		}
		JPanel pnlEntireSession=new JPanel();
		pnl2.add(pnlEntireSession);
		pnlEntireSession.add(entireSemesterChk);
		pnl2.add(repeatCombo);
		for(int i=1;i<31;i++)
		{
			repeatCombo.addItem(i+"");
		}
		startTime=new JComboBox<String>();
		endTime=new JComboBox<String>();
		pnl5.add(startTime);
		pnl5.add(endTime);
		for(int i=0;i<30;i++)
		{
			int hour=i/2+8;
			if(hour<13)
			{
				if(i%2!=0)
				{
					startTime.addItem(hour+":30 AM");
					endTime.addItem(hour+":30 AM");
				}
				else
				{
					startTime.addItem(hour+":00 AM");
					endTime.addItem(hour+":00 AM");
				}
			}
			else
			{
				if(i%2!=0)
				{
					startTime.addItem(hour-12+":30 PM");
					endTime.addItem(hour-12+":30 PM");
				}
				else
				{
					startTime.addItem(hour-12+":00 PM");
					endTime.addItem(hour-12+":00 PM");
				}
			}
				
		};
		pnl5.setLayout(new GridLayout(0, 4, 0, 0));
		
		JPanel pnlShowName=new JPanel();
		pnl5.add(pnlShowName);
		saveAndNew=new JButton("Save and New");
		save=new JButton("   Save   ");
		JPanel pnlSave=new JPanel();
		pnl6.add(pnlSave);
		pnlSave.add(save);
		JPanel pnlSaveAndNew=new JPanel();
		pnl6.add(pnlSaveAndNew);
		pnlSaveAndNew.add(saveAndNew);
		pnl6.add(new JLabel(" "));
		pnl6.add(new JLabel(" "));
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                isRunning=false;
                
            }
        });
		save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				attemptSave();
				setVisible(false);
			}

		});
		saveAndNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				respCode=0;
				attemptSave();
				while(respCode==0)
				{
					try {
						Thread.sleep(16);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
				clearForm();
			}

		});

		this.setSize(600, 300);
	}

	private void attemptSave() 
	{
		String startString[]=((String) startTime.getSelectedItem()).split(":");
		String endString[]=((String) endTime.getSelectedItem()).split(":");
		LocalTime start=LocalTime.of(Integer.parseInt(startString[0]),Integer.parseInt(startString[1].split(" ")[0]));
		LocalTime end=LocalTime.of(Integer.parseInt(endString[0]),Integer.parseInt(endString[1].split(" ")[0]));
		
		if(user.isLoggedIn())//this creates a new appointment booking
		{
			ApptRecord submission=new ApptRecord((String) classCombo.getSelectedItem(),
					(String) user.getUserName(),
					(LocalDate) startWeek.getSelectedItem(),
					((LocalDate) startWeek.getSelectedItem()).plusWeeks(Long.parseLong((String) repeatCombo.getSelectedItem())),
					start,
					end
					);
			for(int i=0;i<7;i++)
			{
				if(daySelector.isSelectedByIndex(i))
				{
					submission.weekDays.add(daySelector.getDayName(i));
				}
			}
			System.out.println(submission.weekDays.size());
			if(!entireSemesterChk.isSelected())
			{
				HackathonMain.apptWs.postAppt(submission);
			}
			else
			{
				LocalDate indexWeek=((LocalDate) startWeek.getSelectedItem());
				LocalDate genStartWeek;
				LocalDate genEndWeek;
				if(indexWeek.getMonthValue()>LocalDate.parse("2019-08-28").getMonthValue() &&//Fall
						indexWeek.getMonthValue()<LocalDate.parse("2019-12-14").getMonthValue())
				{
					genStartWeek=LocalDate.parse(LocalDate.now().getYear()+"-08-28");
					genEndWeek=LocalDate.parse(LocalDate.now().getYear()+"-12-14");
				}
				else if(indexWeek.getMonthValue()>LocalDate.parse("2019-01-14").getMonthValue() &&//Spring
						indexWeek.getMonthValue()<LocalDate.parse("2019-06-14").getMonthValue())
				{
					genStartWeek=LocalDate.parse(LocalDate.now().getYear()+"-01-01");
					genEndWeek=LocalDate.parse(LocalDate.now().getYear()+"-06-14");
				}
				else if(indexWeek.getMonthValue()>LocalDate.parse("2019-05-26").getMonthValue() &&//All Summer
						indexWeek.getMonthValue()<LocalDate.parse("2019-08-13").getMonthValue())
				{
					genStartWeek=LocalDate.parse(LocalDate.now().getYear()+"-05-26");
					genEndWeek=LocalDate.parse(LocalDate.now().getYear()+"-08-13");
				}
				else//Winter
				{
					genStartWeek=LocalDate.parse(LocalDate.now().getYear()+"-01-02");
					genEndWeek=LocalDate.parse(LocalDate.now().getYear()+"-01-23");
				}
				submission.setStartDate(genStartWeek);
				submission.setEndDate(genEndWeek);
				
				HackathonMain.apptWs.postAppt(submission);	
			}
		}
		else//this creates  new appointment request booking
		{
			RequestRecord requestSub=new RequestRecord((String) classCombo.getSelectedItem(),
					(String) user.getUserName(),
					start,
					end
					);

			for(int i=0;i<7;i++)
			{
				if(daySelector.isSelectedByIndex(i))
				{
					requestSub.weekDays.add(daySelector.getDayName(i));
				}
			}
			HackathonMain.requestws.postRequest(requestSub);
		}
		respCode=200;
		
	}
	private void clearForm()
	{
		daySelector.clear();
		classCombo.setSelectedIndex(0);
		repeatCombo.setSelectedIndex(0);
		entireSemesterChk.setSelected(false);
		TBEmail.setText("");
	}
	public void repaintForm()
	{
		daySelector.repaint();
		this.repaint();
	}
	public boolean isRunning() {
		if(user.isAdmin())
			adminMenuBar.refreshFields();
		return isRunning;
	}
}

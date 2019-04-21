package com.andrewrs.user;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.andrewrs.calendar.Appointment;
import com.andrewrs.hackathon.HackathonMain;
import com.andrewrs.ws.RequestRecord;

public class UserRequestManagerForm extends JFrame
{

	/**
	 * 
	 */
	private JTable table;
	private static final long serialVersionUID = 1L;
	private AdminMenu adminMenuBar;
	private ArrayList<RequestRecord> records;
	public UserRequestManagerForm(UserData user)
	{
		{
			adminMenuBar = new AdminMenu();
			adminMenuBar.refreshFields();
			this.setJMenuBar(adminMenuBar);
		}
		table=new JTable();
		
		refreshTable();
		this.setSize(500, 800);
	}

	public void refreshTable()
	{
		int columns=5;
		records=HackathonMain.requestws.getAllRequestRecords();
		Collections.sort(records,new Comparator<RequestRecord>() {
			public int compare(RequestRecord o1, RequestRecord o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}
		});
		int rows=records.size();
		String data[][]=new String[rows][columns];
		for(int i=0;i<records.size();i++)
		{
			data[i][0]=records.get(i).getClassCode();
			data[i][1]=records.get(i).startTimeToString();
			data[i][2]=records.get(i).endTimeToString();
			data[i][3]=records.get(i).getStudentEmail();
			data[i][4]=records.get(i).weekDaysToString();
		}
			
			
		
		String headers[]= {"Class Code","Start Time","EndTime","Student Email","Day of Week"};
		table = new JTable(data,headers);
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	}
}

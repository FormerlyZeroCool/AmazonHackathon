package com.andrewrs.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AdminMenu extends JMenuBar
{

	private JMenu adminMenu;
	private JMenuItem addAppt,reviewRequests,manageUsers,addUser;
	public AdminMenu()
	{
		
		adminMenu = new JMenu("Admin Options");
		addAppt=new JMenuItem("Add Appointments");
		reviewRequests=new JMenuItem("Review Student Requests");
		manageUsers=new JMenuItem("Manage Users");
		addUser=new JMenuItem("Add User");
		//if(ProgramState.getState()!=ProgramState.NEW_APPT)
			adminMenu.add(addAppt);
		//if(ProgramState.getState()!=ProgramState.MANAGE_REQUESTS)
			adminMenu.add(reviewRequests);
		//if(ProgramState.getState()!=ProgramState.MANAGE_USERS)
			adminMenu.add(manageUsers);
		//if(ProgramState.getState()!=ProgramState.NEW_USER)
			adminMenu.add(addUser);
		this.add(adminMenu);
		addAppt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			
					ProgramState.setState(ProgramState.NEW_APPT);
				
			}
			
		});
		manageUsers.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			
					ProgramState.setState(ProgramState.MANAGE_USERS);
				
			}
			
		});
		addUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					ProgramState.setState(ProgramState.NEW_USER);
				
				
			}
			
		});
		reviewRequests.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					ProgramState.setState(ProgramState.MANAGE_REQUESTS);
				
				
			}
			
		});
	}
	public void refreshFields()
	{
		adminMenu.removeAll();

		//if(ProgramState.getState()!=ProgramState.NEW_APPT)
			adminMenu.add(addAppt);
		//if(ProgramState.getState()!=ProgramState.MANAGE_REQUESTS)
			adminMenu.add(reviewRequests);
		//if(ProgramState.getState()!=ProgramState.MANAGE_USERS)
			adminMenu.add(manageUsers);
		//if(ProgramState.getState()!=ProgramState.NEW_USER)
			adminMenu.add(addUser);
	}
}

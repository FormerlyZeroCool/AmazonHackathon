package com.andrewrs.user;

import com.andrewrs.user.NewUserForm;
import com.andrewrs.user.AddApptForm;
public class ProgFrame 
{
	private UserData currentUser;
	private AddApptForm apptForm;
	private NewUserForm newUserForm;
	private UserManagerForm userManager;
	private UserRequestManagerForm requestManager;
	private boolean isRunning=true;
	public ProgFrame(UserData user)
	{

		newUserForm=new NewUserForm(user);
		apptForm=new AddApptForm(user);
		userManager=new UserManagerForm(user);
		requestManager=new UserRequestManagerForm(user);
		newUserForm.setVisible(false);
		apptForm.setVisible(false);
		userManager.setVisible(false);
		requestManager.setVisible(false);
		this.currentUser=user;
		
		ProgramState.setState(ProgramState.NEW_APPT);
		apptForm.setVisible(true);
		
			

		ProgramState.freezeState();	
		
	}
	public void setSomething()
	{
		if(currentUser.isAdmin())
			requestManager.setVisible(true);
		else if(ProgramState.getLastState()!=ProgramState.getState())
		{
			apptForm.setVisible(true);
		}
		else
		{
			apptForm.setVisible(true);
		}
		isRunning=true;
		ProgramState.freezeState();	
	}
	public void update()
	{

		if(!newUserForm.isVisible() && !apptForm.isVisible() && !userManager.isVisible() && !requestManager.isVisible())
			isRunning=false;
		else 
		if(ProgramState.getLastState()!=ProgramState.getState())
		{
			if(ProgramState.getLastState()==ProgramState.NEW_USER)
			{
				newUserForm.setVisible(false);
			}
			else if(ProgramState.getLastState()==ProgramState.NEW_APPT)
			{
				apptForm.setVisible(false);
			}
			else if(ProgramState.getLastState()==ProgramState.MANAGE_USERS)
			{
				userManager.setVisible(false);
			}
			else if(ProgramState.getLastState()==ProgramState.MANAGE_REQUESTS)
			{
				requestManager.setVisible(false);
			}
			if(ProgramState.getState()==ProgramState.NEW_USER)
			{
				newUserForm.setVisible(true);
			}
			else if(ProgramState.getState()==ProgramState.NEW_APPT)
			{
				apptForm.setVisible(true);
			}
			else if(ProgramState.getState()==ProgramState.MANAGE_USERS)
			{
				userManager.setUser(currentUser);
				userManager.refreshTable();
				userManager.setVisible(true);
			}
			else if(ProgramState.getState()==ProgramState.MANAGE_REQUESTS)
			{
				requestManager.setVisible(true);
			}

			ProgramState.freezeState();
		}
		if(ProgramState.getState()==ProgramState.NEW_USER)
		{
			newUserForm.setVisible(true);
		}
		else if(ProgramState.getState()==ProgramState.NEW_APPT)
		{
			apptForm.setVisible(true);
			apptForm.repaintForm();
		}
		else if(ProgramState.getState()==ProgramState.MANAGE_USERS)
		{
			userManager.setVisible(true);
			
		}
		else if(ProgramState.getState()==ProgramState.MANAGE_REQUESTS)
		{
			requestManager.setVisible(true);
			
		}
	}
	public void close()
	{
		apptForm.dispose();
		newUserForm.dispose();
		userManager.dispose();
		requestManager.dispose();
	}
	public boolean isRunning() {
		// TODO Auto-generated method stub

		if(!newUserForm.isVisible() && !apptForm.isVisible() && !userManager.isVisible() && !requestManager.isVisible())
			isRunning=false;
		return isRunning;
	}
}

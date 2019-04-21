package com.andrewrs.hackathon;
import java.awt.Dimension;
import java.time.LocalTime;

import javax.swing.JFrame;

import com.andrewrs.calendar.Calendar;
import com.andrewrs.user.LoginForm;
import com.andrewrs.user.ProgFrame;
import com.andrewrs.user.UserData;
import com.andrewrs.ws.ApptService;
import com.andrewrs.ws.RequestRecord;
import com.andrewrs.ws.RequestService;
public class HackathonMain {
	private static Calendar cal;

	private static ProgFrame frameHandler;
	private static UserData user;
	public static ApptService apptWs;
	public static RequestService requestws;

	
	public static void main(String[] args) {
		requestws=new RequestService();
		LoginForm login=new LoginForm("Login");
		user=login.currentUser;
		login.setVisible(false);
				final JFrame frame=new JFrame("Main Screen");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setSize(new Dimension(800,685));
				 cal=new Calendar(0,10,540,600);
				
				frame.getContentPane().add(cal);
				//frame.getContentPane().add(cApp);
				//db.createTable();
				Thread updateData=new Thread(){
					@Override
					public void run()
					{
						apptWs=new ApptService();
						while (true)
						{

							apptWs.setAppointments();
							cal.clearAppts();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				updateData.start();
				long counter=0;
				long startTime,delta;
				final int mod=5;
				while(true)
				{
					startTime=System.nanoTime();
					if(counter%mod==0)
					counter++;
					cal.setBounds(10, 20,frame.getWidth()-25, frame.getHeight()-50);
					cal.resizeDays();
					cal.setDaysAppts();
					cal.repaint();
					delta=System.nanoTime()-startTime;
					delta/=100000000;
					//System.out.println(delta);

					try {
						Thread.sleep(32-delta);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(counter>=mod*1000000)
						counter=0;
					if(cal.createAppt() || cal.createApptRequest())
					{
						if(!cal.createApptRequest())
						login.setVisible(true);
						while(!login.isLoggedIn() && !cal.createApptRequest())//should loop only if student is not making request
						{//will loop if user is not logged in, and user pressed login button
								try {
									Thread.sleep(32);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							
						}
						login.setVisible(false);
						frameHandler=new ProgFrame(login.currentUser);
						
						while(frameHandler.isRunning())
						{
							frameHandler.update();
							try {
								Thread.sleep(32);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
						cal.login(false);
						cal.closeCreateApptRequest();
						login.resetUser();
						frameHandler.close();
						
					}
				}
			}

		
		
		
		
}

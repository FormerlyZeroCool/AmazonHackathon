package com.andrewrs.user;

public class ProgramState {
	private static int state=0;
	private static int lastState=state;
	public static final int NEW_USER=0,NEW_APPT=1,MANAGE_USERS=2,MANAGE_REQUESTS=3;
	public static int getLastState()
	{
		return ProgramState.lastState;
	}
	public static int getState()
	{
		return ProgramState.state;
	}
	public static void setState(int state)
	{
		ProgramState.lastState=ProgramState.state;
		ProgramState.state=state;
	}
	public static void freezeState()
	{
		ProgramState.lastState=ProgramState.state;
	}
}

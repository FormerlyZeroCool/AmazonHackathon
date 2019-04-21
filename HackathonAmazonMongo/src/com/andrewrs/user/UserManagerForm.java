package com.andrewrs.user;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class UserManagerForm extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private AdminMenu adminMenuBar;
	private ArrayList<UserData> users;
	private UserData currentUser;
	public UserManagerForm(UserData user)
	{
		table=new JTable();

		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		getContentPane().add(table, BorderLayout.CENTER);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		{
			adminMenuBar = new AdminMenu();
			this.setJMenuBar(adminMenuBar);
		}
		final JButton btnUpdateUser = new JButton("Update");
		panel.add(btnUpdateUser);
		
		final JButton btnNewUser = new JButton("New User");
		panel.add(btnNewUser);
		
		final JButton btnDeleteUser = new JButton("Delete");
		panel.add(btnDeleteUser);
		btnUpdateUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 UserData userToUpdate;
				 for(int j=0;j<users.size();j++)
				 {
					 if(users.get(j).getUserName().equals(table.getValueAt(table.getSelectedRow(), 0)))
					 {
						 users.get(j).save();
					 }
				 }
					 
				 
			}
		});
		btnNewUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProgramState.setState(ProgramState.NEW_USER);
				
			}
		});
		btnDeleteUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				users.get(table.getSelectedRow()).deleteOnWS();
				
			}
		});
		this.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					setVisible(false);
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		adminMenuBar.refreshFields();
		this.setSize(500, 500);
	}
	public void refreshTable()
	{
		int columns=3;
		users=currentUser.getAllUsersData();
		int rows=users.size();
		String data[][]=new String[rows][columns];
		for(int i=0;i<users.size();i++)
		{
			data[i][0]=users.get(i).getUserName();
			data[i][1]=users.get(i).getHashedPass().toString();
			data[i][2]=users.get(i).isAdmin()?"Admin":"Tutor";
		}
			
			
		
		String headers[]= {"User","Password","isAdmin"};
		table = new JTable(data,headers);
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	}
	public void setUser(UserData currentUser2) {
		currentUser=currentUser2;
		
	}
	

}

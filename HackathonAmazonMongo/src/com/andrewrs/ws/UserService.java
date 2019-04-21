package com.andrewrs.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.andrewrs.hackathon.HackathonMain;
import com.andrewrs.user.LoginForm;
import com.andrewrs.user.UserData;

public class UserService 
{

	private URL url;
	private HttpURLConnection con;
	private UserData user;
	public UserService(UserData user)
	{
		this.user=user;
	}
	public int postUser()
	{
		int responseCode=400;//Default response not found
		ArrayList<StringKeyPairs> map=new ArrayList<StringKeyPairs>();
		String userId=new BigInteger(LoginForm.hashPass(user.getUserName().toCharArray()).toString(),10).toString(16);
		map.add(new StringKeyPairs("_id", userId,StringKeyPairs.STRING));
		map.add(new StringKeyPairs("userName", user.getUserName(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("password", user.getHashedPass().toString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("isAdmin", Integer.toString(user.isAdmin()?1:0),StringKeyPairs.INT));
		byte outPutData[]=jsonBuilder(map).getBytes();
		System.out.println(new String(outPutData));
        try {

    	url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/users");
		con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		OutputStream io=con.getOutputStream();
		io.write(outPutData);
		io.flush();
			System.out.println(con.getResponseCode());
        InputStream input=con.getInputStream();
        byte arrResp[]=new byte[con.getContentLength()];
        for(int i=0;i<con.getContentLengthLong();i++)
		{
			arrResp[i]=(byte) input.read();
		}
        System.out.println(new String(arrResp));
        responseCode=con.getResponseCode();
		con.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return responseCode;
	}
	public UserData getUserRecordById(String id)
	{
		UserData record=new UserData();
		try{
		url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/users/"+id);
		con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(false);
        InputStream io=con.getInputStream();
		byte body[]=new byte[con.getContentLength()];
        for(int i=0;i<con.getContentLengthLong();i++)
		{
			body[i]=(byte) io.read();
		}
        String bodyResp=new String(body);
        record.setUserName(findDataByField("userName",bodyResp));
        record.setHashedPass(new BigInteger(findDataByField("password",bodyResp)));
        record.setAdmin(findDataByField("isAdmin",bodyResp).equals("true"));
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	public ArrayList<UserData> getAllUserRecords()
	{
		ArrayList<UserData> users=new ArrayList<UserData>();
		try{
		url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/users");
		con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(false);
        InputStream io=con.getInputStream();
		byte body[]=new byte[con.getContentLength()];
        for(int i=0;i<con.getContentLengthLong();i++)
		{
			body[i]=(byte) io.read();
		}
        String bodyResp[]=new String(body).split("},");
        StringBuilder bodyBuilder=new StringBuilder();
        for(int i=0;i<bodyResp.length;i++)
        {
        	bodyBuilder.append(bodyResp[i]);
        	bodyBuilder.append("}");
        	users.add(new UserData(findDataByField("userName",bodyBuilder.toString()),
        			findDataByField("password",bodyBuilder.toString()).replaceAll("potato", "-1"),
        			findDataByField("isAdmin",bodyBuilder.toString()).equals("true")));
        	bodyBuilder.setLength(0);
        }

        con.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	public void updateLocalUserById(String id)
	{
		try {

			url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/users/"+id);
			con=(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(false);
	        InputStream io=con.getInputStream();
			byte body[]=new byte[con.getContentLength()];
            for(int i=0;i<con.getContentLengthLong();i++)
			{
				body[i]=(byte) io.read();
			}
            String bodyResp=new String(body);
            user.setHashedPass(new BigInteger((findDataByField("password",bodyResp))));
            user.setAdmin(findDataByField("isAdmin",bodyResp).equals("ru"));
        	con.disconnect();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	private String jsonBuilder(ArrayList<StringKeyPairs> data)
	{
		StringBuilder appender=new StringBuilder("{");
		for(StringKeyPairs entry:data)
		{
			appender.append("\"");
			appender.append(entry.getKey());
			appender.append("\":");
			if(entry.getDataType()==StringKeyPairs.STRING)
			{
				appender.append("\"");
				appender.append(entry.getValue());
				appender.append("\"");
			}
			else
			{
				appender.append(entry.getValue());
			}

			appender.append(",");
		}
		appender.deleteCharAt(appender.length()-1);
		appender.append('}');
		return appender.toString();
	}
	public String findDataByField(String field,String pData)
	{
		//System.out.println(pData);
		int fieldIndex[]= {pData.indexOf(field, 0),0};
		int endIndex;
		fieldIndex[1]=pData.indexOf(":", fieldIndex[0])+2;
		if(pData.charAt(fieldIndex[1]-1)=='[')
		{
			endIndex=pData.indexOf("]", fieldIndex[1]);
			//System.out.println("calc 1");
		}
		else if(pData.substring(fieldIndex[1],fieldIndex[1]+1).contains("\""))
		{
			endIndex=pData.indexOf("\"", fieldIndex[1]+1)-1;
			//System.out.println("calc 2");
		}	
		else if(pData.indexOf(",", fieldIndex[1])>fieldIndex[1] && pData.length()-1>pData.indexOf(",", fieldIndex[1]))
		{
			endIndex=pData.indexOf(",",fieldIndex[1])-1;
			//System.out.println("calc 3 ");
		}	
		else
		{
			endIndex=pData.indexOf("}", fieldIndex[1]);
			//System.out.println("calc 4");
		}
		//System.out.println(fieldIndex[1] +" "+ endIndex);
		return pData.substring(fieldIndex[1],endIndex);
	}
	public void deleteUser(String userId) 
	{
		int responseCode=0;
		
        try {

    	url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/users/"+userId);
		con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("DELETE");
		con.setDoOutput(true);
		OutputStream io=con.getOutputStream();
		//io.write(outPutData);
		io.flush();
			System.out.println(con.getResponseCode());
        InputStream input=con.getInputStream();
        byte arrResp[]=new byte[con.getContentLength()];
        for(int i=0;i<con.getContentLengthLong();i++)
		{
			arrResp[i]=(byte) input.read();
		}
        System.out.println(new String(arrResp));
        responseCode=con.getResponseCode();
		con.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HackathonMain.apptWs.deleteByUserId(userId);
	}
	
}

package com.andrewrs.ws;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.andrewrs.calendar.Appointment;
import com.andrewrs.user.LoginForm;
import com.andrewrs.user.UserData;

public class ApptService 
{

	private URL url;
	private HttpURLConnection con;
	private Appointment newAppt;
	private ArrayList<ApptRecord> records=new ArrayList<ApptRecord>();
	public ArrayList<Appointment> appts=new ArrayList<Appointment>();
	
	public void setAppointments()
	{
		appts.clear();
		ArrayList<ApptRecord> records= getAllApptRecords();
		ArrayList<Appointment> temp;
		for(int i=0;i<records.size();i++)
		{
			//System.out.println(records.get(i).toString());
			temp=records.get(i).getAppts();
			for(int j=0;j<temp.size();j++)
			{
				appts.add(temp.get(j));
			}
		}
		Collections.sort(appts,new Comparator<Appointment>() {
			public int compare(Appointment o1, Appointment o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}
		});
		//for(int i=0;i<appts.size();i++)
		//{
		//	System.out.println(appts.get(i).toString());
		//}
	}
	public void deleteByUserId(String userId)
	{
		
		for(int i=0;i<records.size();i++)
		{
			if(LoginForm.hashPass(records.get(i).getTutorEmail().toCharArray()).toString(16)==userId)
			{
				deleteById(records.get(i).getId());
			}
		}
	}
	public void deleteById(String id)
	{
		  try {

		    	url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/notes/"+id);
				con=(HttpURLConnection) url.openConnection();
				con.setRequestMethod("DELETE");
				con.setDoOutput(true);
					System.out.println(con.getResponseCode());
		        InputStream input=con.getInputStream();
		        byte arrResp[]=new byte[con.getContentLength()];
		        for(int i=0;i<con.getContentLengthLong();i++)
				{
					arrResp[i]=(byte) input.read();
				}
		        System.out.println(new String(arrResp));
		        System.out.println(con.getResponseCode());
				con.disconnect();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public ArrayList<ApptRecord> getAllApptRecords()
	{
		ArrayList<ApptRecord> appts=new ArrayList<ApptRecord>();
		try{
		url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/notes");
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
        	String startTime[]=findDataByField("startTime",bodyBuilder.toString()).split(":");
        	String endTime[]=findDataByField("endTime",bodyBuilder.toString()).split(":");
        	
        	appts.add(new ApptRecord(findDataByField("_id",bodyBuilder.toString()),
        			findDataByField("classCode",bodyBuilder.toString()),
        			findDataByField("tutorName",bodyBuilder.toString()),
        			LocalDate.parse(findDataByField("startDate",bodyBuilder.toString())),
        			LocalDate.parse(findDataByField("endDate",bodyBuilder.toString())),
        			LocalTime.of(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1])),
        			LocalTime.of(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]))
        	));
        	String days[]=findDataByField("days",bodyBuilder.toString()).replace("\"","").split(",");
        	for(int j=0;j<days.length;j++)
        		appts.get(i).weekDays.add(days[j]);
        	bodyBuilder.setLength(0);
        }

        con.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		records=appts;
		return appts;
	}
	public int postAppt(ApptRecord newAppt)
	{
		int responseCode=400;//Default response not found
		ArrayList<StringKeyPairs> map=new ArrayList<StringKeyPairs>();
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<newAppt.weekDays.size();i++)
		{
			builder.append(newAppt.weekDays.get(i));
			if(i<newAppt.weekDays.size()-1)
				builder.append(",");
		}
		map.add(new StringKeyPairs("days",builder.toString(),StringKeyPairs.STRINGARR));
		map.add(new StringKeyPairs("classCode", newAppt.getClassCode(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("startDate", newAppt.startDateToString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("endDate", newAppt.endDateToString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("startTime", newAppt.startTimeToString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("endTime", newAppt.endTimeToString(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("tutorName", newAppt.getTutorEmail(),StringKeyPairs.STRING));
		map.add(new StringKeyPairs("__v", "0",StringKeyPairs.INT));
		byte outPutData[]=jsonBuilder(map).getBytes();
		System.out.println(new String(outPutData));
        try {

    	url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/notes");
		con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		OutputStream io=con.getOutputStream();
		for(int i=0;i<outPutData.length;i++)
			io.write(outPutData[i]);
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
			else if(entry.getDataType()==StringKeyPairs.STRINGARR)
			{
				appender.append("[");
				String entries[]=entry.getValue().split(",");
				for(int i=0;i<entries.length;i++)
				{
					appender.append("\"");
					appender.append(entries[i]);
					appender.append("\"");
					if(i!=entries.length-1)
					{
						appender.append(",");						
					}
				}
				appender.append("]");
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
}

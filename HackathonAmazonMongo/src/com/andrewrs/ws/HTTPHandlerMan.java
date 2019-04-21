package com.andrewrs.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HTTPHandlerMan 
{
	private String returnJson;
	private URL url;
	private HttpURLConnection con;
	private String xAmzDate="20190416T190653Z";
	private String authguess1="\"AWS4-HMAC-SHA256 Credential=AKIAXAHAXJFCYS45ORBM/20190416/us-east-1/execute-api/aws4_request, SignedHeaders=content-type;host;x-amz-date, Signature=c8ba58644d848789b3efa96a67a5d2aca2e263d7090d0227717661f42619bee\"";
	public HTTPHandlerMan() throws MalformedURLException
	{
		url = new URL("https://wnv9dbdah5.execute-api.us-east-1.amazonaws.com/dev/users");
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(false);
			//for(int n=0;n<con.getContentLength())
			//con.addRequestProperty("Authorization", authguess1);
			//con.addRequestProperty("Authorization", xAmzDate);
            con.setRequestProperty("Accept", "application/json");
			//OutputStream io=con.getOutputStream();
            InputStream io=con.getInputStream();
            //byte arr[]="{\"times\":[\"2:00\",\"3:00\"], \"name\":\"azharM\",\"classCode\":\"CSC200\",\"__v\":0}".getBytes();
            byte arr[]=new byte[con.getContentLength()];
            for(int i=0;i<con.getContentLengthLong();i++)
			{
				// io.write(arr[i]);
				arr[i]=(byte) io.read();
			}
            returnJson=new String(arr);
			System.out.println(con.getResponseCode() + " " +new String(arr));
			con.disconnect();
			//,
			//,\"__v\":0}
			//postJsonString("{\"userName\":\"guestFromJava\",\"password\":\"48696\",\"__v\":0}");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//System.out.println(findDataByField("times",returnJson));
	}
	
	public String jsonBuilder(Map<String,String> data)
	{
		StringBuilder appender=new StringBuilder("{\n");
		for(Map.Entry<String, String> entry:data.entrySet())
		{
			appender.append("\"");
			appender.append(entry.getKey());
			appender.append("\":\"");
			appender.append(entry.getValue());
			appender.append("\"");
		}
		return appender.toString();
	}
	public void postJsonString(String json) throws Exception
	{
		con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		OutputStream io=con.getOutputStream();
		byte arr[]=json.getBytes();
		
		for(int i=0;i<arr.length;i++)
				io.write(arr[i]);
		io.flush();
        System.out.println(con.getResponseCode() +" "+json);
        InputStream input=con.getInputStream();
        byte arrResp[]=new byte[con.getContentLength()];
        for(int i=0;i<con.getContentLengthLong();i++)
		{
			// io.write(arr[i]);
			arrResp[i]=(byte) input.read();
		}
        System.out.println(new String(arrResp));
		con.disconnect();
	}
	public String findDataByField(String field,String pData)
	{
		int fieldIndex[]= {pData.indexOf(field, 0),0};
		int endIndex;
		fieldIndex[1]=pData.indexOf(":", fieldIndex[0])+2;
		if(pData.charAt(fieldIndex[1]-1)=='[')
		{
			endIndex=pData.indexOf("]", fieldIndex[1]);
		}
		else
			endIndex=pData.indexOf("\"", fieldIndex[1]);
		return pData.substring(fieldIndex[1], endIndex);
	}
}

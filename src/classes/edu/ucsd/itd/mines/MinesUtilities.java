/*
 * Created on Apr 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.ucsd.itd.mines;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.net.*;

/**
 * @author vchu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MinesUtilities {
	private MinesUtilities() {}
	
	public static Cookie getCookie(Cookie[] cookies, String cookieName) {
		for(int i=0; i<cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equalsIgnoreCase(cookie.getName()))
			return cookie;
		}
		return null;
	}
	
	public static String getCookieValue(Cookie[] cookies, String cookieName) {
		for(int i=0; i<cookies.length; i++) {
		Cookie cookie = cookies[i];
		if (cookieName.equalsIgnoreCase(cookie.getName()))
			return(cookie.getValue());
		}
		return null;
	}
	
	public static void writeCookiesToFile (Cookie[] cookies, String fileName) throws Exception{
    	String patronStatus = null, patronBehalfOf = null;
    	String researchType = null, sponsorshipProof = null;		
    	String patronLocation = null, academicDepartment = null;
    	String clientIpParam = null, newDestinationURL = null;
    	StringBuffer strBuffer = new StringBuffer();
    	Timestamp ts = new Timestamp(System.currentTimeMillis());
    	
		if(cookies != null) {
    		patronStatus = MinesUtilities.getCookieValue(cookies, 
			"patron_status");
    		//patronBehalfOf = MinesUtilities.getCookieValue(cookies, "patron_behalf_of");
    		patronLocation = MinesUtilities.getCookieValue(cookies, 
			"patron_location");    		
    		researchType = MinesUtilities.getCookieValue(cookies, 
			"research_type");
    		sponsorshipProof = MinesUtilities.getCookieValue(cookies, 
			"sponsor_proof");
    		academicDepartment = MinesUtilities.getCookieValue(cookies, 
			"academic_department");
    		//destinationURL = MinesUtilities.getCookieValue(cookies, 
			//"destination_url");
    		clientIpParam = MinesUtilities.getCookieValue(cookies, 
			"client_ip_param");
    		newDestinationURL = MinesUtilities.getCookieValue(cookies, 
			"new_destination_url");
    		
    		if(ts != null) {
	    		strBuffer.append("\"" + ts + "\"");
	    	} 

    		if(newDestinationURL != null && newDestinationURL.length() > 0) {
    			
    			strBuffer.append(" , \"" + URLDecoder.decode(newDestinationURL,"UTF-8") + "\"");
    		}
    		
    		if(patronStatus != null && patronStatus.length() > 0)
    			strBuffer.append(" , \"" + patronStatus + "\"");
    		
    		/*if(patronBehalfOf != null && patronBehalfOf.length() > 0)
    			strBuffer.append(" , \"" + patronBehalfOf + "\"");*/
    		
    		if(researchType != null && researchType.length() > 0)
    			strBuffer.append(" , \"" + researchType + "\"");
    		
    		if(sponsorshipProof != null && sponsorshipProof.length() > 0)
    			strBuffer.append(" , \"" + sponsorshipProof + "\"");
    		else
    			strBuffer.append(" , \"N/A\"");

    		if(academicDepartment != null && academicDepartment.length() > 0)
    			strBuffer.append(" , \"" + academicDepartment + "\"");
    		else
	    		strBuffer.append(" , \"N/A\"");
    		
    		if(patronLocation != null && patronLocation.length() > 0)
    			strBuffer.append(" , \"" + patronLocation + "\"");
    		
    		if(clientIpParam != null && clientIpParam.length() > 0)
    			strBuffer.append(" , \"" + clientIpParam + "\"");
    		
    		strBuffer.append("\n");
    		writeToFile(fileName, strBuffer);
    		
		}  
	}
		
	public static synchronized void writeToFile (String fileName, StringBuffer strBuffer) {
		BufferedWriter bwriter = null;		
    	try {   	    		
    		bwriter = new BufferedWriter(new FileWriter(fileName, true));
    		bwriter.write(strBuffer.toString());
    		
    		bwriter.close();

    	} catch (IOException ioe){
    		System.out.println("Error!!! IOException in MinesUtilities.writeCookiesToFile(): "+ ioe);
    	} catch (Exception e){
    		System.out.println("Exception in MinesUtilities.writeCookiesToFile(): "+ e);
    	} finally {
    		if(bwriter != null)
    			try {
    				bwriter.close();
    			} catch (IOException ioe) {
    				System.out.println("Error closing file: "+ioe);
    			}
    	}    	
	}	
	
	public static boolean isDomainEqual (String str1, String str2) {
		boolean equal = false;
		try {
			URL url1 = null, url2 = null;
			
			if(!str1.startsWith("http://"))
				url1 = new URL("http://".concat(str1));
			else 
				url1 = new URL(str1);
			
			if(!str2.startsWith("http://"))
				url2 = new URL("http://".concat(str2));
			else 
				url2 = new URL(str2);
			
			if((url1.getHost()).equals(url2.getHost())) {
				equal = true;
			}			
		} catch (MalformedURLException e) {
			System.out.println(" MalformedURLException in MinesUtilities.isDomainEqual(): "+e);
		}		
		
		return equal;
	}	
	
}

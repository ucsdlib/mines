/*
 * Created on Apr 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.ucsd.itd.mines;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.net.*;
/**
 * @author vchu
 *
 */
public class MinesHandlerServlet extends HttpServlet {
		private static String filePath = null;
		private static String dataSourceRef = null;
		private static int timeoutDestUrl = 0;
		private static int timeoutOtherParam = 0;
		
	    public void init(ServletConfig config) throws ServletException {
	    	ServletContext ctx = config.getServletContext();
	    	dataSourceRef = ctx.getInitParameter("dataSourceRef");
	    	timeoutDestUrl = Integer.parseInt((String)ctx.getInitParameter("timeoutDestinationURL"));
	    	timeoutOtherParam = Integer.parseInt((String)ctx.getInitParameter("timeoutOtherParam"));
	    	filePath = ctx.getInitParameter("filePath");
	    	
	        super.init(config);
	    }
	    
	    /** Destroys the servlet.
	     */
	    public void destroy() {
	        
	    }

	    /**
	     * Performs four functions. First, sends the data collected in the survey
	     * form to database.  Second, creates 6 cookies required to populate the
	     * datasource in the event that the user requests a different electronic 
	     * resource within the next 20 minutes.  Third, inserts client's IP address
	     * and a timestamp. Fourth, redirects the user to request destination url
	     * by passing the request back to the router.
	     * 
	     * @param request servlet request
	     * @param response servlet response
	     */
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, java.io.IOException {
	    	String destinationURL = request.getParameter("destination_url");
	    	String patronStatus = request.getParameter("patron_status");
	    	//String patronBehalfOf = request.getParameter("patron_behalf_of");
	    	String patronLocation = request.getParameter("patron_location");
	    	String researchType = request.getParameter("research_type");
	    	String sponsorshipProof = request.getParameter("sponsor_proof");
	    	String academicDepartment = request.getParameter("academic_department");
	    	String clientIpParam = request.getParameter("client_ip_param");
	    	String clientDevice = request.getParameter("client_device");
	    	
	    	String redirectURL = destinationURL != null ? destinationURL : "http://ucsd.edu";
	    	Timestamp ts = new Timestamp(System.currentTimeMillis());
	    	Cookie cookie = null;
	    	StringBuffer strBuffer = new StringBuffer();    	
	    	DataSource ds = null;
	    	Connection con = null;
	    	Context ctx = null;
	    	request.getSession(true);
	    	try {		    			
		    	
/*	          cookie = new Cookie("junk", "testing");
	          cookie.setMaxAge(timeoutOtherParam);  
	          response.addCookie(cookie); */	    			    	
	    		ctx = new InitialContext();
		    	ds = (DataSource) ctx.lookup(dataSourceRef);
	            con = ds.getConnection();  	
	    	  
	    		if(ts != null) {
		    		strBuffer.append("\"" + ts + "\"");
		    	} 
	    		
	    		if(destinationURL != null && destinationURL.length() > 0) {
		    		cookie = new Cookie("destination_url", URLEncoder.encode(destinationURL,"UTF-8"));
		    		cookie.setMaxAge(timeoutDestUrl);  
		    		response.addCookie(cookie);	 
		    		
		    		cookie = new Cookie("new_destination_url", URLEncoder.encode(destinationURL,"UTF-8"));
		    		cookie.setMaxAge(timeoutDestUrl);  
		    		response.addCookie(cookie);	
		    		
		    		strBuffer.append(" , \"" + URLDecoder.decode(destinationURL,"UTF-8") + "\"");
		    	} 		   
	    		
		    	if(patronStatus != null && patronStatus.length() > 0) {
		    		cookie = new Cookie("patron_status", patronStatus);
		    		cookie.setMaxAge(timeoutOtherParam);  
		    		response.addCookie(cookie);
		    		strBuffer.append(" , \"" + patronStatus + "\"");    		
		    	}
		    	
		    	/*if(patronBehalfOf != null && patronBehalfOf.length() > 0) {
		    		cookie = new Cookie("patron_behalf_of", patronBehalfOf);
		    		cookie.setMaxAge(timeoutOtherParam);  
		    		response.addCookie(cookie);
		    		strBuffer.append(" , \"" + patronBehalfOf + "\"");		    		
		    	}*/

		    	if(researchType != null && researchType.length() > 0) {
		    		cookie = new Cookie("research_type", researchType);
		    		cookie.setMaxAge(timeoutOtherParam);  
		    		response.addCookie(cookie);
		    		strBuffer.append(" , \"" + researchType+ "\"");		    		
		    	}
			    	
		    	if(sponsorshipProof != null && sponsorshipProof.length() > 0) {
		    		cookie = new Cookie("sponsor_proof", sponsorshipProof);
		    		cookie.setMaxAge(timeoutOtherParam);
		    		response.addCookie(cookie);
		    		strBuffer.append(" , \"" + sponsorshipProof+ "\"");
		    	} else
		    		strBuffer.append(" , \"N/A\"");
		    	
		    	if(academicDepartment != null && academicDepartment.length() > 0) {
		    		cookie = new Cookie("academic_department", academicDepartment);
		    		cookie.setMaxAge(timeoutOtherParam);  
		    		response.addCookie(cookie);
		    		strBuffer.append(" , \"" + academicDepartment+ "\"");
		    	} else
		    		strBuffer.append(" , \"N/A\"");
		    	
		    	if(patronLocation != null && patronLocation.length() > 0) {   		    				    		
		    		cookie = new Cookie("patron_location", patronLocation);
		    		cookie.setMaxAge(timeoutOtherParam);
		    		response.addCookie(cookie);  
		    		
		    		strBuffer.append(" , \"" + patronLocation + "\"");
		    		
		    		if(MinesDbUtilities.isIpExist(patronLocation, con))
		    			MinesDbUtilities.updateIpTimestamp(patronLocation, con);
		    		else
		    			MinesDbUtilities.insertIP(patronLocation, con);
		    	}
	
		    	if(clientIpParam != null && clientIpParam.length() > 0) {   		    				    		
		    		cookie = new Cookie("client_ip_param", clientIpParam);
		    		cookie.setMaxAge(timeoutOtherParam);
		    		response.addCookie(cookie);  
		    		strBuffer.append(" , \"" + clientIpParam + "\"");		    			    	
		    	}
		    	
		    	if(clientDevice != null && clientDevice.length() > 0) {
		    		cookie = new Cookie("client_device", clientDevice);
		    		cookie.setMaxAge(timeoutOtherParam);  
		    		response.addCookie(cookie);
		    		strBuffer.append(" , \"" + clientDevice+ "\"");
		    	} else
		    		strBuffer.append(" , \"N/A\"");
		    	
		    	strBuffer.append("\n");

		    	MinesUtilities.writeToFile(filePath, strBuffer);	    	
		    	
	    	} catch (Exception ex) {
            	System.out.println("Exception in MinesHandlerServlet: "+ex);
            } finally {
				MinesDbUtilities.closeConnection(con);			
			}	

        	if(redirectURL.startsWith("http://"))
        		response.sendRedirect(redirectURL);
        	else
        		response.sendRedirect("http://"+redirectURL);
	    }
	    
	    /**
	     * Performs logic on the request and looks up a cookie named destination_url.  
	     * If cookie is not found, user will be forwarded to the survey form. If cookie 
	     * is found, inserts source_ip and timestamp into database.  Also check to see
	     * if the cookie value is the same as the request destination url. If they are 
	     * not equal, writes the values of other cookies such as patron_status, 
	     * patron_location, research_type and sponsorship to a file.
	     * User will be redirected to request destination URL. 
	     * 
	     * @param request servlet request
	     * @param response servlet response
	     */
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, java.io.IOException {    	
	    	Cookie[] cookies = request.getCookies();
	    	Cookie tempDestURLcookie = null;
	    	String requestDestinationURL = request.getParameter("link");
	    	String clientIpParam = (String)request.getRemoteAddr();
	    	String sourceIP = request.getParameter("ip");
	    		        
	    	String destinationURL = null;
	    	String newDestinationURL = null;
	    	DataSource ds = null;
	    	Connection con = null;
	    	Context ctx = null;
	    		    		
	    	request.setAttribute("clientIp", sourceIP);	    	
	    	request.setAttribute("requestURL", requestDestinationURL);	    	
	    	request.setAttribute("clientIpParam", clientIpParam);
	    	   		
	    	boolean sendRequestToSurvey = true;
	    	
	    	try {  	    	            
		    	if(cookies != null) {
		    		destinationURL = MinesUtilities.getCookieValue(cookies, 
		    				"destination_url");
		    	
		    		if(destinationURL != null)
		    			destinationURL = URLDecoder.decode(destinationURL,"UTF-8");
		    		
		    		newDestinationURL = MinesUtilities.getCookieValue(cookies, "new_destination_url");
		    	
		    		if(newDestinationURL != null)
		    			newDestinationURL = URLDecoder.decode(newDestinationURL,"UTF-8");
		    	
		    		if(destinationURL != null) {
		    			/* if cookie destination_url is not equal to request url,
		    			 * write other cookies values to local file
		    			 * */
		    			tempDestURLcookie = MinesUtilities.getCookie(cookies, 
	    				"new_destination_url");		
		    			//requestDestinationURL = URLEncoder.encode(requestDestinationURL,"UTF-8");
	    				tempDestURLcookie.setValue( URLEncoder.encode(requestDestinationURL,"UTF-8") ) ;
	    				tempDestURLcookie.setMaxAge(timeoutDestUrl);		 		    		
	    				response.addCookie( tempDestURLcookie ) ;
	    				cookies = request.getCookies();
		    				
	    				if (!MinesUtilities.isDomainEqual(requestDestinationURL, newDestinationURL)) {			    				
		    				MinesUtilities.writeCookiesToFile(cookies, filePath);
	    				}
		    			
	    	    		ctx = new InitialContext();
	    		    	ds = (DataSource) ctx.lookup(dataSourceRef);
	    	            con = ds.getConnection();
		    			/* update sourceIP along with a timestamp into database */
		    			if(sourceIP != null) {		    					   
		    				MinesDbUtilities.updateIpTimestamp(sourceIP, con);	
		    			}
		    			
		    			sendRequestToSurvey = false;
		    			
		    		}
		    	} 
	    	} catch (Exception ex) {
	    		System.out.println("Exception in MinesHandlerServlet.doGet(): "+ex);
            } finally {
				MinesDbUtilities.closeConnection(con);			
			}
            
            if(sendRequestToSurvey)
            	getServletConfig().getServletContext().getRequestDispatcher("/survey.jsp").forward(request, response);            
            else {
            	if(requestDestinationURL.startsWith("http://"))
            		response.sendRedirect(requestDestinationURL);
            	else
            		response.sendRedirect("http://"+requestDestinationURL);
            }
	    }
	    	    
}

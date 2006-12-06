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

/**
 * @author vchu
 *
 */
public class DbTestServlet extends HttpServlet {
		private static String dataSourceRef = null;
		
	    public void init(ServletConfig config) throws ServletException {
	    	ServletContext ctx = config.getServletContext();
	    	dataSourceRef = ctx.getInitParameter("dataSourceRef");
	    	
	        super.init(config);
	    }
	    
	    /** Destroys the servlet.
	     */
	    public void destroy() {
	        
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, java.io.IOException {
	    	doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, java.io.IOException {    	
	    	DataSource ds = null;
	    	Connection con = null;
	    	Context ctx = null;
	    	String error = null;
	    	try {  	    	            		    	
	    		ctx = new InitialContext();
		    	ds = (DataSource) ctx.lookup(dataSourceRef);
	            con = ds.getConnection();		    					   
				if(MinesDbUtilities.testRead(con))
					error = "Connected to the database";					
				else
					error = "Problem connecting to database";
		    			
	    	} catch (Exception ex) {
            	log("Exception in DbTestServlet.doGet(): "+ex);
            	error = "Exception: "+ex;
            } 
            
	    	response.setContentType("text/html");
            java.io.PrintWriter out = response.getWriter();
            out.println(error);
	    }
	    	    
}

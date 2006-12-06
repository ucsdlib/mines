/*
 * Created on Apr 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.ucsd.itd.mines;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vchu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MinesDbUtilities {
	private MinesDbUtilities() {}
	
	public static void insertIP (String ip, Connection con){	
		if(ip != null) {
			PreparedStatement pstmt = null;
			try {
			    String query = "insert into tbl_mines_client values (inet_aton(?),now())";	
			    pstmt = con.prepareStatement(query);
			    pstmt.clearParameters();
			    
				pstmt.setString(1,ip);
				
				pstmt.execute();
			} catch (SQLException e) {
				System.out.println("SQLException in insertIP: "+e);
			} /*finally {
				closeConnection(con, pstmt);			
			}*/
		}
	}
	
	public static void updateIpTimestamp (String ip, Connection con){	
		Statement stmt = null;
		if(ip != null) {			
			try {			
			    String query = "update tbl_mines_client set clm_date_time = now() " +
			    		"where clm_source_ip = inet_aton('"+ ip +"')";	
			    stmt = con.createStatement();
				
			    stmt.executeUpdate(query);	

			} catch (SQLException e) {
				System.out.println("SQLException in updateIpTimestamp: "+e);
			} /*finally {
				closeConnection(con, stmt, null);			
			}*/			
		}
	}	
	
	public static boolean isIpExist (String ip, Connection con){	
		Statement stmt = null;
		ResultSet rs = null;
		boolean exist = false;
		if(ip != null) {
			try {
			    String query = "select count(*) as c from tbl_mines_client " +
			    		"where clm_source_ip = inet_aton('"+ip+"')";	
			    stmt = con.createStatement();
			    						
				rs = stmt.executeQuery(query);
				
				int count = 0;
				if(rs.next())
					count = rs.getInt("c");
				
				if(count == 1)
					exist = true;
				
			} catch (SQLException e) {
				System.out.println("SQLException in updateTimestamp: "+e);
			} /*finally {
				closeConnection(null, stmt, rs);
			}*/
		}
		
		return exist;
	}
	
	public static boolean testRead (Connection con){	
		Statement stmt = null;
		ResultSet rs = null;
		boolean isDbUp = false;
		if(con != null) {
			try {
			    String query = "select 1";	
			    stmt = con.createStatement();
			    						
				rs = stmt.executeQuery(query);
				
				if(rs.next())
					isDbUp = true;
				
			} catch (SQLException e) {
				System.out.println("SQLException in testRead(): "+e);
			} finally {
				closeConnection(con, stmt, rs);
			}
		}
		return isDbUp;
	}
	
	public static void closeConnection(Connection con) {
		if (con != null) {
			 try {
		    	con.close();
		    } catch(SQLException sqle) {}
		}
	}	
	
	public static void closeConnection(Connection con, PreparedStatement pstmt) {
		closeConnection(con, pstmt, null);
	}	

	public static void closeConnection(PreparedStatement pstmt, ResultSet rs) {
		closeConnection(null, pstmt, rs);
	}
	
	public static void closeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(rs != null) {
			try {
			    rs.close();
			} catch(SQLException sqle) {}
		}
		
		if (pstmt != null) {
		    try {
		    	pstmt.close();
		    } catch(SQLException sqle) {}
		}
		
		if (con != null) {
			 try {
		    	con.close();
		    } catch(SQLException sqle) {}
		}
	}	
	
	public static void closeConnection(Connection con, Statement stmt , ResultSet rs) {
	
		if(rs != null) {
			try {
			    rs.close();
			} catch(SQLException sqle) {}
		}
		
		if (stmt != null) {
		    try {
		    	stmt.close();
		    } catch(SQLException sqle) {}
		}
		
		if (con != null) {
			 try {
		    	con.close();
		    } catch(SQLException sqle) {}
		}
	}			
}

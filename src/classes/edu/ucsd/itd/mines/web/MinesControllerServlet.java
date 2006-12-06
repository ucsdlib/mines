package edu.ucsd.itd.mines.web;

import edu.ucsd.itd.mines.SurveyField;
import edu.ucsd.itd.mines.service.ServiceLocatorImpl;
import edu.ucsd.itd.mines.service.ServiceLocator;
import edu.ucsd.itd.mines.service.MinesService;
import edu.ucsd.itd.mines.util.ConversionUtil;
import edu.ucsd.itd.mines.dto.MinesSurvey;
import edu.ucsd.itd.mines.MinesUtilities;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.*;

/**
 * Handles GET and POST for Mines.
 */
public class MinesControllerServlet extends HttpServlet {

    /**
     * Default URL to redirect to.
     */
    public static final String DEFAULT_REDIRECT_URL = "http://ucsd.edu";

    /**
     * Name of parameter containing the link.
     */
    public static final String PARAM_LINK = "link";

    /**
     * Name of parameter containing the ip.
     */
    public static final String PARAM_IP = "ip";
    
    /**
     * Name of attribute binding to the client IP.
     */
    public static final String ATTR_CLIENT_IP = "clientIp";

    /**
     * Name of attribute binding to the client IP.
     */
    public static final String ATTR_CLIENT_IP_PARAM = "clientIpParam";
    
    /**
     * Name of the attribute binding to the request URL.
     */
    public static final String ATTR_REQUEST_URL = "requestURL";

    /**
     * Name of the page to forward to.
     */
    public static final String FORWARD_PAGE_SURVEY = "/survey.jsp";

    private static int timeoutDestUrl = 0;
    private static int timeoutOtherParam = 0;

    // The ServiceLocator
    static MinesService minesService;

    public void init(ServletConfig config) throws ServletException {
        ServletContext ctx = config.getServletContext();
        timeoutDestUrl = Integer.parseInt((String) ctx.getInitParameter("timeoutDestinationURL"));
        timeoutOtherParam = Integer.parseInt((String) ctx.getInitParameter("timeoutOtherParam"));

        // set up the service to use.
        ServiceLocator serviceLocator = ServiceLocatorImpl.getInstance();
        minesService = serviceLocator.getMinesService();

        super.init(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String requestDestinationUrl = req.getParameter(PARAM_LINK);
        String clientIpParam = (String)req.getRemoteAddr();
    	String sourceIp = req.getParameter(PARAM_IP);
    	
        boolean sendRequestToSurvey = true;

        MinesSurvey minesSurvey = ConversionUtil.cookiesToMinesSurvey(req.getCookies());
        String destinationUrl = minesSurvey.getDestinationUrl();
        String newDestinationURL = minesSurvey.getNewDestinationUrl();

        if(destinationUrl != null) {
            sendRequestToSurvey = false;
			
			if(updateCookie(req, res, SurveyField.NEW_DESTINATION_URL.getFieldName(), 
					requestDestinationUrl))
				minesSurvey.setNewDestinationUrl(requestDestinationUrl);
			else
				log("can't udpate new_destination_url cookie");
			
            if(newDestinationURL != null && 
            		!MinesUtilities.isDomainEqual(requestDestinationUrl, newDestinationURL)) {
                minesService.saveSurvey(minesSurvey, sourceIp);
            }
        }

        if (sendRequestToSurvey) {
            // set up forwarding and forwards user to survey
            req.setAttribute(ATTR_CLIENT_IP, sourceIp);
            req.setAttribute(ATTR_CLIENT_IP_PARAM, clientIpParam);
            req.setAttribute(ATTR_REQUEST_URL, requestDestinationUrl);
            getServletConfig().getServletContext().getRequestDispatcher(FORWARD_PAGE_SURVEY).forward(req, res);
        } else {
        	if(requestDestinationUrl.startsWith("http://"))
        		res.sendRedirect(requestDestinationUrl);
        	else
        		res.sendRedirect("http://"+requestDestinationUrl);
        }
    }

    /**
     * First cookies are processed.
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String redirectUrl = DEFAULT_REDIRECT_URL;

        // Process Cookies
        Map<SurveyField, String> cookieValueMap = new EnumMap<SurveyField, String>(SurveyField.class);
        for(SurveyField type : SurveyField.values()) {
            String name = type.getFieldName();
            String value = req.getParameter(name);

            if(value != null) {
                cookieValueMap.put(type, value);
                
                Cookie cookie = new Cookie(type.getFieldName(), value);

                // if NOT destintation URL, set the default ('other') timeout value.
                if(!SurveyField.DESTINATION_URL.getFieldName().equals(name)) {
                    cookie.setMaxAge(timeoutOtherParam);
                } else if(SurveyField.DESTINATION_URL.getFieldName().equals(name)) {
                    // destination url found, set timeout and update redirecturl.
                    cookie.setMaxAge(timeoutDestUrl);
                    redirectUrl = value;
                }

                res.addCookie(cookie);
            }
        }

        // Convert to DTO and persist to file and database
        MinesSurvey minesSurvey = ConversionUtil.mapToMinesSurvey(cookieValueMap);
        minesService.saveSurvey(minesSurvey, minesSurvey.getPatronLocation());

        // Redirect
        if(redirectUrl.startsWith("http://"))
        	res.sendRedirect(redirectUrl);
        else
        	res.sendRedirect("http://"+redirectUrl);
    }
    
	 private boolean updateCookie(HttpServletRequest req, HttpServletResponse res,  String cookieName,
	 		String newValue) {
	 	Cookie[] cookies = req.getCookies();
	 	Cookie cookie = null;
	 	boolean successUpdate = false;
	 	
	 	if(cookies != null) {
		 	cookie = MinesUtilities.getCookie(cookies, cookieName);		 		    		
			cookie.setValue( newValue ) ;
			cookie.setMaxAge(timeoutDestUrl);		 		    		
			res.addCookie( cookie ) ;
			successUpdate = true;
	 	}
	 	
	 	return successUpdate;
	 }
}

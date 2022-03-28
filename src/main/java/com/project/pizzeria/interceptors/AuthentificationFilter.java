package com.project.pizzeria.interceptors;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pizzeria.beans.Token;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.TokenService;
import com.project.pizzeria.services.UserService;
import com.project.pizzeria.utils.Constants;
import com.project.pizzeria.utils.enumuration.UserStatus;


/**
 * Servlet Filter implementation class AuthentificationFilter
 */
@WebFilter("/api/*")
public class AuthentificationFilter implements Filter {
	private static TokenService tokenService;
	private static UserService userService;

    /**
     * Default constructor. 
     */
    public AuthentificationFilter() {
    	tokenService = new TokenService();
    	userService = new UserService();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse responseHttp = (HttpServletResponse)response;
		 responseHttp.addHeader("Access-Control-Allow-Origin", "*");
	     responseHttp.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
	     responseHttp.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
	     responseHttp.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
	     if (httpRequest.getMethod().equals("OPTIONS")) {
	         responseHttp.setStatus(HttpServletResponse.SC_OK);
	         return;
	     }
		String token = httpRequest.getHeader(Constants.AUTHORIZATION_HEADER);
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		if(token==null)
		{
			httpServletResponse.setStatus(401);
	        httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
	        (new ObjectMapper()).writeValue(httpServletResponse.getWriter(), 
	        		new ExceptionHandler(new Exception("UNAUTHNETIFICATED")));
	        return;
		}
		token=token.substring(Constants.AUTHORIZATION_PREFIX.length());
		try {
			Token tokenData = tokenService.checkTokenValidity(token);
			if(tokenData.getUser()==null)
				throw new SQLException("Invalid token");
			if(tokenData.getExpiration().before(new Timestamp(System.currentTimeMillis())))
				throw new SQLException("Expired token");
			User user = userService.findUserById(tokenData.getUser());
			if(!user.getStatus().equals(UserStatus.ACTIVE.toString()))
				throw new SQLException("Inactive user");
			request.setAttribute("user", user);
			request.setAttribute("token", tokenData);
		} catch (SQLException e) {
			httpServletResponse.setStatus(401);
	        httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
	        (new ObjectMapper()).writeValue(httpServletResponse.getWriter(), 
	        		new ExceptionHandler(e));
	        return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

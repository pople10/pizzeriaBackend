package com.project.pizzeria.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pizzeria.beans.Log;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.LogService;
import com.project.pizzeria.utils.GenericUtil;
import com.project.pizzeria.utils.enumuration.UserRole;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
@WebFilter("/api/*")
public class AuthorizationFilter implements Filter {
	
	private Map<String,List<String>> middleware = new HashMap<String,List<String>>();
	
	private static LogService logService = new LogService();

    /**
     * Default constructor. 
     */
    public AuthorizationFilter() {
    	List<String> onlyUser = new ArrayList<String>();
    	onlyUser.add(UserRole.USER.toString());
        middleware.put("api/user",onlyUser);
    	List<String> onlyAdmin = new ArrayList<String>();
    	onlyAdmin.add(UserRole.ADMIN.toString());
        middleware.put("api/admin",onlyAdmin);
        List<String> shared = new ArrayList<String>();
        shared.add(UserRole.ADMIN.toString());
        shared.add(UserRole.USER.toString());
        shared.add(UserRole.DELIVERY.toString());
        middleware.put("api/shared",shared);
        List<String> deliveryOnly = new ArrayList<String>();
        deliveryOnly.add(UserRole.DELIVERY.toString());
        middleware.put("api/delivery", deliveryOnly);
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
		String url = ((HttpServletRequest)request).getRequestURL().toString();
		User user = (User) request.getAttribute("user");
		if(user!=null)
			for(Entry<String,List<String>> entry : this.middleware.entrySet())
			{
				if(url.contains(entry.getKey())&&!entry.getValue().contains(user.getRole()))
				{
					HttpServletResponse httpServletResponse = (HttpServletResponse)response;
					httpServletResponse.setStatus(403);
			        httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
			        (new ObjectMapper()).writeValue(httpServletResponse.getWriter(), 
			        		new ExceptionHandler(new Exception("UNAUTHORIZED")));
			        return;
				}
			}
		chain.doFilter(request, response);
		/* Post interceptor */
		Log log = new Log();
		if(user!=null)
			log.setUser(user.getId());
		log.setIp(GenericUtil.getClientIpAddress((HttpServletRequest)request));
		logService.logForUser(log);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

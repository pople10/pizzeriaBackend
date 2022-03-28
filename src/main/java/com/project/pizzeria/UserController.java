package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.beans.Token;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.TokenService;
import com.project.pizzeria.services.UserService;
import com.project.pizzeria.utils.Constants;
import com.project.pizzeria.utils.enumuration.UserRole;

@Singleton
@Path("/")
public class UserController {
	@Context
    HttpServletRequest request;
	
	private static UserService userService = new UserService();
	private static TokenService tokenService = new TokenService();

	
	@Path("/")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response signout()
	{
		String tokenString = request.getHeader(Constants.AUTHORIZATION_HEADER);
		tokenString=tokenString.substring(Constants.AUTHORIZATION_PREFIX.length());
		try {
			Token token = tokenService.checkTokenValidity(tokenString);
			if(!userService.logout(token))
				throw new SQLException("Unsuccessfull");
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@Path("/auth/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user)
	{		
		user.setRole(UserRole.USER.toString());
		try {
			userService.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(user).build();
	}
	
	@Path("/api/admin/account/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(User user)
	{		
		try {
			userService.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(user).build();
	}
	
	@Path("/api/admin/account")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccounts()
	{		
		List<User> list = new ArrayList<User>();
		try {
			 list = userService.findAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
	
	@Path("/api/admin/account/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccount(@PathParam("id") Long id )
	{		
		User list = null;
		try {
			 list = userService.findUserById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
	
	@Path("/api/admin/account/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response disable(@PathParam("id") Long id)
	{		
		try {
			 userService.disable(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@Path("/auth/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		Token token = new Token();
		User userDto = new User();
		try {
			token= userService.login(user);
			userDto=userService.findUserByEmail(user.getEmail());
			map.put("token", token);
			map.put("user", userDto);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(map).build();
	}
}

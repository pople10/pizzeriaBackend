package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
import com.project.pizzeria.services.UserService;
import com.project.pizzeria.utils.enumuration.UserRole;

@Singleton
@Path("/")
public class UserController {
	@Context
    HttpServletRequest request;
	
	private static UserService userService = new UserService();

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
		Token token = new Token();
		try {
			token= userService.login(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(token).build();
	}
	
	@Path("/api/auth/logout")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(User user)
	{
		Token token = (Token)request.getAttribute("token");
		try {
			if(!userService.logout(token))
				throw new SQLException("Unsuccessfull");
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).build();
	}
}

package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.TokenService;
import com.project.pizzeria.services.UserService;

@Path("/api")
@Singleton
public class UserAdminController {
	@Context
    HttpServletRequest request;
	
	private static UserService userService = new UserService();
	
	@Path("/admin/account")
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
	
	@Path("/admin/account")
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
	
	@Path("/admin/account/{id}")
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
	
	@Path("/admin/account/{id}")
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
	
}

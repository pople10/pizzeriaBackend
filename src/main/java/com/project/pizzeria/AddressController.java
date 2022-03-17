package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.beans.Address;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.AddressService;
import com.project.pizzeria.utils.enumuration.UserRole;

@Singleton
@Path("/api/user/address")
public class AddressController {
	@Context
    HttpServletRequest request;
	
	private static AddressService addressService = new AddressService();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Address address)
	{		
		User user = (User)request.getAttribute("user");
		address.setUser(user.getId());
		try {
			addressService.createAddress(address);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(address).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAddressesForUser()
	{		
		User user = (User)request.getAttribute("user");
		List<Address> list = new ArrayList<Address>();
		try {
			list=addressService.getAllAddressesByUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
}

package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.beans.Order;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.OrderService;
import com.project.pizzeria.utils.enumuration.OrderStatus;
import com.project.pizzeria.utils.enumuration.OrderType;

@Singleton
@Path("/api")
public class OrderController {
	@Context
    HttpServletRequest request;
	
	private static OrderService orderService = new OrderService();
	
	@POST
	@Path("/user/order")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Order order)
	{
		User user = (User) request.getAttribute("user");
		try {
			orderService.createOrder(order, user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(order).build();
	}
	
	@GET
	@Path("/user/order/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderForUser(@PathParam("id") Long id)
	{
		User user = (User) request.getAttribute("user");
		Map<String,Object> order = new HashMap<String,Object>();
		try {
			order=orderService.getOrderDataByIdForUser(user,id);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
	
	@GET
	@Path("/user/order")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersForUser()
	{
		User user = (User) request.getAttribute("user");
		List<Order> orders = new ArrayList<Order>();
		try {
			orders=orderService.getAllOrders(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(orders).build();
	}
	
	@Path("/admin/status")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderStatus()
	{	
		List<String> list = new ArrayList<String>();
		for(OrderStatus orderType : OrderStatus.values())
		{
			list.add(orderType.toString());
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
	
	@Path("/delivery/order")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrdersForDelivery()
	{	
		User user = (User) request.getAttribute("user");
		List<Order> orders = new ArrayList<Order>();
		try {
			orders=orderService.getOrdersForDelivery(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(orders).build();
	}
	
	@Path("/delivery/order/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderForDelivery(@PathParam("id") Long id)
	{	
		User user = (User) request.getAttribute("user");
		Order order = new Order();
		try {
			order=orderService.getOrderForDelivery(id, user);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
	
	@Path("/delivery/order/{id}/out")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response outForDelivery(@PathParam("id") Long id)
	{	
		User user = (User) request.getAttribute("user");
		Order order = new Order();
		try {
			order=orderService.getOrderForDelivery(id, user);
			orderService.updateOrderStatus(id,OrderStatus.OUT_FOR_DELIVERY);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
	
	@Path("/delivery/order/{id}/deliveried")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response deliveried(@PathParam("id") Long id)
	{	
		User user = (User) request.getAttribute("user");
		Order order = new Order();
		try {
			order=orderService.getOrderForDelivery(id, user);
			orderService.updateOrderStatus(id,OrderStatus.DELIVERED);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
	
	@GET
	@Path("/user/admin/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@PathParam("id") Long id)
	{
		Map<String,Object> order = new HashMap<String,Object>();
		try {
			order=orderService.getOrderDataById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
	
	@GET
	@Path("/admin/order")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders()
	{
		List<Order> orders = new ArrayList<Order>();
		try {
			orders=orderService.getAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(orders).build();
	}
	
	@Path("/admin/order/{id}/status/{status}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatus(@PathParam("id") Long id,@PathParam("status") String status)
	{	
		Order order = new Order();
		try {
			orderService.updateOrderStatus(id,OrderStatus.valueOf(status));
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
	
	@Path("/admin/order/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignDelivery(@PathParam("id") Long id,@FormParam("delivery") Long delivery)
	{	
		Order order = new Order();
		try {
			orderService.assignDelivery(id, delivery);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(order).build();
	}
}

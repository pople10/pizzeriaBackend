package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.beans.Product;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.ProductService;
import com.project.pizzeria.utils.enumuration.CouponType;
import com.project.pizzeria.utils.enumuration.OrderType;

@Singleton
@Path("/public")
public class PublicController {
	@Context
    HttpServletRequest request;
	
	private static ProductService productService = new ProductService();
	
	@Path("/order/type")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderTypes()
	{	
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(OrderType orderType : OrderType.values())
		{
			Map<String,String> map = new HashMap<String,String>();
			map.put("label",orderType.getLabel());
			map.put("value",orderType.toString());
			list.add(map);
		}
		return Response.status(Response.Status.OK).entity(list).build();
	} 
	
	@Path("/coupon/type")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCouponType()
	{	
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(CouponType orderType : CouponType.values())
		{
			Map<String,String> map = new HashMap<String,String>();
			map.put("label",orderType.getLabel());
			map.put("value",orderType.toString());
			list.add(map);
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
	
	@Path("/product")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProducts()
	{	
		List<Product> list = new ArrayList<Product>();
		try {
			list=productService.findAllAvailableProducts();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
	
	@Path("/product/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("id") Long id)
	{	
		Product product = new Product();
		try {
			product=productService.findProductById(id);
			if(!product.getAvailability())
				throw new SQLException("Product is not available");
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(product).build();
	}
}

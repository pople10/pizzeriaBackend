package com.project.pizzeria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.utils.enumuration.CouponType;
import com.project.pizzeria.utils.enumuration.OrderType;

@Singleton
@Path("/public")
public class PublicController {
	@Context
    HttpServletRequest request;
	
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
}

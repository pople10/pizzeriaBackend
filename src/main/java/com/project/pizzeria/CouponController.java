package com.project.pizzeria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.pizzeria.beans.Coupon;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.CouponService;

@Singleton
@Path("/api/admin/coupon")
public class CouponController {
	@Context
    HttpServletRequest request;
	
	private static CouponService couponService = new CouponService();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Coupon coupon)
	{		
		try {
			couponService.createCoupon(coupon);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(coupon).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Coupon coupon)
	{		
		try {
			couponService.updateCoupon(coupon);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(coupon).build();
	}
	
	@Path("/{code}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("code") String code)
	{		
		try {
			couponService.deleteCoupon(code);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@Path("/{code}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoupon(@PathParam("code") String code)
	{		
		Coupon coupon = new Coupon();
		try {
			coupon=couponService.findCouponByCode(code);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(coupon).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoupons()
	{		
		List<Coupon> list = new ArrayList<Coupon>();
		try {
			list=couponService.findAllCoupons();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
}

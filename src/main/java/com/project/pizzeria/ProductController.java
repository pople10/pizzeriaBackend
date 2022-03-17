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

import com.project.pizzeria.beans.Product;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.exeptions.ExceptionHandler;
import com.project.pizzeria.services.ProductService;

@Singleton
@Path("/api")
public class ProductController {
	@Context
    HttpServletRequest request;
	
	private ProductService productService = new ProductService();
	
	@Path("/api/admin/product")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Product product)
	{		
		try {
			productService.createProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.CREATED).entity(product).build();
	}
	
	@Path("/api/admin/product")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modify(Product product)
	{		
		try {
			productService.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(product).build();
	}

	@Path("/api/admin/product/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Long id)
	{		
		try {
			productService.deleteProduct(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@Path("/api/admin/product")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProducts()
	{		
		List<Product> list = new ArrayList<Product>();
		try {
			list=productService.findAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(list).build();
	}
	
	@Path("/api/shared/product")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableProduct()
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
	
	@Path("/api/shared/product/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableProduct(@PathParam("id") Long id)
	{		
		Product product = new Product();
		try {
			product=productService.findProductById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(
					new ExceptionHandler(e)).build();
		}
		return Response.status(Response.Status.OK).entity(product).build();
	}

}

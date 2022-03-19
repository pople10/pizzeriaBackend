package com.project.pizzeria.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.project.pizzeria.beans.Coupon;
import com.project.pizzeria.beans.Order;
import com.project.pizzeria.beans.Product;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.dao.daoImp.OrderDao;
import com.project.pizzeria.utils.enumuration.OrderStatus;
import com.project.pizzeria.utils.enumuration.OrderType;
import com.project.pizzeria.utils.enumuration.UserRole;

public class OrderService {
	private static OrderDao orderDao;
	private static AddressService addressService;
	private static ProductService productService;
	private static CouponService couponService;
	private static UserService userService;
	
	public OrderService() 
	{
		orderDao = new OrderDao();
		addressService = new AddressService();
		productService = new ProductService();
		couponService = new CouponService();
		userService = new UserService();
	}
	
	public boolean createOrder(Order order,User user) throws SQLException
	{
		if(!order.getType().equals(OrderType.CASH_ON_DELIVERY.toString()))
			order.setType(OrderType.RESERVATION.toString());
		if(addressService.findAddressById(order.getAddress()).getUser()!=user.getId())
			throw new SQLException("Not allowed to retrieve this data");
		Coupon coupon = couponService.findCouponByCode(order.getCoupon());
		if(coupon.getId()==null||coupon.getExpiration().before(new Date()))
			throw new SQLException("Coupon expired or doesn't exist");
		Product product = productService.findProductById(order.getProduct());
		if(product.getId()==null||product.getAvailability()!=true)
			throw new SQLException("Product doesn't exist or it is not available");
		order.setDelivery(null);
		order.setWanted_at(new Timestamp(order.getWanted_at().getTime()+TimeUnit.MINUTES.toMillis(product.getPreparation_time_in_min())));
		order.setStatus(OrderStatus.PENDING.toString());
		order.setCreated_at(new Timestamp(System.currentTimeMillis()));
		order.setUpdated_at(order.getCreated_at());
		return orderDao.create(order.toMap());
	}
	
	public boolean assignDelivery(Long idOrder,Long idUser) throws SQLException
	{
		User user = userService.findUserById(idUser);
		if(user.getId()==null||user.getRole().equals(UserRole.DELIVERY.toString()))
			throw new SQLException("User is not a delivery or doesn't exist");
		Order order = getOrderById(idOrder);
		if(order.getId()==null)
			throw new SQLException("Order doesn't exist");
		if(order.getDelivery()!=null)
			throw new SQLException("A delivery man already assigned");
		order.setDelivery(idUser);
		return this.updateOrder(order);
	}
	
	public boolean updateOrderStatus(Long id,OrderStatus orderStatus) throws SQLException
	{
		Order order = getOrderById(id);
		if(order.getId()==null)
			throw new SQLException("Order doesn't exist");
		if(order.getStatus().equals(OrderStatus.COMPLETED.toString()))
			throw new SQLException("Already completed order");
		order.setStatus(orderStatus.toString());
		return this.updateOrder(order);
	}
	
	public boolean updateOrder(Order order) throws SQLException
	{
		return orderDao.update(order.toMap(), order.getId());
	}
	
	public Order getOrderById(Long id) throws SQLException
	{
		Order order = new Order();
		return (Order) order.mapper(orderDao.findById(id),order);
	}
	
	public Order getOrderByIdForUser(User user,Long id) throws SQLException
	{
		Order order = new Order();
		order = (Order) order.mapper(orderDao.findById(id),order);
		if(addressService.findAddressById(order.getAddress()).getUser()!=user.getId())
			throw new SQLException("Not allowed to retrieve this data");
		return order;
	}
}

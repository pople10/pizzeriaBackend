package com.project.pizzeria.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.project.pizzeria.beans.Address;
import com.project.pizzeria.beans.Coupon;
import com.project.pizzeria.beans.Order;
import com.project.pizzeria.beans.Product;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.dao.daoImp.OrderDao;
import com.project.pizzeria.utils.enumuration.CouponType;
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
	
	public Order createOrder(Order order,User user) throws SQLException
	{
		if(!order.getType().equals(OrderType.CASH_ON_DELIVERY.toString()))
			order.setType(OrderType.RESERVATION.toString());
		if(!addressService.findAddressById(order.getAddress()).getUser().equals(user.getId()))
			throw new SQLException("Not allowed to retrieve this data");
		Coupon coupon = new Coupon();
		if(order.getCoupon()!=null)
		{
			coupon = couponService.findCouponByCode(order.getCoupon());
			if(coupon.getId()==null||coupon.getExpiration().before(new Date()))
				throw new SQLException("Coupon expired or doesn't exist");
		}
		Product product = productService.findProductById(order.getProduct());
		if(product.getId()==null||product.getAvailability().equals(false))
			throw new SQLException("Product doesn't exist or it is not available");
		Float amount = product.getPrice();
		order.setTotal(amount);
		order.setPaid(order.getTotal());
		if(order.getCoupon()!=null&&coupon.getId()!=null)
		{
			String couponType = coupon.getType();
			if(couponType.equals(CouponType.POURCENCTAGE.toString()))
				order.setPaid((float) (order.getPaid()*(1f-coupon.getAmount()*0.01)));
			else
			{
				Float paid = order.getPaid()-coupon.getAmount();
				if(paid<0)
					order.setPaid(0f);
				else
					order.setPaid(paid);
			}
		}
		order.setDelivery(null);
		if(order.getWanted_at().before(new Date()))
			throw new SQLException("You want it before time or what?");
		order.setWanted_at(new Timestamp(order.getWanted_at().getTime()+TimeUnit.MINUTES.toMillis(product.getPreparation_time_in_min())));
		order.setStatus(OrderStatus.PENDING.toString());
		order.setCreated_at(new Timestamp(System.currentTimeMillis()));
		order.setUpdated_at(order.getCreated_at());
		Long id=orderDao.createOrder(order.toMap());
		return this.getOrderById(id);
	}
	
	public List<Order> getAllOrders() throws SQLException
	{
		Order order = new Order();
		return (List<Order>) order.mapperList(orderDao.findAll(), order);
	}
	
	public List<Order> getAllOrders(User user) throws SQLException
	{
		Order order = new Order();
		List<Address> addressList = addressService.getAllAddressesByUser(user);
		return (List<Order>) order.mapperList(orderDao.findByAddressList(addressList), order);
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
		order.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		return orderDao.update(order.toMap(), order.getId());
	}
	
	private Order getOrderById(Long id) throws SQLException
	{
		Order order = new Order();
		return (Order) order.mapper(orderDao.findById(id),order);
	}
	
	private Order getOrderByIdForUser(User user,Long id) throws SQLException
	{
		Order order = new Order();
		order = (Order) order.mapper(orderDao.findById(id),order);
		if(order.getId()==null)
			throw new SQLException("Order doesn't exist");
		if(!addressService.findAddressById(order.getAddress()).getUser().equals(user.getId()))
			throw new SQLException("Not allowed to retrieve this data");
		return order;
	}
	
	public Map<String,Object> getOrderDataByIdForUser(User user,Long id) throws SQLException
	{
		Map<String,Object> map = new HashMap<String,Object>();
		Order order = getOrderByIdForUser(user,id);
		map.put("order", order);
		Address address = addressService.findAddressById(order.getAddress());
		map.put("address",address);
		map.put("user",userService.findUserById(address.getUser()));
		map.put("product", productService.findProductById(order.getProduct()));
		return map;
	}
	
	public List<Order> getOrdersForDelivery(User user) throws SQLException
	{
		Order order = new Order();
		return (List<Order>) order.mapperList(orderDao.findByDelivery(user.getId()), order);
	}
	
	public Order getOrderForDelivery(Long id,User user) throws SQLException
	{
		Order order = new Order();
		order = (Order) order.mapper(orderDao.findById(id), order);
		if(order.getId()==null||order.getDelivery()==null||!order.getDelivery().equals(user.getId()))
			throw new SQLException("Your are not responsible for this order");
		return order;
	}
	
	public Map<String,Object> getOrderDataById(Long id) throws SQLException
	{
		Map<String,Object> map = new HashMap<String,Object>();
		Order order = getOrderById(id);
		map.put("order", order);
		Address address = addressService.findAddressById(order.getAddress());
		map.put("address",address);
		map.put("user",userService.findUserById(address.getUser()));
		map.put("product", productService.findProductById(order.getProduct()));
		User delivery = userService.findUserById(order.getDelivery());
		if(delivery.getId()==null)
			delivery=null;
		map.put("delivery",delivery);
		return map;
	}
	
	public List<User> getDeliveries() throws SQLException
	{
		return userService.getDeliveries();
	}
}

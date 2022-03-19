package com.project.pizzeria.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.project.pizzeria.beans.Coupon;
import com.project.pizzeria.dao.daoImp.CouponDao;

public class CouponService {
	private static CouponDao couponDao;
	
	public CouponService()
	{
		couponDao = new CouponDao();
	}
	
	public boolean createCoupon(Coupon coupon) throws SQLException
	{
		Coupon tmp = this.findCouponByCode(coupon.getCode());
		if(tmp.getId()!=null)
			throw new SQLException("Coupon already exists with that code");
		return couponDao.create(coupon.toMap());
	}
	
	public Coupon findCouponByCode(String code) throws SQLException
	{
		Coupon tmp = new Coupon();
		return (Coupon)tmp.mapper(couponDao.findByCode(code), tmp);
	}
	
	public List<Coupon> findAllCoupons() throws SQLException
	{
		Coupon tmp = new Coupon();
		return (List<Coupon>) tmp.mapperList(couponDao.findAll(), tmp);
	}
	
	public boolean deleteCoupon(String code) throws SQLException
	{
		Coupon coupon = this.findCouponByCode(code);
		coupon.setExpiration(new Timestamp(System.currentTimeMillis()));
		return couponDao.update(coupon.toMap(), coupon.getId());
	}

}

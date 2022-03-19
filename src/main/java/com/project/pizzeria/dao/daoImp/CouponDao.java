package com.project.pizzeria.dao.daoImp;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pizzeria.dao.genericdao.GenericDao;

public class CouponDao extends GenericDao {

	public CouponDao() {
		super("coupon");
	}
	
	public ResultSet findByCode(String code) throws SQLException
	{
		return this.findByColumn(code, "code");
	}

}

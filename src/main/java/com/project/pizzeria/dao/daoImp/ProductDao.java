package com.project.pizzeria.dao.daoImp;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pizzeria.dao.genericdao.GenericDao;

public class ProductDao extends GenericDao {

	public ProductDao() {
		super("products");
	}
	
	public ResultSet findByAvailability(Boolean flag) throws SQLException
	{
		return super.findByColumn(flag, "availability");
	}
}

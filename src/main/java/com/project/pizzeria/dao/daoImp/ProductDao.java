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
		String result="0";
		if(flag.equals(true))
			result="1";
		return super.findByColumn(result, "availability");
	}
}

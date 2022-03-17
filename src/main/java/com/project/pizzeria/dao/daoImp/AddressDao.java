package com.project.pizzeria.dao.daoImp;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pizzeria.dao.genericdao.GenericDao;

public class AddressDao extends GenericDao {

	public AddressDao() {
		super("address");
	}

	public ResultSet findByUserId(Long id) throws SQLException
	{
		return super.findByColumn(id, "user");
	}
}

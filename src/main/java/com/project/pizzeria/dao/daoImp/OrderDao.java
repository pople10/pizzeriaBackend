package com.project.pizzeria.dao.daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.pizzeria.beans.Address;
import com.project.pizzeria.dao.DBConnection;
import com.project.pizzeria.dao.genericdao.GenericDao;
import com.project.pizzeria.utils.GenericUtil;

public class OrderDao extends GenericDao {

	public OrderDao() {
		super("orders");
	}

	public ResultSet findByAddressList(List<Address> list) throws SQLException
	{
		List<Long> listToUse = new ArrayList<>();
		for(Address address : list)
		{
			listToUse.add(address.getId());
		}
		try {
			 String sql = "SELECT * FROM "+DB_NAME+" WHERE address IN "+GenericUtil.generateInClauseArgs(listToUse);
			 PreparedStatement statement = DBConnection.openSession().getConnection().prepareStatement(sql);
			 //statement.setString(1,);
			 //System.out.println(sql);
			 return statement.executeQuery();
		 }
		 catch(SQLException sqlExp)
		 {
			 throw sqlExp;
		 }
		 catch(Exception e)
		 {
			 return null;
		 }
		 finally {
			 //DBConnection.openSession().closeSession();
		 }
	}
	
	public ResultSet findByDelivery(Long id) throws SQLException
	{
		return this.findByColumn(id, "delivery");
	}
}

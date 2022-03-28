package com.project.pizzeria.dao.daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.project.pizzeria.beans.Address;
import com.project.pizzeria.dao.DBConnection;
import com.project.pizzeria.dao.genericdao.GenericDao;
import com.project.pizzeria.utils.GenericUtil;

public class OrderDao extends GenericDao {

	public OrderDao() {
		super("orders");
	}
	
	public Long createOrder(Map<String,String> args) throws SQLException
	{
		try {
			 String sql = "INSERT INTO "+DB_NAME+" ("+GenericUtil.generateArgs(args)+") VALUES ("+GenericUtil.generateQuestionMarks(args)+")";
			 PreparedStatement statement = DBConnection.openSession().getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			 statement = GenericUtil.injectData(args, statement);
			 if(statement.executeUpdate()!=0)
			 {
				 try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				      if (generatedKeys.next()) {
				          return generatedKeys.getLong(1);
				      }
				      else {
				          throw new SQLException("Creating order failed.");
				      }
			      }
			 }
			 else
				 throw new SQLException("Creating order failed.");
		 }
		 catch(SQLException sqlExp)
		 {
			 throw sqlExp;
		 }
		 catch(Exception e)
		 {
			 return 0l;
		 }
		 finally {
			 DBConnection.openSession().closeSession();
		 }
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

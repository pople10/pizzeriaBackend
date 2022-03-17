package com.project.pizzeria.dao.daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pizzeria.dao.DBConnection;
import com.project.pizzeria.dao.genericdao.GenericDao;

public class TokenDao extends GenericDao{
	public TokenDao()
	{
		super("token");
	}

	public ResultSet findByToken(String token) throws SQLException
	{
		try {
			 String sql = "SELECT * FROM "+DB_NAME+" WHERE token=?";
			 PreparedStatement statement = DBConnection.openSession().getConnection().prepareStatement(sql);
			 statement.setString(1,token);
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
}

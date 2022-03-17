package com.project.pizzeria.dao.daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.pizzeria.dao.DBConnection;
import com.project.pizzeria.dao.genericdao.GenericDao;

public class UserDao extends GenericDao {

	public UserDao() {
		super("user");
	}
	
	public boolean checkIfEmailExist(String email) throws SQLException
	{
		try {
			 String sql = "SELECT * FROM "+DB_NAME+" WHERE email=?";
			 PreparedStatement statement = DBConnection.openSession().getConnection().prepareStatement(sql);
			 statement.setString(1,email);
			 return statement.executeQuery().isBeforeFirst();
		 }
		 catch(SQLException sqlExp)
		 {
			 throw sqlExp;
		 }
		 catch(Exception e)
		 {
			 return false;
		 }
		 finally {
			 //DBConnection.openSession().closeSession();
		 }
	}
	
	public ResultSet findByEmail(String email) throws SQLException
	{
		try {
			 String sql = "SELECT * FROM "+DB_NAME+" WHERE email=?";
			 PreparedStatement statement = DBConnection.openSession().getConnection().prepareStatement(sql);
			 statement.setString(1,email);
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

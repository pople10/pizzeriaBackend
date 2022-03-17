package com.project.pizzeria.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.project.pizzeria.beans.Token;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.beans.generic.GenericBean;
import com.project.pizzeria.dao.daoImp.TokenDao;
import com.project.pizzeria.dao.daoImp.UserDao;
import com.project.pizzeria.utils.Constants;
import com.project.pizzeria.utils.enumuration.UserStatus;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserService {
	private static UserDao userDao;
	private static TokenDao tokenDao;
	
	public UserService()
	{
		userDao = new UserDao();
		tokenDao = new TokenDao();
	}
	
	public Token login(User user) throws SQLException
	{
		if(user.getPassword()==null||user.getEmail()==null)
			throw new SQLException("Empty data");
		User tmp = new User();
		tmp.mapper(this.userDao.findByEmail(user.getEmail()), tmp);
		if(tmp.getEmail()==null)
			throw new SQLException("Bad credential");
		BCrypt.Result result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), tmp.getPassword());
		if(!result.verified)
			throw new SQLException("Bad credential");
		if(!tmp.getStatus().equals(UserStatus.ACTIVE.toString()))
			throw new SQLException("Your account is not active");
		Token token = new Token();
		token.setExpiration(new Timestamp(System.currentTimeMillis()+Constants.TOKEN_EXP));
		token.setUser(tmp.getId());
		token.setToken(
				BCrypt.withDefaults().hashToString(12, 
						(token.getUser()+""+System.currentTimeMillis()).toCharArray())
				);
		tokenDao.create(token.toMap());
		return token;
	}

	public boolean createUser(User user) throws SQLException
	{
		if(userDao.checkIfEmailExist(user.getEmail()))
			throw new SQLException("Email aleady exists");
		if(user.getPassword()!=null)
			user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
		user.setStatus(UserStatus.ACTIVE.toString());
		user.setCreated_at(new Timestamp(System.currentTimeMillis()));
		user.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		return userDao.create(user.toMap());
	}
	
	public boolean updateUser(User user,Long id) throws SQLException
	{
		return userDao.update(user.toMap(),id);
	}
	
	public boolean deleteUser(Long id) throws SQLException
	{
		return userDao.delete(id);
	}
	
	public User findUserById(Long id) throws SQLException
	{
		User tmp = new User();
		return (User) (new User()).mapper(userDao.findById(id),tmp);
	}
	
	public List<User> findAllUsers() throws SQLException
	{
		User tmp = new User();
		return (List<User>) tmp.mapperList(userDao.findAll(),tmp);
	}
	
	public boolean disable(Long id) throws SQLException
	{
		User user = findUserById(id);
		if(user.getId()==null)
			throw new SQLException("Account doesn't exist");
		if(!user.getStatus().equals(UserStatus.ACTIVE.toString()))
			throw new SQLException("Already disabled");
		user.setStatus(UserStatus.DISABLED.toString());
		user.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		return userDao.update(user.toMap(), id);
	}

	public boolean logout(Token token) throws SQLException {
		return tokenDao.delete(token.getId());
	}
}

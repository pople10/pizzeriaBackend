package com.project.pizzeria.services;

import java.sql.SQLException;

import com.project.pizzeria.beans.Token;
import com.project.pizzeria.dao.daoImp.TokenDao;

public class TokenService {
	private static TokenDao tokenDao;
	
	public TokenService()
	{
		tokenDao = new TokenDao();
	}
	
	public Token checkTokenValidity(String token) throws SQLException
	{
		Token out = new Token();
		out.mapper(tokenDao.findByToken(token), out);
		return out;
	}

}

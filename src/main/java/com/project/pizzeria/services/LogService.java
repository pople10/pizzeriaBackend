package com.project.pizzeria.services;

import java.sql.SQLException;

import com.project.pizzeria.beans.Log;
import com.project.pizzeria.dao.daoImp.LogDao;

public class LogService {
	private LogDao logDao;
	
	public LogService()
	{
		logDao = new LogDao();
	}
	
	public boolean logForUser(Log log)
	{
		try {
			return logDao.create(log.toMap());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

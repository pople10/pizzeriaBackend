package com.project.pizzeria.beans.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.pizzeria.beans.User;
import com.project.pizzeria.utils.GenericUtil;

public class GenericBean implements Serializable{
	public Map<String,String> toMap()
	{
		Map<String,String> out = new HashMap<String,String>();
		Class ourClass = this.getClass();
		for(Field field : ourClass.getDeclaredFields())
		{
			String name = field.getName();
			if(name=="id")
				continue;
			try {
				Method method = ourClass.getMethod("get"+GenericUtil.toTitleCase(name), null);
				Object result = method.invoke(this, null);
				if(result!=null)
					out.put(name, result.toString());
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return out;
	}
	
	public GenericBean mapper(ResultSet set,GenericBean out)
	{
		Class ourClass = this.getClass();
		try {
			while (set.next()) {
				for(Field field : ourClass.getDeclaredFields())
				{
					String name = field.getName();
					try {
						Class type = field.getType();
						Method method = ourClass.getMethod("set"+GenericUtil.toTitleCase(name),type);
						Class resultSetClass = set.getClass();
						Method resultSetMethod = resultSetClass.getMethod("get"+type.getSimpleName(), String.class);
						Object result = method.invoke(out, resultSetMethod.invoke(set,name));
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		} catch (SecurityException | SQLException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public List<? extends GenericBean> mapperList(ResultSet set,GenericBean tmp)
	{
		List<GenericBean> out = new ArrayList<GenericBean>();
		Class ourClass = this.getClass();
		try {
			while (set.next()) {
				GenericBean tmp2=null;
				try {
					try {
						tmp2 = tmp.getClass().getDeclaredConstructor().newInstance();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(Field field : ourClass.getDeclaredFields())
				{
					String name = field.getName();
					try {
						Class type = field.getType();
						Method method = ourClass.getMethod("set"+GenericUtil.toTitleCase(name),type);
						Class resultSetClass = set.getClass();
						Method resultSetMethod = resultSetClass.getMethod("get"+type.getSimpleName(), String.class);
						Object result = method.invoke(tmp2, resultSetMethod.invoke(set,name));
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				out.add(tmp2);
			}
		} catch (SecurityException | SQLException e) {
			e.printStackTrace();
		}
		return out;
	}
	
}

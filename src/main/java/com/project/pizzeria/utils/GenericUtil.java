package com.project.pizzeria.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

public class GenericUtil {
	public static String generateQuestionMarks(Map<String,String> args)
	{
		String out="";
		int size = args.size();
		for(int i=0;i<size;i++)
		{
			if(i==size-1)
			{
				out+="?";
				continue;
			}
			out+="?,";
		}
		return out;
	}
	
	public static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder(input.length());
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	
	public static String generateArgs(Map<String,String> args)
	{
		String out="";
		for(Entry<String,String> entry : args.entrySet())
		{
			out+=entry.getKey()+",";
		}
		out=removeLastCharFromString(out);
		return out;
	} 
	
	public static String generateArgsForUpdate(Map<String,String> args)
	{
		String out="";
		for(Entry<String,String> entry : args.entrySet())
		{
			out+=entry.getKey()+"=?,";
		}
		out=removeLastCharFromString(out);
		return out;
	} 
	
	public static PreparedStatement injectData(Map<String,String> args,PreparedStatement old) throws SQLException
	{
		int cmp=1;
		for(Entry<String,String> entry : args.entrySet())
		{
			old.setString(cmp, entry.getValue());
			cmp++;
		}
		return old;
	}
	
	public static PreparedStatement injectData(Map<String,String> args,PreparedStatement old,Long id) throws SQLException
	{
		int cmp=1;
		for(Entry<String,String> entry : args.entrySet())
		{
			old.setString(cmp, entry.getValue());
			cmp++;
		}
		old.setLong(cmp, id);
		return old;
	}
	
	public static String removeLastCharFromString(String string)
	{
		StringBuffer sb= new StringBuffer(string);   
		sb.deleteCharAt(sb.length()-1);  
		return sb.toString();
	}
}

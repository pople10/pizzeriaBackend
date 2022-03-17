package com.project.pizzeria.services;

import java.sql.SQLException;
import java.util.List;

import com.project.pizzeria.beans.Address;
import com.project.pizzeria.beans.User;
import com.project.pizzeria.dao.daoImp.AddressDao;

public class AddressService {
	private static AddressDao addressDao;
	
	public AddressService()
	{
		addressDao = new AddressDao();
	}

	public boolean createAddress(Address address) throws SQLException
	{
		if(address.getAddress2()==null)
			address.setAddress2("");
		return addressDao.create(address.toMap());
	}
	
	public Address findAddressById(Long id) throws SQLException
	{
		Address tmp = new Address();
		return (Address) tmp.mapper(addressDao.findById(id), tmp);
	}
	
	public List<Address> getAllAddressesByUser(User user) throws SQLException
	{
		Address tmp = new Address();
		return (List<Address>) tmp.mapperList(addressDao.findByUserId(user.getId()), tmp);
	}
}

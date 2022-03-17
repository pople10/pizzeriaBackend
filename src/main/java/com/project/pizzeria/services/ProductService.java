package com.project.pizzeria.services;

import java.sql.SQLException;
import java.util.List;

import com.project.pizzeria.beans.Product;
import com.project.pizzeria.dao.daoImp.ProductDao;

public class ProductService {
	private static ProductDao productDao;

	public ProductService() {
		productDao = new ProductDao();
	}
	
	public boolean createProduct(Product product) throws SQLException
	{
		return productDao.create(product.toMap());
	}
	
	public boolean updateProduct(Product product) throws SQLException
	{
		return productDao.update(product.toMap(),product.getId());
	}
	
	public Product findProductById(Long id) throws SQLException
	{
		Product tmp = new Product();
		return (Product)tmp.mapper(productDao.findById(id), tmp);
	}
	
	public List<Product> findAllProducts() throws SQLException
	{
		Product tmp = new Product();
		return (List<Product>)tmp.mapperList(productDao.findAll(), tmp);
	}
	
	public List<Product> findAllAvailableProducts() throws SQLException
	{
		Product tmp = new Product();
		return (List<Product>)tmp.mapperList(productDao.findByAvailability(true), tmp);
	}
	
	public boolean deleteProduct(Long id) throws SQLException
	{
		Product product = this.findProductById(id);
		product.setAvailability(false);
		return productDao.update(product.toMap(),product.getId());
	}

}

package com.dd.dao;

import java.util.List;
import java.util.Set;

import com.dd.data.Brand;


public interface ConfigDAO {

	public List<Brand> getVehBrands()  throws DAOException, RuntimeException;
}

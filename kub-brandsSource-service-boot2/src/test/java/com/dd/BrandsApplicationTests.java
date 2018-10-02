package com.dd;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.dd.dao.ConfigDAO;
import com.dd.dao.DAOException;
import com.dd.data.Brand;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BrandProducerApplication.class)
@SpringBootTest
public class BrandsApplicationTests {
	
	@Autowired
	private ConfigDAO dao;

	@Test
	public void testDao() throws DAOException, RuntimeException {
		List<Brand> list = dao.getVehBrands();
		System.out.println("############### list:" + list);
		//assertNotNull(list);
	}

}

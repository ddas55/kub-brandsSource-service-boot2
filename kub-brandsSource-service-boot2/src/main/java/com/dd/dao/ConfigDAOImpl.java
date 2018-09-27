package com.dd.dao;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import com.dd.data.Brand;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Component
public class ConfigDAOImpl implements ConfigDAO{
	
	private final Logger logger = LoggerFactory.getLogger(ConfigDAOImpl.class);
	
	@Autowired
	private MongoDbFactory mongoDbFactory;
		
	@Override
	public List<Brand> getVehBrands() throws DAOException, RuntimeException {
		FindIterable<Document> doc;
		MongoCollection<Document> topBrandsColl = null;
		List<Brand> brands = new LinkedList<Brand>();
		try {
			logger.info("************ ConfigDAOImpl mongoClient :" +  mongoDbFactory.getDb() );
			topBrandsColl = mongoDbFactory.getDb().getCollection("VehMakes");
			BasicDBObject query = new BasicDBObject();
			doc = topBrandsColl.find(query).sort(new BasicDBObject("make",1));
			Brand eachBrnd =null;
			for (Document document : doc) {
				eachBrnd = new Brand();
				eachBrnd.setBrand((String)document.get("make"));
				eachBrnd.setSelected(false);
				brands.add(eachBrnd);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			//throw new DAOException(e1.getMessage());
		}
		if( brands.isEmpty()) {
			Brand bmw = new Brand();
    		bmw.setBrand("BMW");
    		Brand lr = new Brand();
    		lr.setBrand("Land Rover");
    		Brand mb = new Brand();
    		mb.setBrand("Mercedes Benz");
    		Brand pr = new Brand();
    		pr.setBrand("Porche");
    		Brand ts = new Brand();
    		ts.setBrand("Tesla");
    		brands.add(bmw);
    		brands.add(lr);
    		brands.add(mb);
    		brands.add(pr);
    		brands.add(ts);
		}
		return brands;
	}

}
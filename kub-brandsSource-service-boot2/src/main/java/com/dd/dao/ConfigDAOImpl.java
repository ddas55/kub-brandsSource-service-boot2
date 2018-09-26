package com.dd.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import com.dd.data.Brand;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Component
public class ConfigDAOImpl implements ConfigDAO{
	
	@Autowired
	private MongoDbFactory mongo;
	
	@Value("${spring.data.mongodb.host}")
	private String mongo_host;
	
	@Value("${spring.data.mongodb.port}")
	private String mongo_port;
	
	@Value("${spring.data.mongodb.database}")
	private String mongo_database;
	
	@Value("${spring.data.mongodb.username}")
	private String mongo_username;
	
	@Value("${spring.data.mongodb.password}")
	private String mongo_password;
	
		
	@Override
	public List<Brand> getVehBrands() throws DAOException, RuntimeException {
		
		System.out.println("$$$$$$ S.O.P ConfigDAOImpl.mongo_host:" + mongo_host + " ,mongo_port:" + mongo_port
				+ " ,mongo_database:" + mongo_database + " ,mongo_username:" + mongo_username
				+ " ,mongo_password:" + mongo_password );
		
		
		//DBCollection topBrandsColl=null;
		//DBCursor cursor =null;
		FindIterable<Document> doc;
		MongoCollection<Document> topBrandsColl = null;
		//DBObject eachDbObject=null;
		List<Brand> brands = new LinkedList<Brand>();
		try {
			topBrandsColl = mongo.getDb().getCollection("VehMakes");
			BasicDBObject query = new BasicDBObject();
			doc = topBrandsColl.find(query).sort(new BasicDBObject("make",1));
			Brand eachBrnd =null;
			//Iterator iterator = doc.iterator();
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
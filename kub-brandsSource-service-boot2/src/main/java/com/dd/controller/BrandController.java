package com.dd.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dd.dao.ConfigDAO;
import com.dd.dao.DAOException;
import com.dd.data.Brand;

@RestController
@RequestMapping("/brands")
@CrossOrigin
public class BrandController {


	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	private static int hit=0;
	private static int random=(int)(Math.random()*100);
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${myapp.msg}")
	private String msg;
	
	@Autowired
	private ConfigDAO dao;

	@RequestMapping("/healthz")
	public String healthz() {
		//logger.info("** healthz Called random:" + random);
	    return String.valueOf(System.currentTimeMillis());
	}
	
	@RequestMapping("/rediness")
	public String rediness() {
		//logger.info("** rediness Called random:" + random);
		//Load large data or configuration files during startup. 
		return String.valueOf(System.currentTimeMillis());
		//return new ResponseEntity(System.currentTimeMillis(),HttpStatus.OK);
	}
	
    @RequestMapping(path="/allbrandsui" , method= RequestMethod.GET)
    public String allbrandsui() {
    	hit++;
       	logger.info("## BrandController.Hit:" + hit);
    	logger.info("@@ BrandController.random:" + random);
    	List<Brand> brands = new ArrayList<>();
    	try {
			brands = dao.getVehBrands();
		}
    	catch (DAOException | RuntimeException e) {
			logger.error(" # allbrands.ERROR :" ,e.getMessage());
			e.printStackTrace();
		}
        return getHTML(brands);
    }
    
    @RequestMapping(path="/allbrands",method = RequestMethod.GET)
    public ResponseEntity<?> allbrands() {
    	hit++;
    	System.out.println("$$$$$$ S.O.P BrandController.Hit:" + hit);
       	System.out.println("$$$$$$ S.O.P BrandController.random:" + random);
    	
       	logger.info("## logger BrandController.Hit:" + hit);
    	logger.info("@@ logger BrandController.random:" + random);
    	List<Brand> brands = new ArrayList<>();
    	try {
    		brands = dao.getVehBrands();
			logger.info("@@ logger BrandController.brands:" + brands);
			return new ResponseEntity<List<Brand>>(brands,HttpStatus.OK);
	
		} catch (RuntimeException e) {
			logger.error(" # allbrands.ERROR :" ,e.getMessage());
			e.printStackTrace();
		}
    	catch (DAOException  e) {
			logger.error(" # allbrands.ERROR :" ,e.getMessage());
			e.printStackTrace();
		}
    	return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private String getHTML(List<Brand> brands) {
    	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    	String tm=ft.format(System.currentTimeMillis());
    	StringBuilder sb = new StringBuilder();
    	sb.append("<html>");
    		sb.append("<body>");
    		sb.append("<table>");
    		sb.append("<tr>");
    			sb.append("<td valign='top'>");
    			sb.append("<table bgcolor='#dcccff'");
    			
    				sb.append("<tr>");
    					sb.append("<td>");sb.append("<b>HIT:</b>  " + hit );sb.append("</td>");
    				sb.append("</tr>");
    				sb.append("<tr>");
						sb.append("<td>");sb.append("<b>TIME</b>:  " + tm );sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>STATIC RANDOM#</b> :  " + random);sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>APP NAME</b> :  " + applicationName);sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>FROM PROP FILE</b> : " + msg);sb.append("</td>");
					sb.append("</tr>");
				sb.append("</table>");
    			sb.append("</td>");
    			
    			sb.append("<td>");
    			sb.append("<table bgcolor='#dcccff'");
	    			if(null!=brands) {
	    				
						for (Brand brand : brands) {
							sb.append("<tr>");sb.append("<td>");sb.append(brand.getBrand());sb.append("</td>");sb.append("</tr>");
						}

					}
    			sb.append("</table>");
    			sb.append("</td>");
    		
    		sb.append("</tr>");
    		sb.append("</table>");
    		sb.append("</body>");
    	sb.append("</html>");
    	return sb.toString();
    }


}

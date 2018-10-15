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
import com.dd.data.AppStatusInfo;
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
	@RequestMapping(path="/appinforaw" , method= RequestMethod.GET)
    public String appinforaw() {
    	AppStatusInfo asi =getAppStatus();
    	return asi.toString();
    }
	
	@RequestMapping(path="/appinfo" , method= RequestMethod.GET)
    public String getAppInfo() {
		AppStatusInfo asi =getAppStatus();
       	logger.info("## BrandController.asi:" + asi);
        return getHTML(asi,null);
    }
	
    @RequestMapping(path="/allbrandsui" , method= RequestMethod.GET)
    public String allbrandsui() {
    	AppStatusInfo asi =getAppStatus();
       	logger.info("## BrandController.asi:" + asi);
    	List<Brand> brands = new ArrayList<>();
    	try {
			brands = dao.getVehBrands();
		}
    	catch (DAOException | RuntimeException e) {
			logger.error(" # allbrands.ERROR :" ,e.getMessage());
			e.printStackTrace();
		}
        return getHTML(asi,brands);
    }
    
    @RequestMapping(path="/allbrands",method = RequestMethod.GET)
    public ResponseEntity<?> allbrands() {
    	AppStatusInfo asi =getAppStatus();
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
    
    private String getHTML(AppStatusInfo asi,List<Brand> brands) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<html>");
    		sb.append("<body>");
    		sb.append("<table>");
    		sb.append("<tr>");
    			sb.append("<td valign='top'>");
    			sb.append("<table bgcolor='#74f293'");//#7baee5-blue , #74f293-green
    			
    				sb.append("<tr>");
    					sb.append("<td>");sb.append("<b>HIT:</b>  " + asi.getHit() );sb.append("</td>");
    				sb.append("</tr>");
    				sb.append("<tr>");
						sb.append("<td>");sb.append("<b>TIME</b>:  " + asi.getTime() );sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>STATIC RANDOM#</b> :  " + asi.getRandom());sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>APP NAME</b> :  " + asi.getAppname());sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>Msg</b> : " + asi.getFrompropfile());sb.append("</td>");
					sb.append("</tr>");
					sb.append("<tr>");
						sb.append("<td>");sb.append("<b>VERSION</b> : " + asi.getVersion());sb.append("</td>");
					sb.append("</tr>");
					
				sb.append("</table>");
    			sb.append("</td>");
    		if(null!=brands) {	
    			sb.append("<td>");
    			sb.append("<table bgcolor='#dcccff'");
	    			if(null!=brands) {
	    				
						for (Brand brand : brands) {
							sb.append("<tr>");sb.append("<td>");sb.append(brand.getBrand());sb.append("</td>");sb.append("</tr>");
						}

					}
    			sb.append("</table>");
    			sb.append("</td>");
    		}
    		sb.append("</tr>");
    		sb.append("</table>");
    		sb.append("</body>");
    	sb.append("</html>");
    	return sb.toString();
    }
    private AppStatusInfo getAppStatus() {
		hit++;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    	String tm=ft.format(System.currentTimeMillis());
    	AppStatusInfo appstatus = new AppStatusInfo();
    	appstatus.setHit(String.valueOf(hit));
    	appstatus.setRandom(String.valueOf(random));
    	appstatus.setAppname(applicationName);
    	appstatus.setTime(tm);
    	appstatus.setVersion("V2-Green");
    	return appstatus;
    }

}

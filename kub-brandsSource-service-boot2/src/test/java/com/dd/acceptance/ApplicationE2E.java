package com.dd.acceptance;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import static org.junit.Assert.assertTrue;



public class ApplicationE2E{
	private final Logger logger = LoggerFactory.getLogger(ApplicationE2E.class);
	private String host="localhost";//"192.168.99.100";
    private int port = 8070;//31004
    private RestTemplate restTemplate;
    private URL baseURL;

    @Before()
    public void setUp() throws Exception {
    	host = System.getProperty("host");
    	if(null!=System.getProperty("port")) {
    		port = Integer.parseInt(System.getProperty("port"));
    	}
      	System.out.println("------ k8sHost:" + host + " , k8sSvcNodePort:" + port); 
      	logger.info("************ k8sHost:" + host + " , k8sSvcNodePort:" + port);
        // replace that with UAT server host
    	//http://192.168.99.100:31004/svcbrands/brands/allbrands
        this.baseURL = new URL("http://" + host + ":" + port + "/svcbrands/");
        // disable proxy if you wanna run locally
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("userproxy.glb.ebc.local", port));
        requestFactory.setProxy(proxy);
        restTemplate = new RestTemplate();
    }
    // example of true end to end call which call UAT real endpoint
    
    @Test
    public void test_is_server_up() {
        assertTrue(restTemplate.getForEntity(baseURL + "brands/allbrands", String.class).getStatusCode().is2xxSuccessful());
    }


}
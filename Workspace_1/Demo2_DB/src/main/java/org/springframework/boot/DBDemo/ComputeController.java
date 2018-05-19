package org.springframework.boot.DBDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * 数据库服务测试
 */
@RestController
public class ComputeController {
	
	private final Logger logger = Logger.getLogger(getClass());
	private static String url= "jdbc:mysql://localhost:3306/scenariodb";
	private static Connection connection;
    @Autowired
    private DiscoveryClient client;
    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }
    
    @RequestMapping(value = "/dbConnection" ,method = RequestMethod.GET)
    public String dbConnection(@RequestParam String sqlName, @RequestParam String password) {
        ServiceInstance instance = client.getLocalServiceInstance();
        String result="Connection: False";
        try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url, sqlName, password);
			result="Connection: True";
		} catch (ClassNotFoundException e) {
			result="Connection: DriverFalse";
			e.printStackTrace();
		} catch (SQLException e) {
			result="Connection: SQLFalse";
			e.printStackTrace();
		}        
        logger.info("/dbConnection, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + result);
        return result;
    }
    
}

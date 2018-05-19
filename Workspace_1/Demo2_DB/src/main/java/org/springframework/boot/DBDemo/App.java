package org.springframework.boot.DBDemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 数据库服务提供方
 * Port：2225
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class App 
{
	public static void main( String[] args )
    {
    	new SpringApplicationBuilder(App.class).web(true).run(args);
    }
}

package org.springframework.boot.Demo2_1;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务提供方 Service-a
 * port：2222
 *
 */

@EnableDiscoveryClient
@SpringBootApplication
public class App2a 
{
	public static void main( String[] args )
    {
    	new SpringApplicationBuilder(App2a.class).web(true).run(args);
    }
}

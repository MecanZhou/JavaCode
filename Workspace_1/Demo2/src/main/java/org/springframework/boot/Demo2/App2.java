package org.springframework.boot.Demo2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务提供方 Compute-Service
 * 无Zull
 * port：2222
 */

@EnableDiscoveryClient
@SpringBootApplication
public class App2 
{
    public static void main( String[] args )
    {
    	new SpringApplicationBuilder(App2.class).web(true).run(args);
    }
}

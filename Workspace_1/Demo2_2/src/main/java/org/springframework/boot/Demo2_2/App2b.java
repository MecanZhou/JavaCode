package org.springframework.boot.Demo2_2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */

@EnableDiscoveryClient
@SpringBootApplication
public class App2b 
{
	public static void main( String[] args )
    {
    	new SpringApplicationBuilder(App2b.class).web(true).run(args);
    }
}

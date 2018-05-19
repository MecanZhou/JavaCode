package org.springframework.boot.Demo1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心
 * port：1111
 */

@EnableEurekaServer
@SpringBootApplication
public class App1 {

	public static void main(String[] args) {
		new SpringApplicationBuilder(App1.class).web(true).run(args);
	}

}

package Enruka.Zuul;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul
 * port：5555
 */
@EnableZuulProxy
@SpringCloudApplication

public class AppZuul {
	public static void main(String[] args) {
		new SpringApplicationBuilder(AppZuul.class).web(true).run(args);
	}
}
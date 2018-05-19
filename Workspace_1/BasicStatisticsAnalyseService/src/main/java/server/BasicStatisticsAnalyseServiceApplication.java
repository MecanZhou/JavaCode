package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 基本统计数据计算服务
 * @author Administrator
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BasicStatisticsAnalyseServiceApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(BasicStatisticsAnalyseServiceApplication.class).web(true).run(args);
	}
}

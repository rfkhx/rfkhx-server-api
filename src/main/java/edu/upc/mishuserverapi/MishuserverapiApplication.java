package edu.upc.mishuserverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MishuserverapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MishuserverapiApplication.class, args);
	}

}

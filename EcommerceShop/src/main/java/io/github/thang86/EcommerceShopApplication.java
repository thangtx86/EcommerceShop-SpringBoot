package io.github.thang86;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
*  EcommerceShopApplication.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-11-11    ThangTX     Create
*/

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.github.thang86.repository")
@EnableJpaAuditing
@EnableAutoConfiguration
@Configuration
public class EcommerceShopApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceShopApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EcommerceShopApplication.class);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer factory) {
		// TODO Auto-generated method stub
		 MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		 mappings.add("html", "text/html;charset=utf-8");
		 factory.setMimeMappings(mappings );
		
	}
	
	

}


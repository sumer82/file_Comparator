package com.fileComparator.fileComparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class FileComparatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileComparatorApplication.class, args);
	}

	 @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();

	        // Allow requests from the specific origin (e.g., http://127.0.0.1:5500)
	        config.addAllowedOrigin("http://127.0.0.1:5500");

	        // Allow common HTTP methods (GET, POST, PUT, DELETE, etc.)
	        config.addAllowedMethod("*");

	        // Allow headers you need for your API (e.g., "Content-Type", "Authorization")
	        config.addAllowedHeader("*");

	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }

}

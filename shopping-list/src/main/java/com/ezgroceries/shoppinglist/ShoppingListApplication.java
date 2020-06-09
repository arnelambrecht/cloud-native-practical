package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.external.cocktail.CocktailDBService;
import com.ezgroceries.shoppinglist.internal.cocktail.CocktailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
public class ShoppingListApplication {
	public static void main(String[] args) {
		// This is the starting point of the application
		SpringApplication.run(ShoppingListApplication.class, args);
	}

	@Bean
	public CocktailService cocktailService() {
		return new CocktailDBService();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
}

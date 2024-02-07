package com.BlogApi.config;

import java.lang.annotation.Annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@OpenAPIDefinition(
		
		info=@Info(
			title="Blog Application",
			description=" Blogging Applicatio Apis ",
			summary = "Blag Application Created by Manoj",
			termsOfService="learning",
			contact=@Contact(
			name="Manoj kadam",
			email="manoj@gmail.com"
			),
			license=@License(
			name="BCCI"
		),
			version="v1"
		),
		servers= {@Server(
				description="dev",
				url = "http://localhost:8080"
				),
				@Server(
						description="test",
						url = "http://localhost:8080"
						)
		}
		
		
		)

@Configuration

public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		
		String SchemaName="bearerscheme";
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(SchemaName))
				.components(new Components().addSecuritySchemes(SchemaName, new SecurityScheme().name(SchemaName)
				.type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("Bearer"))
						
						);
	}
	
}

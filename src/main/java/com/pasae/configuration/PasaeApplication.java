package com.pasae.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "domain")
@EntityScan(basePackages = "domain")
@RestController
public class PasaeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasaeApplication.class, args);
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.anyRequest().authenticated()
				.and().exceptionHandling()
			      .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
				.and().logout().logoutSuccessUrl("/").permitAll();
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources)
				throws Exception {
			resources.resourceId("pasae");
		}

	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients.inMemory().withClient("my-trusted-client")
					.authorizedGrantTypes("password", "authorization_code",
							"refresh_token", "implicit")
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
					.scopes("read", "write", "trust").resourceIds("pasae")
					.accessTokenValiditySeconds(180).and()
					.withClient("my-client-with-registered-redirect")
					.authorizedGrantTypes("authorization_code").authorities("ROLE_CLIENT")
					.scopes("read", "trust").resourceIds("pasae")
					.redirectUris("http://anywhere?key=value").and()
					.withClient("my-client-with-secret")
					.authorizedGrantTypes("client_credentials", "password")
					.authorities("ROLE_CLIENT").scopes("read").resourceIds("pasae")
					.secret("secret");
			// @formatter:on
		}

	}

	
}
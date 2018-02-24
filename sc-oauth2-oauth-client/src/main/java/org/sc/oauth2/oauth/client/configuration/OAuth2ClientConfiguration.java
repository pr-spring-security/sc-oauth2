package org.sc.oauth2.oauth.client.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfiguration {

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(){
		OAuth2RestTemplate auth2RestTemplate = new OAuth2RestTemplate(clientCredentials(), oauth2ClientContext);
		return auth2RestTemplate;
	}
	
	@Bean
	public OAuth2ProtectedResourceDetails resourceOwner(){
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setClientId("trusted");
		details.setClientSecret("secret");
		details.setAccessTokenUri("http://localhost:8080/oauth/token");
		details.setUsername("admin");
		details.setPassword("123");
		details.setScope(Arrays.asList("trust"));
		return details;
	}
	
	@Bean
	public OAuth2ProtectedResourceDetails authorizationCode(){
		AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
		details.setClientId("client-a");
		details.setClientSecret("secret");
		details.setAccessTokenUri("http://localhost:8080/oauth/token");
		details.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		details.setScope(Arrays.asList("public_profile", "email", "date_of_birth", "place_of_birth"));
		return details;
	}
	
	@Bean
	public OAuth2ProtectedResourceDetails clientCredentials(){
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setClientId("client-a");
		details.setClientSecret("secret");
		details.setAccessTokenUri("http://localhost:8080/oauth/token");
		details.setScope(Arrays.asList("public_profile", "email", "date_of_birth", "place_of_birth"));
		return details;
	}
	
	
}

package org.sc.oauth2.oauth.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManagerBean;
	
	@Bean
	public TokenStore tokenStore(){
		return new InMemoryTokenStore();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.tokenStore(tokenStore())
				.authenticationManager(authenticationManagerBean);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("trusted")
			.secret("secret")
			.scopes("trust")
			.authorities("ROLE_TRUSTED_CLIENT")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(120)
			.refreshTokenValiditySeconds(15*60)
		.and()
			.withClient("client-a")
			.secret("secret")
			.scopes("public_profile", "email", "date_of_birth", "place_of_birth")
			.authorities("ROLE_CLIENT_A")
			.authorizedGrantTypes("authorization_code", "client_credentials", "implicit", "refresh_token")
			.redirectUris("http://localhost:8282/client")
			.accessTokenValiditySeconds(120)
			.refreshTokenValiditySeconds(15*60)
		;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			//.checkTokenAccess("permitAll()");
			.checkTokenAccess("isAuthenticated()");
		//security.tokenKeyAccess("")
	}
	
}

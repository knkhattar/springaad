package com.example.aad.ss52x.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import com.example.aad.ss52x.util.ConfidConstants;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().oauth2Login();
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(this.azureadClientRegistration());
	}

	private ClientRegistration azureadClientRegistration() {
		ClientRegistration.Builder builder = ClientRegistration.withRegistrationId("azure");
		builder.clientId(ConfidConstants.clientId);
		builder.clientSecret(ConfidConstants.clientSecret);
		builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
		builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
		builder.redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}");
		builder.scope("openid", "profile");
		builder.authorizationUri("https://login.microsoftonline.com/common/oauth2/v2.0/authorize");
		builder.tokenUri("https://login.microsoftonline.com/common/oauth2/v2.0/token");
		builder.userInfoUri("https://graph.microsoft.com/oidc/userinfo");

		builder.jwkSetUri("https://login.microsoftonline.com/common/discovery/keys");
		builder.clientName("azure");
		// builder.userNameAttributeName("name");
		builder.userNameAttributeName(IdTokenClaimNames.SUB);
		return builder.build();
	}
}

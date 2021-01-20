package org.arch.auth.jwt.jackson2.model;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.arch.framework.ums.jackson2.deserializer.ArchUserDeserializer;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import top.dcenter.ums.security.jwt.jackson2.deserializer.BaseJwtMixin;
import top.dcenter.ums.security.jwt.jackson2.deserializer.BearerTokenAuthenticationDeserializer;
import top.dcenter.ums.security.jwt.jackson2.deserializer.BearerTokenAuthenticationTokenDeserializer;
import top.dcenter.ums.security.jwt.jackson2.deserializer.DefaultOAuth2AuthenticatedPrincipalDeserializer;
import top.dcenter.ums.security.jwt.jackson2.deserializer.JwtAuthenticationTokenDeserializer;

/**
 * auth-sdk Jackson2 Module
 * @author YongWu zheng
 * @version V2.0  Created by 2020/10/28 10:58
 */
public class AuthSdkJackson2Module extends SimpleModule {

	private static final long serialVersionUID = 5029625645865781935L;

	public AuthSdkJackson2Module() {
		super(AuthSdkJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(Jwt.class,
		                            BaseJwtMixin.class);
		context.setMixInAnnotations(JwtAuthenticationToken.class,
		                            JwtAuthenticationTokenDeserializer.JwtAuthenticationTokenMixin.class);
		context.setMixInAnnotations(DefaultOAuth2AuthenticatedPrincipal.class,
		                            DefaultOAuth2AuthenticatedPrincipalDeserializer.DefaultOAuth2AuthenticatedPrincipalMixin.class);
		context.setMixInAnnotations(BearerTokenAuthentication.class,
		                            BearerTokenAuthenticationDeserializer.BearerTokenAuthenticationMixin.class);
		context.setMixInAnnotations(BearerTokenAuthenticationToken.class,
		                            BearerTokenAuthenticationTokenDeserializer.BearerTokenAuthenticationTokenMixin.class);
		context.setMixInAnnotations(ArchUser.class,
		                            ArchUserDeserializer.ArchUserMixin.class);
	}
}
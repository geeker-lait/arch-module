package org.arch.auth.sso.jackson2.model;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import me.zhyd.oauth.model.AuthUser;
import org.arch.framework.jackson2.deserializer.ArchUserDeserializer;
import org.arch.framework.userdetails.ArchUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import top.dcenter.ums.security.core.auth.jackson.deserializes.RememberMeAuthenticationTokenJsonDeserializer;
import top.dcenter.ums.security.core.auth.jackson.deserializes.SmsCodeLoginAuthenticationTokenJsonDeserializer;
import top.dcenter.ums.security.core.auth.jackson.deserializes.UsernamePasswordAuthenticationTokenJsonDeserializer;
import top.dcenter.ums.security.core.auth.mobile.SmsCodeLoginAuthenticationToken;
import top.dcenter.ums.security.core.oauth.deserializes.AnonymousAuthenticationTokenJsonDeserializer;
import top.dcenter.ums.security.core.oauth.deserializes.Auth2AuthenticationTokenJsonDeserializer;
import top.dcenter.ums.security.core.oauth.deserializes.AuthUserJsonDeserializer;
import top.dcenter.ums.security.core.oauth.deserializes.TemporaryUserDeserializer;
import top.dcenter.ums.security.core.oauth.deserializes.UserDeserializer;
import top.dcenter.ums.security.core.oauth.token.Auth2AuthenticationToken;
import top.dcenter.ums.security.core.oauth.userdetails.TemporaryUser;
import top.dcenter.ums.security.jwt.jackson2.deserializer.BaseJwtMixin;
import top.dcenter.ums.security.jwt.jackson2.deserializer.BearerTokenAuthenticationDeserializer;
import top.dcenter.ums.security.jwt.jackson2.deserializer.BearerTokenAuthenticationTokenDeserializer;
import top.dcenter.ums.security.jwt.jackson2.deserializer.DefaultOAuth2AuthenticatedPrincipalDeserializer;
import top.dcenter.ums.security.jwt.jackson2.deserializer.JwtAuthenticationTokenDeserializer;

/**
 * auth-sso Jackson2 Module
 * @author YongWu zheng
 * @version V2.0  Created by 2020/10/28 10:58
 */
public class ArchJackson2Module extends SimpleModule {

	public ArchJackson2Module() {
		super(ArchJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
	    context.setMixInAnnotations(Auth2AuthenticationToken.class,
                                    Auth2AuthenticationTokenJsonDeserializer.Auth2AuthenticationTokenMixin.class);
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
		context.setMixInAnnotations(AnonymousAuthenticationToken.class,
		                            AnonymousAuthenticationTokenJsonDeserializer.AnonymousAuthenticationTokenMixin.class);
		context.setMixInAnnotations(User.class,
		                            UserDeserializer.UserMixin.class);
		context.setMixInAnnotations(ArchUser.class,
		                            ArchUserDeserializer.ArchUserMixin.class);
		context.setMixInAnnotations(TemporaryUser.class,
		                            TemporaryUserDeserializer.TemporaryUserMixin.class);
		context.setMixInAnnotations(AuthUser.class,
		                            AuthUserJsonDeserializer.AuthUserMixin.class);
		context.setMixInAnnotations(SmsCodeLoginAuthenticationToken.class,
		                            SmsCodeLoginAuthenticationTokenJsonDeserializer.SmsCodeLoginAuthenticationTokenMixin.class);
		context.setMixInAnnotations(UsernamePasswordAuthenticationToken.class,
		                            UsernamePasswordAuthenticationTokenJsonDeserializer.UsernamePasswordAuthenticationTokenMixin.class);
		context.setMixInAnnotations(RememberMeAuthenticationToken.class,
		                            RememberMeAuthenticationTokenJsonDeserializer.RememberMeAuthenticationTokenMixin.class);
	}
}
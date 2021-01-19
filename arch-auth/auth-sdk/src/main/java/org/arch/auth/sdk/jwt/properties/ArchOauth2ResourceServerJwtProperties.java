package org.arch.auth.sdk.jwt.properties;

import org.arch.auth.sdk.factory.MixPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * auth sdk OAuth 2.0 resource server jwt properties.
 *
 * @author Madhura Bhave
 * @author Artsiom Yudovin
 * @since 2.1.0
 */
@Configuration
@PropertySource(value = {"classpath:auth-jwt.yml"}, factory = MixPropertySourceFactory.class)
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public class ArchOauth2ResourceServerJwtProperties {

	/**
	 * JSON Web Key URI to use to verify the JWT token.
	 */
	private String jwkSetUri;

	/**
	 * JSON Web Algorithm used for verifying the digital signatures.
	 */
	private String jwsAlgorithm = "RS256";

	/**
	 * URI that can either be an OpenID Connect discovery endpoint or an OAuth 2.0
	 * Authorization Server Metadata endpoint defined by RFC 8414.
	 */
	private String issuerUri;

	/**
	 * Location of the file containing the public key used to verify a JWT.
	 */
	private Resource publicKeyLocation;

	public String getJwkSetUri() {
		return this.jwkSetUri;
	}

	public void setJwkSetUri(String jwkSetUri) {
		this.jwkSetUri = jwkSetUri;
	}

	public String getJwsAlgorithm() {
		return this.jwsAlgorithm;
	}

	public void setJwsAlgorithm(String jwsAlgorithm) {
		this.jwsAlgorithm = jwsAlgorithm;
	}

	public String getIssuerUri() {
		return this.issuerUri;
	}

	public void setIssuerUri(String issuerUri) {
		this.issuerUri = issuerUri;
	}

	public Resource getPublicKeyLocation() {
		return this.publicKeyLocation;
	}

	public void setPublicKeyLocation(Resource publicKeyLocation) {
		this.publicKeyLocation = publicKeyLocation;
	}

	public String readPublicKey() throws IOException {
		String key = "spring.security.oauth2.resourceserver.public-key-location";
		Assert.notNull(this.publicKeyLocation, "PublicKeyLocation must not be null");
		if (!this.publicKeyLocation.exists()) {
			throw new InvalidConfigurationPropertyValueException(key, this.publicKeyLocation,
					"Public key location does not exist");
		}
		try (InputStream inputStream = this.publicKeyLocation.getInputStream()) {
			return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		}
	}
}
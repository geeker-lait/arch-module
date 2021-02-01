package org.arch.auth.sdk.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.arch.auth.jwt.config.JwtRedisSerializerAutoConfiguration;
import org.arch.auth.sdk.redis.properties.AuthSdkRedisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import top.dcenter.ums.security.jwt.config.JwtAutoConfiguration;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * auth-sdk redis 自动配置
 * @author Mark Paluch
 * @author Andy Wilkinson
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.17 23:07
 */
@Configuration
@AutoConfigureAfter({AuthSdkAutoConfiguration.class})
@AutoConfigureBefore({JwtRedisSerializerAutoConfiguration.class, JwtAutoConfiguration.class})
public class AuthSdkRedisAutoConfiguration {

    private final AuthSdkRedisProperties properties;
    private final RedisSentinelConfiguration sentinelConfiguration;
    private final RedisClusterConfiguration clusterConfiguration;

    public AuthSdkRedisAutoConfiguration(AuthSdkRedisProperties properties,
                                         ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider,
                                         ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
        this.properties = properties;
        this.sentinelConfiguration = sentinelConfigurationProvider.getIfAvailable();
        this.clusterConfiguration = clusterConfigurationProvider.getIfAvailable();
    }

    @Bean(value = "jwtRedisConnectionFactory")
    LettuceConnectionFactory jwtRedisConnectionFactory(
            ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ClientResources clientResources) {
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(builderCustomizers, clientResources,
                                                                                getProperties().getLettuce().getPool());
        return createLettuceConnectionFactory(clientConfig);
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(LettuceClientConfiguration clientConfiguration) {
        if (getSentinelConfig() != null) {
            return new LettuceConnectionFactory(getSentinelConfig(), clientConfiguration);
        }
        if (getClusterConfiguration() != null) {
            return new LettuceConnectionFactory(getClusterConfiguration(), clientConfiguration);
        }
        return new LettuceConnectionFactory(getStandaloneConfig(), clientConfiguration);
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(
            ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ClientResources clientResources, AuthSdkRedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = createBuilder(pool);
        applyProperties(builder);
        if (StringUtils.hasText(getProperties().getUrl())) {
            customizeConfigurationFromUrl(builder);
        }
        builder.clientOptions(createClientOptions());
        builder.clientResources(clientResources);
        builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(AuthSdkRedisProperties.Pool pool) {
        if (pool == null) {
            return LettuceClientConfiguration.builder();
        }
        return new PoolBuilderFactory().createBuilder(pool);
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(
            LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (getProperties().isSsl()) {
            builder.useSsl();
        }
        if (getProperties().getTimeout() != null) {
            builder.commandTimeout(getProperties().getTimeout());
        }
        if (getProperties().getLettuce() != null) {
            AuthSdkRedisProperties.Lettuce lettuce = getProperties().getLettuce();
            if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(getProperties().getLettuce().getShutdownTimeout());
            }
        }
        if (StringUtils.hasText(getProperties().getClientName())) {
            builder.clientName(getProperties().getClientName());
        }
        return builder;
    }

    private ClientOptions createClientOptions() {
        ClientOptions.Builder builder = initializeClientOptionsBuilder();
        Duration connectTimeout = getProperties().getConnectTimeout();
        if (connectTimeout != null) {
            builder.socketOptions(SocketOptions.builder().connectTimeout(connectTimeout).build());
        }
        return builder.timeoutOptions(TimeoutOptions.enabled()).build();
    }

    private ClientOptions.Builder initializeClientOptionsBuilder() {
        if (getProperties().getCluster() != null) {
            ClusterClientOptions.Builder builder = ClusterClientOptions.builder();
            AuthSdkRedisProperties.Lettuce.Cluster.Refresh refreshProperties =
                    getProperties().getLettuce().getCluster().getRefresh();
            ClusterTopologyRefreshOptions.Builder refreshBuilder =
                    ClusterTopologyRefreshOptions.builder()
                                                 .dynamicRefreshSources(refreshProperties.isDynamicRefreshSources());
            if (refreshProperties.getPeriod() != null) {
                refreshBuilder.enablePeriodicRefresh(refreshProperties.getPeriod());
            }
            if (refreshProperties.isAdaptive()) {
                refreshBuilder.enableAllAdaptiveRefreshTriggers();
            }
            return builder.topologyRefreshOptions(refreshBuilder.build());
        }
        return ClientOptions.builder();
    }

    private void customizeConfigurationFromUrl(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        ConnectionInfo connectionInfo = parseUrl(getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    /**
     * Inner class to allow optional commons-pool2 dependency.
     */
    private static class PoolBuilderFactory {

        LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(AuthSdkRedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(properties));
        }

        private GenericObjectPoolConfig<?> getPoolConfig(AuthSdkRedisProperties.Pool properties) {
            GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getTimeBetweenEvictionRuns() != null) {
                config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
            }
            if (properties.getMaxWait() != null) {
                config.setMaxWaitMillis(properties.getMaxWait().toMillis());
            }
            return config;
        }

    }

    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        if (StringUtils.hasText(this.properties.getUrl())) {
            ConnectionInfo connectionInfo = parseUrl(this.properties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            config.setUsername(connectionInfo.getUsername());
            config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
        }
        else {
            config.setHostName(this.properties.getHost());
            config.setPort(this.properties.getPort());
            config.setUsername(this.properties.getUsername());
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }
        config.setDatabase(this.properties.getDatabase());
        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        }
        AuthSdkRedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(createSentinels(sentinelProperties));
            config.setUsername(this.properties.getUsername());
            if (this.properties.getPassword() != null) {
                config.setPassword(RedisPassword.of(this.properties.getPassword()));
            }
            if (sentinelProperties.getPassword() != null) {
                config.setSentinelPassword(RedisPassword.of(sentinelProperties.getPassword()));
            }
            config.setDatabase(this.properties.getDatabase());
            return config;
        }
        return null;
    }

    /**
     * Create a {@link RedisClusterConfiguration} if necessary.
     * @return {@literal null} if no cluster settings are set.
     */
    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        if (this.properties.getCluster() == null) {
            return null;
        }
        AuthSdkRedisProperties.Cluster clusterProperties = this.properties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        config.setUsername(this.properties.getUsername());
        if (this.properties.getPassword() != null) {
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }
        return config;
    }

    protected final AuthSdkRedisProperties getProperties() {
        return this.properties;
    }

    private List<RedisNode> createSentinels(AuthSdkRedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(nonNull(parts) && parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.parseInt(parts[1])));
            }
            catch (RuntimeException ex) {
                throw new IllegalStateException("Invalid redis sentinel property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (!"redis".equals(scheme) && !"rediss".equals(scheme)) {
                throw new RedisUrlSyntaxException(url);
            }
            boolean useSsl = ("rediss".equals(scheme));
            String username = null;
            String password = null;
            if (uri.getUserInfo() != null) {
                String candidate = uri.getUserInfo();
                int index = candidate.indexOf(':');
                if (index >= 0) {
                    username = candidate.substring(0, index);
                    password = candidate.substring(index + 1);
                }
                else {
                    password = candidate;
                }
            }
            return new ConnectionInfo(uri, useSsl, username, password);
        }
        catch (URISyntaxException ex) {
            throw new RedisUrlSyntaxException(url, ex);
        }
    }

    static class ConnectionInfo {

        private final URI uri;

        private final boolean useSsl;

        private final String username;

        private final String password;

        ConnectionInfo(URI uri, boolean useSsl, String username, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.username = username;
            this.password = password;
        }

        boolean isUseSsl() {
            return this.useSsl;
        }

        String getHostName() {
            return this.uri.getHost();
        }

        int getPort() {
            return this.uri.getPort();
        }

        String getUsername() {
            return this.username;
        }

        String getPassword() {
            return this.password;
        }

    }

    /**
     * Exception thrown when a Redis URL is malformed or invalid.
     *
     * @author Scott Frederick
     */
    static class RedisUrlSyntaxException extends RuntimeException {

        private static final long serialVersionUID = -525702723177852412L;
        private final String url;

        RedisUrlSyntaxException(String url, Exception cause) {
            super(buildMessage(url), cause);
            this.url = url;
        }

        RedisUrlSyntaxException(String url) {
            super(buildMessage(url));
            this.url = url;
        }

        String getUrl() {
            return this.url;
        }

        private static String buildMessage(String url) {
            return "Invalid Redis URL '" + url + "'";
        }

    }


}

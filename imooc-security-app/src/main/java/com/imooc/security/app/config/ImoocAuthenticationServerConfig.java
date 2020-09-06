package com.imooc.security.app.config;

import com.imooc.security.core.properties.OauthClientProperties;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @author zhang.suxing
 * @date 2020/8/2 19:27
 **/
@Configuration
@EnableAuthorizationServer
public class ImoocAuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private TokenStore tokenStore;

    /**
     * 客户端配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauthProperties().getOauthClientProperties())) {
            for (OauthClientProperties oauthClientProperties : securityProperties.getOauthProperties().getOauthClientProperties()) {
                builder.withClient(oauthClientProperties.getClientId())
                        .secret(oauthClientProperties.getClientSecurity())
                        .accessTokenValiditySeconds(oauthClientProperties.getExpirePeriod())
                        .authorizedGrantTypes("refresh_token", "password")
                        .scopes("all", "read", "write");
            }
        }
    }

    /**
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
        .tokenStore(tokenStore);
    }
}

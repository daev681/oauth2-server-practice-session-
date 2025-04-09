package practice.oauth2server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity

import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class AuthorizationServerConfig {

    @Bean
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer()

        // ⭐ 여기서 OIDC 활성화
        authorizationServerConfigurer.oidc { oidc -> oidc.clientRegistrationEndpoint { it -> } }

        http
                .securityMatcher(authorizationServerConfigurer.endpointsMatcher)
                .authorizeHttpRequests { authorize -> authorize.anyRequest().authenticated() }
                .csrf { csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.endpointsMatcher) }
                .apply(authorizationServerConfigurer)

        return http.build()
    }
    @Bean
    fun authServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)

        http.getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
                .oidc(Customizer.withDefaults()) // <-- 이거 꼭 있어야 함!

        return http.build()
    }
}
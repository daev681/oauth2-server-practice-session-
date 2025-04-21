package practice.oauth2server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .authorizeHttpRequests { authz ->
                    authz
                            .requestMatchers("/oauth2/**", "/.well-known/**").permitAll()
                            .anyRequest().authenticated()
                }
                .formLogin(Customizer.withDefaults()) // ✅ Deprecated 되지 않은 방식
                .sessionManagement {
                    it.sessionFixation().none() // 세션 ID 변경을 방지
                }
        return http.build()
    }


    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("test")
                .password("{noop}123123")  // 요거!
                .roles("USER")
                .build()
        println("✅ InMemoryUser 등록됨: ${user.username}")
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}

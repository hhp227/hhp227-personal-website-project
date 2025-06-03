package kr.hhp227.Webapp.config

import kr.hhp227.Webapp.service.UserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        AntPathRequestMatcher("/"),
                        AntPathRequestMatcher("/Register"),
                        AntPathRequestMatcher("/RegisterProcess"),
                        AntPathRequestMatcher("/About"),
                        AntPathRequestMatcher("/Contact")
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { login ->
                login
                    .loginPage("/Login")
                    .loginProcessingUrl("/LoginProcess")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutRequestMatcher(AntPathRequestMatcher("/Logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
            }
        return http.build()
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userService)
            .passwordEncoder(userService.getPasswordEncoder())
            .and()
            .build()
    }

    @Bean
    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
        val adminUser = User
            .withUsername("Admin")
            .password(userService.getPasswordEncoder().encode("AdminTest"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(adminUser)
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }
}
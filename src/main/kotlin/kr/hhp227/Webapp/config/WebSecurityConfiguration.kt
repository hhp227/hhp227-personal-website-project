package kr.hhp227.Webapp.config

import kr.hhp227.Webapp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/Register", "/RegisterProcess", "/About", "/Contact")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/Login")
            .loginProcessingUrl("/LoginProcess")
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/Logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .permitAll()
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer() = WebSecurityCustomizer { web ->
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }

    // 사용자 인증 매니저
    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth
            .inMemoryAuthentication()
            .withUser("Admin")
            .password(passwordEncoder.encode("AdminTest"))
            .roles("ADMIN")
    }
}
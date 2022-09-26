package com.registerlogin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
@RequestMapping("/register")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


//    @Autowired
//    private UserDetailsService service;
//

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/login").access("hasRole()");
        http.formLogin().loginProcessingUrl("/login").failureForwardUrl("/Register/login").usernameParameter("username").passwordParameter("password").permitAll();
        //http.authorizeRequests().antMatchers("/destroy").hasRole("/").anyRequest().authenticated();
        http.logout().deleteCookies().logoutSuccessUrl("/login?logout");
        http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired");
        //http.rememberMe().tokenRepository(persistentTokenRepository()).rememberMeCookieDomain("domainCookie").rememberMeCookieName("customer").userDetailsService(service).tokenValiditySeconds(60).useSecureCookie(true);
    }

    @SuppressWarnings("unused")
    private SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth  = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("redirect:/Register/home");
        return auth;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();

    }

}

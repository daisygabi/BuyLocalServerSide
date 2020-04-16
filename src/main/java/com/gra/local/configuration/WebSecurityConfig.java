package com.gra.local.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configure what paths are allowed or not from clients
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/account/").permitAll() // create vendor account
                .antMatchers(HttpMethod.POST, "/api/v1/account/validate/**").permitAll() // validate vendor account
                .antMatchers(HttpMethod.POST, "/api/v1/account/verifyAccount/**").permitAll() // verify vendor account phone number
                .antMatchers(HttpMethod.GET, "/api/v1/account/confirmCode/**").permitAll() // verifying code
                // Products
                .antMatchers(HttpMethod.GET, "/api/v1/products/").permitAll() // get all products
                .antMatchers(HttpMethod.POST, "/api/v1/products/").permitAll() // add a product
                .antMatchers(HttpMethod.PUT, "/api/v1/products/").permitAll() // update product details
                .antMatchers(HttpMethod.DELETE, "/api/v1/products/").permitAll() // delete product

                // Login
                .antMatchers(HttpMethod.POST, "/api/v1/login").permitAll() // login
                .antMatchers(HttpMethod.PUT, "/api/v1/account/password").permitAll() // add password to existing vendor account

                // Disallow everything else
                .anyRequest().authenticated();

        // Optional, if you want to test the API from a browser
        http.httpBasic();

        // Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/api/v1/");
    }

    @Override
    public void configure(WebSecurity web) {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public");
    }
}

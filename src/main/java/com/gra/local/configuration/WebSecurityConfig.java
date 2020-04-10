package com.gra.local.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configure what paths are allowed or not from clients
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
                // Products
                .antMatchers(HttpMethod.POST, "/api/v1/products/").permitAll() // add a product
                .antMatchers(HttpMethod.PUT, "/api/v1/products/").permitAll() // update product details
                .antMatchers(HttpMethod.DELETE, "/api/v1/products/").permitAll() // delete product

                // Disallow everything else
                .anyRequest().authenticated();

        // Optional, if you want to test the API from a browser
        http.httpBasic();

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

package com.example.stageperfectionementbackend.security;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //List all products
        http.authorizeRequests().antMatchers("/api/allProducts/**").hasAnyAuthority("ADMIN", "USER");

        //Consult product per id
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ADMIN", "USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/photos/**").permitAll();

        //Add product
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("ADMIN","USER");

        http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/**").hasAnyAuthority("ADMIN","USER");

        //Modify product
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("ADMIN","USER");

        //Delete product
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN");

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


    }



}


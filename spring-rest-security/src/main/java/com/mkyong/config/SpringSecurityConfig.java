package com.mkyong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);

        /*auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN");*/

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and().authorizeRequests().
                antMatchers(HttpMethod.GET, "/books/**").hasRole("USER").
                antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN"). //save
                antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN"). //update
                antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN").//update
                antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN"). //delete*/
                and().
                csrf().disable();

        /*http
                .authorizeRequests()
                .antMatchers("/books").hasRole("USERS")
                .antMatchers("/books/**").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

    }

    @Bean
    public UserDetailsService userDetailsService() {
        // for testing only
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }

}

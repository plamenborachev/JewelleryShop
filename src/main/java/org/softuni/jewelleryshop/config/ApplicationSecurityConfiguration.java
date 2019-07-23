package org.softuni.jewelleryshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/js/**",
                        "/css/**",
                        "/products/details/**",
                        "/top-offers/**",
                        "/fetch/**",
//                        "/callback/",
//                        "/webjars/**",
                        "/error/**")
                    .permitAll()
                .antMatchers(
                        "/",
                        "/users/register",
                        "/users/login")
                    .anonymous()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("jewellery-shop.herokuapp.com/users/login.html")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");
    }
}

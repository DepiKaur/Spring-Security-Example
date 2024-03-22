package com.example.springsecurityexample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-03-19
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean    // Password Encryption
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired    // Authentication
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean     // Authorization
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize -> authorize
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/images/**").permitAll()
                            .requestMatchers("/register/**").permitAll()
                            .requestMatchers("/index").permitAll()
                            .requestMatchers("/user/**").hasRole("CUSTOMER")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/usersList").hasRole("ADMIN")
                            .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/success", true)
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                                .logoutSuccessUrl("/")
                )
                .exceptionHandling(
                        configurer -> configurer
                                .accessDeniedPage("/access-denied")
                );

        return http.build();
    }


    /*
    // add support for JDBC ...no more hard-coded users
    @Bean
    public UserDetailsManager userDetailsManager(DataSource datasource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(datasource);

        // define query to retrieve a user by username

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_email, password, active from members where user_email=?");

        // define query to retrieve the authorities/roles by username

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_email, role from roles where user_email=?");

        return jdbcUserDetailsManager;
    }

 */


    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john@test.com")
                .password("{noop}test123")
                .roles("CUSTOMER")
                .build();

        UserDetails mary = User.builder()
                .username("mary@test.com")
                .password("{noop}test123")
                .roles("CUSTOMER")
                .build();

        UserDetails susan = User.builder()
                .username("susan@test.com")
                .password("{noop}test123")
                .roles("CUSTOMER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(john, mary, susan);
    }

 */

}

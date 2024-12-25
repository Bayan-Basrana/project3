package com.example.bank2.Config;

import com.example.bank2.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Config {
private final MyUserDetailsService myUserDetailsService;


@Bean
public DaoAuthenticationProvider daoAuthenticationProvider (){
    DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
    return daoAuthenticationProvider;
}

@Bean
public SecurityFilterChain securityFilterChain (HttpSecurity http) throws  Exception {
    http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .authenticationProvider(daoAuthenticationProvider())
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/employee/register","/api/v1/customer/register").permitAll()
            .requestMatchers("/api/v1/employee/update","/api/v1/employee/delete").hasAuthority("EMPLOYEE")
            .requestMatchers("/api/v1/customer/update","/api/v1/account/create","/api/v1/account/update/",
                    "/api/v1/account/delete/","/api/v1/account/myAccounts","/api/v1/account/view-account-details/"
            ,"/api/v1/account/deposit/","/api/v1/account/withdraw/","/api/v1/account/transfer/"
            ,"/api/v1/customer/delete").hasAuthority("CUSTOMER")
            .requestMatchers("/api/v1/user/get-all-user","/api/v1/account/getAllAccounts","/api/v1/account/active-account/"
            ,"/api/v1/employee/get-all-employee","/api/v1/employee/get-employee-by-id/").hasAuthority("ADMIN")
            .requestMatchers("/api/v1/account/block-account/","/api/v1/customer/get-all-customer"
            ,"/api/v1/customer/get-customer-by-id/").hasAnyAuthority("ADMIN","EMPLOYEE")
            .anyRequest().authenticated()
            .and()
            .logout().logoutUrl("/api/v1/logout")
            .deleteCookies("JSESSIONID=5146DEE38B6DD63E07B92D08F76F6457; Path=/; HttpOnly;")
            .invalidateHttpSession(true)
            .and()
            .httpBasic();
    return http.build();

}








}

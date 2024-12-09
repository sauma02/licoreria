/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Admin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        try {
            
            return http.formLogin(form -> form
            .loginPage("/login")
            .failureUrl("/login")
            .permitAll()
            )
            .authorizeHttpRequests(res -> res
            .requestMatchers("/home", "/css/**", "/js/**", "/images/**", "/api/productos/**").permitAll()
            .anyRequest().authenticated()
            ).build();
             
            
            
            
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("Error: " + e.getMessage() + e.getLocalizedMessage());
        }
    }
}

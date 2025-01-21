/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Admin
 */
@Configuration

public class WebConfig implements WebMvcConfigurer {

    @Value("${valor.ruta}")
    private String ruta;
    @Value("${valor.form}")
    private String form;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); // Set maximum file size
        factory.setMaxRequestSize(DataSize.ofMegabytes(20)); // Set maximum request size
        return factory.createMultipartConfig();
    }
    
    
    
    
    
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/formularios/**").addResourceLocations("file: "+this.form);
        registry.addResourceHandler("/uploads/**").addResourceLocations("file: " + this.ruta);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                
                .authorizeHttpRequests(res -> res
                        .requestMatchers("/home","/static/**",
                                "/archivos/**",
                                "/js/**",
                                "/css/**",
                                "/about", 
                                "/listaProductos",
                                "/api/productos/**").permitAll().anyRequest().authenticated()

                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authentication -> authentication;
    }

    @Bean
    //Se crea el bean nuevo para poder redireccionar basado en el rol de la persona
    public CustomSuccessHandler myAuthenticationSuccessHandler() {
        return new CustomSuccessHandler();
    }
}

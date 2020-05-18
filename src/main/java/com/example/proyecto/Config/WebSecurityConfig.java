package com.example.proyecto.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
        .loginPage("/loginForm").loginProcessingUrl("/processLogin")
                .usernameParameter("correo")
                .passwordParameter("password")
                .defaultSuccessUrl("/redirectByRole",true);
        http.logout().logoutUrl("/cerrar").logoutSuccessUrl("/");
        http.authorizeRequests().antMatchers("/admin","/admin/**").hasAuthority("administrador");
        http.authorizeRequests().antMatchers("/sede","/sede/**").hasAuthority("sede");
        http.authorizeRequests().antMatchers("/gestor","/gestor/**").hasAuthority("gestor");
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Autowired
    DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT correo,password,activo FROM sw2_proyecto.usuarios\n" +
                        "where correo =?")
                .authoritiesByUsernameQuery("SELECT correo,tipo FROM sw2_proyecto.usuarios\n" +
                        "where correo = ? and activo = '1'");
    }
}

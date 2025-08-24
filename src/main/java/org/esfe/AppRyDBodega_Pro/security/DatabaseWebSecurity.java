package org.esfe.AppRyDBodega_Pro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

public class DatabaseWebSecurity {

    @Bean
    public UserDetailsManager customUsers(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username, password, status from usuarios where username = ?");
        users.setAuthoritiesByUsernameQuery("select u.username, r.nombreRol from rol ur " +
                "inner join usuarios u on r.id = u.usuario_rol " +
                "inner join roles r on r.id = u.rol_rol " +
                "where u.username = ?");
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize

                //aperturar el acceso a los recursos estaticos
                .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()

                //las vistas publicas que no requieren autenticación
                .requestMatchers("/", "/privacy", "/terms").permitAll()

                //todas las demas vistas necesitan autenticación
                .requestMatchers("/grupos/**").hasAnyAuthority("admin")
                .requestMatchers("/docentes/**").hasAnyAuthority("admin")
                .requestMatchers("/asignaciones/**").hasAnyAuthority("admin")
                .requestMatchers("/alumnos/**").hasAnyAuthority("admin", "docente")

                .anyRequest().authenticated());
        http.formLogin(form -> form.loginPage("/login").permitAll());

        return http.build();
    }


}
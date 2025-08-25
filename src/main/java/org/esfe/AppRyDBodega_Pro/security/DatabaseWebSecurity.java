package org.esfe.AppRyDBodega_Pro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        users.setUsersByUsernameQuery(
                "select username, password, status as enabled " +
                        "from usuarios where username = ?"
        );

        users.setAuthoritiesByUsernameQuery(
                "select u.username, r.nombre_rol as authority " +
                        "from usuarios u inner join roles r on r.id = u.id_rol " +
                        "where u.username = ?"
        );

        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/", "/privacy", "/terms").permitAll()

                .requestMatchers("/usuarios/**").hasAuthority("Administrador")
                .requestMatchers("/roles/**").hasAuthority("Administrador")
                .requestMatchers("/categorias/**").hasAnyAuthority("Administrador", "SupervisorBodega")
                .requestMatchers("/proveedores/**").hasAnyAuthority("Administrador", "SupervisorBodega")
                .requestMatchers("/productos/**").hasAnyAuthority("Administrador", "SupervisorBodega")
                .requestMatchers("/tipoMovimientos/**").hasAnyAuthority("Administrador", "SupervisorBodega")
                .requestMatchers("/movimientos/**").hasAnyAuthority("Administrador", "SupervisorBodega")

                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
        );

        http.logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

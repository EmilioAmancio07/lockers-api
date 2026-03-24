package mx.cua.uam.lockersapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF temporalmente para que tu JS pueda hacer POST sin problemas
                .authorizeHttpRequests(auth -> auth
                        // 1. RUTAS PÚBLICAS (Todos entran)
                        .requestMatchers("/css/**", "/js/**", "/assets/**", "/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/dashboard/**").permitAll()

                        // 3. RUTAS CON AUTORIZACIÓN (Solo el rol ADMIN puede crear o modificar)
                        .requestMatchers(HttpMethod.POST, "/api/alumnos", "/api/asignaciones").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/lockers/**", "/api/asignaciones/**").hasRole("ADMIN")

                        // 2. RUTAS PRIVADAS (Cualquier usuario logueado puede ver el sistema)
                        .requestMatchers("/alumnos.html", "/lockers.html", "/asignaciones.html").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/alumnos", "/api/lockers", "/api/trimestres", "/api/asignaciones").authenticated()

                        // Cualquier otra cosa, pide login
                        .anyRequest().authenticated()
                )
                // Reemplaza tus bloques formLogin y logout con estos:
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/alumnos.html", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    // Aquí creamos a nuestros dos usuarios de prueba para la presentación
    @Bean
    public UserDetailsService userDetailsService() {
        // Usuario 1: El Administrador (Puede ver y modificar todo)
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("uam123")
                .roles("ADMIN")
                .build();

        // Usuario 2: El Asistente (Puede entrar y ver, pero no puede modificar ni asignar lockers)
        UserDetails asistente = User.withDefaultPasswordEncoder()
                .username("asistente")
                .password("uam123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, asistente);
    }
}
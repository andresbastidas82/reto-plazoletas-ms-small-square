package com.pragma.ms_small_square.infrastructure.configuration;

import com.pragma.ms_small_square.infrastructure.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF: Esencial para APIs REST stateless.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Manejo de excepciones: Usar nuestro EntryPoint para errores 401.
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))

                // 3. Gestión de sesión: No crear ni usar sesiones HTTP.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Definir reglas de autorización para las rutas.
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas sin autenticación.
                        .requestMatchers("/api/v1/small-square/get-restaurants").permitAll()
                        .requestMatchers("/api/v1/small-square/dishes-by-restaurant").permitAll()
                        // Rutas para Propietarios (OWNER)
                        .requestMatchers("/api/v1/small-square/create-restaurant").hasRole("OWNER")
                        .requestMatchers("/api/v1/small-square/create-dish").hasRole("OWNER")
                        .requestMatchers("/api/v1/small-square/update-dish/**").hasRole("OWNER")
                        .requestMatchers("/api/v1/small-square/update-status-dish/**").hasRole("OWNER")
                        .requestMatchers("/api/v1/small-square/create-order").hasRole("CUSTOMER")

                        // Rutas para Clientes (CLIENT)
                        .requestMatchers("/api/v1/dish/list-by-restaurant/**").hasRole("CLIENT")

                        // Rutas para Empleados (EMPLOYEE)
                        .requestMatchers("/api/v1/small-square/orders-by-state").hasRole("EMPLOYEE")
                        .requestMatchers("/api/v1/small-square/assign-order-to-employee/**").hasAnyRole("EMPLOYEE", "ADMIN", "OWNER")
                        .requestMatchers("/api/v1/small-square/notify-order-ready/**").hasAnyRole("EMPLOYEE", "ADMIN", "OWNER")

                        // Cualquier otra petición requiere que el usuario esté autenticado.
                        .anyRequest().authenticated()
                )

                // 5. Añadir nuestro filtro JWT antes del filtro de autenticación estándar.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

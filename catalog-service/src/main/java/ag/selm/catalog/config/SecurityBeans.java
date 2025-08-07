package ag.selm.catalog.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        return http
//                .authorizeHttpRequests(authz ->
//                        authz.requestMatchers(HttpMethod.POST, "/catalog-api/products").hasAuthority("SCOPE_edit-catalog")
//                                .requestMatchers(HttpMethod.PUT, "/catalog-api/products/{productId:\\d+}").hasAuthority("SCOPE_edit-catalog")
//                                .requestMatchers(HttpMethod.DELETE, "/catalog-api/products/{productId:\\d+}").hasAuthority("SCOPE_edit-catalog")
//                                .requestMatchers(HttpMethod.GET).hasAuthority("SCOPE_view-catalog")
//                                .anyRequest().denyAll())
//                .csrf(CsrfConfigurer::disable)
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2ResourceServer(oauth2ResourceServer ->
//                        oauth2ResourceServer.jwt(Customizer.withDefaults()))
//                .build();


        // Basic authentification
                return http
                .authorizeHttpRequests(authz ->
//                        authz.requestMatchers("/catalog-api/products/**").hasRole("SERVICE")
//                                .anyRequest().permitAll())
                        authz.requestMatchers("/catalog-api/products/**").permitAll())
//                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .build();
    }
}
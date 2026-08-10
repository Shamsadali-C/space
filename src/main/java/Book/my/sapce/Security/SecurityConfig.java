package Book.my.sapce.Security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.util.ClassUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    public UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthFilter jwtAuthFilter) throws Exception {
       http.csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers(
                               "/auth/**",
                               "/swagger-ui/**",
                               "/v3/api-docs/**",
                               "/swagger-ui.html")
                       .permitAll()
                       .requestMatchers("/admin/**").hasAuthority("ADMIN")
                       .requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                       .requestMatchers("/venue/**").hasAnyAuthority("OWNER","ADMIN")
                       .requestMatchers("/booking/**").hasAnyAuthority("USER","OWNER","ADMIN")
                       .anyRequest().authenticated()
               )
               .sessionManagement(session->
               session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//               .formLogin(Customizer.withDefaults());
//               .logout(log ->log
//                       .logoutUrl("/logout")
//                       .logoutSuccessUrl("/login?logout")
//                       .permitAll())
//               .httpBasic(Customizer.withDefaults());


       return http.build();




   }


   @Bean
   public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
       DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
       provider.setPasswordEncoder(passwordEncoder());
//       provider.setUserDetailsService(userDetailsService);
               return provider;
   }

//   @Bean  //just for first user adding
//    public UserDetailsService userDetailsService(){
//       UserDetails user= User.builder()
//               .username("user")
//               .password(passwordEncoder().encode("user123"))
//               .roles("USER")
//               .build();
//
//       return new InMemoryUserDetailsManager(user,admin);
//    }

//    @Bean
//    @ConditionalOnProperty( name= "swagger.enabled",havingValue ="true")
//    @ConditionalOnProperties()

}

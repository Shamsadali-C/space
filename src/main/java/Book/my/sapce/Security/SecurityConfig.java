package Book.my.sapce.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers("/auth").permitAll()
                       .requestMatchers("/admin/**").hasAuthority("ADMIN")
                       .requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                       .requestMatchers("/venue/**").hasAnyAuthority("OWNER","ADMIN")
                       .requestMatchers("/booking/**").hasAnyAuthority("USER","OWNER","ADMIN")
                       .anyRequest().authenticated()
               )
//               .sessionManagement(session->
//               session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

               .formLogin(Customizer.withDefaults())
//               .logout(log ->log
//                       .logoutUrl("/logout")
//                       .logoutSuccessUrl("/login?logout")
//                       .permitAll())
               .httpBasic(Customizer.withDefaults());


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


}

package gs.example.globalsolution;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Definindo os usuários (user e admin)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password")  // {noop} sem codificação de senha, apenas para testes
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // Configuração do filtro de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desabilita CSRF para facilitar os testes (recomendado para APIs)
                .authorizeRequests()
                // Protegendo os endpoints específicos dos controladores
                .requestMatchers("/dispositivos/**").hasRole("ADMIN")
                .requestMatchers("/configuracoes/**").hasRole("ADMIN") // Outros controladores
                .requestMatchers("/consumo-energia/**").hasRole("ADMIN")
                .requestMatchers("/statusDispositivos/**").hasRole("ADMIN")
                .requestMatchers("/usuarios/**").hasRole("ADMIN")
                // Outros endpoints não são restritos
                .anyRequest().authenticated()
                .and()
                .httpBasic();  // Usa autenticação básica

        return http.build();
    }
}

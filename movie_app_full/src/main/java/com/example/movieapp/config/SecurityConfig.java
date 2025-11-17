package com.example.movieapp.config;

import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.repository.MovieMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/member/join", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(MovieMemberRepository memberRepository) {
        return username -> {
            MovieMember member = memberRepository.findById(username)
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            String role = member.getRolename();
            if (role != null && role.startsWith("ROLE_")) {
                role = role.substring("ROLE_".length());
            }
            return User.builder()
                    .username(member.getId())
                    .password(member.getPassword())
                    .roles(role == null ? "USER" : role)
                    .disabled(member.getEnabled() == 0)
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

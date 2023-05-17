package io.playdata.security.config;

import io.playdata.security.login.model.AccountDTO;
import io.playdata.security.login.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration  // 설정 등록 어노테이션
@EnableWebSecurity  // 웹 보안 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                //username을 사용한 Java 람다식
                username -> {
                    AccountDTO accountDTO = accountRepository.findByUsername(username);  // 특정한 유저가 존재하는지
                    if (accountDTO!=null){
                        // 정의한 DTO를 연결해서 사용
                        return User.withUsername(accountDTO.getUsername())
                                .password(accountDTO.getPassword())
                                .authorities(Collections.emptyList())
                                .build();
                    } else {
                        // Spring Security에서 미리 정의된 에러
                        throw new UsernameNotFoundException("해당 유저가 존재하지 않습니다");
                    }
                }).passwordEncoder(passwordEncoder());  // 복호화 -> bcrypt
        // 암호가 DB에 저장될 때, 그대로 저장 되면 보안문제 -> 인코딩을 변환
        }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Spring Security를 위한 사전 작업

    // configure -> http 요청이 들어왔을 때 설정

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }


}


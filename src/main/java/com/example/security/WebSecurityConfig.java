package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//WebSecurityConfigurerAdapterはSpringSecurityにより提供されている抽象クラス
//Spring Securityに関する設定を行うクラスはこれを継承する必要がある
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Override
    //configureメソッド-実際に認証に関する設定を行なっている
    protected void configure(HttpSecurity http) throws Exception {
        http
        	//authorizeRequestsメソッド-認証に必要なページと不要なページを設定するためのメソッド
        	//.antMatchers("/", "/user/registration", "/css/**").permitAll()という記述で、認証が不要なページを指定
        	//antMatchersメソッドの引数に設定するページのパスを指定し、permitAllメソッドでそれらのページに見認証状態のアクセスを許可
            .authorizeRequests()
                .antMatchers("/", "/user/registration", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
            //fornLoginメソッド-ログインに関する様々な設定を行うメソッド
            .formLogin()
            	//loginPage-ログイン画面のパスを指定
                .loginPage("/user/login")
                //defaultSuccessUrl-ログイン成功時のパスを指定
                .defaultSuccessUrl("/", true)
                .usernameParameter("email")
                //見認証状態でのアクセスを許可
                .permitAll()
                .and()
            //logout-ログアウトに関する設定
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/");
    }
    //configureGlobalメソッド-パスワードの暗号化方法の指定
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
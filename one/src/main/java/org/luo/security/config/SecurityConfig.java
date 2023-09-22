package org.luo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String password = encoder.encode("123");   //对密码进行加密
//        auth.inMemoryAuthentication().withUser("lucy").password(password).roles("admin");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        退出
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/test/hello").permitAll();
//        配置没有权限访问跳转的自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.formLogin()  //自定义自己编写的登录页面
                .loginPage("/login.html")  //登录页面设置
                .loginProcessingUrl("/user/login")  //登录访问路径
                //.defaultSuccessUrl("/test/index").permitAll()  //登录成功之后，跳转路径
                .defaultSuccessUrl("/success.html").permitAll()
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll()  //设置哪些路径可以直接访问，不需要认证
//                1.当前登录用户，只有具有admins权限才可以访问这个路径(单个用户) hasAuthority
//                .antMatchers("/test/index").hasAuthority("admins")
//                2.当前登录用户，有admins,admin,manager都可以访问(多个用户) hasAnyAuthority
//                .antMatchers("/test/index").hasAnyAuthority("admins,admin,manager")
//                3.hasRole方法  ROLE_sale
                .antMatchers("/test/index").hasRole("sale")
                .anyRequest().authenticated()
                .and().csrf().disable();  //关闭crsf防护
    }
}

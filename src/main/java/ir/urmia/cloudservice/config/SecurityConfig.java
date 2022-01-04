package ir.urmia.cloudservice.config;

import ir.urmia.cloudservice.config.security.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers(SecurityConstant.getPermitAllUrls())
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();
//
//        http.csrf().disable();
//
//        http.formLogin().loginPage("/login");
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");


        http
                .csrf().disable()
                .authorizeRequests().mvcMatchers("/hey")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        http.logout();

    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception
//    {
//        auth.inMemoryAuthentication()
//                .withUser("mahdi")
//                .password("{noop}password")
//                .roles("USER");
//    }

}

package eu.komanda30.kupra.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan({ "eu.komanda30.kupra.security" })
@PropertySource("classpath:/security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private Environment environment;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                PasswordEncoder passwordEncoder)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        final String secret = environment.getRequiredProperty("security.secret");
        return new StandardPasswordEncoder(secret);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .loginProcessingUrl("/login/process")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .rememberMe()
                .and()
            .authorizeRequests()
                .antMatchers(
                        "/login*",
                        "/error/**",
                        "/registration/**",
                        "/css/**",
                        "/fonts/**",
                        "/images/**",
                        "/lightbox/**",
                        "/js/**").permitAll()
                .anyRequest().authenticated();
    }
}

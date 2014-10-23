package eu.komanda30.kupra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@ComponentScan({ "eu.komanda30.kupra.security" })
@PropertySource("file:${KUPRA_CONFIG_DIR}/security.properties")
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
                .loginProcessingUrl("/login_process")
                .defaultSuccessUrl("/successtmp")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers(
                        "/error/**",
                        "/registration/**",
                        "/css/**",
                        "/fonts/**",
                        "/img/**",
                        "/js/**").permitAll()
               // .anyRequest().authenticated();
        .anyRequest().permitAll();
    }
}

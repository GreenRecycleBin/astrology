package io.github.greenrecyclebin.astrology;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(PathRequest.toH2Console());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().anyRequest().permitAll();
//        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//        .permitAll()
//        .anyRequest()
//        .authenticated()
//        .and()
//        .formLogin()
//        .permitAll();
  }
}

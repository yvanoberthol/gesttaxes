package org.yvanscoop.gesttaxes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("yvanoberthol").password("{noop}1234").roles("Admin","User")
                .and().withUser("jaures").password("{noop}leroi").roles("User");*/
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials,active from user where username =?").
                authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username =?")
                .rolePrefix("ROLE_")
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        //tous les requetes sont protégées
       // http.authorizeRequests().anyRequest().authenticated();
        //autoriser ce qui ont le role admin d'ajouter une entreprise
        http.authorizeRequests()
                .antMatchers("/ajoutEntreprise","/ajoutTaxe","/formEntreprise","/formTaxe").hasRole("Admin");
        http.authorizeRequests()
                .antMatchers("/formEntreprise","/formTaxe")
                .access("hasRole('Designer') or hasRole('Admin')");
        http.authorizeRequests()
                .antMatchers("/entreprises","/taxes","/alltaxes")
                .access("hasRole('User') or hasRole('Admin') or hasRole('Designer')");
        http.exceptionHandling().accessDeniedPage("/403");
    }
}

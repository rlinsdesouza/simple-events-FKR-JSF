//package br.edu.ifpb.simpleevents.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//	
//@Configuration
//@EnableWebSecurity
//public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
////	https://o7planning.org/en/11705/create-a-login-application-with-spring-boot-spring-security-jpa
////	https://blog.algaworks.com/spring-security/
////	https://www.baeldung.com/spring-boot-security-autoconfiguration
////	https://stackabuse.com/password-encoding-with-spring-security/
//	
////	jsf
////	https://codenotfound.com/jsf-primefaces-spring-security-example.html
//	
////	@Autowired
////	private UserDetailsSimpleEvent usuarioDetalhes;
//
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	
//		//paginas que nao precisam de login
//		http.authorizeRequests()
//									.antMatchers("/",
//												"/usuarios/form",
//												"/usuarios/save",
//												"/datafaker",
//												"/pesquisar**",
////												"/eventos/**",
//												"/logout").permitAll()
//									.antMatchers("/especialidades/**", "/usuarios").access("hasRole('ROLE_ADMIN')")
//									.antMatchers("/javax.faces.resource/**","/resources/**", "/css/**", "/webjars/**").permitAll()
//
//									.and().formLogin()
//							        		.loginPage("/login.xhtml")
//											.defaultSuccessUrl("/", true)
//											.usernameParameter("app_username")
//							                .passwordParameter("app_password")
//											.failureUrl("/login.xhtml?error=true")
//							        		.permitAll()
//									.and().logout().logoutSuccessUrl("/")
//									.and().rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
//
//									
//									//necessario apenas para liberar o acesso ao banco de testes h2-console
//									.and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
//									.and().csrf().ignoringAntMatchers("/h2-console/**")
//									.and().headers().frameOptions().sameOrigin();
//		
//		// not needed as JSF 2.2 is implicitly protected against CSRF
//		http.csrf().disable();
//		
//	super.configure(http);
//	}
//	
//
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////	auth
////		.userDetailsService(usuarioDetalhes)
////		.passwordEncoder(new BCryptPasswordEncoder());
////	}
//	
//	  @Autowired
//	  public void configureGlobal(AuthenticationManagerBuilder auth)
//	      throws Exception {
//	    auth.inMemoryAuthentication().withUser("user")
//	        .password("1234").roles("USER").and()
//	        .withUser("admin").password("admin").roles("ADMIN");
//	  }
//
//}

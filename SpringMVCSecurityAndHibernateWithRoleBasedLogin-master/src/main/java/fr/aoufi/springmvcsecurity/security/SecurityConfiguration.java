package fr.aoufi.springmvcsecurity.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("myUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	PersistentTokenRepository persistentTokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Ex�cution de configureGlobalSecurity() - SecurityConfiguration");
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	
	/**
	 * WebSecurityConfigurerAdapter fournie une configuration par d�faut 
	 * dans la m�thode configure(HttpSecurity http).
	 * 
	 * L'acc�s aux URL est r�gi comme suit:
	 * '/' '/welcome' & '/list': accessible � tous
	 * '/newuser' & '/delete-user-*': Accessible uniquement � Admin
	 * '/edit-user-*': Accessible � Admin & DBA
	 */
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/welcome", "/login", "/logout")
				.permitAll();
		
		// Pour tous les r�les: Lister tous les utilisateurs
		http.authorizeRequests().antMatchers("/list")
				.access("hasAnyRole('USER', 'ADMIN', 'DBA')");
		
		// Pour Admin seulement: Suppression et cr�ation d'un utilisateur
		http.authorizeRequests().antMatchers("/newuser/**", "/delete-user-*")
				.access("hasRole('ADMIN')");
		
		// Pour DBA et Admin: Modification d'un compte utilisateur 
		http.authorizeRequests().antMatchers("/edit-user-*")
				.access("hasAnyRole('ADMIN', 'DBA')");
		
		http
			.authorizeRequests()
				.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.defaultSuccessUrl("/list")
				.usernameParameter("username")
				.passwordParameter("password")
				.and()
			.rememberMe()
				.rememberMeParameter("remember-me")
			.tokenRepository(persistentTokenRepository)
				.tokenValiditySeconds(86400)
				.and().csrf().and().exceptionHandling()
			.accessDeniedPage("/Access_Denied");
	}
	
	/**
	 * Cette m�thode permet de crypter le mot de passe dans la base de donn�es, 
	 * on utilisant la classe BCryptPasswordEncoder. (voir la d�mo - package runtime)
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Puisque nous stockons les informations d'identification dans la base de donn�es, 
	 * la configuration de DaoAuthenticationProvider avec UserDetailsService serait utile.
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	/**
	 * Cette m�thode fournie la fonctionnalit� "RememberMe", qui permettra d'identifier l'utilisateur � travers plusieurs sessions. 
	 * "RememberMe" ne d�marre qu'apr�s la fin de la session (avec un d�lai d'inactivit�). 
	 * 
	 * Et pour garder la trace des donn�es de jetons dans la base de donn�es, 
	 * nous avons configur� une impl�mentation PersistentTokenRepository.
	 * @return
	 */
	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, persistentTokenRepository);
		return tokenBasedservice;
	}
	
	/**
	 * AuthenticationTrustResolver: Indique si le jeton d'authentification transmis 
	 * repr�sente un utilisateur anonyme. AuthenticationTrustResolverImpl et son impl�mentation correspondante.  
	 * Cette interface fournit une m�thode isAnonymous (Authentication), 
	 * qui permet aux classes int�ress�es de prendre en compte ce type particulier d'�tat d'authentification. 
	 * @return
	 */
	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}
}
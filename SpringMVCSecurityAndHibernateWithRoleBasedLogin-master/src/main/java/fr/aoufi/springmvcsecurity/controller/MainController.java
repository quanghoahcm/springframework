package fr.aoufi.springmvcsecurity.controller;

import fr.aoufi.springmvcsecurity.model.Role;
import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.service.RoleService;
import fr.aoufi.springmvcsecurity.service.UserService;
import fr.aoufi.springmvcsecurity.validator.UserFormValidator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"user_roles"})
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	UserFormValidator userFormValidator;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	MessageSource messageSource;
	
	/**
	 * Pour stocker les informations de connexion persistantes 
	 * dans une table de la base de donn�es ('PERSISTENT_LOGINS') entre les sessions.
	 */
	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	/**
	 * Pour �valuer les jetons d'authentification
	 */
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	/**
	 * Pour contr�ler les champs du formulaire d'inscription
	 * @InitBinder: Permet d'dentifier les m�thodes qui initialisent le WebDataBinder.
	 * WebDataBinder: est un DataBinder qui lie le param�tre de requ�te aux objets JavaBean.
	 * Il fournit des m�thodes pour assigner nos classes de validateurs. 
	 * En utilisant les m�thodes addValidators () et setValidator (), nous pouvons 
	 * assigner nos instances de validateurs.
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}

	@RequestMapping(value = {"/", "/welcome"}, method = {RequestMethod.GET})
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "C'est la page d'accueil!");
		return "welcomePage";
	}
	
	/**
	 * Cette m�thode permet d'ajouter un nouvel utilisateur.
	 */
	@RequestMapping(value = {"/newuser", "/userregist"}, method = {RequestMethod.GET})
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedonuser", getPrincipal());
		return "registration";
	}
	
	/**
	 * Cette m�thode sera appel�e lors de la soumission du formulaire, en g�rant la requ�te POST pour 
	 * enregistrer l'utilisateur dans la bd. Il valide �galement l'entr�e de l'utilisateur
	 */
	@RequestMapping(value = {"/newuser", "/userregist"}, method = {RequestMethod.POST})
	public String saveUser(@Valid User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			logger.info("Retour � la page registration.jsp");
			return "registration";
		} else {
			userService.saveUser(user);
			model.addAttribute("success", messageSource.getMessage("", new String[]{user.getUsername()}, Locale.getDefault()) + " "
							+ user.getFirstName() + " " + user.getLastName() + " ");
			model.addAttribute("loggedonuser", getPrincipal());
			return "registrationsuccess";
		}
	}
	
	/**
	 * Cette m�thode permet de mettre � jour un utilisateur existant.
	 */
	@RequestMapping(value = {"/edit-user-{username}"}, method = {RequestMethod.GET})
	public String editUser(@PathVariable String username, ModelMap model) {
		User user = userService.findByUname(username);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedonuser", getPrincipal());
		return "registration";
	}
	
	/**
	 * Cette m�thode sera appel�e lors de la soumission du formulaire, en g�rant la requ�te POST pour
	 * mettre � jour l'utilisateur dans la bd. Il valide �galement l'entr�e de l'utilisateur
	 */
	@RequestMapping(value = {"/edit-user-{username}"}, method = {RequestMethod.POST})
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String username) {
		if (result.hasErrors()) {
			return "registration";
		} else {			
			/*
		    //d�commenter ce bloc pour mettre � jour le "username".
			if(!userService.isUserUNAMEUnique(user.getId(), user.getUsername())){
				FieldError unameError = new FieldError("user", "username", 
												messageSource.getMessage("registration.uniqueness.username", new String[]{user.getUsername()}, 
												Locale.getDefault())); 
			    result.addError(unameError); 
			    return "registration"; 
			}
			*/			
			userService.updateUser(user);
			model.addAttribute("success", messageSource.getMessage("", new String[]{user.getUsername()}, Locale.getDefault()) + " "
							+ user.getFirstName() + " " + user.getLastName() + " ");
			model.addAttribute("loggedonuser", getPrincipal());
			return "updatesuccess";
		}
	}
	
	/**
	 * Cette m�thode permet de supprimer un utilisateur par son username
	 */
	@RequestMapping(value = {"/delete-user-{username}"}, method = {RequestMethod.GET})
	public String deleteUser(@PathVariable String username) {
		userService.deleteUserByUname(username);
		return "redirect:/list";
	}
	
	/**
	 * Lister tous les utilisateurs
	 */
	@RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
	public String listUsers(ModelMap model) {
		List<User>  users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedonuser", getPrincipal());
		return "userslist";
	}
	
	/**
	 * Fournir la liste des r�les d'utilisateurs aux vues
	 */
	@ModelAttribute("roleList")
	public List<Role> initializeUserRoles() {
		return roleService.findAll();
	}
	
	/**
	 * Cette m�thode g�re les requ�tes GET de connexion . 
	 * Si les utilisateurs, d�j� connect�s, essayent d'acc�der � nouveau � la
	 * page de connexion, ils seront redirig�s vers la page qui affiche la liste des utilisateurs.
	 */
	@RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
	public String loginPage() {
		// Utilisateur d�j� connect� ?
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			return "redirect:/list";
		}
	}
	
	/**
	 * G�re les requ�tes de d�connexion
	 */
	@RequestMapping(value = {"/logout"}, method = {RequestMethod.GET})
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// new SecurityContextLogoutHandler().logout(request, response,
			// auth);
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication((null) );
		}
		return "redirect:/login?logout";
	}
	
	/**
	 * Redirection lors d'un acc�s refus�
	 */
	@RequestMapping(value = {"/Access_Denied"}, method = {RequestMethod.GET})
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedonuser", getPrincipal());
		return "accessDenied";
	}
	
	/**
	 * Obtenir le principal [username] de l'utilisateur actuellement connect�.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * Renvoie true si l'utilisateur est d�j� authentifi�, sinon false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}
}
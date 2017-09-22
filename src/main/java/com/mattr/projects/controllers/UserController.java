package com.mattr.projects.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mattr.projects.models.Pack;
import com.mattr.projects.models.Subscription;
import com.mattr.projects.models.User;
import com.mattr.projects.services.SubService;
import com.mattr.projects.services.UserService;
import com.mattr.projects.validator.UserValidator;

@Controller
@SessionAttributes("dates")
public class UserController {
	private UserService userService;
	private UserValidator userValidator;
	private SubService subService;
    
	public UserController(UserService userService, UserValidator userValidator,SubService subService) {
		this.userService = userService;
		this.userValidator = userValidator;
		this.subService = subService;
	}
	
	@ModelAttribute("dates")
    public int[] getStateList(){
		int[] dates = new int[31];
		for(int i=0;i<31;i++) {
			dates[i] = i+1;
		}
		return dates;
    }
	
	@RequestMapping("/registration")
	public String viewLogReg(@Valid @ModelAttribute("user") User user,BindingResult result) {
		return "loginReg";
	}
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		userValidator.validate(user, result);
		
	    if (result.hasErrors()) {
	        return "loginReg";
	    }
	    List<User> users = userService.allUsers();
	    if(users.size()==0 || users==null) {
	    	System.out.println("No admins, new user will be admin");
	    	userService.saveUserWithAdminRole(user);
	    	return "redirect:/admin";
	    }else {
	    	System.out.println("Regular USER Created!!!");
	    	userService.saveWithUserRole(user);
	    	return "redirect:/";
	    }
    }
    
    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model,@Valid @ModelAttribute("user") User user,BindingResult result) {
        
    	if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
            System.out.println("Error created");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
            System.out.println("Logout created");
        }
        return "loginReg";
    }
    
    @RequestMapping("/")
    public String afterLogin(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("currentUser",user);
        if(user.isAdmin()) {
        	return "redirect:/admin";
        }
        else if(user.getSubscription()==null) {
        	return "redirect:/selection";
        }
        return "homePage";
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model,@Valid @ModelAttribute("pack") Pack pack, BindingResult result) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	if(!user.isAdmin()) {
        	return "redirect:/";
        }
        model.addAttribute("currentUser", user);
        //table1 ArrayList
        ArrayList<User> users = userService.allUsers();
        model.addAttribute("users", users);
        
        //table2 ArrayList
        ArrayList<Pack> packs = subService.allPack();
        model.addAttribute("packs", packs);
        
        return "adminPage";
    }
    @PostMapping("/admin")
    public String createPack(Principal principal, Model model,@Valid @ModelAttribute("pack") Pack pack, BindingResult result) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	if(!user.isAdmin()) {
        	return "redirect:/";
        }
        model.addAttribute("currentUser", user);
    	
    	if (!result.hasErrors()) {
    		//Add Entry of <<Model>>
    		subService.addPackage(pack);    		
    	}
    	//table1 ArrayList
        ArrayList<User> users = userService.allUsers();
        model.addAttribute("users", users);

    	//table2 ArrayList
        ArrayList<Pack> packs = subService.allPack();
        model.addAttribute("packs", packs);
        
    	return "adminPage";
    }
    @RequestMapping("/activate/{id}")
    public String activate(Principal principal, @PathVariable("id") Long id) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	if(!user.isAdmin()) {
        	return "redirect:/";
        }
    	subService.activate(id);
    	return "redirect:/admin";
    }
    @RequestMapping("/deactivate/{id}")
    public String deactivate(Principal principal, @PathVariable("id") Long id) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	if(!user.isAdmin()) {
        	return "redirect:/";
        }
    	subService.deactivate(id);
    	return "redirect:/admin";
    }
    @RequestMapping("/delete/{id}")
    public String destroyPackage(Principal principal, @PathVariable("id") Long id) {
    	String username = principal.getName();
    	User user = userService.findByUsername(username);
    	if(!user.isAdmin()) {
        	return "redirect:/";
        }
    	subService.deletePack(id);
    	return "redirect:/admin";
    }
    @RequestMapping("/selection")
    public String viewSub(Principal principal, Model model,@Valid @ModelAttribute("subscription") Subscription sub, BindingResult result) {
    	String username = principal.getName();
        User user = userService.findByUsername(username);
        if(user.isAdmin()) {
        	return "redirect:/admin";
        }
        model.addAttribute("currentUser",user);
    	ArrayList<Pack> packages = subService.allActivePack();
    	model.addAttribute("packages",packages);
    	return "newSub";
    }
    @PostMapping("/selection")
    public String createSub(Principal principal, Model model,@Valid @ModelAttribute("subscription") Subscription sub, BindingResult result) {
    	String username = principal.getName();
        User user = userService.findByUsername(username);
        if(user.isAdmin()) {
        	return "redirect:/admin";
        }
        model.addAttribute("currentUser",user);
    	if (result.hasErrors()) {
    		ArrayList<Pack> packages = subService.allActivePack();
        	model.addAttribute("packages",packages);
        	return "newSub";
    	}else{
    		sub.setUser(user);
    		subService.addSub(sub);
    		return "redirect:/profile";//go to dashboard
    	}
    }
    @RequestMapping("/profile")
    public String home(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        if(user.isAdmin()) {
        	return "redirect:/admin";
        }
        model.addAttribute("currentUser",user);
        Subscription userSub = subService.userSub(user);
        model.addAttribute("sub", userSub);
        return "homePage";
    }
}

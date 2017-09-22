package com.mattr.projects.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mattr.projects.models.User;
import com.mattr.projects.repositories.RoleRepository;
import com.mattr.projects.repositories.UserRepository;
import com.mattr.projects.models.Role;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveWithUserRole(User user) {
    	List<Role> roles = Arrays.asList(roleRepository.findByName("ROLE_USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
    	ArrayList<Role> roles = new ArrayList<Role>();
    	roles.add(roleRepository.findByName("ROLE_ADMIN"));
    	roles.add(roleRepository.findByName("ROLE_USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User updateUser(User user) {
    	user.setUpdatedAt(new Date());
    	return userRepository.save(user);
    }
	public List<User> allAdmins(){
		Role role = roleRepository.findByName("ROLE_ADMIN");
		return role.getUsers();
	}
	public ArrayList<User> allUsers(){
		return (ArrayList<User>)userRepository.findAll();
	}
//	public void makeAdmin(Long id) {
//		User user = userRepository.findOne(id);
//		if(!user.isAdmin()) {
//			this.saveUserWithAdminRole(user);
//		}
//	}
//	public void destroyUser(Long id) {
//		userRepository.delete(id);
//	}
}

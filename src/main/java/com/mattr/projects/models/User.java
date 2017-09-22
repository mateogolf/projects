package com.mattr.projects.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

//imports removed for brevity

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Email
	private String username;
	@Size(min=8, message="Password must be greater than 8 characters")
	private String password;
	@Transient
	private String passwordConfirmation;
	@Size(min=2,max=255) //extra
	private String firstName;//extra
	@Size(min=2,max=255)//extra
	private String lastName;//extra
	
	
	//Relationships
	@OneToOne(mappedBy="user", fetch=FetchType.LAZY)
	private Subscription subscription;
	
	
	
	public Subscription getSubscription() {
		return subscription;
	}


	public void setSub(Subscription sub) {
		this.subscription = subscription;
	}


	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")//extra
	private Date createdAt;
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")//extra
	private Date updatedAt;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
 
	public User() {
	}
	 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
	    this.id = id;
	}
	
	public String getUsername() {
	    return username;
	}
	
	public void setUsername(String username) {
	    this.username = username;
	}
	
	public String getPassword() {
	    return password;
	}
	
	public void setPassword(String password) {
	    this.password = password;
	}
	
	public String getPasswordConfirmation() {
	    return passwordConfirmation;
	}
	
	public void setPasswordConfirmation(String passwordConfirmation) {
	    this.passwordConfirmation = passwordConfirmation;
	}
	
	public Date getCreatedAt() {
	    return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
	    this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
	    return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
	    this.updatedAt = updatedAt;
	}
	
	public List<Role> getRoles() {
	    return roles;
	}
	
	public void setRoles(List<Role> roles) {
	    this.roles = roles;
	}
	 
	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		this.updatedAt = new Date();
	}
	public boolean isAdmin() {
		for(Role role:roles) {
			if(role.getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}

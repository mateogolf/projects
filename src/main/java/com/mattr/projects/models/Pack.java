package com.mattr.projects.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="packages")
public class Pack {
	@Id
	@GeneratedValue
	private Long id;
	@Size(min=2) 
	private String name;
	@Range(min=1)
	private Float cost;
	private boolean availability;
	
	//Relationships
	@OneToMany(mappedBy="pack", fetch=FetchType.LAZY)
	private List<Subscription> subs;
	
	
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")//extra
	private Date createdAt;
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")//extra
	private Date updatedAt;
	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		this.updatedAt = new Date();
	}
	public Pack() {
	}

	public Pack(String name, Float cost, boolean availability) {
		this.name = name;
		this.cost = cost;
		this.availability = true;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public List<Subscription> getSubs() {
		return subs;
	}

	public void setSubs(List<Subscription> subs) {
		this.subs = subs;
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
	
	public int countSubs() {
		if(this.subs==null) {
			return 0;
		}
		return subs.size();
	}

}

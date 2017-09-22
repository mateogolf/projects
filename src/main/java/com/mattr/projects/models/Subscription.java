package com.mattr.projects.models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.assertj.core.util.DateUtil;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="subscriptions")
public class Subscription {
	@Id
	@GeneratedValue
	private Long id;
	@Range(min=1,max=31)
    private int dueDate;
	
	//Relationships
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pack_id")
	private Pack pack;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
	
	
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
	public Subscription() {
	}

	public Subscription(int dueDate, Pack pack, User user) {
		this.dueDate = dueDate;
		this.pack = pack;
		this.user = user;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public int getDueDate() {
		return dueDate;
	}

	public void setDueDate(int dueDate) {
		this.dueDate = dueDate;
	}

	public Pack getPack() {
		return pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	public Date nextDueDate() {
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		int year = today.getYear()+1900;
		System.out.println(today);
		System.out.println(year+1900);
		cal.set(year, today.getMonth()+1, 1);
		int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date result = new Date();
//		System.out.println(year);
		if(today.getDay()<=this.dueDate) {			
			if(dueDate<= lastday) {
				cal.set(year, today.getMonth()+1, this.dueDate);
			}else {
				cal.set(year, today.getMonth()+1, lastday);
			}
			result = cal.getTime();//new Date();
		}
		else {
			if(dueDate<= lastday) {
				cal.set(year, today.getMonth(), this.dueDate);
			}else {
				cal.set(year, today.getMonth(), lastday);
			}
			result = cal.getTime();
		}
		return result;
	}
	
}

package com.mattr.projects.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mattr.projects.models.Subscription;
import com.mattr.projects.models.User;

public interface SubRepo extends CrudRepository<Subscription,Long>{
	Subscription findByUser(User user);
}

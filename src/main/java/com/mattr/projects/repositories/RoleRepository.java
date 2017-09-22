package com.mattr.projects.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mattr.projects.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
	Role findByName(String name);
}

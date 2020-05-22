package edu.upc.mishuserverapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.mishuserverapi.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

	Role findByName(String name);
    
}
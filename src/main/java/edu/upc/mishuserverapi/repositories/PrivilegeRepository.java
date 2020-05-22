package edu.upc.mishuserverapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.mishuserverapi.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

	Privilege findByName(String name);
    
}
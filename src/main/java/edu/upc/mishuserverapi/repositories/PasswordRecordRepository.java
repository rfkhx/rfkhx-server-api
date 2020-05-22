package edu.upc.mishuserverapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.mishuserverapi.model.PasswordRecord;

public interface PasswordRecordRepository extends JpaRepository<PasswordRecord,Long>{
    
}
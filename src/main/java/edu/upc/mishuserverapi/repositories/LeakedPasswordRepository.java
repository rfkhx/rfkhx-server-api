package edu.upc.mishuserverapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.mishuserverapi.model.LeakedPassword;

public interface LeakedPasswordRepository extends JpaRepository<LeakedPassword, Long> {
    List<LeakedPassword> findByShorthashIgnoreCase(String shorthash);
}
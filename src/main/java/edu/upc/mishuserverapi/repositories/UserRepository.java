package edu.upc.mishuserverapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.mishuserverapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	Optional<User> findById(Long id);
}
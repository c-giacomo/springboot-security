package it.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.security.jwt.model.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> { 
	
	public Users findByUsername(String username);
}

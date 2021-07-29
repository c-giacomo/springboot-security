package it.multiple.auth.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.multiple.auth.jwt.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	public Users findByUsername(String username);

}

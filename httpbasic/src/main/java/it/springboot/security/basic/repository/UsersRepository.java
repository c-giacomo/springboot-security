package it.springboot.security.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.springboot.security.basic.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> { 
	
	public Users findByNome(String nome);
}

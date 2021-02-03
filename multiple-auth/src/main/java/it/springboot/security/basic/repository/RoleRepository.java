package it.springboot.security.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.springboot.security.basic.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

}

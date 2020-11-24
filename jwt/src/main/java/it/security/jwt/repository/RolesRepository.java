package it.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.security.jwt.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

}

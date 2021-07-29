package it.multiple.auth.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.multiple.auth.jwt.model.Roles;
import it.multiple.auth.jwt.model.Users;
import it.multiple.auth.jwt.repository.RolesRepository;
import it.multiple.auth.jwt.repository.UsersRepository;

@SpringBootApplication
public class MultipleProfileAuthJwtApplication implements CommandLineRunner {
	
	@Autowired private UsersRepository usersRepository;
	@Autowired private RolesRepository rolesRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultipleProfileAuthJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Users giacomo = new Users("Giacomo", "Chiavolotti", "giacomo", new BCryptPasswordEncoder().encode("230483"));
		List<Users> users = new ArrayList<Users>();
		users.add(giacomo);
		
		Roles admin = new Roles("Admin");
		List<Roles> roles = new ArrayList<Roles>();
		roles.add(admin);
		
		users.forEach(i -> i.setRoles(roles));
		roles.forEach(i -> i.setUsers(users));
		
		rolesRepository.saveAll(roles);
		usersRepository.saveAll(users);
	}

}

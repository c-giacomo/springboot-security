package it.springboot.security.basic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.springboot.security.basic.model.Roles;
import it.springboot.security.basic.model.Users;
import it.springboot.security.basic.repository.RoleRepository;
import it.springboot.security.basic.repository.UsersRepository;

@SpringBootApplication
public class HttpbasicApplication implements CommandLineRunner {
	
	@Autowired private UsersRepository userRepository;
	@Autowired private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(HttpbasicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Roles role = new Roles();
		role.setDescription("ADMIN");
		
		Users user = new Users();
		user.setNome("Giacomo");
		user.setCognome("Chiavolotti");
		user.setUsername("giacomo");
		user.setPassword(new BCryptPasswordEncoder().encode("230483"));
		user.setRoles(Arrays.asList(role));
		
		role.setUsers(Arrays.asList(user));
		
		userRepository.save(user);
		roleRepository.save(role);
	}

}

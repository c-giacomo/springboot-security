package it.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.security.jwt.model.Roles;
import it.security.jwt.model.Users;
import it.security.jwt.repository.RolesRepository;
import it.security.jwt.repository.UsersRepository;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {
	
	@Autowired private UsersRepository usersRepository;
	@Autowired private RolesRepository rolesRepository;

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<Users> userList = new ArrayList<Users>();
		Users admin = new Users();
		admin.setNome("Giacomo");
		admin.setCognome("Chiavolotti");
		admin.setPassword(new BCryptPasswordEncoder().encode("230483"));
		
		userList.add(admin);
		
		Roles adminRole = new Roles();
		adminRole.setDescription("ADMIN");
		adminRole.setUsers(userList);
		
		Roles opRole = new Roles();
		opRole.setDescription("OPERATOR");
		opRole.setUsers(userList);
		
		List<Roles> rolesList = new ArrayList<Roles>();
		rolesList.add(adminRole);
		rolesList.add(opRole);
		
		admin.setRoles(rolesList);
		usersRepository.save(admin);
	
		rolesRepository.saveAll(rolesList);
		
	}

}

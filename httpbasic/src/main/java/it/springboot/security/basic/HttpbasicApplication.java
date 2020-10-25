package it.springboot.security.basic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.springboot.security.basic.model.Roles;
import it.springboot.security.basic.model.Users;
import it.springboot.security.basic.repository.UsersRepository;

@SpringBootApplication
public class HttpbasicApplication implements CommandLineRunner {
	
	@Autowired private UsersRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HttpbasicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Users user = new Users();
		user.setUsername("admin");
		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		user.setRoles(Arrays.asList(new Roles("ADMIN")));
		userRepository.save(user);
	}

}

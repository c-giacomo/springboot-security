package it.springboot.security.basic.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.springboot.security.basic.model.Users;
import it.springboot.security.basic.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationUserDetailService implements UserDetailsService {
	
	private final UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersRepository.findByNome(username);
    	List<GrantedAuthority> grantedAuthorities = user.getRoles()
				.stream()
				.map(roles -> new SimpleGrantedAuthority(roles.getDescription()))
				.collect(Collectors.toList());
    	
    	return new User(user.getNome(), user.getPassword(), grantedAuthorities);
	}

}

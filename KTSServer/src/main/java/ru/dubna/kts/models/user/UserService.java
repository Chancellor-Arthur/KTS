package ru.dubna.kts.models.user;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.dubna.kts.exceptions.specific.NotFoundException;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User create(CredentialsDto credentialsDto) {
		User user = new User(credentialsDto.getUsername(), passwordEncoder.encode(credentialsDto.getPassword()));
		return userRepository.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getByUsername(username);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ALL")));
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User getByUsername(String username) {
		return findByUsername(username).orElseThrow(
				() -> new NotFoundException(String.format("Пользователь с именем: '%s' не найден", username)));
	}

	public User getById(UUID id) {
		return findById(id).orElseThrow(
				() -> new NotFoundException(String.format("Пользователь с идентификатором: '%d' не найден", id)));
	}
}

package ru.liga.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.liga.domain.User;
import ru.liga.repo.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long id = Long.parseLong(username);
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    }
}

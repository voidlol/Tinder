package ru.liga.repo;

import org.springframework.data.repository.CrudRepository;
import ru.liga.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(Long id);
}

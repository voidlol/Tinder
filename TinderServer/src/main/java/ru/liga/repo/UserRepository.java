package ru.liga.repo;

import org.springframework.data.repository.CrudRepository;
import ru.liga.domain.SexType;
import ru.liga.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User getUserById(Long id);
    List<User> findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(SexType userSex, SexType lookingFor);
}

package ru.liga.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.method.P;
import ru.liga.domain.Profile;
import ru.liga.domain.SexType;

import java.util.List;
import java.util.Set;

public interface ApplicationRepository extends CrudRepository<Profile, Long> {

    List<Profile> findAll();
}

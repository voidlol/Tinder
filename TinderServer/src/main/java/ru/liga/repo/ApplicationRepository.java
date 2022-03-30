package ru.liga.repo;

import org.springframework.data.repository.CrudRepository;
import ru.liga.domain.Application;
import ru.liga.domain.SexType;

import java.util.Set;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Set<Application> findApplicationBySexTypeAndLookingForContaining(SexType userSex, SexType looking);
}

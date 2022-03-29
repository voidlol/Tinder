package ru.liga.repo;

import org.springframework.data.repository.CrudRepository;
import ru.liga.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

}

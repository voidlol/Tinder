package ru.liga.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.method.P;
import ru.liga.domain.Profile;
import ru.liga.domain.SexType;

import java.util.List;
import java.util.Set;

public interface ApplicationRepository extends CrudRepository<Profile, Long> {

    @Query(value = "select * from profile\n" +
            "where sex_type in (select looking_for from sex_type where profile_id = :profileId)\n" +
            "and id <> :profileId\n" +
            "and id not in (select to_profile from profile_likes where from_profile = :profileId)\n" +
            "and id in (select profile_id from sex_type where looking_for = :sexType)", nativeQuery = true)
    Set<Profile> findAllValidProfilesForUser(Long profileId, int sexType);
}

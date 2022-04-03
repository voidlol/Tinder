package ru.liga.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.liga.domain.Profile;

import java.util.Set;

public interface ApplicationRepository extends CrudRepository<Profile, Long> {

    @Query(value = "select distinct id, description, name, sex_type from tinder_db.profile " +
            "inner join tinder_db.profile_likes pl on pl.from_profile != :profileId " +
            "inner join tinder_db.sex_type on looking_for = :sexType and sex_type.profile_id = id " +
            "where id != :profileId", nativeQuery = true)
    Set<Profile> findAllValidProfilesForUser(@Param("profileId") Long profileId, @Param("sexType") int sexType);
}

package ru.liga.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.liga.domain.Profile;

import java.util.Set;

public interface ApplicationRepository extends CrudRepository<Profile, Long> {

    @Query(value = "select * from tinder_db.profile " +
            "where sex_type in (select looking_for from tinder_db.sex_type where profile_id = :profileId) " +
            "and id <> :profileId " +
            "and id not in (select to_profile from tinder_db.profile_likes where from_profile = :profileId) " +
            "and id in (select profile_id from tinder_db.sex_type where looking_for = :sexType);", nativeQuery = true)
    Set<Profile> findAllValidProfilesForUser(@Param("profileId") Long profileId, @Param("sexType") int sexType);
}

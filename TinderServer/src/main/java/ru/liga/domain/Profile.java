package ru.liga.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private SexType sexType;
    @ElementCollection(targetClass = SexType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "sex_type", joinColumns = @JoinColumn(name = "profile_id"))
    private Set<SexType> lookingFor;

    @ManyToMany()
    @JoinTable(name = "profile_likes",
            joinColumns = {@JoinColumn(name = "from_profile")},
            inverseJoinColumns = {@JoinColumn(name = "to_profile")}
    )
    @JsonIgnore
    private Set<Profile> weLike = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "profile_likes",
            joinColumns = {@JoinColumn(name = "to_profile")},
            inverseJoinColumns = {@JoinColumn(name = "from_profile")}
    )
    @JsonIgnore
    private Set<Profile> whoLikedMe = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return getId().equals(profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

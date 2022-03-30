package ru.liga.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"whomILiked", "whoLikedUs"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private SexType sexType;
    @ElementCollection(targetClass = SexType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "sex_type", joinColumns = @JoinColumn(name = "application_id"))
    @Enumerated(EnumType.STRING)
    private Set<SexType> lookingFor;

    @ManyToMany()
    @JoinTable(name = "applications_likes",
            joinColumns = {@JoinColumn(name = "whom_i_liked")},
            inverseJoinColumns = {@JoinColumn(name = "who_liked_me")}
    )
    private Set<Application> whomILiked = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "applications_likes",
            joinColumns = {@JoinColumn(name = "who_liked_me")},
            inverseJoinColumns = {@JoinColumn(name = "whom_i_liked")}
    )
    private Set<Application> whoLikedMe = new HashSet<>();

}

package ru.liga.domain;

import javax.persistence.*;

@Entity
@Table(name = "application_likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Application from;
    @ManyToOne
    private Application to;
}

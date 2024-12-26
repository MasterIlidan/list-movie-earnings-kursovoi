package ru.students.listmovieearningskursovoi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies_actors")
public class MovieActors {
    @Column(name = "movie")
    private Long movieId;
    @Id
    @Column(name = "actor")
    private String actor;
}

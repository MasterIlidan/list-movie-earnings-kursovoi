package ru.students.listmovieearningskursovoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.students.listmovieearningskursovoi.entity.MovieActors;

@Repository
public interface ActorRepository extends JpaRepository<MovieActors, String> {

}

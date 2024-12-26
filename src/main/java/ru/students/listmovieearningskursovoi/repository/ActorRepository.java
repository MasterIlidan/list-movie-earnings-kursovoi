package ru.students.listmovieearningskursovoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.students.listmovieearningskursovoi.entity.MovieActors;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<MovieActors, String> {

    Optional<MovieActors> findByMovieId(Long movieId);
    List<MovieActors> findAllByMovieId(Long movieId);
    void deleteAllByMovieId(Long movieId);
}

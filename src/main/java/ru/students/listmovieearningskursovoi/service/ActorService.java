package ru.students.listmovieearningskursovoi.service;

import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.entity.MovieActors;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    void save(MovieActors movie);

    List<MovieActors> getMoviesByActor(String actor);

    List<MovieActors> getAllMovies();;

    void deleteAllMovieActorsByMovieId(Long movieId);

    void deleteAllMovieActorsByActor(String actor);
    Optional<MovieActors> getActorByMovieId(Long movieId);
}

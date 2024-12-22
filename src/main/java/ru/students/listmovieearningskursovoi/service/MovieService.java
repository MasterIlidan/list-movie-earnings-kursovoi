package ru.students.listmovieearningskursovoi.service;

import ru.students.listmovieearningskursovoi.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    void save(Movie movie);

    List<Movie> getMoviesByEmail(String email);

    List<Movie> getAllMovies();;

    void deleteMovie(Long id);

    void addMovieActors(String actors);
    Optional<Movie> getMovieById(Long movieId);
}

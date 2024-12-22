package ru.students.listmovieearningskursovoi.service;

import org.springframework.stereotype.Service;
import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void save(Movie movie) {
        List<String> actors = List.of(movie.getActors().split(","));

        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getMoviesByEmail(String email) {
        return movieRepository.findByAddBy(email);
    }


    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void addMovieActors(String actors) {

    }

    @Override
    public Optional<Movie> getMovieById(Long movieId) {
        return movieRepository.findById(movieId);
    }
}

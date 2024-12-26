package ru.students.listmovieearningskursovoi.service;

import org.springframework.stereotype.Service;
import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.entity.MovieActors;
import ru.students.listmovieearningskursovoi.repository.ActorRepository;
import ru.students.listmovieearningskursovoi.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void save(Movie movie) {

        movieRepository.save(movie);

        for (String actor : movie.getActors().split(",")) {
            actor = actor.trim();
            String finalActor = actor;
            List<MovieActors> allById = actorRepository.findAllByMovieId(movie.getId());
            boolean isFound = allById.stream().anyMatch(x ->
                    x.getActor().equals(finalActor));

            if (!isFound) {
                MovieActors movieActor = new MovieActors( movie.getId(), actor);

                actorRepository.save(movieActor);
            }

        }
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

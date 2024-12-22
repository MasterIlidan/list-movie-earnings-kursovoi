package ru.students.listmovieearningskursovoi.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.entity.MovieActors;
import ru.students.listmovieearningskursovoi.repository.ActorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public void save(MovieActors movie) {

    }

    @Override
    public List<MovieActors> getMoviesByActor(String actor) {
        return List.of();
    }

    @Override
    public List<MovieActors> getAllMovies() {
        return List.of();
    }

    @Transactional
    @Override
    public void deleteAllMovieActorsByMovieId(Long movieId) {
        actorRepository.deleteAllByMovieId(movieId);
    }

    @Override
    public void deleteAllMovieActorsByActor(String actor) {

    }

    @Override
    public Optional<MovieActors> getActorByMovieId(Long movieId) {
        return Optional.empty();
    }
}

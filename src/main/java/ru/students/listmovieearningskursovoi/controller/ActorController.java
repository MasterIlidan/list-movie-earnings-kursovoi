package ru.students.listmovieearningskursovoi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.entity.MovieActors;
import ru.students.listmovieearningskursovoi.repository.ActorRepository;
import ru.students.listmovieearningskursovoi.service.MovieServiceImpl;

import java.util.*;

@Slf4j
@Controller
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieServiceImpl movieServiceImpl;


    @GetMapping({"/actorlist"})
    public ModelAndView getAllActors() {
        Collection<SimpleGrantedAuthority> authorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getAuthorities();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth : {}", auth);

        ModelAndView mav = new ModelAndView("list-actors");
        List<MovieActors> movieActorsList = actorRepository.findAll();
        Map<String,List<String>> movieActorsMap = new HashMap<>();
        for (MovieActors movieActors : movieActorsList) {
            String[] movies = movieActors.getMovieId().split(",");
            List<String> moviesList = new ArrayList<>();
            for (String movieId : movies) {
                Optional<Movie> optionalMovie = movieServiceImpl
                        .getMovieById(Long.parseLong(movieId));
                if (optionalMovie.isPresent()) {
                    Movie movie = optionalMovie.get();
                    moviesList.add(movie.getMovieName());
                } else {
                    moviesList.add("id.%s.deleted".formatted(movieId));
                }
            }
            movieActorsMap.put(movieActors.getActor(), moviesList);

        }

        mav.addObject("actors", movieActorsMap);
        log.info("/list actors -> connection");
        return mav;
    }



}
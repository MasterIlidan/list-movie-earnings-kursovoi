package ru.students.listmovieearningskursovoi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.students.listmovieearningskursovoi.entity.Currency;
import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.entity.MovieActors;
import ru.students.listmovieearningskursovoi.entity.User;
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

        Map<String, List<String>> movieActorsMap = new HashMap<>();

        for (MovieActors movieActors : movieActorsList) {

            String actor = movieActors.getActor();
            Long movieId = movieActors.getMovieId();
            if (movieId == null) {
                movieActorsMap.put(actor, List.of(""));
                continue;
            }

            Optional<Movie> movie = movieServiceImpl.getMovieById(movieId);
            String movieName;
            if (movie.isPresent()) {
                movieName = movie.get().getMovieName();
            } else {
                movieName = "%d.deleted".formatted(movieId);
            }

            if (movieActorsMap.containsKey(actor)) {
                movieActorsMap.get(actor).add(movieName);
            } else {
                movieActorsMap.put(actor, List.of(movieName));
            }

            //movieActorsMap.put(movieActors.getActor(), moviesList);
        }

        mav.addObject("actors", movieActorsMap);
        log.info("/list actors -> connection");
        return mav;
    }

    @PostMapping("/saveActor")
    public RedirectView saveStudent(@ModelAttribute MovieActors actor) {
        actorRepository.save(actor);
        return new RedirectView("/actorlist");
    }

    @GetMapping("/addActorForm")
    public ModelAndView addStudentForm() {
        ModelAndView mav = new ModelAndView("add-actor-form");
        MovieActors movieActor = new MovieActors( null, "");
        mav.addObject("actor", movieActor);
        return mav;
    }

    @GetMapping("/deleteActor")
    public String deleteActor(@RequestParam Long actor) {
        actorRepository.deleteById(String.valueOf(actor));
        return "redirect:/actorlist";
    }

}
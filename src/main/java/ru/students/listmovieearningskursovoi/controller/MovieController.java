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
import ru.students.listmovieearningskursovoi.entity.User;
import ru.students.listmovieearningskursovoi.repository.ActorRepository;
import ru.students.listmovieearningskursovoi.repository.CurrencyRepository;
import ru.students.listmovieearningskursovoi.repository.MovieRepository;
import ru.students.listmovieearningskursovoi.service.MovieServiceImpl;
import ru.students.listmovieearningskursovoi.service.UserServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class MovieController {


    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @GetMapping({"/list"})
    public ModelAndView getAllStudents() {
        Collection<SimpleGrantedAuthority> authorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getAuthorities();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth : {}", auth);


        //вывод всех записей если пользователь - администратор
        for (SimpleGrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ADMIN")) {
                ModelAndView mav = new ModelAndView("list-movies-admin");
                mav.addObject("movies", movieServiceImpl.getAllMovies());
                return mav;
            }
        }
        org.springframework.security.core.userdetails.User currentUser =
                (org.springframework.security.core.userdetails.User)
                        auth.getPrincipal();


        String email = userServiceImpl.findUserByEmail(currentUser.getUsername()).getEmail();
        ModelAndView mav = new ModelAndView("list-movies");
        mav.addObject("movies", movieServiceImpl.getMoviesByEmail(email));
        log.info("/list -> connection");

        return mav;
    }

    @GetMapping("/addMovieForm")
    public ModelAndView addStudentForm() {
        ModelAndView mav = new ModelAndView("add-movie-form");
        Movie movie = new Movie();
        mav.addObject("movie", movie);

        List<Currency> currencies = currencyRepository.findAll();
        mav.addObject("currencies", currencies);
        return mav;
    }

    @PostMapping("/saveMovie")
    public RedirectView saveStudent(@ModelAttribute Movie movie) {
        org.springframework.security.core.userdetails.User currentUser =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        User currentUserr =  userServiceImpl.findUserByEmail(currentUser.getUsername());
        movie.setAddBy(currentUserr.getEmail());
        movieServiceImpl.save(movie);
        return new RedirectView("/list");
    }

    @GetMapping("/showUpdateMovieForm")
    public ModelAndView showUpdateForm(@RequestParam Long movieId) {
        ModelAndView mav = new ModelAndView("add-movie-form");
        Optional<Movie> optionalMovie = movieServiceImpl.getMovieById(movieId);
        Movie movie = new Movie();
        if (optionalMovie.isPresent()) {
            movie = optionalMovie.get();
        }
        mav.addObject("movie", movie);
//TODO: не тянет из бд
        List<Currency> currencies = currencyRepository.findAll();
        mav.addObject("currencies", currencies);
        return mav;
    }

    @GetMapping("/deleteMovie")
    public String deleteStudent(@RequestParam Long movieId) {
        movieServiceImpl.deleteMovie(movieId);
        return "redirect:/list";
    }
}

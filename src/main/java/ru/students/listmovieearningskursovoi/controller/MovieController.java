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
import ru.students.listmovieearningskursovoi.entity.Movie;
import ru.students.listmovieearningskursovoi.entity.User;
import ru.students.listmovieearningskursovoi.repository.MovieRepository;
import ru.students.listmovieearningskursovoi.service.UserServiceImpl;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

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
                mav.addObject("movies", movieRepository.findAll());
                return mav;
            }
        }
        org.springframework.security.core.userdetails.User currentUser =
                (org.springframework.security.core.userdetails.User)
                        auth.getPrincipal();


        String email = userServiceImpl.findUserByEmail(currentUser.getUsername()).getEmail();
        ModelAndView mav = new ModelAndView("list-movies");
        mav.addObject("movies", movieRepository.findByAddBy(email));
        log.info("/list -> connection");

        return mav;
    }

    @GetMapping("/addMovieForm")
    public ModelAndView addStudentForm() {
        ModelAndView mav = new ModelAndView("add-movie-form");
        Movie movie = new Movie();
        mav.addObject("movie", movie);
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
        movieRepository.save(movie);
        return new RedirectView("/list");
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long movieId) {
        ModelAndView mav = new ModelAndView("add-movie-form");
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        Movie movie = new Movie();
        if (optionalMovie.isPresent()) {
            movie = optionalMovie.get();
        }
        mav.addObject("movie", movie);
        return mav;
    }

    @GetMapping("/deleteMovie")
    public String deleteStudent(@RequestParam Long movieId) {
        movieRepository.deleteById(movieId);
        return "redirect:/list";
    }
    @GetMapping("/about")
    public String about(){
        return "redirect:/about";
    }
}

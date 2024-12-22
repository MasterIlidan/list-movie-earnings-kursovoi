package ru.students.listmovieearningskursovoi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.students.listmovieearningskursovoi.repository.ActorRepository;

import java.util.Collection;

@Slf4j
@Controller
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;


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
        mav.addObject("actors", actorRepository.findAll());
        log.info("/list actors -> connection");

        return mav;
    }



}
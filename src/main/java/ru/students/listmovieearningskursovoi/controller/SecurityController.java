package ru.students.listmovieearningskursovoi.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.students.listmovieearningskursovoi.dto.UserDto;
import ru.students.listmovieearningskursovoi.entity.Role;
import ru.students.listmovieearningskursovoi.entity.User;
import ru.students.listmovieearningskursovoi.repository.RoleRepository;
import ru.students.listmovieearningskursovoi.service.UserService;
import ru.students.listmovieearningskursovoi.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SecurityController {

    private final RoleRepository roleRepository;
    private final UserServiceImpl userServiceImpl;
    private UserService userService;

    public SecurityController(UserService userService, RoleRepository roleRepository, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userServiceImpl = userServiceImpl;
    }
    @GetMapping("/index")
    public String home() {return "index";}

    @GetMapping("/")
    public RedirectView homePage() {
        return new RedirectView("/index");
    }

    @GetMapping("login")
    public String login() {return "login";}

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "User with this email already exist");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "users";
    }
    @GetMapping("/roles")
    public List<Role> roles() {
        return roleRepository.findAll();
    }
    @PostMapping("/userUpdate{email}")
    public void changeUserRole (@PathVariable String email, @RequestParam List<String> requestRoles) {
        User user = userService.findUserByEmail(email);
        List<Role> roles = new ArrayList<>();
        for (String stringRole: requestRoles) {
            Role role = new Role(stringRole);
            roles.add(role);
        }
        user.setRoles(roles);
        userService.saveUser(user);
    }
    @GetMapping("/userUpdate{email}")
    public ModelAndView updateUser (@PathVariable String email) {
        ModelAndView mav = new ModelAndView("user-edit-form");
        Optional<User> optionalUser = Optional.ofNullable(userService.findUserByEmail(email));
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        mav.addObject("user", user);
        return mav;
    }
}

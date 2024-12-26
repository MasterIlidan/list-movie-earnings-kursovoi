package ru.students.listmovieearningskursovoi.service;

import ru.students.listmovieearningskursovoi.dto.UserDto;
import ru.students.listmovieearningskursovoi.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(UserDto userDto);
    void saveUser(User user);
    User findUserByEmail(String email);
Optional<User> findUserById(Long id);
    List<UserDto> findAllUsers();
}

package ru.students.listmovieearningskursovoi.service;

import ru.students.listmovieearningskursovoi.dto.UserDto;
import ru.students.listmovieearningskursovoi.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);
    void saveUser(User user);
    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}

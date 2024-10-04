package ru.students.listmovieearningskursovoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.students.listmovieearningskursovoi.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}

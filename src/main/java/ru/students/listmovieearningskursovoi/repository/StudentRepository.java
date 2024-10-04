package ru.students.listmovieearningskursovoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.students.listmovieearningskursovoi.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

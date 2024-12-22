package ru.students.listmovieearningskursovoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.students.listmovieearningskursovoi.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}

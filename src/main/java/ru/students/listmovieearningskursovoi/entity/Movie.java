package ru.students.listmovieearningskursovoi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "movie_Name")
    private String movieName;
    @Column(name = "release_Date")
    private Date date;
    @Column(name = "earnings")
    private Float earnings;
    @Column(name = "currency")
    private String currency;
    @Column(name = "add_by")
    private String addBy;
    @Column(name = "actors")
    private String actors;
}

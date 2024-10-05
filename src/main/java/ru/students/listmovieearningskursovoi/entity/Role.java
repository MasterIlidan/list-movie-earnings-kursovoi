package ru.students.listmovieearningskursovoi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(nullable = false, unique = true, name = "role_Name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();
}

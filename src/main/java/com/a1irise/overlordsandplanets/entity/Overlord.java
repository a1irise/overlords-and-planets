package com.a1irise.overlordsandplanets.entity;

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
@Table(name = "overlord", schema = "public", catalog = "overlords_and_planets")
public class Overlord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "overlord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Planet> planets = new ArrayList<>();

    public Overlord(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

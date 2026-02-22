package com.movie_recommendation.movie_recommendation.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hollywood")
@Data
public class Hollywood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "movie_id")
    private Integer movie_id;

    @Column(name = "title")
    private String title;

    @Column(name = "release_year")
    private Integer release_year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "director")
    private String director;

    @Column(name = "movie_poster")
    private String movie_poster;

    @Column(name = "trailer_link")
    private String trailer_link;

    @Column(name = "cast")
    private String cast;

}
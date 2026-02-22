package com.movie_recommendation.movie_recommendation.controller;

import com.movie_recommendation.movie_recommendation.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    // GET ALL MOVIES BY INDUSTRY
    @GetMapping("/industry")
    public List<?> getByIndustry(@RequestParam String industry) {
        return service.getMoviesByIndustry(industry);
    }

    // FILTER MOVIES
    @GetMapping("/filter/{industry}")
    public List<?> filterMovies(
            @PathVariable String industry,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double ratingMin,
            @RequestParam(required = false) Double ratingMax
    ) {
        return service.getFilteredMovies(
                industry,
                year,
                genre,
                ratingMin,
                ratingMax
        );
    }
}
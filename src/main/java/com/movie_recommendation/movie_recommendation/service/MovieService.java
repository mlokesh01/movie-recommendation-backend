package com.movie_recommendation.movie_recommendation.service;

import com.movie_recommendation.movie_recommendation.entity.Industry;
import com.movie_recommendation.movie_recommendation.repository.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final TollyRepository tolly;
    private final KollyRepository kolly;
    private final MollyRepository molly;
    private final SandalRepository sandal;
    private final BollyRepository bolly;
    private final HollyRepository holly;

    public MovieService(
            TollyRepository tolly,
            KollyRepository kolly,
            MollyRepository molly,
            SandalRepository sandal,
            BollyRepository bolly,
            HollyRepository holly
    ) {
        this.tolly = tolly;
        this.kolly = kolly;
        this.molly = molly;
        this.sandal = sandal;
        this.bolly = bolly;
        this.holly = holly;
    }

    // ---------------- GET ALL BY INDUSTRY ----------------

    public List<?> getMoviesByIndustry(String industry) {
        Industry ind = parseIndustry(industry);

        switch (ind) {
            case TOLLYWOOD:
                return tolly.findAll();
            case KOLLYWOOD:
                return kolly.findAll();
            case MOLLYWOOD:
                return molly.findAll();
            case SANDALWOOD:
                return sandal.findAll();
            case BOLLYWOOD:
                return bolly.findAll();
            case HOLLYWOOD:
                return holly.findAll();
            default:
                return List.of();
        }
    }

    // ---------------- FILTER ----------------

    public List<?> getFilteredMovies(
            String industry,
            Integer year,
            String genre,
            Double ratingMin,
            Double ratingMax
    ) {

        Industry ind = parseIndustry(industry);
        List<String> genres = normalizeGenres(genre);

        switch (ind) {
            case TOLLYWOOD:
                return filterTolly(year, ratingMin, ratingMax, genres);
            case KOLLYWOOD:
                return filterKolly(year, ratingMin, ratingMax, genres);
            case MOLLYWOOD:
                return filterMolly(year, ratingMin, ratingMax, genres);
            case SANDALWOOD:
                return filterSandal(year, ratingMin, ratingMax, genres);
            case BOLLYWOOD:
                return filterBolly(year, ratingMin, ratingMax, genres);
            case HOLLYWOOD:
                return filterHolly(year, ratingMin, ratingMax, genres);
            default:
                return List.of();
        }
    }

    // ---------------- Per Industry ----------------

    private List<?> filterTolly(Integer year, Double ratingMin, Double ratingMax, List<String> genres) {
        return filterMultipleGenres(genres, g ->
                tolly.findMoviesByFilters(year, ratingMin, ratingMax, g)
        );
    }

    private List<?> filterKolly(Integer year, Double ratingMin, Double ratingMax, List<String> genres) {
        return filterMultipleGenres(genres, g ->
                kolly.findMoviesByFilters(year, ratingMin, ratingMax, g)
        );
    }

    private List<?> filterMolly(Integer year, Double ratingMin, Double ratingMax, List<String> genres) {
        return filterMultipleGenres(genres, g ->
                molly.findMoviesByFilters(year, ratingMin, ratingMax, g)
        );
    }

    private List<?> filterSandal(Integer year, Double ratingMin, Double ratingMax, List<String> genres) {
        return filterMultipleGenres(genres, g ->
                sandal.findMoviesByFilters(year, ratingMin, ratingMax, g)
        );
    }

    private List<?> filterBolly(Integer year, Double ratingMin, Double ratingMax, List<String> genres) {
        return filterMultipleGenres(genres, g ->
                bolly.findMoviesByFilters(year, ratingMin, ratingMax, g)
        );
    }

    private List<?> filterHolly(Integer year, Double ratingMin, Double ratingMax, List<String> genres) {
        return filterMultipleGenres(genres, g ->
                holly.findMoviesByFilters(year, ratingMin, ratingMax, g)
        );
    }

    // ---------------- Core Genre Logic ----------------

    private List<?> filterMultipleGenres(
            List<String> genres,
            java.util.function.Function<String, List<?>> query
    ) {
        if (genres == null || genres.isEmpty()) {
            return query.apply(null);
        }

        return genres.stream()
                .flatMap(g -> query.apply(g).stream())
                .distinct()
                .toList();
    }

    // ---------------- Helpers ----------------

    private List<String> normalizeGenres(String genre) {
        if (genre == null || genre.trim().isEmpty()) return null;

        return Arrays.stream(genre.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();
    }

    private Industry parseIndustry(String industry) {
        return Industry.valueOf(industry.trim().toUpperCase());
    }
}
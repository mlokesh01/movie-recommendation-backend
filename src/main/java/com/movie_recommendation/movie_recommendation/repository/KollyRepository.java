package com.movie_recommendation.movie_recommendation.repository;

import com.movie_recommendation.movie_recommendation.entity.Kollywood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface KollyRepository extends JpaRepository<Kollywood, Integer> {

    @Query("""
        SELECT m FROM Kollywood m
        WHERE (:year IS NULL OR m.release_year = :year)
        AND (:ratingMin IS NULL OR m.rating >= :ratingMin)
        AND (:ratingMax IS NULL OR m.rating <= :ratingMax)
        AND (
            :genre IS NULL OR
            LOWER(m.genre) LIKE CONCAT('%', :genre, '%')
        )
    """)
    List<Kollywood> findMoviesByFilters(
            @Param("year") Integer year,
            @Param("ratingMin") Double ratingMin,
            @Param("ratingMax") Double ratingMax,
            @Param("genre") String genre
    );
}
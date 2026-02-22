package com.movie_recommendation.movie_recommendation.repository;

import com.movie_recommendation.movie_recommendation.entity.Sandalwood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface SandalRepository extends JpaRepository<Sandalwood, Integer> {

    @Query("""
        SELECT m FROM Sandalwood m
        WHERE (:year IS NULL OR m.release_year = :year)
        AND (:ratingMin IS NULL OR m.rating >= :ratingMin)
        AND (:ratingMax IS NULL OR m.rating <= :ratingMax)
        AND (
            :genre IS NULL OR
            LOWER(m.genre) LIKE CONCAT('%', :genre, '%')
        )
    """)
    List<Sandalwood> findMoviesByFilters(
            @Param("year") Integer year,
            @Param("ratingMin") Double ratingMin,
            @Param("ratingMax") Double ratingMax,
            @Param("genre") String genre
    );
}
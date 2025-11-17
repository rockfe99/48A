package com.example.movieapp.repository;

import com.example.movieapp.entity.MovieInfo;
import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.entity.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Integer> {

    List<MovieRating> findByMovieOrderByCreatedAtDesc(MovieInfo movie);

    List<MovieRating> findByMemberOrderByCreatedAtDesc(MovieMember member);

    Optional<MovieRating> findByMovieAndMember(MovieInfo movie, MovieMember member);

    @Query("select coalesce(avg(r.score), 0) from MovieRating r where r.movie = :movie")
    Double averageScore(MovieInfo movie);
}

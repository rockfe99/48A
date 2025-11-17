package com.example.movieapp.repository;

import com.example.movieapp.entity.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieInfoRepository extends JpaRepository<MovieInfo, Integer> {
}

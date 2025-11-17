package com.example.movieapp.repository;

import com.example.movieapp.entity.MovieMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieMemberRepository extends JpaRepository<MovieMember, String> {
}

package com.example.movieapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_rating",
       uniqueConstraints = @UniqueConstraint(name = "uk_movie_rating", columnNames = {"movie_num", "id"}))
public class MovieRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_num")
    private Integer ratingNum;

    @ManyToOne
    @JoinColumn(name = "movie_num", nullable = false)
    private MovieInfo movie;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private MovieMember member;

    private int score;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public MovieRating() {}

    public Integer getRatingNum() { return ratingNum; }
    public void setRatingNum(Integer ratingNum) { this.ratingNum = ratingNum; }

    public MovieInfo getMovie() { return movie; }
    public void setMovie(MovieInfo movie) { this.movie = movie; }

    public MovieMember getMember() { return member; }
    public void setMember(MovieMember member) { this.member = member; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

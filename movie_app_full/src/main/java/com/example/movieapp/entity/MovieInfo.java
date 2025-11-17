package com.example.movieapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_info")
public class MovieInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_num")
    private Integer movieNum;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private MovieMember writer;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 100)
    private String director;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public MovieInfo() {}

    public Integer getMovieNum() { return movieNum; }
    public void setMovieNum(Integer movieNum) { this.movieNum = movieNum; }

    public MovieMember getWriter() { return writer; }
    public void setWriter(MovieMember writer) { this.writer = writer; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

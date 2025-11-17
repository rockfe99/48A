package com.example.movieapp.service;

import com.example.movieapp.entity.MovieInfo;
import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.entity.MovieRating;
import com.example.movieapp.repository.MovieRatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingService {

    private final MovieRatingRepository ratingRepository;

    public RatingService(MovieRatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<MovieRating> findByMovie(MovieInfo movie) {
        return ratingRepository.findByMovieOrderByCreatedAtDesc(movie);
    }

    public List<MovieRating> findByMember(MovieMember member) {
        return ratingRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    public Double average(MovieInfo movie) {
        Double avg = ratingRepository.averageScore(movie);
        if (avg == null) return 0.0;
        return Math.round(avg * 10.0) / 10.0;
    }

    @Transactional
    public String writeRating(MovieInfo movie, MovieMember member, int score) {
        if (score < 1 || score > 5) {
            return "점수는 1~5 사이여야 합니다.";
        }
        if (ratingRepository.findByMovieAndMember(movie, member).isPresent()) {
            return "이미 이 영화에 대해 별점을 등록했습니다.";
        }
        MovieRating rating = new MovieRating();
        rating.setMovie(movie);
        rating.setMember(member);
        rating.setScore(score);
        ratingRepository.save(rating);
        return null;
    }

    public void deleteRating(Integer ratingNum, MovieMember current) {
        ratingRepository.findById(ratingNum).ifPresent(r -> {
            if (r.getMember().getId().equals(current.getId())) {
                ratingRepository.delete(r);
            }
        });
    }
}

package com.example.movieapp.service;

import com.example.movieapp.entity.MovieInfo;
import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.repository.MovieInfoRepository;
import com.example.movieapp.repository.MovieRatingRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieInfoRepository movieInfoRepository;
    private final MovieRatingRepository movieRatingRepository;

    public MovieService(MovieInfoRepository movieInfoRepository, MovieRatingRepository movieRatingRepository) {
        this.movieInfoRepository = movieInfoRepository;
        this.movieRatingRepository = movieRatingRepository;
    }

    public List<MovieInfo> listAllMovies() {
        return movieInfoRepository.findAll(Sort.by(Sort.Direction.DESC, "movieNum"));
    }

    public Map<Integer, Double> averageScores(List<MovieInfo> movies) {
        return movies.stream()
                .collect(Collectors.toMap(
                        MovieInfo::getMovieNum,
                        m -> {
                            Double avg = movieRatingRepository.averageScore(m);
                            if (avg == null) return 0.0;
                            return Math.round(avg * 10.0) / 10.0;
                        }
                ));
    }

    public MovieInfo writeMovie(MovieMember writer, MovieInfo movie) {
        movie.setWriter(writer);
        return movieInfoRepository.save(movie);
    }

    public MovieInfo getMovie(Integer movieNum) {
        return movieInfoRepository.findById(movieNum).orElse(null);
    }
}

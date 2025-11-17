package com.example.movieapp.controller;

import com.example.movieapp.entity.MovieInfo;
import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.entity.MovieRating;
import com.example.movieapp.repository.MovieMemberRepository;
import com.example.movieapp.service.MovieService;
import com.example.movieapp.service.RatingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final RatingService ratingService;
    private final MovieMemberRepository memberRepository;

    public MovieController(MovieService movieService, RatingService ratingService, MovieMemberRepository memberRepository) {
        this.movieService = movieService;
        this.ratingService = ratingService;
        this.memberRepository = memberRepository;
    }

    private MovieMember currentMember(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return null;
        Object principal = authentication.getPrincipal();
        String id;
        if (principal instanceof UserDetails userDetails) {
            id = userDetails.getUsername();
        } else {
            id = principal.toString();
        }
        return memberRepository.findById(id).orElse(null);
    }

    @GetMapping
    public String list(Model model) {
        var movies = movieService.listAllMovies();
        Map<Integer, Double> avgMap = movieService.averageScores(movies);
        model.addAttribute("movies", movies);
        model.addAttribute("avgMap", avgMap);
        return "movie/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("movie", new MovieInfo());
        return "movie/form";
    }

    @PostMapping
    public String create(@ModelAttribute("movie") MovieInfo movie,
                         Authentication authentication,
                         Model model) {
        MovieMember member = currentMember(authentication);
        if (member == null) {
            model.addAttribute("msg", "로그인 후 이용해 주세요.");
            model.addAttribute("url", "/login");
            return "message";
        }
        movieService.writeMovie(member, movie);
        return "redirect:/movies";
    }

    @GetMapping("/{movieNum}")
    public String detail(@PathVariable Integer movieNum,
                         Authentication authentication,
                         Model model) {
        MovieInfo movie = movieService.getMovie(movieNum);
        if (movie == null) {
            model.addAttribute("msg", "영화 정보를 찾을 수 없습니다.");
            model.addAttribute("url", "/movies");
            return "message";
        }
        Double avg = ratingService.average(movie);
        List<MovieRating> ratingList = ratingService.findByMovie(movie);
        MovieMember me = currentMember(authentication);
        MovieRating myRating = null;
        if (me != null) {
            for (MovieRating r : ratingList) {
                if (r.getMember().getId().equals(me.getId())) {
                    myRating = r;
                    break;
                }
            }
        }
        model.addAttribute("movie", movie);
        model.addAttribute("avg", avg);
        model.addAttribute("ratings", ratingList);
        model.addAttribute("myRating", myRating);
        return "movie/detail";
    }
}

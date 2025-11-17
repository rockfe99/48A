package com.example.movieapp.controller;

import com.example.movieapp.entity.MovieInfo;
import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.service.MovieService;
import com.example.movieapp.service.RatingService;
import com.example.movieapp.repository.MovieMemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RatingController {

    private final RatingService ratingService;
    private final MovieService movieService;
    private final MovieMemberRepository memberRepository;

    public RatingController(RatingService ratingService, MovieService movieService, MovieMemberRepository memberRepository) {
        this.ratingService = ratingService;
        this.movieService = movieService;
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

    @PostMapping("/ratings")
    @ResponseBody
    public Map<String, Object> rate(@RequestParam("movieNum") Integer movieNum,
                                    @RequestParam("score") int score,
                                    Authentication authentication) {
        Map<String, Object> result = new HashMap<>();
        MovieInfo movie = movieService.getMovie(movieNum);
        if (movie == null) {
            result.put("success", false);
            result.put("message", "영화를 찾을 수 없습니다.");
            return result;
        }
        MovieMember member = currentMember(authentication);
        if (member == null) {
            result.put("success", false);
            result.put("message", "로그인 후 이용해 주세요.");
            return result;
        }

        String error = ratingService.writeRating(movie, member, score);
        if (error != null) {
            result.put("success", false);
            result.put("message", error);
            return result;
        }
        result.put("success", true);
        result.put("avg", ratingService.average(movie));
        return result;
    }
}

package com.example.movieapp.controller;

import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.entity.MovieRating;
import com.example.movieapp.repository.MovieMemberRepository;
import com.example.movieapp.service.RatingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final RatingService ratingService;
    private final MovieMemberRepository memberRepository;

    public MyPageController(RatingService ratingService, MovieMemberRepository memberRepository) {
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
    public String myRatings(Authentication authentication, Model model) {
        MovieMember member = currentMember(authentication);
        if (member == null) {
            model.addAttribute("msg", "로그인 후 이용해 주세요.");
            model.addAttribute("url", "/login");
            return "message";
        }
        List<MovieRating> list = ratingService.findByMember(member);
        model.addAttribute("list", list);
        return "mypage/list";
    }

    @PostMapping("/{ratingNum}/delete")
    public String delete(@PathVariable Integer ratingNum,
                         Authentication authentication) {
        MovieMember member = currentMember(authentication);
        if (member != null) {
            ratingService.deleteRating(ratingNum, member);
        }
        return "redirect:/mypage";
    }
}

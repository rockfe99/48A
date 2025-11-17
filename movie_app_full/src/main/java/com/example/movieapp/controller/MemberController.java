package com.example.movieapp.controller;

import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new MovieMember());
        return "member/join";
    }

    @PostMapping("/member/join")
    public String join(@ModelAttribute("member") MovieMember member,
                       @RequestParam("rawPassword") String rawPassword,
                       Model model) {
        String errorMessage = memberService.join(member, rawPassword);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "member/join";
        }
        model.addAttribute("msg", "회원가입이 완료되었습니다. 로그인 해 주세요.");
        model.addAttribute("url", "/login");
        return "message";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

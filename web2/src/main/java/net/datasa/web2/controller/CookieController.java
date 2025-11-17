package net.datasa.web2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("ck")
@Controller
public class CookieController {
    //쿠키 저장
    @GetMapping("cookie1")
    public String cookie1(HttpServletResponse response) {
        //쿠키생성
        Cookie c1 = new Cookie("str", "abcd");
        Cookie c2 = new Cookie("num", "1");
        //쿠키의 유지 시간
        c1.setMaxAge(60*60*24*3);
        c2.setMaxAge(60*60*24*3);
        //쿠키 경로
        c1.setPath("/");
        c2.setPath("/");

        response.addCookie(c1);
        response.addCookie(c2);
        return "redirect:/";
    }

    //쿠키 삭제
    @GetMapping("cookie2")
    public String cookie2() {
        //같은 이름의 쿠키 생성 -> 유지시간 0초 -> 경로도 같게 -> 저장
        return "redirect:/";
    }

    @GetMapping("cookie3")
    public String cookie3(
            @CookieValue(name="str", defaultValue = "") String s,
            @CookieValue(name="num", defaultValue = "1") int n
    ) {
        log.debug("str:{}, num:{}", s, n);
        return "redirect:/";
    }

    //JavaScript의 localStorage, sessionStorage
    @GetMapping("local")
    public String local() {
        return "local";
    }

}

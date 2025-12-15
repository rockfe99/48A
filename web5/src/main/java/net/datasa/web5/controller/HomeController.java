package net.datasa.web5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 화면
 */

@Controller
public class HomeController {

    /**
     * 메인화면으로 이동
     */
    @GetMapping({"", "/"})
    public String home() {
        return "home";
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

    /**
     * 시큐리티 관련 타임리프 테스트
     */
    @GetMapping("thymeleaf")
    public String thymeleaf() {
        return "thymeleaf";
    }
}

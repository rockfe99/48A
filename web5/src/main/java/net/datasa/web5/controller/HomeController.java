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

}

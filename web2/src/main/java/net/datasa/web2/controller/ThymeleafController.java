package net.datasa.web2.controller;

import lombok.extern.slf4j.Slf4j;
import net.datasa.web2.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping("th")
public class ThymeleafController {

    @GetMapping("thymeleaf1")
    public String thymeleaf1(Model model) {
        String str = "문자열";
        int num = 100;
        Person p = new Person("aaa", "111", "김철수", "서울시");
        String tag = "<marquee>html태그</marquee>";
        String url = "https://google.com";

        model.addAttribute("str", str);
        model.addAttribute("num", num);
        model.addAttribute("person", p);
        model.addAttribute("tag", tag);
        model.addAttribute("url", url);

        int n1 = 1000000;
        double n2 = 123.4567;
        double n3 = 0.05;
        LocalDate localDate = LocalDate.now();
        LocalDateTime localTime = LocalDateTime.now();

        model.addAttribute("n1", n1);
        model.addAttribute("n2", n2);
        model.addAttribute("n3", n3);
        model.addAttribute("localDate", localDate);
        model.addAttribute("localTime", localTime);
        return "thView/thymeleaf1";
    }


}

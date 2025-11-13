package net.datasa.web2;

import lombok.extern.slf4j.Slf4j;
import net.datasa.web2.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
    /**
     * 메인 화면으로 이동
     *
     * @return
     */
    @GetMapping({"", "/"})
    public String home() {
        return "home";      //home.html로 포워딩
    }

    @GetMapping("lombok")
    public String lombokTest() {
        System.out.println("lombokTest 왔다감");

        Person p = new Person();
        Person p2 = new Person("aaa", "111", "홍길동", "서울시");

        p2.setId("bbbbb");
        System.out.println(p2);

        return "redirect:/";    // http://localhost:9999/로 리다이렉트
    }

    @GetMapping("logger")
    public String loggerTest() {
        // @Slf4j를 붙이면 log객체 생성
        //error>warn>info>debug>trace 순서로 중요도
        //application.properties에서 단계 조절
        log.info("info로 출력");
        log.debug("debug로 출력");
        log.error("error로 출력");
        log.warn("warn으로 출력");

        int age = 10;
        String name = "김철수";
        log.debug("이름은 {} 나이는 {}세이다.", name, age);

        return "redirect:/";
    }

}
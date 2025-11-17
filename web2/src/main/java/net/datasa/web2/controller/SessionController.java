package net.datasa.web2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("ss")
@Controller
public class SessionController {

    //세션에 데이터 저장 (Key:String, Value:모든 객체)
    @GetMapping("session1")
    public String session1(HttpSession session) {
        session.setAttribute("name", "홍길동");
        return "redirect:/";
    }
    //세션의 값 읽기 (Object 타입)
    @GetMapping("session2")
    public String session2(HttpSession session) {
        String n = (String)session.getAttribute("name");
        log.debug("세션의 값:{}", n);
        return "redirect:/";
    }
    //세션의 값 삭제
    @GetMapping("session3")
    public String session3(HttpSession session) {
        session.removeAttribute("name");
        return "redirect:/";
    }

    //로그인 폼으로 이동
    @GetMapping("login")
    public String login() {
        return "sessionView/login";
    }

    //로그인 처리
    @PostMapping("login")
    public String loginProcess(
        @RequestParam("id") String id,
        @RequestParam("pw") String pw,
        HttpSession session
    ) {

        log.debug("{}/{}", id, pw);
        if (id.equals("abc") && pw.equals("123")) {
            session.setAttribute("loginId", id);
            return "redirect:/";
        }
        else {
            return "sessionView/login";
        }

    }

    //로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginId");
        return "redirect:/";
    }

    //로그인 확인
    @GetMapping("test")
    public String test(HttpSession session) {
        //세션에 "loginId"값이 있으면 test.html로이동
        //없으면 로그인 경로로 이동
        String id = (String) session.getAttribute("loginId");
        if (id != null) {
            return "sessionView/test";
        }
        else {
            return "redirect:/ss/login";
        }

    }
}

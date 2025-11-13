package net.datasa.web1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //메인화면으로 이동
    @GetMapping("")
    public String main() {
        return "home";  //resources/templates/home.html로 이동
    }

    //두번째 페이지
    @GetMapping("/test1")       //외부에서 접근하는 URL
    public String test1() {     //Java코드 내에서 호출할때
        return "test1";         //html파일의 경로와 이름
    }

    //세번째 페이지
    @GetMapping("/css/test2")
    public String test2() {
        return "test2";
    }
}
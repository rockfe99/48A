package net.datasa.web2.controller;

import lombok.extern.slf4j.Slf4j;
import net.datasa.web2.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/param")
@Slf4j
@Controller
public class ParamController {
    //웹브라우저에서
    // localhost:9999/param/view1로 요청
    @GetMapping("/view1")
    public String view1() {
        //templates아래의 paramView폴더에
        //view1.html로 포워딩
        return "paramView/view1";
    }

    // http://localhost:9999/param/input1
    @PostMapping("/input1")
    public String input1(
         @RequestParam("id") String id,
         @RequestParam("password") String password,
         @RequestParam("name") String name,
         @RequestParam("address") String address
    ) {
        log.debug("아이디:{}, 비번:{}, 이름:{}, 주소:{}", id, password, name, address);
        return "redirect:/";
    }

    //두번째 폼으로 이동
    @GetMapping("/view2")
    public String view2() {
        return "paramView/view2";
    }
    //두번째 폼에서 입력한 내용 전달 받음
    @PostMapping("/input2")
    public String input2(@ModelAttribute Person p) {
        log.debug("{}", p);
        return "redirect:/";
    }

    @GetMapping("/input3")
    public String input3(
            @RequestParam("num") Integer num) {

        log.debug("num:{}", num);
        return "redirect:/";
    }

    @GetMapping("model")
    public String model(Model m) {
        int n = 200;
        String s = "김철수";
        Person p = new Person("a", "b", "c", "d");

        m.addAttribute("number", n);
        m.addAttribute("name", s);
        m.addAttribute("person", p);

        return "paramView/model";
    }

    @GetMapping("view4")
    public String view4() {
        //이름과 주민등록번호를 입력받을 수 있는 Form을 보여준다.
    }

    @PostMapping("view5")
    public String view5() {
        //view4에서 입력한 이름과 주민등록번호를 전달받는다.
        //나이를 계산한다.
        //view5.html로 포워딩해서 이름과 계산한 나이를 출력한다.
    }

}

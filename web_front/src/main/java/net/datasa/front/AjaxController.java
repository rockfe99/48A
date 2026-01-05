package net.datasa.front;

import lombok.extern.slf4j.Slf4j;
import net.datasa.front.dto.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("ajax")
@Controller
public class AjaxController {

    @GetMapping("aj1")
    public String aj1() {
        return "aj1";
    }
    @GetMapping("aj2")
    public String aj2() {
        return "aj2";
    }

    @ResponseBody
    @GetMapping("test1")
    public void test1() {
        log.debug("test1 실행");
    }

    @ResponseBody
    @GetMapping("test2")
    public void test2(@RequestParam("str") String s) {
        log.debug(s);
    }

    @ResponseBody
    @GetMapping("test3")
    public String test3() {
        String s = "서버에서 보낸 문자열";
        return s;
    }

    @ResponseBody
    @PostMapping("insert1")
    public void insert1(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("phone") String phone) {

        log.debug("insert1() : name={} / age={} / phone={}", name, age, phone);
    }

    @ResponseBody
    @PostMapping ("insert2")
    public void insert2(Person person) {
        log.debug("insert2() : {}", person);
    }

    @ResponseBody
    @PostMapping ("insert3")
    public void insert3(Person person) {
        log.debug("insert3() : {}", person);
    }

    @ResponseBody
    @GetMapping ("getObject")
    public Person getObject() {
        Person p = new Person("홍길동", 22, "010-1111-1111");
        return p;
    }

}

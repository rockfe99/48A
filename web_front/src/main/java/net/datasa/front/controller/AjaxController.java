package net.datasa.front.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.front.dto.Member;
import net.datasa.front.dto.Person;
import net.datasa.front.service.AjaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("ajax")
@Controller
public class AjaxController {

    private final AjaxService ajaxService;


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


    @ResponseBody
    @GetMapping("getList")
    public ArrayList<Person> getList() {
        ArrayList<Person> list = new ArrayList<>();

        list.add(new Person("홍길동", 30, "010-1111-1111"));
        list.add(new Person("김철수", 40, "010-2222-2222"));
        list.add(new Person("이영희", 50, "010-3333-4444"));

        return list;
    }

    /**
     * 배열 전달
     */
    @ResponseBody
    @PostMapping("sendArray")
    public void sendArray(String[] ar) {
        if (ar == null) {
            log.debug("ar : null");
        }
        else {
            for (String s : ar) {
                log.debug("배열요소 : {}", s);
            }
        }
    }

    /**
     * 객체 배열 전달
     */
    @ResponseBody
    @PostMapping("sendObjectArray")
    public void sendArray(String ar) throws Exception {
        if (ar == null) {
            log.debug("ar : null");
            return;
        }

        log.debug("전달받은 문자열 : {}", ar);
        // "[{name:'aaa',age:11,phone:'1111'},{name:'bbb',age:22,phone:'2222'}]"

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Person> list =
                objectMapper.readValue(ar, new TypeReference<ArrayList<Person>>() {});
        log.debug("변환결과 리스트 : {}", list);

        for (Object ob : list) {
            log.debug("요소타입 : {}", ob.getClass());
            log.debug("요소값 : {}", ob);
        }

    }


    @GetMapping("recommend")
    public String recommend() {
        return "recommend";
    }

    /**
     * 추천수 증가
     */
    @ResponseBody
    @GetMapping("like")
    public ResponseEntity<?> vote(@RequestParam Integer num) throws Exception {
        try {
            Integer cnt = 0;
            cnt = ajaxService.like(num);
            return ResponseEntity.ok(cnt);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("추천실패");
        }
    }

    /**
     * ID중복확인 페이지로 이동
     */
    @GetMapping("join")
    public String join() {
        return "join";
    }

    /**
     * ID 중복 확인. 해당 ID가 있으며 true
     */
    @ResponseBody
    @GetMapping("idcheck")
    public ResponseEntity<?> idcheck(@RequestParam String userId) {
        boolean result = ajaxService.findMemberId(userId);
        return ResponseEntity.ok(result);
    }

    /**
     * 회원 가입 처리
     * @param member 회원 정보
     * @return 메인화면으로 이동
     */
    @PostMapping("join")
    public String join(Member member) {
        log.debug("join() : {}", member);
        ajaxService.join(member);
        return "redirect:/";
    }

}

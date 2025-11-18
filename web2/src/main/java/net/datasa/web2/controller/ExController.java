package net.datasa.web2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("ex")
@Controller
public class ExController {

    @GetMapping("exam1")
    public String exam1() {
        //이름과 주민등록번호를 입력받을 수 있는 Form을 보여준다.
        return "입력폼이 있는 HTML파일";
    }

    @PostMapping("exam1")
    public String exam1Calc() {
        //입력한 이름과 주민등록번호를 전달받는다.
        //나이를 계산한다.
        //HTML로 포워딩해서 이름과 계산한 나이를 출력한다.
        return "결과 출력할 HTML 파일";
    }

    @GetMapping("exam2")
    public String exam2() {
        //쿠키를 읽는다. 있으면 저장된 값을 읽는다. 없으면 0으로 처리.
        //이전에 저장된 숫자에 더하기 1을 하여 방문횟수를 계산한다.
        //방문횟수를 쿠키로 생성하여 저장하고
        //모델에도 저장한 후 HTML로 이동하여 환영 문구 출력
        return "출력할 HTML 파일";
    }

}

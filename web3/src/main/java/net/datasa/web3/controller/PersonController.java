package net.datasa.web3.controller;

import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDTO;
import net.datasa.web3.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping("test")
    public String test() {
        service.test();
        return "redirect:/";
    }
    @GetMapping("input")
    public String input() {
        return "input";
    }
    @PostMapping("input")
    public String input(@ModelAttribute PersonDTO dto) {
        log.debug("전달받은값:{}", dto);
        /*
        서비스로 PersonDTO객체를 전달한다
        서비스의 메소드에서 PersonEntity를 생성한다
        엔티티에 받은 DTO객체의 값을 옮겨서
        리포지토리의 메소드를 통해 DB에 저장한다.
        */
        return "redirect:/";
    }
}

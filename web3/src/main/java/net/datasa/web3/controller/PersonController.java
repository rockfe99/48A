package net.datasa.web3.controller;

import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDTO;
import net.datasa.web3.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

        service.insertPerson(dto);

        /*
        서비스로 PersonDTO객체를 전달한다
        서비스의 메소드에서 PersonEntity를 생성한다
        엔티티에 받은 DTO객체의 값을 옮겨서
        리포지토리의 메소드를 통해 DB에 저장한다.
        */
        return "redirect:/";
    }

    //delete?id=aaa
    @GetMapping("delete")
    public String del(@RequestParam(name="id") String id) {
        service.deletePerson(id);
        return "redirect:/";
    }

    //select?id=bbb 경로로 요청
    @GetMapping("select")
    public String select(
            @RequestParam(name="id") String id,
            Model model) {
        PersonDTO dto = service.selectPerson(id);
        model.addAttribute("dto", dto);
        return "select";
    }

    /**
     * 모든 회원 목록 보기
     * @return 회원목록 출력 HTML
     */
    @GetMapping("selectAll")
    public String selectAll(Model model) {
        List<PersonDTO> list = service.selectAll();
        model.addAttribute("list", list);
        return "selectAll";
    }

    @GetMapping("update")
    public String update (Model model,
                          @RequestParam("id") String id) {
        //Model과 요청파라미터 id를 전달받는다.
        //서비스에 id를 전달하여 회원정보를 조회한다
        PersonDTO dto = service.selectPerson(id);
        //회원정보를 Model에 저장하고 html로 포워딩
        model.addAttribute("person", dto);
        return "update";
    }


    @PostMapping("update")
    public String update(@ModelAttribute PersonDTO dto) {
        log.debug("전달된 값 : {}", dto);

        service.updatePerson(dto);
        return "redirect:selectAll";
    }

}

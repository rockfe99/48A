package net.datasa.web3.controller;

import net.datasa.web3.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping("test")
    public String test() {
        service.test();
        return "redirect:/";
    }
}

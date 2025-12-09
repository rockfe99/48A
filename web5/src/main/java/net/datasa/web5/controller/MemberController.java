package net.datasa.web5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원정보 관련 콘트롤러
 */

@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 화면으로 이동
     */
    @GetMapping("login")
    public String login() {
        return "memberView/login";
    }

    /**
     * 가입폼으로 이동
     * @return 가입폼 HTML
     */
    @GetMapping("join")
    public String join() {
        return "memberView/join";
    }

    /**
     * 가입 정보를 입력받아 회원으로 등록하고 메인 페이지로 리다이렉트한다.
     * @param member 사용자가 입력한 가입 정보
     * @return 메인 페이지 리다이렉트 URL
     */
    @PostMapping("join")
    public String join(@ModelAttribute MemberDTO member) {
        log.debug("전달된 가입정보 : ", member);

        memberService.join(member);
        return "redirect:/";
    }
}

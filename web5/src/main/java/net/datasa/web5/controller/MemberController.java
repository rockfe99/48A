package net.datasa.web5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.security.AuthenticatedUser;
import net.datasa.web5.service.MemberService;
import org.apache.catalina.users.AbstractUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 개인정보 수정폼으로 이동
     * 로그인정보를 확인하고, ID로 개인정보 조회 후 Model에 저장
     * @return 수정 폼 HTML파일 경로
     */
    @GetMapping("info")
    public String info(@AuthenticationPrincipal AuthenticatedUser user
            , Model model) {

        MemberDTO dto = memberService.getMember(user.getUsername());
        model.addAttribute("member", dto);
        return "memberView/info";
    }

    /**
     * 개인정보 수정 처리
     */
    @PostMapping("info")
    public String info(@AuthenticationPrincipal AuthenticatedUser user, @ModelAttribute MemberDTO member) {
        log.debug("수정할 정보 : {}", member);
        member.setMemberId(user.getUsername());
        memberService.updateMember(member);
        return "redirect:/";
    }

    /**
     * ID 중복확인 창 열기
     * @return
     */
    @GetMapping("idCheck")
    public String idCheck() {
        return "memberView/idCheck";
    }

    /**
     * ID 중복확인 처리
     * @param searchId 조회할 아이디
     * @param model
     * @return
     */
    @PostMapping("idCheck")
    public String idCheck(@RequestParam("searchId") String searchId, Model model) {
        log.debug("조회할 ID : {}", searchId);

        //서비스로 ID를 전달하여 사용해도 되는지(true), 안되는지(false) 리턴
        boolean result = memberService.idCheck(searchId);

        model.addAttribute("searchId", searchId);
        model.addAttribute("result", result);

        return "memberView/idCheck";
    }

    /**
     * 회원 검색 페이지로 이동
     * @return
     */
    @GetMapping("search")
    public String search() {
        return "memberView/search";
    }

    /**
     * 전달된 이름과 일치하는 회원 조회
     * @param name      조회할 회원이름
     * @param model
     * @return          이름이 일치하는 회원목록
     */
    @PostMapping("searchName")
    public String searchName(@RequestParam("searchName") String name, Model model) {
        log.debug("검색할 이름 : {}", name);
        List<MemberDTO> memberList = memberService.searchByName(name);

        model.addAttribute("searchName", name);
        model.addAttribute("memberList", memberList);
        return "/memberView/search";
    }

    /**
     * 전달된 조건에 해당하는 회원 조회
     * @param searchMember      조회할 조건
     * @param model
     * @return                  조회결과 회원목록
     */
    @PostMapping("search")
    public String search(@ModelAttribute MemberDTO searchMember, Model model) {
        log.debug("검색할 조건 : {}", searchMember);
        List<MemberDTO> memberList = memberService.search(searchMember);

        model.addAttribute("searchMember", searchMember);
        model.addAttribute("memberList", memberList);
        return "/memberView/search";
    }



}

package net.datasa.web4.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web4.dto.GuestbookDTO;
import net.datasa.web4.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 방명록 콘트롤러
 * @since 25.12.01
 */
@RequestMapping("book")
@RequiredArgsConstructor
@Slf4j
@Controller
public class GuestbookController {

    //서비스 객체
    final GuestbookService service;

    /**
     * 글쓰기 폼으로 이동
     * @return 글쓰기 HTML 파일 경로
     */
    @GetMapping("write")
    public String write() {
        return "writeForm";
    }

    /**
     * 게시글 전달받아 저장
     * @param dto 사용자가 입력폼에서 입력한 이름,비번,내용
     * @return
     */
    @PostMapping("write")
    public String write(@ModelAttribute GuestbookDTO dto) {
        log.debug("{}", dto);
        service.write(dto);
        return "redirect:/";
    }

    /**
     * 전체 글 보기
     * @param model
     * @return HTML 파일 경로
     */
    @GetMapping("list")
    public String list(Model model) {
        List<GuestbookDTO> dtoList = service.getList();
        model.addAttribute("guestbookList", dtoList);
        return "list";
    }

    /**
     * 글번호와 비밀번호를 전달받아 글 삭제
     * @param dto   삭제할 글번호와 비밀번호
     * @param ra    오류메시지 저장한 후 리다이렉트
     * @return      글목록보기 경로
     */
    @PostMapping("delete")
    public String delete(@ModelAttribute GuestbookDTO dto,
                         RedirectAttributes ra) {
        log.debug("삭제 정보 : {}", dto);
        try {
            service.delete(dto);
        }
        catch(EntityNotFoundException e) {
            ra.addFlashAttribute("msg", e.getMessage());
        }
        catch (RuntimeException e) {
            ra.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:list";
    }

    /**
     * 글수정 폼으로 이동
     * @param num      수정할 글 번호
     * @return         수정 폼 HTML
     */
    @GetMapping("update")
    public String update(@RequestParam("num") Integer num, Model model) {
        GuestbookDTO guestbook = service.get(num);
        model.addAttribute("guestbook", guestbook);
        return "updateForm";
    }

    @PostMapping("update")
    public String update(@ModelAttribute GuestbookDTO dto,
                         RedirectAttributes redirectAttributes) {
        try {
            service.update(dto);
        }
        catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:list";
    }

}

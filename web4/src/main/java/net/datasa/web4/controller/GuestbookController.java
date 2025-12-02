package net.datasa.web4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web4.dto.GuestbookDTO;
import net.datasa.web4.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 방명록 콘트롤러
 * @since 25.12.01
 */
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


}

package net.datasa.web4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 방명록 콘트롤러
 * @since 25.12.01
 */
@Controller
public class GuestbookController {
    /**
     * 글쓰기 폼으로 이동
     * @return 글쓰기 HTML 파일 경로
     */
    @GetMapping("write")
    public String write() {
        return "writeForm";
    }


}

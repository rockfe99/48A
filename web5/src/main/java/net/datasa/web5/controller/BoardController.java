package net.datasa.web5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.security.AuthenticatedUser;
import net.datasa.web5.service.BoardService;
import net.datasa.web5.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 관련 콘트롤러
 */

@Slf4j
@RequestMapping("board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시판 목록
     */
    @GetMapping("list")
    public String list() {
        return "boardView/list";
    }

}

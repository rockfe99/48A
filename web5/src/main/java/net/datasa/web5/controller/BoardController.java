package net.datasa.web5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.BoardDTO;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.security.AuthenticatedUser;
import net.datasa.web5.service.BoardService;
import net.datasa.web5.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 게시판 전체 글 목록. 검색 및 페이지 지정 없이 모두 조회
     * @param model
     * @return 글 목록 페이지
     */
    @GetMapping("list")
    public String list(Model model) {
        List<BoardDTO> boardList = boardService.getList();
        model.addAttribute("boardList", boardList);
        return "boardView/list";
    }

    /**
     * 글쓰기 폼으로 이동
     * @return 글쓰기폼 HTML 파일 경로
     */
    @GetMapping("write")
    public String write() {
        return "boardView/write";
    }

    /**
     * 글 저장
     * @param boardDTO 작성한 글 정보 (제목, 내용)
     * @param user 로그인한 사용자 정보
     * @return 게시판 글목록 경로
     */
    @PostMapping("write")
    public String write(
            @ModelAttribute BoardDTO boardDTO
            , @AuthenticationPrincipal AuthenticatedUser user) {

        //작성한 글에 사용자 아이디 추가
        boardDTO.setMemberId(user.getUsername());
        log.debug("저장할 글 정보 : {}", boardDTO);


        try {
            boardService.write(boardDTO);
            return "redirect:list";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "boardView/writeForm";
        }
    }

    /**
     * 게시글 상세보기
     * @param model     모델
     * @param boardNum  조회할 글 번호
     * @return 게시글 상세보기 HTML 경로
     */
    @GetMapping("read")
    public String read(Model model, @RequestParam("boardNum") int boardNum) {
        log.debug("조회할 글번호 : {}", boardNum);

        try {
            BoardDTO boardDTO = boardService.getBoard(boardNum);

            model.addAttribute("board", boardDTO);
            return "boardView/read";
        }
        catch (Exception e) {
            return "redirect:list";
        }
    }

}

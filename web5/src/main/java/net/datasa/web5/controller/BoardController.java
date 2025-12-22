package net.datasa.web5.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.BoardDTO;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.dto.ReplyDTO;
import net.datasa.web5.security.AuthenticatedUser;
import net.datasa.web5.service.BoardService;
import org.springframework.beans.factory.annotation.Value;
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

    //application.properties의 게시판 관련 설정 값
    //첨부파일 저장 경로
    @Value("${board.uploadPath}")
    String uploadPath;

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
            , @AuthenticationPrincipal AuthenticatedUser user
            , MultipartFile upload) {

        //작성한 글에 사용자 아이디 추가
        boardDTO.setMemberId(user.getUsername());
        log.debug("저장할 글 정보 : {}", boardDTO);

        if (upload != null) {
            log.debug("Empty : {}", upload.isEmpty());
            log.debug("파라미터 이름 : {}", upload.getName());
            log.debug("파일명 : {}", upload.getOriginalFilename());
            log.debug("파일크기 : {}", upload.getSize());
            log.debug("파일종류 : {}", upload.getContentType());
            log.debug("저장할 경로 : {}", uploadPath);
        }

        try {
            boardService.write(boardDTO, uploadPath, upload);
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

    /**
     * 게시글 삭제
     * @param boardNum      삭제할 글번호
     * @param user          로그인한 사용자 정보
     * @return              글목록 경로
     */
    @GetMapping("delete")
    public String delete(
            @RequestParam("boardNum") int boardNum
            , @AuthenticationPrincipal AuthenticatedUser user) {

        try {
            boardService.delete(boardNum, user.getUsername(), uploadPath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:list";
    }

    /**
     * 게시글 수정 폼으로 이동
     * @param boardNum      수정할 글번호
     * @param user          로그인한 사용자 정보
     * @return              수정폼 HTML
     */
    @GetMapping("update")
    public String update(
            Model model
            , @RequestParam("boardNum") int boardNum
            , @AuthenticationPrincipal AuthenticatedUser user) {

        try {
            BoardDTO boardDTO = boardService.getBoard(boardNum);
            if (!boardDTO.getMemberId().equals(user.getUsername())) {
                throw new RuntimeException("수정 권한이 없습니다.");
            }
            model.addAttribute("board", boardDTO);
            return "boardView/update";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:list";
        }
    }

    /**
     * 게시글 수정 처리
     * @param boardDTO      수정할 글 정보
     * @param user          로그인한 사용자 정보
     * @return              수정폼 HTML
     */
    @PostMapping("update")
    public String update(
            @ModelAttribute BoardDTO boardDTO
            , @AuthenticationPrincipal AuthenticatedUser user
            , MultipartFile upload) {

        try {
            boardDTO.setMemberId(user.getUsername());
            boardService.update(boardDTO, uploadPath, upload);
            return "redirect:read?boardNum=" + boardDTO.getBoardNum();

        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:list";
        }
    }

    /**
     * 첨부 파일 다운로드
     * @param boardNum      게시글 번호
     * @param response      응답 정보
     */
    @GetMapping("download")
    public void download(
            @RequestParam("boardNum") Integer boardNum
            , HttpServletResponse response) {

        boardService.download(boardNum, response, uploadPath);
    }

    /**
     * 리플 쓰기
     * @param replyDTO      저장할 리플 정보
     * @param user          로그인 사용자 정보
     * @return              게시글 보기 경로로 이동
     */
    @PostMapping("replyWrite")
    public String replyWrite(@ModelAttribute ReplyDTO replyDTO
            , @AuthenticationPrincipal AuthenticatedUser user) {
        replyDTO.setMemberId(user.getUsername());
        boardService.replyWrite(replyDTO);
        return "redirect:read?boardNum=" + replyDTO.getBoardNum();
    }

    /**
     * 리플 삭제
     * @param replyDTO 삭제할 리플번호와 본문 글번호
     * @param user 로그인한 사용자 정보
     * @return 게시글 상세보기 경로
     */
    @GetMapping("replyDelete")
    public String replyDelete(
            @ModelAttribute ReplyDTO replyDTO
            , @AuthenticationPrincipal AuthenticatedUser user) {

        try {
            boardService.replyDelete(replyDTO.getReplyNum(), user.getUsername());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:read?boardNum=" + replyDTO.getBoardNum();
    }

}

package net.datasa.front.controller;

import lombok.RequiredArgsConstructor;
import net.datasa.front.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import net.datasa.front.dto.Comment;

import java.util.List;

/**
 * 리플 달기 AJAX 요청 처리용 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RestController  //@RestController : @Controller + @ResponseBody
@RequestMapping("ajax")
public class CommentRestController {

	private final CommentService commentService;


	/**
	 * 리플 저장
	 * @param comment 저장할 리플 정보
	 */
	@PostMapping ("insert")
	public void insert(Comment comment) {
		log.info("전달된 객체 : {}", comment);
		commentService.insertComment(comment);
	}

	/**
	 * 리플 목록 조회
	 * @return 리플 목록
	 */
	@GetMapping("list")
	public ResponseEntity<?> list() {
        List<Comment> list = commentService.selectComment();
		return ResponseEntity.ok(list);
    }

	/**
	 * 리플 삭제
	 * @param num  삭제할 번호
	 */
	@GetMapping("delete")
	public void deleteComment(int num) {
		log.info("전달된 번호 : {}", num);
        commentService.deleteComment(num);
        return;
	}

}

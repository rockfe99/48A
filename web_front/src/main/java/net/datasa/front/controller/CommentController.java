package net.datasa.front.controller;

import lombok.RequiredArgsConstructor;
import net.datasa.front.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 리플 콘트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("ajax")
@Controller
public class CommentController {

	/**
	 * 댓글 작성 페이지로 이동
	 */
	@GetMapping("comment")
	public String comment() {
		return "comment";
	}
	
}

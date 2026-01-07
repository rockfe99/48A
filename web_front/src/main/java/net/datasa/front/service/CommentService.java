package net.datasa.front.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.front.dto.Comment;
import net.datasa.front.entity.CommentEntity;
import net.datasa.front.repository.CommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 리플 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 리플 저장     *
     * @param comment 저장할 리플 정보
     */
    public void insertComment(Comment comment) {
        CommentEntity commentEntity = CommentEntity.builder()
                .name(comment.getName())
                .comment(comment.getComment())
                .build();
        commentRepository.save(commentEntity);
    }

    /**
     * 리플 목록 조회
     * @return 리플목록
     */
    public List<Comment> selectComment() {
        Sort sort = Sort.by(Sort.Direction.DESC, "num");
        List<CommentEntity> commentEntities = commentRepository.findAll();

        ArrayList<Comment> comments = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntities) {
            comments.add(
                Comment.builder()
                   .num(commentEntity.getNum())
                   .name(commentEntity.getName())
                   .comment(commentEntity.getComment())
                   .build());
        }
        return comments;
    }

    /**
     * 리플 삭제
     * @param num 삭제할 리플 번호
     */
    public void deleteComment(int num) {
        commentRepository.deleteById(num);
    }
}

package net.datasa.web5.repository;

import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 Repository
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    //제목에 전달된 문자열이 포함된 글 조회
    List<BoardEntity> findByTitleContaining(String word, Sort sort);
    //제목 또는 내용에 문자열이 포함된 글 조회
    List<BoardEntity> findByTitleContainingOrContentsContaining(String wordTitle, String wordContents, Sort sort);

    //제목 검색 1페이지 분량
    Page<BoardEntity> findByTitleContaining(String str, Pageable pageable);

    //본문 검색 1페이지 분량
    Page<BoardEntity> findByContentsContaining(String str, Pageable pageable);

    //아이디 검색 1페이지 분량
    Page<BoardEntity> findByMember_MemberId(String memberId, Pageable pageable);
}

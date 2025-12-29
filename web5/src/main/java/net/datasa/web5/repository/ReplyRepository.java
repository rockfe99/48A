package net.datasa.web5.repository;

import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 리플 관련 repository
 */

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {

    //아래 메소드는 이 예제에서는 사용되지 않음.
    //현재 글 상세보기페이지의 리플 목록은 Entity에서 처리함.

    //한 게시글의 리플 조회
    List<ReplyEntity> findByBoard_BoardNum(int boardNum, Sort sort);

    //한 게시글의 리플 조회 (JPQL 사용. 테이블명이 아닌 Entity 이름 사용)
    @Query("""
        SELECT r
        FROM ReplyEntity r
        WHERE r.board.boardNum = :boardNum
    """)
    List<ReplyEntity> findRepliesByBoardNum(
            @Param("boardNum") int boardNum,
            Sort sort
    );

}

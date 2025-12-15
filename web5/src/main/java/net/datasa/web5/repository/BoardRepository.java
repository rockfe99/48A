package net.datasa.web5.repository;

import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 Repository
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

}

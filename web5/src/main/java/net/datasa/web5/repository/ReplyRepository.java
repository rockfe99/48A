package net.datasa.web5.repository;

import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 리플 관련 repository
 */

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {

}

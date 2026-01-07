package net.datasa.front.repository;

import net.datasa.front.entity.CommentEntity;
import net.datasa.front.entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 리플 repository
 */

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}

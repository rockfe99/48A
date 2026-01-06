package net.datasa.front.repository;

import net.datasa.front.entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 추천 테스트 repository
 */

@Repository
public interface RecommendRepository extends JpaRepository<RecommendEntity, Integer> {

}

package net.datasa.front.repository;

import net.datasa.front.entity.MemberEntity;
import net.datasa.front.entity.RecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ID 중복 확인 repository
 */

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}

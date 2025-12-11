package net.datasa.web5.repository;

import net.datasa.web5.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 회원 정보 Repository
 */

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}

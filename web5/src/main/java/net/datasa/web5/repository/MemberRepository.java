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
    //쿼리메소드. 이름으로 검색
    // select * from web5_member where member_name = '전달받은문자열';
    List<MemberEntity> findAllByMemberName(String name);

    //이름, 이메일, 전화번호에 포함된 문자열로 검색
    List<MemberEntity> findAllByMemberNameContainingAndEmailContainingAndPhoneContaining(String memberName, String email, String phone);
}

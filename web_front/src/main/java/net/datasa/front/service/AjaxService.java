package net.datasa.front.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.front.dto.Member;
import net.datasa.front.entity.MemberEntity;
import net.datasa.front.entity.RecommendEntity;
import net.datasa.front.repository.MemberRepository;
import net.datasa.front.repository.RecommendRepository;
import org.springframework.stereotype.Service;

/**
 * Ajax 테스트 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AjaxService {

    private final RecommendRepository recommendRepository;
    private final MemberRepository memberRepository;

    /**
     * 추천 테스트
     * @param num            추천할 글번호
     */
    public Integer like(Integer num) throws Exception {
        RecommendEntity recommendEntity = recommendRepository.findById(num)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        Integer likeCount = recommendEntity.getLikeCount() + 1;
        recommendEntity.setLikeCount(likeCount);
        return likeCount;
    }


    /**
     * ID 존재하는지 확인
     * @param memberid
     * @return
     */
    public boolean findMemberId(String memberid) {
        return memberRepository.existsById(memberid);
    }

    /**
     * 회원 가입 처리
     * @param member
     */
    public void join(Member member) {
        MemberEntity memberEntity = MemberEntity.builder()
                .userId(member.getUserId())
                .name(member.getName())
                .build();

        memberRepository.save(memberEntity);
    }
}

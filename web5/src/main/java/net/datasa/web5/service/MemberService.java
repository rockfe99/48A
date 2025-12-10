package net.datasa.web5.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원정보 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    //암호화 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    //회원 관련 리포지토리
    private final MemberRepository memberRepository;

    /**
     * 회원 가입 처리
     * @param dto 회원 정보
     */
    public void join(MemberDTO dto) {
        MemberEntity entity = MemberEntity.builder()
                .memberId(dto.getMemberId())
                .memberPassword(passwordEncoder.encode(dto.getMemberPassword()))
                .memberName(dto.getMemberName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .enabled(true)
                .rolename("ROLE_USER")
                .build();

        memberRepository.save(entity);
    }

    /**
     * 회원정보 조회
     * @param id 조회할 아이디
     * @return 한 명의 회원정보
     */
    public MemberDTO getMember(String id) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id + " : 아이디가 없습니다."));

        MemberDTO dto = MemberDTO.builder()
                .memberId(entity.getMemberId())
                .memberPassword(entity.getMemberPassword())
                .memberName(entity.getMemberName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .enabled(entity.getEnabled())
                .rolename(entity.getRolename())
                .build();

        return dto;
    }

    /**
     * 회원정보 수정
     * 비밀번호는 새로 입력한 경우에만 수정한다.
     * @param dto 수정할 회원정보
     */
    public void updateMember(MemberDTO dto) {
        MemberEntity entity = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getMemberId() + " : 아이디가 없습니다."));

        if (!dto.getMemberPassword().isEmpty()) {
            entity.setMemberPassword(passwordEncoder.encode(dto.getMemberPassword()));
        }
        entity.setMemberName(dto.getMemberName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());

        memberRepository.save(entity);
    }

}

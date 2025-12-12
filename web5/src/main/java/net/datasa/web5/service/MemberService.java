package net.datasa.web5.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * 신규 가입 시 ID를 사용해도 되는지 조회
     * @param searchId  조회할 아이디
     * @return          사용가능 여부. 가능한 경우 true
     */
    public boolean idCheck(String searchId) {
        return !memberRepository.existsById(searchId);
    }

    //임시: 회원목록 전체 조회
    public List<MemberDTO> search() {
        List<MemberEntity> entityList = memberRepository.findAll();
        List<MemberDTO> dtoList = new ArrayList<>();
        MemberDTO dto = null;

        for (MemberEntity entity : entityList) {
            dtoList.add(entityToDto(entity));
        }
        return dtoList;
    }

    /**
     * 이름으로 회원 검색
     * @param name 검색할 이름
     * @return 이름이 같은 회원목록
     */
    public List<MemberDTO> searchByName(String name) {

        List<MemberEntity> entityList = memberRepository.findAllByMemberName(name);
        List<MemberDTO> dtoList = new ArrayList<>();

        for (MemberEntity entity : entityList) {
            dtoList.add(entityToDto(entity));
        }
        return dtoList;
    }


    /**
     * 전달된 조건에 해당하는 회원 조회
     * @param searchMember 이름, 이메일, 전화번호 등 검색 조건
     * @return              조건에 맞는 회원 목록
     */
    public List<MemberDTO> search(MemberDTO searchMember) {
        List<MemberEntity> entityList = memberRepository.findAllByMemberNameContainingAndEmailContainingAndPhoneContaining(
                searchMember.getMemberName(), searchMember.getEmail(), searchMember.getPhone());
        List<MemberDTO> dtoList = new ArrayList<>();

        for (MemberEntity entity : entityList) {
            dtoList.add(entityToDto(entity));
        }
        return dtoList;
    }

    /**
     * MemberEntity를 MemberDTO로 변환
     * @param entity 원본 엔티티 객체
     * @return       사본 DTO 객체
     */
    public MemberDTO entityToDto(MemberEntity entity) {
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
     * MemberDTO를 MemberEntity로 변환
     * @param dto       원본 DTO 객체
     * @return          사본 Entity 객체
     */
    public MemberEntity dtoToEntity(MemberDTO dto) {
        MemberEntity entity = MemberEntity.builder()
                .memberId(dto.getMemberId())
                .memberPassword(dto.getMemberPassword())
                .memberName(dto.getMemberName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .enabled(dto.getEnabled())
                .rolename(dto.getRolename())
                .build();
        return entity;
    }

}

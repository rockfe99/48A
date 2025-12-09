package net.datasa.web5.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자 인증 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticatedUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.info("로그인 시도 : {}", id);

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException(id + " : 없는 ID입니다.");
                });

        log.debug("조회정보 : {}", memberEntity);

        // 인증정보 생성 (세션에 저장되는 정보)
        AuthenticatedUser user = AuthenticatedUser.builder()
                .id(memberEntity.getMemberId())
                .password(memberEntity.getMemberPassword())
                .name(memberEntity.getMemberName())
                .enabled(memberEntity.getEnabled())
                .roleName(memberEntity.getRolename())
                .build();

        return user;
    }
}

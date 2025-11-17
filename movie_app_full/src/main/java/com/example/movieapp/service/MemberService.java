package com.example.movieapp.service;

import com.example.movieapp.entity.MovieMember;
import com.example.movieapp.repository.MovieMemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MovieMemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MovieMemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String join(MovieMember member, String rawPassword) {
        if (member.getId() == null || member.getId().length() < 3
                || rawPassword == null || rawPassword.length() < 3
                || member.getName() == null || member.getName().length() < 3) {
            return "아이디, 비밀번호, 이름은 3글자 이상 입력해야 합니다.";
        }
        if (memberRepository.existsById(member.getId())) {
            return "이미 사용 중인 아이디입니다.";
        }
        member.setPassword(passwordEncoder.encode(rawPassword));
        memberRepository.save(member);
        return null; // 성공
    }
}

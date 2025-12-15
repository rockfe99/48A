package net.datasa.web5.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.BoardRepository;
import net.datasa.web5.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    //게시판 관련 리포지토리
    private final BoardRepository boardRepository;


}

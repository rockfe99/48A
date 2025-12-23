package net.datasa.web5.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.BoardDTO;
import net.datasa.web5.dto.MemberDTO;
import net.datasa.web5.dto.ReplyDTO;
import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.entity.ReplyEntity;
import net.datasa.web5.repository.BoardRepository;
import net.datasa.web5.repository.MemberRepository;
import net.datasa.web5.repository.ReplyRepository;
import net.datasa.web5.util.FileManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    //게시판 관련 리포지토리
    private final BoardRepository boardRepository;

    //리플 관련 리포지토리
    private final ReplyRepository replyRepository;

    //회원정보 관련 리포지토리
    private final MemberRepository memberRepository;

    //파일 저장, 삭제 처리 클래스
    private final FileManager fileManager;


    /**
     * 글 저장하기
     * @param boardDTO      저장할 글 정보
     * @param path          파일 저장할 경로
     * @param file          업로드한 파일
     */
    public void write(BoardDTO boardDTO, String path, MultipartFile file) throws Exception {
        MemberEntity memberEntity =
            memberRepository.findById(boardDTO.getMemberId())
                .orElseThrow(()-> new EntityNotFoundException("회원 아이디가 없습니다."));

        String filename = null;
        String originalName = null;

        //첨부파일이 있으면 지정된 위치에 저장하고 파일명을 Entity에 저장
        if (file != null && !file.isEmpty()) {
            filename = fileManager.saveFile(path, file);
            originalName = file.getOriginalFilename();
        }

        BoardEntity boardEntity = BoardEntity.builder()
                .member(memberEntity)
                .title(boardDTO.getTitle())
                .contents(boardDTO.getContents())
                .viewCount(0)
                .likeCount(0)
                .fileName(filename)
                .originalName(originalName)
                .build();
        boardRepository.save(boardEntity);
    }

    /**
     * 검색 후 지정한 한페이지 분량의 글 목록 조회
     *
     * @param page        현재 페이지
     * @param pageSize    한 페이지당 글 수
     * @param searchType  검색 대상 (title, contents, id)
     * @param searchWord  검색어
     * @return 한페이지의 글 목록
     */
    public Page<BoardDTO> getList(int page, int pageSize, String searchType, String searchWord) {
        //Page 객체는 번호가 0부터 시작
        page--;

        //페이지 조회 조건 (현재 페이지, 페이지당 글수, 정렬 순서, 정렬 기준 컬럼)
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "boardNum");

        Page<BoardEntity> entityPage = null;

        switch (searchType) {
            case "title" :
                entityPage = boardRepository.findByTitleContaining(searchWord, pageable);     break;
            case "contents" :
                entityPage = boardRepository.findByContentsContaining(searchWord, pageable);     break;
            case "id" :
                entityPage = boardRepository.findByMember_MemberId(searchWord, pageable);     break;
            default :
                entityPage = boardRepository.findAll(pageable);     break;
        }

        //entityPage의 각 요소들을 순회하면서 convertToDTO() 메소드로 전달하여 DTO로 변환하고
        //이를 다시 새로운 Page객체로 만든다.
        Page<BoardDTO> boardDTOPage = entityPage.map(this::entityToDTO);
        return boardDTOPage;
    }


    /**
     * 게시글 1개 조회
     * @param boardNum          글번호
     * @return the BoardDTO     글 정보
     * @throws EntityNotFoundException 게시글이 없을 때 예외
     */
    public BoardDTO getBoard(int boardNum) {
        BoardEntity entity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("해당 번호의 글이 없습니다."));

        entity.setViewCount(entity.getViewCount() + 1);
        log.debug("{}번 게시물 조회 결과 : {}", boardNum, entity);

        BoardDTO dto = entityToDTO(entity);
        //BoardEntity 내의 replyList를 변환하여 BoardDTO에 대입
        List<ReplyDTO> replyDTOList = new ArrayList<ReplyDTO>();
        for (ReplyEntity replyEntity : entity.getReplyList()) {
            ReplyDTO replyDTO = replyEntityToReplyDTO(replyEntity);
            replyDTOList.add(replyDTO);
        }
        dto.setReplyList(replyDTOList);

        return dto;
    }

    /**
     * 게시글 삭제
     * @param boardNum  삭제할 글번호
     * @param username  로그인한 아이디
     * @param path      파일 저장경로
     */
    public void delete(int boardNum, String username, String path) throws Exception{
        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        if (!boardEntity.getMember().getMemberId().equals(username)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        //첨부파일이 있으면 삭제
        if (boardEntity.getFileName() != null) {
            fileManager.deleteFile(path, boardEntity.getFileName());
        }
        boardRepository.delete(boardEntity);
    }

    /**
     * 게시글 수정
     * @param boardDTO      수정할 글정보와 로그인한 아이디
     * @param uploadPath    파일 저장할 경로
     * @param upload        업로드된 파일
     */
    public void update(BoardDTO boardDTO, String uploadPath, MultipartFile upload)
            throws Exception {
        BoardEntity boardEntity = boardRepository.findById(boardDTO.getBoardNum())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        if (!boardEntity.getMember().getMemberId().equals(boardDTO.getMemberId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        //전달된 정보 수정
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContents(boardDTO.getContents());

        //업로드된 파일이 있으면 기존 파일 삭제하고 새로 저장
        if (upload != null && !upload.isEmpty()) {
            if (boardEntity.getFileName() != null) {
                fileManager.deleteFile(uploadPath, boardEntity.getFileName());
            }
            String fileName = fileManager.saveFile(uploadPath, upload);
            boardEntity.setOriginalName(upload.getOriginalFilename());
            boardEntity.setFileName(fileName);
        }
    }

    /**
     * 파일 다운로드
     * @param boardNum          글 번호
     * @param response          응답 정보
     * @param uploadPath        파일 저장 경로
     */
    public void download(Integer boardNum, HttpServletResponse response, String uploadPath) {
        //전달된 글 번호로 글 정보 조회
        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        //원래의 파일명
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(boardEntity.getOriginalName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //저장된 파일 경로
        String fullPath = uploadPath + "/" + boardEntity.getFileName();

        //서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
        FileInputStream filein = null;
        ServletOutputStream fileout = null;

        try {
            filein = new FileInputStream(fullPath);
            fileout = response.getOutputStream();

            //Spring의 파일 관련 유틸 이용하여 출력
            FileCopyUtils.copy(filein, fileout);

            filein.close();
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 리플 저장
     * @param replyDTO 작성한 리플 정보
     * @throws EntityNotFoundException 사용자 정보가 없을 때, 게시글이 없을 때 예외
     */
    public void replyWrite(ReplyDTO replyDTO) {
        MemberEntity memberEntity = memberRepository.findById(replyDTO.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("사용자 아이디가 없습니다."));

        BoardEntity boardEntity = boardRepository.findById(replyDTO.getBoardNum())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        ReplyEntity entity = ReplyEntity.builder()
                .board(boardEntity)
                .member(memberEntity)
                .contents(replyDTO.getContents())
                .build();

        replyRepository.save(entity);
    }

    /**
     * 리플 삭제
     * @param replyNum  삭제할 리플 번호
     * @param username  로그인한 아이디
     */
    public void replyDelete(Integer replyNum, String username) {
        ReplyEntity replyEntity = replyRepository.findById(replyNum)
                .orElseThrow(() -> new EntityNotFoundException("리플이 없습니다."));

        if (!replyEntity.getMember().getMemberId().equals(username)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        replyRepository.delete(replyEntity);
    }

    /**
     * DB에서 조회한 게시글 정보인 BoardEntity 객체를 BoardDTO 객체로 변환
     * @param entity    게시글 정보 Entity 객체
     * @return          게시글 정보 DTO 개체
     */
    private BoardDTO entityToDTO(BoardEntity entity) {
        return BoardDTO.builder()
                .boardNum(entity.getBoardNum())
                .memberId(entity.getMember() != null ? entity.getMember().getMemberId() : null)
                .memberName(entity.getMember() != null ? entity.getMember().getMemberName() : null)
                .title(entity.getTitle())
                .contents(entity.getContents())
                .viewCount(entity.getViewCount())
                .likeCount(entity.getLikeCount())
                .originalName(entity.getOriginalName())
                .fileName(entity.getFileName())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }


    /**
     * ReplyEntity객체를 ReplyDTO 객체로 변환
     * @param entity    리플 정보 Entity 객체
     * @return          리플 정보 DTO 객체
     */
    private ReplyDTO replyEntityToReplyDTO(ReplyEntity entity) {
        return ReplyDTO.builder()
                .replyNum(entity.getReplyNum())
                .boardNum(entity.getBoard().getBoardNum())
                .memberId(entity.getMember().getMemberId())
                .memberName(entity.getMember().getMemberName())
                .contents(entity.getContents())
                .createDate(entity.getCreateDate())
                .build();
    }

}

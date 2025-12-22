package net.datasa.web5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 정보 엔티티
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "web5_board")
public class BoardEntity {
    //게시글 테이블의 기본키
    //기본키 생성 전략 : 자동 증가(auto-increment) 값으로 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_num")
    private Integer boardNum;                       //게시글 일련번호

    //작성자 정보 (외래키)
    //다대일 관계. 게시글 여러개가 회원정보 하나를 참조한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;

    //글 제목
    @Column
    private String title;

    //글 내용
    @Column
    private String contents;

    //조회수
    @Column
    private Integer viewCount;

    //추천수
    @Column
    private Integer likeCount;

    //첨부파일의 원래 이름
    @Column
    private String originalName;

    //첨부파일의 저장된 이름
    @Column
    private String fileName;

    //작성 시간
    @CreatedDate
    @Column
    private LocalDateTime createDate;

    //수정 시간
    @LastModifiedDate
    @Column
    private LocalDateTime updateDate;

    //현재 게시글을 참조하는 리플 목록
    @OneToMany(mappedBy = "board")
    private List<ReplyEntity> replyList;
}

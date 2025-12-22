package net.datasa.web5.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
/**
 * 리플정보 엔티티
 */
@Builder
@Data
@ToString(exclude = {"board"}) //toString()메소드에 board빼고 정의
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "web5_reply")
public class ReplyEntity {
    //리플 테이블의 기본키 (일련번호)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer replyNum;

    //게시글 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_num", referencedColumnName = "board_num")
    private BoardEntity board;

    //작성자 정보 (외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MemberEntity member;

    //리플 내용
    @Column
    private String contents;

    //작성 시간
    @CreatedDate
    @Column
    private LocalDateTime createDate;

}

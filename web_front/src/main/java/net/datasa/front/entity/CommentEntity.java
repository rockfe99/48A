package net.datasa.front.entity;

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
 * 리플정보 엔티티
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ajax_comment")
public class CommentEntity {
    //일련번호. 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer num;

    //작성자 이름
    @Column
    private String name;

    //리플 내용
    @Column
    private String comment;

}

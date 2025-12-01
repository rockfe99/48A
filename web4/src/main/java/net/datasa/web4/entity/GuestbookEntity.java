package net.datasa.web4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * guestbook 테이블과 매핑되는 엔티티
 *
 * 저장날짜의 자동 처리
 * 1. 환경설정 클래스 또는 main() 포함된 클래스에
 *     @EnableJpaAuditing
 * 2. Entity 클래스에
 *     @EntityListeners(AuditingEntityListener.class)
 * 3. 날짜/시간 변수에
 *     @CreatedDate
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "guestbook")
public class GuestbookEntity {
    //primary key
    @Id
    //auto_increment 자동증가번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Integer num;
    @Column
    private String name;
    @Column
    private String password;
    @Column(name="message", nullable = false, columnDefinition = "text")
    private String message;
    @Column
    private LocalDateTime inputdate;
}

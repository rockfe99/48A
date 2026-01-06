package net.datasa.front.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 추천 테스트 엔티티
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ajax_recommend")
public class RecommendEntity {

    //게시글 테이블의 기본키
    @Id
    @Column
    private Integer num;

    //작성자 정보
    @Column
    private String userId;

    //추천수
    @Column
    private Integer likeCount;
}

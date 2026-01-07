package net.datasa.front.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ID중복확인 테스트 엔티티
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ajax_idcheck")
public class MemberEntity {

    //회원 테이블의 기본키
    @Id
    @Column
    private String userId;

    //이름
    @Column
    private String name;
}

package net.datasa.web5.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 Entity
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "web5_member")
public class MemberEntity {
    @Id
    @Column(name = "member_id", length = 30)
    String memberId;

    @Column(name = "member_password", nullable = false, length = 100)
    String memberPassword;

    @Column(name = "member_name", nullable = false, length = 30)
    String memberName;

    @Column(name = "email", length = 50)
    String email;

    @Column(name = "phone", length = 30)
    String phone;

    @Column(name = "address", length = 200)
    String address;

    @Column(name = "enabled", columnDefinition = "tinyint default 1 check(enabled in (0, 1))")
    Boolean enabled;

    @Column(name = "rolename", columnDefinition = "varchar(30) default 'role_user' check (rolename in ('ROLE_USER', 'ROLE_ADMIN'))")
    String rolename;

}

package net.datasa.web5.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    String memberId;                //회원 아이디
    String memberPassword;          //비밀번호
    String memberName;              //이름
    String email;                   //이메일
    String phone;                   //전화번호
    String address;                 //주소
    Boolean enabled;                //계정상태
    String rolename;                //권한명
}

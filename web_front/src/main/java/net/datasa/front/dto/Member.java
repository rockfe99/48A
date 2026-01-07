package net.datasa.front.dto;

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
public class Member {

    private String userId;  //회원ID
    private String name;    //이름
}

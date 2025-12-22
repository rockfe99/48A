package net.datasa.web5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 리플 정보 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Integer replyNum;                       //리플 일련번호
    private Integer boardNum;                       //게시글 일련번호
    private String memberId;                        //작성자 아이디
    private String memberName;                      //작성자 이름
    private String contents;                        //리플 내용
    private LocalDateTime createDate;               //작성 시간
}

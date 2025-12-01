package net.datasa.web4.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시글 정보 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GuestbookDTO {
    private Integer num;
    private String name;
    private String password;
    private String message;
    private LocalDateTime inputdate;
}

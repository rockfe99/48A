package net.datasa.front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 리플 달기 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	int num;
	String name;
	String comment;
}

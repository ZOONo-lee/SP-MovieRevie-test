package org.zerock.mreview.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
  private Long reviewnum; //Review에 대한 번호

  private Long mno; //Movie에 대한 번호
  private Long mid; //Member에 대한 번호

  private String nickname;
  private String email;
  private int grade;
  private String text;
  private LocalDateTime regDate, modDate;
}

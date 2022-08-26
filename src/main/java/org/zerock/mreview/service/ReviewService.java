package org.zerock.mreview.service;

import java.util.List;

import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

public interface ReviewService {
  List<ReviewDTO> getListOfMovie(Long mno);

  Long register(ReviewDTO dto);

  void modify(ReviewDTO dto);

  void remove(Long reviewnum);

  default Review dtoToEntity(ReviewDTO dto) {
    Review review = Review.builder()
        .reviewnum(dto.getReviewnum())
        .movie(Movie.builder().mno(dto.getMno()).build())
        .member(Member.builder().mid(dto.getMid()).build())
        .grade(dto.getGrade())
        .text(dto.getText())
        .build();
    return review;
  }

  default ReviewDTO entityToDTO(Review review) {
    ReviewDTO dto = ReviewDTO.builder()
        .reviewnum(review.getReviewnum())
        .mno(review.getMovie().getMno())
        .mid(review.getMember().getMid())
        .nickname(review.getMember().getNickname())
        .email(review.getMember().getEmail())
        .grade(review.getGrade())
        .text(review.getText())
        .regDate(review.getRegDate())
        .modDate(review.getModDate())
        .build();
    return dto;
  }
}

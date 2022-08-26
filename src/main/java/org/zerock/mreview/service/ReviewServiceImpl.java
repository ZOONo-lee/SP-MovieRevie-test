package org.zerock.mreview.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository repository;

  @Override
  public List<ReviewDTO> getListOfMovie(Long mno) {
    Movie movie = Movie.builder().mno(mno).build();
    List<Review> result = repository.findByMovie(movie);

    return result.stream().map(
      review -> entityToDTO(review)).collect(Collectors.toList());
  }

  @Override
  public Long register(ReviewDTO dto) {
    log.info("register... " + dto);
    // Review review = dtoToEntity(dto);
    // repository.save(review);
    // return review.getReviewnum();
    return repository.save(dtoToEntity(dto)).getReviewnum();
  }

  @Override
  public void modify(ReviewDTO dto) {
    log.info("modify... " + dto);
    Optional<Review> result = repository.findById(dto.getReviewnum());
    if(result.isPresent()){
      Review review = result.get();
      review.changeGrade(dto.getGrade());
      review.changeText(dto.getText());
      repository.save(review);
    }
  }

  @Override
  public void remove(Long reviewnum) {
    repository.deleteById(reviewnum);
  }
}

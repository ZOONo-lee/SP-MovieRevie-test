package org.zerock.mreview.repository;

import java.util.List;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

@SpringBootTest
public class ReviewRepositoryTests {
  @Autowired
  ReviewRepository repository;

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void insertReviews(){
    IntStream.rangeClosed(1, 200).forEach(i->{
      Long mno = (long)(Math.random()*100)+1;

      Long mid = (long)(Math.random()*100)+1;
      Member member = Member.builder().mid(mid).build();
      Review review = Review.builder().member(member)
                      .movie(Movie.builder().mno(mno).build())
                      .grade((int)(Math.random()*5)+1)
                      .text("이 영화에 대한 느낌...").build();
      repository.save(review);
    });
  }

  @Test
  public void testGetMovieReviews(){
    Movie movie = Movie.builder().mno(95L).build();
    List<Review> result = repository.findByMovie(movie);
    result.forEach(movieReview -> {
      System.out.print(movieReview.getReviewnum());
      System.out.print("\t"+movieReview.getGrade());
      System.out.print("\t"+movieReview.getText());
      System.out.print("\t"+movieReview.getMember().getEmail());
      System.out.println("-------------------");
    });
  }

  @Transactional
  @Commit
  @Test
  public void testDeleteMember(){
    Member member = Member.builder().mid(2L).build();
    repository.deleteByMember(member);
    memberRepository.deleteById(member.getMid());
  }
}

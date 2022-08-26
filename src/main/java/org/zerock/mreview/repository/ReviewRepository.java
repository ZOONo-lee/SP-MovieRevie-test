package org.zerock.mreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
  List<Review> findByMovie(Movie movie);

  @Modifying
  @Query("delete from Review r where r.member = :member ")
  void deleteByMember(Member member);

  @Modifying
  @Query("delete from Review r where r.movie.mno=:mno")
  void deleteByMno(Long mno);
}

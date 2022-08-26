package org.zerock.mreview.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

  @Modifying
  @Query("delete from MovieImage mi where mi.movie.mno=:mno")
  void deleteByMno(long mno);

  @Transactional
  @Modifying
  @Query("delete from MovieImage mi where mi.uuid=:uuid")
  void deleteByUuid(String uuid);

  @Query("select mi from MovieImage mi where mi.movie.mno=:mno")
  List<MovieImage> findByMno(Long mno);
}

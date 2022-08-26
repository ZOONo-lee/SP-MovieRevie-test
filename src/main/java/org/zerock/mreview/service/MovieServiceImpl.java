package org.zerock.mreview.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.MovieImageRepository;
import org.zerock.mreview.repository.MovieRepository;
import org.zerock.mreview.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
  private final MovieRepository movieRepository;
  private final MovieImageRepository miRepository;
  private final ReviewRepository reviewRepository;

  @Override
  public void removeUuid(String uuid) {
    log.info("deleteImage...... uuid: "+uuid);
    miRepository.deleteByUuid(uuid);
  }

  @Transactional
  @Override
  public Long register(MovieDTO dto) {
    log.info("register.......");
    Map<String, Object> entityMap = dtoToEntity(dto);

    Movie movie = (Movie) entityMap.get("movie");// 자부자
    List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

    movieRepository.save(movie);
    movieImageList.forEach(new Consumer<MovieImage>() {
      @Override
      public void accept(MovieImage movieImage) {
        miRepository.save(movieImage);
      }
    });

    return movie.getMno();
  }

  @Override
  public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO req) {
    // 요청하는 페이지에 대한 정보를 가진 객체 Pageable
    // An object Pageable with information about the requesting page
    Pageable pageable = req.getPageable(Sort.by("mno").descending());
    // 해당페이지에 대한 정보(list)를 가진 객체 Page
    // An object with information(list) about the page.
    Page<Object[]> result = movieRepository.getListPage(pageable);
    Function<Object[], MovieDTO> fn = new Function<Object[], MovieDTO>() {
      @Override
      public MovieDTO apply(Object[] t) {
        return entityToDTO(
            (Movie) t[0],
            (List<MovieImage>) (Arrays.asList((MovieImage) t[1])),
            (Double) t[2],
            (Long) t[3]);
      }
    };
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public MovieDTO getMovie(Long mno) {
    List<Object[]> result = movieRepository.getMovieWithAll(mno);
    Movie movie = (Movie) result.get(0)[0];
    List<MovieImage> movieImageList = new ArrayList();
    result.forEach(new Consumer<Object[]>() {
      @Override
      public void accept(Object[] arr) {
        MovieImage movieImage = (MovieImage) arr[1];
        movieImageList.add(movieImage);
      }
    });
    Double avg = (Double) result.get(0)[2];
    Long reviewCnt = (Long) result.get(0)[3];
    return entityToDTO(movie, movieImageList, avg, reviewCnt);
  }

  @Override
  public void modify(MovieDTO dto) {
    Optional<Movie> result = movieRepository.findById(dto.getMno());
    if (result.isPresent()) {
      Movie movie = result.get();
      movie.changeTitle(dto.getTitle());
      movieRepository.save(movie);
    }
  }

  @Transactional
  @Override
  public List<String> removeWithReviewsAndMovieImages(Long mno) {
    List<MovieImage> list = miRepository.findByMno(mno);
    List<String> result = new ArrayList<>();
    list.forEach(new Consumer<MovieImage>() {
      @Override
      public void accept(MovieImage t) {
        result.add(t.getPath()+File.separator+t.getUuid()+"_"+t.getImgName());
      }
    });
    miRepository.deleteByMno(mno);
    reviewRepository.deleteByMno(mno);
    movieRepository.deleteById(mno);
    return result;
  }

}

package org.zerock.mreview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

public interface MovieService {
  Long register(MovieDTO dto);
  PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO req);
  MovieDTO getMovie(Long mno);
  void modify(MovieDTO dto);
  List<String> removeWithReviewsAndMovieImages(Long mno);
  void removeUuid(String uuid);

  default MovieDTO entityToDTO(Movie movie, List<MovieImage> mi,Double avg,
  Long reviewCnt){
    MovieDTO movieDTO = MovieDTO.builder().mno(movie.getMno())
    .title(movie.getTitle()).regDate(movie.getRegDate()).modDate(movie.getModDate())
    .build();

    List<MovieImageDTO> movieImageDTOs = mi.stream().map(
      new Function<MovieImage, MovieImageDTO>() {
        public MovieImageDTO apply(MovieImage t) {
          return MovieImageDTO.builder()
          .imgName(t.getImgName()).path(t.getPath()).uuid(t.getUuid())
          .build();
        };
      }).collect(Collectors.toList());
      movieDTO.setImageDTOList(movieImageDTOs);
      movieDTO.setAvg(avg);
      movieDTO.setReviewCnt(reviewCnt.intValue());
      return movieDTO;
  }
  default Map<String, Object> dtoToEntity(MovieDTO dto){
    //MovieDTO로 부터 Movie, MovieImage 두개를 나눠서 담기 위한 map 선언
    Map<String, Object> entityMap = new HashMap<>();

    //First Element of Map
    Movie movie = Movie.builder().mno(dto.getMno()).title(dto.getTitle()).build();
    entityMap.put("movie", movie); 
    
    List<MovieImageDTO> imageDTOList = dto.getImageDTOList();
    //poster있을때만 MovieImageDTO를 MovieImage 변환
    if(imageDTOList != null && imageDTOList.size() > 0) {
      List<MovieImage> movieImageList = imageDTOList.stream().map(
        new Function<MovieImageDTO,MovieImage>() {
          @Override
          public MovieImage apply(MovieImageDTO t) {
            MovieImage movieImage = MovieImage.builder().path(t.getPath())
                                    .imgName(t.getImgName()).uuid(t.getUuid())
                                    .movie(movie).build();
            return movieImage;
          }
        }
      ).collect(Collectors.toList());
      //Second Element of Map
      entityMap.put("imgList", movieImageList);
    }
    return entityMap;
  }
}

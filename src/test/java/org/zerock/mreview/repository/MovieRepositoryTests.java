package org.zerock.mreview.repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

@SpringBootTest
public class MovieRepositoryTests {
  @Autowired
  private MovieRepository repository;

  @Autowired
  private MovieImageRepository miRepository;

  @Test
  public void insertMovies(){
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Movie movie = Movie.builder().title("Movie..."+i).build();
      System.out.println("-------------------------------");
      repository.save(movie);

      int count = (int)(Math.random()*5)+1;
      for (int j = 0; j < count; j++) {
        MovieImage mi = MovieImage.builder().uuid(UUID.randomUUID().toString())
                        .movie(movie).imgName("test"+j+".jpg").build();
        miRepository.save(mi);
      }
      System.out.println("-------------------------------");
    });
  }

  @Test
  public void testGetListPage(){
    PageRequest req = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
    Page<Object[]> result = repository.getListPage(req);
    for(Object[] obj : result.getContent()){
      System.out.println(Arrays.toString(obj));
    }
  }
  @Test
  public void testGetListPageWithFirstImage(){
    PageRequest req = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
    Page<Object[]> result = repository.getListPageWithFirstImage(req);
    for(Object[] obj : result.getContent()){
      System.out.println(Arrays.toString(obj));
    }
  }
  @Test
  public void testGetListPageWithLatestImage(){
    PageRequest req = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
    Page<Object[]> result = repository.getListPageWithLatestImage(req);
    for(Object[] obj : result.getContent()){
      System.out.println(Arrays.toString(obj));
    }
  }

  @Test
  public void testGetMovieWithAll(){
    List<Object[]> result = repository.getMovieWithAll(92L);
    for (int i = 0; i < result.size(); i++) {
      System.out.println(Arrays.toString(result.get(i)));
    }
  }

  @Test
  public void testGetList(){
    PageRequest req = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
    Page<Object[]> result = repository.getListPage(req);
    for(Object[] obj : result.getContent()){
      System.out.println(Arrays.toString(obj));
    }
  }
}

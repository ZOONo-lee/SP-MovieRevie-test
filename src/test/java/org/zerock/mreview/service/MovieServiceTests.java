package org.zerock.mreview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;

@SpringBootTest
public class MovieServiceTests {
  @Autowired
  private MovieService service;

  @Test
  void testGetList() {
    PageRequestDTO requestDTO = new PageRequestDTO();
    PageResultDTO<MovieDTO, Object[]> result = service.getList(requestDTO);
    for(MovieDTO dto : result.getDtoList()) System.out.println(dto);
  }
}

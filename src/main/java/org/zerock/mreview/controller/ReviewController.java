package org.zerock.mreview.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService service;

  @GetMapping("/{mno}/all")
  public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno) {
    log.info("list......... mno:" + mno);
    List<ReviewDTO> reviewDTOs = service.getListOfMovie(mno);
    return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
  }

  @PostMapping("/{mno}")
  public ResponseEntity<Long> addReview(@RequestBody ReviewDTO dto) {
    log.info("add........... dto: " + dto);
    return new ResponseEntity<>(service.register(dto), HttpStatus.OK);
  }

  @PutMapping("/{mno}/{reviewnum}")
  public ResponseEntity<Long> modifyReview(
      @RequestBody ReviewDTO dto, @PathVariable Long reviewnum) {
    log.info("modify........... dto: " + dto);
    service.modify(dto);
    return new ResponseEntity<>(reviewnum, HttpStatus.OK);
  }

  @DeleteMapping("/{mno}/{reviewnum}")
  public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum){
    log.info("remove............. reviewnum: " + reviewnum);
    service.remove(reviewnum);
    return new ResponseEntity<>(reviewnum, HttpStatus.OK);
  }

}

package org.zerock.mreview.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping("/register")
  public void register(){}

  @PostMapping("/register")
  public String registerPost(MovieDTO dto, RedirectAttributes ra){
    log.info("register post... "+dto);
    Long mno = movieService.register(dto);
    ra.addFlashAttribute("msg", mno+ " 등록");
    return "redirect:/movie/list";
  }

  @GetMapping({"","/"})
  public String list(){
    return "redirect:/movie/list";
  }
  @GetMapping("/list")
  public void list(@ModelAttribute("requestDTO") PageRequestDTO req, Model model){
    log.info("PageRequest:" + req);
    model.addAttribute("result", movieService.getList(req));
  }

  @GetMapping({"/read", "/modify"})
  public void read(long mno, 
  @ModelAttribute("requestDTO") PageRequestDTO req, Model model){
    MovieDTO dto = movieService.getMovie(mno);
    model.addAttribute("dto", dto);
  }

  @PostMapping("/modify")
  public String modify(MovieDTO dto, RedirectAttributes ra, PageRequestDTO req){
    log.info("modify post... dto: " + dto);
    movieService.modify(dto);
    ra.addFlashAttribute("msg", dto.getMno() + " 수정");
    ra.addAttribute("mno", dto.getMno());
    ra.addAttribute("page", req.getPage());
    ra.addAttribute("type", req.getType());
    ra.addAttribute("keyword", req.getKeyword());
    return "redirect:/movie/read";
  }

  @Value("${org.zerock.upload.path}")
  private String uploadPath;

  @PostMapping("/remove")
  public String remove(Long mno, RedirectAttributes ra, PageRequestDTO req){
    log.info("remove post... mno: " + mno);
    List<String> result = movieService.removeWithReviewsAndMovieImages(mno);
    log.info("result>>"+result);
    result.forEach(new Consumer<String>() {
      @Override
      public void accept(String fileName) {
        try {
          log.info("removeFile............"+fileName);
          String srcFileName = URLDecoder.decode(fileName, "UTF-8");
          File file = new File(uploadPath + File.separator + srcFileName);
          file.delete();
          File thumb = new File(file.getParent(),"s_"+file.getName());
          thumb.delete();
        } catch (Exception e) {
          log.info("remove file : "+e.getMessage());
        }
      }
    });
    if(movieService.getList(req).getDtoList().size() == 0 && req.getPage() != 1) {
      req.setPage(req.getPage()-1);
    }
    ra.addFlashAttribute("msg", mno + " 삭제");
    ra.addAttribute("page", req.getPage());
    ra.addAttribute("type", req.getType());
    ra.addAttribute("keyword", req.getKeyword());
    return "redirect:/movie/list";
  }
}

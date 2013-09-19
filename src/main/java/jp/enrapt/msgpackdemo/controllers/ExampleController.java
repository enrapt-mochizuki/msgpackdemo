package jp.enrapt.msgpackdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExampleController {
  @RequestMapping("/download")
  public String download() {
    return "download";
  }

  @RequestMapping("/upload")
  public String upload() {
    return "upload";
  }
}

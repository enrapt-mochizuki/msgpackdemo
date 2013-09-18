package jp.enrapt.msgpackdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExampleController {
  @RequestMapping("/example")
  public String example() {
    return "example";
  }

  @RequestMapping("/upload")
  public String upload() {
    return "upload";
  }
}

package jp.enrapt.msgpackdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class APIController {
  @RequestMapping("/hello")
  public ResponseEntity<String> hello(@RequestParam("name") String name) {
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("hello " + name, HttpStatus.OK);
    return responseEntity;
  }
}
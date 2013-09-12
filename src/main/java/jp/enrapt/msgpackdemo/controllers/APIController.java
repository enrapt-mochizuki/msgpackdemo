package jp.enrapt.msgpackdemo.controllers;

import java.io.IOException;

import org.msgpack.MessagePack;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.enrapt.msgpackdemo.messages.Customer;

@Controller
public class APIController {
  @RequestMapping("/hello")
  public ResponseEntity<String> hello(@RequestParam("name") String name) {
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("hello " + name, HttpStatus.OK);
    return responseEntity;
  }

  @RequestMapping("/msgpackdemo")
  public ResponseEntity<byte[]> msgpackdemo() throws IOException {
    MessagePack msgpack = new MessagePack();
    Customer customer = new Customer();
    customer.name = "Sherlock Shellingford";
    customer.age = 15;

    byte[] body = msgpack.write(customer);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/x-msgpack");

    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
    return responseEntity;
  }
}
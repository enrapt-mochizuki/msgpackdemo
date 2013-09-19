package jp.enrapt.msgpackdemo.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

import org.apache.commons.codec.binary.Base64;

import org.msgpack.MessagePack;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
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

  @RequestMapping("/api/download")
  public ResponseEntity<byte[]> msgpackdemo() throws IOException {
    MessagePack msgpack = new MessagePack();
    msgpack.register(Customer.class);
    Customer customer = new Customer("Sherlock Shellingford", 15);
    Map<String, Object> map = beanToNamedMap(customer);

    byte[] packed = msgpack.write(map);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/x-msgpack");

    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(packed, headers, HttpStatus.OK);
    return responseEntity;
  }

  private static Map<String, Object> beanToNamedMap(Object bean) {
    BeanMap beanMap = new BeanMap(bean);
    Map<String, Object> map = new HashMap<String, Object>();
    for (Object obj : beanMap.entrySet()) {
      Map.Entry<String, Object> entry = (Map.Entry<String, Object>)obj;
      if (!entry.getKey().equals("class"))
        map.put(entry.getKey(), entry.getValue());
    }

    return map;
  }

  @RequestMapping("/msgpackuploaddemo")
  public ResponseEntity<String> msgpackdemo(@RequestBody String body) throws IOException {
    byte[] decoded = Base64.decodeBase64(body);
    MessagePack msgpack = new MessagePack();
    String unpacked = msgpack.read(decoded, String.class);
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("\"" + unpacked + "\" uploaded", HttpStatus.OK);
    return responseEntity;
  }
}
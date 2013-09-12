package jp.enrapt.msgpackdemo.messages;

import org.msgpack.annotation.Message;

@Message
public class Customer {
  public String name;
  public int age;
}

package jp.enrapt.msgpackdemo.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.math.BigInteger;

public class JSONObjectTraverser {
  public interface Processor<T> {
    T processNull();
    T processBoolean(boolean bool);
    T processInteger(int integer);
    T processLong(long lng);
    T processBigInteger(BigInteger bigInteger);
    T processDouble(double dbl);
    T processArray(ArrayList array);
    T processMap(LinkedHashMap<String, Object> map);
    T processString(String str);
  }

  public static <T> T traverse(Object object, Processor<T> processor) {
    if (object == null) {
      return processor.processNull();
    } else if (object instanceof Boolean) {
      return processor.processBoolean((Boolean)object);
    } else if (object instanceof Integer) {
      return processor.processInteger((Integer)object);
    } else if (object instanceof Long) {
      return processor.processLong((Long)object);
    } else if (object instanceof BigInteger) {
      return processor.processBigInteger((BigInteger)object);
    } else if (object instanceof Double) {
      return processor.processDouble((Double)object);
    } else if (object instanceof ArrayList) {
      return processor.processArray((ArrayList)object);
    } else if (object instanceof LinkedHashMap) {
      return processor.processMap((LinkedHashMap<String, Object>)object);
    } else if (object instanceof String) {
      return processor.processString((String)object);
    } else {
      throw new AssertionError();
    }
  }
}

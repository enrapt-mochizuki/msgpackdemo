package jp.enrapt.msgpackdemo.utils;

import org.msgpack.type.Value;
import org.msgpack.type.ValueFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONObjectToValueTransformer {
  public static Value transform(Object object) {
    return JSONObjectTraverser.traverse(object, new ToValueProcessor());
  }

  private static class ToValueProcessor implements JSONObjectTraverser.Processor<Value> {
    @Override
    public Value processNull() {
      return ValueFactory.createNilValue();
    }

    @Override
    public Value processBoolean(boolean bool) {
      return ValueFactory.createBooleanValue(bool);
    }

    @Override
    public Value processInteger(int integer) {
      return ValueFactory.createIntegerValue(integer);
    }

    @Override
    public Value processLong(long lng) {
      return ValueFactory.createIntegerValue(lng);
    }

    @Override
    public Value processBigInteger(BigInteger bigInteger) {
      return ValueFactory.createIntegerValue(bigInteger);
    }

    @Override
    public Value processDouble(double dbl) {
      return ValueFactory.createFloatValue(dbl);
    }

    @Override
    public Value processArray(ArrayList array) {
      int size = array.size();
      Value[] values = new Value[size];

      for (int n = 0; n < size; n++) {
        Object element = array.get(n);
        Value processedElement = JSONObjectTraverser.traverse(element, this);
        values[n] = processedElement;
      }

      return ValueFactory.createArrayValue(values);
    }

    @Override
    public Value processMap(LinkedHashMap<String, Object> map) {
      int size = map.size() * 2;
      Value[] valuePairs = new Value[size];

      int n = 0;
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        String key = entry.getKey();
        Object value = entry.getValue();
        Value processedKey = ValueFactory.createRawValue(key);
        Value processedValue = JSONObjectTraverser.traverse(value, this);
        valuePairs[n++] = processedKey;
        valuePairs[n++] = processedValue;
      }

      return ValueFactory.createMapValue(valuePairs);
    }

    @Override
    public Value processString(String str) {
      return ValueFactory.createRawValue(str);
    }
  }
}

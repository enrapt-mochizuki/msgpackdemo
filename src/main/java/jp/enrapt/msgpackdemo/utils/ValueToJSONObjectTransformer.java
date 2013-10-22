package jp.enrapt.msgpackdemo.utils;

import org.msgpack.type.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class ValueToJSONObjectTransformer {
  public static Object transform(Value value) {
    return ValueTraverser.traverse(value, new ToJSONObjectProcessor());
  }

  private static class ToJSONObjectProcessor implements ValueTraverser.Processor<Object> {
    @Override
    public Object processNilValue(NilValue nilValue) {
      return null;
    }

    @Override
    public Object processBooleanValue(BooleanValue booleanValue) {
      return Boolean.valueOf(booleanValue.getBoolean());
    }

    @Override
    public Object processIntegerValue(IntegerValue integerValue) {
      return integerValue.getBigInteger();
    }

    @Override
    public Object processFloatValue(FloatValue floatValue) {
      return floatValue.getDouble();
    }

    @Override
    public Object processArrayValue(ArrayValue arrayValue) {
      List<Object> list = new ArrayList<Object>();

      for (Value element : arrayValue.getElementArray()) {
        Object processedElement = ValueTraverser.traverse(element, this);
        list.add(processedElement);
      }

      return list;
    }

    @Override
    public Object processMapValue(MapValue mapValue) {
      Map<Object, Object> map = new LinkedHashMap<Object, Object>();

      for (MapValue.Entry<Value, Value> entry : mapValue.entrySet()) {
        Value key = entry.getKey();
        Value value = entry.getValue();
        Object processedKey = ValueTraverser.traverse(key, this);
        Object processedValue = ValueTraverser.traverse(value, this);
        map.put(processedKey, processedValue);
      }

      return map;
    }

    @Override
    public Object processRawValue(RawValue rawValue) {
      return rawValue.getString();
    }
  }
}
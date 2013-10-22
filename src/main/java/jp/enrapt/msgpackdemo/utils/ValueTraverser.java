package jp.enrapt.msgpackdemo.utils;

import org.msgpack.type.*;

public class ValueTraverser {
  public interface Processor<T> {
    T processNilValue(NilValue nilValue);
    T processBooleanValue(BooleanValue booleanValue);
    T processIntegerValue(IntegerValue integerValue);
    T processFloatValue(FloatValue floatValue);
    T processArrayValue(ArrayValue arrayValue);
    T processMapValue(MapValue mapValue);
    T processRawValue(RawValue rawValue);
  }

  public static <T> T traverse(Value value, Processor<T> processor) {
    if (value.isNilValue()) {
      return processor.processNilValue(value.asNilValue());
    } else if (value.isBooleanValue()) {
      return processor.processBooleanValue(value.asBooleanValue());
    } else if (value.isIntegerValue()) {
      return processor.processIntegerValue(value.asIntegerValue());
    } else if (value.isFloatValue()) {
      return processor.processFloatValue(value.asFloatValue());
    } else if (value.isArrayValue()) {
      return processor.processArrayValue(value.asArrayValue());
    } else if (value.isMapValue()) {
      return processor.processMapValue(value.asMapValue());
    } else if (value.isRawValue()) {
      return processor.processRawValue(value.asRawValue());
    } else {
      throw new AssertionError();
    }
  }
}
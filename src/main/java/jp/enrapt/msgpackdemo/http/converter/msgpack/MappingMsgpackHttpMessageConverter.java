package jp.enrapt.msgpackdemo.http.converter.msgpack;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import jp.enrapt.msgpackdemo.utils.JSONObjectToValueTransformer;
import jp.enrapt.msgpackdemo.utils.ValueToJSONObjectTransformer;

public class MappingMsgpackHttpMessageConverter
  extends AbstractHttpMessageConverter<Object>
  implements GenericHttpMessageConverter<Object> {

  public MappingMsgpackHttpMessageConverter() {
    super(new MediaType("application", "x-msgpack"));
  }

  @Override
  protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
    // This method is called by AbstractHttpMessageConverter#read.
    // But this class override AbstractHttpMessageConverter#read,
    // thus this method is never called.
    throw new UnsupportedOperationException();
  }

  @Override
  protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    Object rawData = objectMapper.convertValue(o, Object.class);
    Value value = JSONObjectToValueTransformer.transform(rawData);
    Base64OutputStream encodedBodyStream = new Base64OutputStream(outputMessage.getBody());
    messagePack.write(encodedBodyStream, value);
  }

  @Override
  public boolean canRead(Class<?> clazz, MediaType mediaType) {
    JavaType javaType = createJavaType(clazz, null);
    return super.canRead(mediaType) && objectMapper.canDeserialize(javaType);
  }

  @Override
  public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
    JavaType javaType = createJavaType(type, contextClass);
    return super.canRead(mediaType) && objectMapper.canDeserialize(javaType);
  }

  @Override
  public boolean canWrite(Class<?> clazz, MediaType mediaType) {
    return super.canWrite(mediaType) && objectMapper.canSerialize(clazz);
  }

  @Override
  public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
    JavaType javaType = createJavaType(type, contextClass);
    return readImpl(javaType, inputMessage);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    throw new UnsupportedOperationException();
  }

  private JavaType createJavaType(Type type, Class<?> contextClass) {
    return TypeFactory.defaultInstance().constructType(type, contextClass);
  }

  private Object readImpl(JavaType javaType, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
    Base64InputStream decodedBodyStream = new Base64InputStream(inputMessage.getBody());
    Value unpacked = messagePack.read(decodedBodyStream);
    Object rawDara = ValueToJSONObjectTransformer.transform(unpacked);
    Object ret = objectMapper.convertValue(rawDara, javaType.getRawClass());
    return ret;
  }

  private ObjectMapper objectMapper = new ObjectMapper();
  private MessagePack messagePack = new MessagePack();
}
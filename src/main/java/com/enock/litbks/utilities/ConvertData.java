package com.enock.litbks.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData{

  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public <T> T convert(String json, Class<T> gClass) {
    try {
      return objectMapper.readValue(json, gClass);
    } catch (
        JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}

package com.enock.litbks.utilities;

public interface IConvertData {
  <T> T convert(String url, Class<T> gClass);
}

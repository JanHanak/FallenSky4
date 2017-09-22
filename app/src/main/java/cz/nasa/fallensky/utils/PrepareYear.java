package cz.nasa.fallensky.utils;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

public class PrepareYear extends StringBasedTypeConverter<Integer> {





  @Override
  public Integer getFromString(String string) {
    return Integer.getInteger(string.substring(0,3));
  }

  @Override
  public String convertToString(Integer object) {
    return Integer.toString(object);
  }
}
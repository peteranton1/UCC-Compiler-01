package org.ardvark.ast;

import java.util.List;

public class StringUtils {

  public String concat(List<String> strs){
    if(strs == null){
      return "";
    }
    StringBuilder buf = new StringBuilder();
    buf.append("\"");
    for(String str: strs){
      buf.append(dequote(str));
    }
    buf.append("\"");
    return buf.toString();
  }

  public String dequote(String s) {
    final char Quote = '"';
    if(s == null || s.length() < 2 ||
        Quote != s.charAt(0) || Quote != s.charAt(s.length()-1)){
      return s;
    }
    return s.substring(1,s.length()-1);
  }
}

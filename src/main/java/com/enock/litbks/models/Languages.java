package com.enock.litbks.models;

public enum Languages {
  SPANISH("es", "español"),
  ENGLISH("en", "ingles"),
  FRENCH("fr", "frances"),
  ITALIAN("it", "italiano"),
  PORTUGUESE("pt", "portugues"),
  HUNGARIAN("hu", "hungaro");


  private String languagesId;
  private String languaguesTranslate;

  Languages(String languagesId, String languaguesTranslate) {
    this.languagesId = languagesId;
    this.languaguesTranslate = languaguesTranslate;
  }


  public static Languages fromString (String text) {
    for (Languages lan : Languages.values()){
      if(lan.languagesId.equalsIgnoreCase(text)){
        return lan;
      }
    }
    throw new IllegalArgumentException("Ninguna idioma encontrada: " + text);
  }

  public static Languages fromSpanish (String text) {
    for (Languages lan : Languages.values()){
      if(lan.languaguesTranslate.equalsIgnoreCase(text)){
        return lan;
      }
    }
    throw new IllegalArgumentException("Ninguna idioma encontrada: " + text);
  }
}

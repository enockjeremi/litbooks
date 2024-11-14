package com.enock.litbks.models;

import com.enock.litbks.models.recods.DataAuthor;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.enock.litbks.utilities.Utilities.reversedName;

@Entity
@Table(name = "authors")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @Column(unique = true)
  private String name;
  private Integer birthYear;
  private Integer deathYear;

  @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
  private List<Book> books;

  public Author() {
  }

  public Author(DataAuthor dataAuthor) {
    this.name = dataAuthor.name();
    this.birthYear = dataAuthor.birthYear();
    this.deathYear = dataAuthor.deathYear();
  }

  @Override
  public String toString() {
    return String.format("""
        
        ---------- Autor ----------
        - Nombre: %s
        - Año de nacimiento: %d
        - Año de fallecido: %d
        - Libros publicados: %s
        ---------------------------

        """, reversedName(name), birthYear, deathYear, getNameBook());
  }

  public List<String> getNameBook(){
    return books.stream().map(Book::getTitle).collect(Collectors.toList());
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public Integer getDeathYear() {
    return deathYear;
  }

  public void setDeathYear(Integer deathYear) {
    this.deathYear = deathYear;
  }
}

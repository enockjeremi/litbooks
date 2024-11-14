package com.enock.litbks.models;

import com.enock.litbks.models.recods.DataBook;
import jakarta.persistence.*;

import java.util.List;

import static com.enock.litbks.utilities.Utilities.reversedName;

@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @Column(unique = true)
  private String title;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "books_authors", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
  )
  private List<Author> authors;

  private List<String> languages;

  private Double downloadCount;

  public Book() {
  }

  public Book(DataBook dataBook) {
    this.title = dataBook.title();
    this.languages = dataBook.languages();
    this.downloadCount = dataBook.downloadCount();
  }

  @Override
  public String toString() {
    return String.format("""
        
        ---------- Libro ----------
        - Titulo: %s
        - Autor/es: %s
        - Idioma: %s
        - Total de descargas: %.1f
        ---------------------------
        
        """, title, getAuthorsName(), languages, downloadCount);

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public List<String> getAuthorsName() {
    return getAuthors().stream().map(m -> reversedName(m.getName())).toList();
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public Double getDownloadCount() {
    return downloadCount;
  }

  public void setDownloadCount(Double downloadCount) {
    this.downloadCount = downloadCount;
  }
}

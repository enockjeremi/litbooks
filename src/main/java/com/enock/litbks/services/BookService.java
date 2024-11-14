package com.enock.litbks.services;

import com.enock.litbks.models.Author;
import com.enock.litbks.models.Book;
import com.enock.litbks.models.recods.DataAuthor;
import com.enock.litbks.models.recods.DataBook;
import com.enock.litbks.models.recods.Results;
import com.enock.litbks.repositories.AuthorRepository;
import com.enock.litbks.repositories.BookRepository;
import com.enock.litbks.utilities.ConvertData;
import com.enock.litbks.utilities.FetchAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  private final FetchAPI fetchAPI = new FetchAPI();
  private final ConvertData convertData = new ConvertData();

  @Autowired
  public BookService(BookRepository book, AuthorRepository author) {
    this.bookRepository = book;
    this.authorRepository = author;
  }

  public void addBookToDataBase(String nameBook) {
    String URL_BASE = "https://gutendex.com/books/?search=";
    var json = fetchAPI.fetchData(URL_BASE + nameBook.replace(" ", "+"));
    Results dataResults = convertData.convert(json, Results.class);
    Optional<DataBook> dataBook = dataResults.results().stream().findFirst();

    if (dataBook.isPresent()) {
      var b = dataBook.get();
      Book book = new Book(b);
      List<Author> authorExist = new ArrayList<>();
      for (DataAuthor author : b.authors()) {
        Optional<Author> aFound = this.authorRepository.findByNameContainsIgnoreCase(author.name()).stream().findFirst();
        if (aFound.isPresent()) {
          authorExist.add(aFound.get());
        } else {
          Author saveAuthor = this.authorRepository.save(new Author(author));
          authorExist.add(saveAuthor);
        }
      }
      book.setAuthors(authorExist);
      this.bookRepository.save(book);
      System.out.println(book);
    } else {
      System.out.println("\nlibro no encontrado.\n");
    }
  }

  public Optional<Book> findBookByTitle(String nameBook) {
    return this.bookRepository.findByTitleContainsIgnoreCase(nameBook).stream().findFirst();
  }

  public Optional<Author> findAuthorByName(String nameAuthor) {
    return this.authorRepository.findByNameContainsIgnoreCase(nameAuthor).stream().findFirst();
  }

  public void findAllBooks() {
    var listBooks = this.bookRepository.findAll();
    if (listBooks.isEmpty()) {
      System.out.println("\nAun no hay libros registrados. \n");
    } else {
      listBooks.forEach(System.out::println);
    }
  }

  public void findAllAuthors() {
    var listAuthors = this.authorRepository.findAll();
    if (listAuthors.isEmpty()) {
      System.out.println("\nAun no hay autores registrados. \n");
    } else {
      listAuthors.forEach(System.out::println);
    }
  }

  public void findAuthorOrderByBirthYear(Integer year) {
    var listAuthor = this.authorRepository.findAuthorByYear(year);
    if (listAuthor.isEmpty()) {
      System.out.println("\nNo hay autores vivos durante ese año.\n");
    } else {
      listAuthor.forEach(System.out::println);
    }
  }

  public void findBookPerLanguages(String lan) {
    var listBooks = this.bookRepository.findAll();
    var list = listBooks.stream().filter(book -> book.getLanguages() != null && book.getLanguages().contains(lan)).toList();
    if (list.isEmpty()) {
      System.out.println("\nNo se han encontrado libros el idioma: " + lan + "\n");
    } else {
      list.forEach(System.out::println);
    }
  }

  public List<String> findLanguagesInDataBase() {
    return this.bookRepository.findLanguagesInDataBase().stream().toList();
  }

  public void topBookPerDownloadCount() {
    List<Book> listTop = this.bookRepository.findAll();
    listTop.stream().sorted(Comparator.comparing(Book::getDownloadCount).reversed())
        .limit(5)
        .forEach(System.out::println);
  }

  public void staSummaryPerDownloadCount() {
    List<Book> listSta = this.bookRepository.findAll();
    DoubleSummaryStatistics sta = listSta.stream().filter(f -> f.getDownloadCount() > 0)
        .collect(Collectors.summarizingDouble(Book::getDownloadCount));

    String estPrint = String.format("""
            Media de descargas por libro: %.1f
            Cantidad minima de descargas: %.1f
            Cantidad maxima de descargas: %.1f
            Total de descargas: %.1f
            Total de libros contados: %.1f
            """, Double.valueOf(sta.getAverage()),
        Double.valueOf(sta.getMin()),
        Double.valueOf(sta.getMax()),
        Double.valueOf(sta.getSum()),
        Double.valueOf(sta.getCount()));

    System.out.println(estPrint);
  }
}

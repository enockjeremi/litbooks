package com.enock.litbks.ui;

import com.enock.litbks.models.Author;
import com.enock.litbks.models.Book;
import com.enock.litbks.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

  private final Scanner keyboard = new Scanner(System.in);
  private BookService bookService;
  private Map<String, String> listLanguages = Map.of(
      "es", "es - Español",
      "en", "en - Ingles",
      "fr", "fr - Frances",
      "hu", "hu - Hungaro",
      "pt", "pt - Portugues",
      "pl", "pl - Polaco"
  );

  @Autowired
  public Main(BookService book) {
    this.bookService = book;
  }

  public void menu() {
    var choice = -1;
    while (choice != 0) {

      String menu = """
          Elige una opcion:
          
          1 - Buscar libro por titulo
          2 - Buscar autores por nombre
          3 - Listar libros registrados
          4 - Listar autores registrados
          5 - Listar autores vivos en un determinado año
          6 - Listar libros por idioma
          7 - Top libros mas descargados
          8 - Estadistas de desacrgas
          
          0 - Salir
          
          """;

      System.out.println(menu);
      choice = keyboard.nextInt();
      keyboard.nextLine();

      switch (choice) {
        case 1:
          findBookByTitle();
          break;
        case 2:
          findAuthorByName();
          break;
        case 3:
          findAllBooks();
          break;
        case 4:
          findAllAuthors();
          break;
        case 5:
          findAuthorOrderByBirthYear();
          break;
        case 6:
          findBookPerLanguages();
          break;
        case 7:
          topBookPerDownloadCount();
          break;
        case 8:
          staSummaryPerDownloadCount();
          break;
      }
    }
  }

  private void findBookByTitle() {
    System.out.println("¿Cual es el libro que buscas?");
    String titleBook = keyboard.nextLine();
    Optional<Book> bFound = this.bookService.findBookByTitle(titleBook);

    if (bFound.isPresent()) {
      System.out.println(bFound.get());
    } else {
      this.bookService.addBookToDataBase(titleBook);
    }
  }

  private void findAuthorByName() {
    System.out.println("¿Que autor deseas buscar?");
    String nameAuthor = keyboard.nextLine();
    Optional<Author> aFound = this.bookService.findAuthorByName(nameAuthor);

    if (aFound.isPresent()) {
      System.out.println(aFound.get());
    } else {
      System.out.println("\nNo se ha encontrado autor con el nombre: " + nameAuthor + "\n");
    }
  }

  private void findAllBooks() {
    this.bookService.findAllBooks();
  }

  private void findAllAuthors() {
    this.bookService.findAllAuthors();
  }

  private void findAuthorOrderByBirthYear() {
    System.out.println("¿Cual es el año que deseas buscar?");
    String authorYear = keyboard.nextLine();
    try {
      int year = Integer.parseInt(authorYear);
      this.bookService.findAuthorOrderByBirthYear(year);
    } catch (NumberFormatException e) {
      System.out.println("\nDebes introducir un numero.\n");
    }
  }

  private void findBookPerLanguages() {
    List<String> languages = this.bookService.findLanguagesInDataBase();
    System.out.println("Idiomas disponibles: ");
    for (String lan : languages) {
      System.out.println(this.listLanguages.getOrDefault(lan, lan));
    }
    var lan = keyboard.nextLine();
    this.bookService.findBookPerLanguages(lan);
  }

  public void topBookPerDownloadCount() {
    this.bookService.topBookPerDownloadCount();
  }

  private void staSummaryPerDownloadCount() {
    this.bookService.staSummaryPerDownloadCount();
  }

}

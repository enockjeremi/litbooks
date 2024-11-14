package com.enock.litbks.repositories;

import com.enock.litbks.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findByTitleContainsIgnoreCase(String title);
  @Query(value = "SELECT unnest(languages) AS language FROM books GROUP BY language", nativeQuery = true)
  List<String> findLanguagesInDataBase();
}

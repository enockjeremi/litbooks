package com.enock.litbks.repositories;

import com.enock.litbks.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  List<Author> findByNameContainsIgnoreCase(String name);

  @Query("select a from Author a where a.birthYear < :year and a.deathYear > :year")
  List<Author> findAuthorByYear(Integer year);

}

package com.enock.litbks.models.recods;

import com.enock.litbks.models.Author;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<DataAuthor> authors,
    @JsonAlias("languages") List<String> languages,
    @JsonAlias("download_count") Double downloadCount
) {
}

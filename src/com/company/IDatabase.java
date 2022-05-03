package com.company;

import com.company.model.Movie;

import java.util.stream.Stream;

interface IDatabase {

    Stream<Movie> selectAll();

    Stream<Movie> selectOne(String title);

    void insert(String title);

    void deleteOne(String title);

    void deleteAll();

}

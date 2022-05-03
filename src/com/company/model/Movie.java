package com.company.model;

import org.bson.types.ObjectId;

public class Movie {
    public ObjectId idMongo;
    public String idSql;
    public String title;

    public Movie(String idSql, String title) {
        this.idSql = idSql;
        this.title = title;
    }

    public Movie(ObjectId idMongo, String title) {
        this.idMongo = idMongo;
        this.title = title;
    }


    public String toStringSql() {
        return "Movie{" +
                "id='" + idSql + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


    public String toStringMongo() {
        return "Movie{" +
                "id=" + idMongo +
                ", title='" + title + '\'' +
                '}';
    }
}

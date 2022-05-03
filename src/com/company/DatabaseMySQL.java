package com.company;

import com.company.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseMySQL implements IDatabase{

    private static final String uri = "jdbc:mysql://localhost/mydatabase?user=myuser&password=mypass";

    @Override
    public void insert(String title) {
        try(Connection conn = DriverManager.getConnection(uri)) {
            //INSERT
            PreparedStatement statement = conn.prepareStatement("INSERT INTO movies(title) VALUES(?)");
            statement.setString(1, title);
            statement.executeUpdate();

            //QUERY
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM movies");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Stream<Movie> selectAll() {
        try(Connection conn = DriverManager.getConnection(uri)) {

            final ArrayList<Movie> movies = new ArrayList<>();

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM movies");
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getString("id"), resultSet.getString("title"));
                movies.add(movie);
            }
            return movies.stream();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Stream<Movie> selectOne(String title) {
        try(Connection conn = DriverManager.getConnection(uri)) {

            final ArrayList<Movie> movies = new ArrayList<>();

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM movies WHERE title = '" + title + "'");
            while (resultSet.next()) {
                Movie movie = new Movie(resultSet.getString("id"), resultSet.getString("title"));
                movies.add(movie);
            }

            return movies.stream();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    @Override
    public void deleteOne(String title) {
        try(Connection conn = DriverManager.getConnection(uri)) {
            //INSERT
            PreparedStatement statement = conn.prepareStatement("DELETE FROM movies WHERE title = '" + title + "'");
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try(Connection conn = DriverManager.getConnection(uri)) {
            //INSERT
            PreparedStatement statement = conn.prepareStatement("DELETE FROM movies");
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

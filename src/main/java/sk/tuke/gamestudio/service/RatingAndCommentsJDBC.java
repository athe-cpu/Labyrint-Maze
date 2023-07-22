package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.RatingAndComments;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingAndCommentsJDBC implements RatingAndCommentsService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "q";
    public static final String INSERT_STATEMENT = "INSERT INTO rating_and_comments (player,game,rate,comment) VALUES (?, ?, ?, ?);";
    public static final String SELECT = "SELECT player,game,rate,comment FROM rating_and_comments WHERE game = ?;";
    public static final String DELETE_STATEMENT = "DELETE from rating_and_comments";

    public void addRC(RatingAndComments ratingAndComments) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)
        ) {
            statement.setString(1, ratingAndComments.getPlayer());
            statement.setString(2, ratingAndComments.getGame());
            statement.setInt(3, ratingAndComments.getRate());
            statement.setString(4, ratingAndComments.getComment());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GamestudioException("Problem inserting score", e);
        }
    }
    public List<RatingAndComments> getTopScores(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<RatingAndComments> ratingAndComments = new ArrayList<>();
                while (rs.next()) {
                    ratingAndComments.add(new RatingAndComments(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
                }
                return ratingAndComments;
            }
        } catch (SQLException e) {
            throw new GamestudioException("Problem selecting score", e);
        }
    }

    @Override
    public void updateByName(int id ) {

    }

    @Override
    public int  getAverage(String game) {
        return 0;
    }


    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GamestudioException("Problem deleting score", e);
        }
    }
}

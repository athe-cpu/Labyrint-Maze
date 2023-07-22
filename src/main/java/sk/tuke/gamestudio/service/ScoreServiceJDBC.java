package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "q";
    public static final String SELECT = "SELECT player, game, level_passed, played_at, invisible, easy_Difficulty_Time, medium_Difficulty_Time, hard_Difficulty_Time, score FROM score WHERE game = ? ORDER BY score DESC LIMIT 10;";
    public static final String INSERT_STATEMENT = "INSERT INTO score ( player, game, level_passed, played_at, invisible, easy_Difficulty_Time, medium_Difficulty_Time, hard_Difficulty_Time, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_STATEMENT = "DELETE from score";

    @Override
    public void addScore(Score score) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)
        ) {
           // statement.setInt(10, score.getIdent());
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getLevelPassed());
            statement.setTimestamp(4, new Timestamp(score.getPlayed_at().getTime()));
            statement.setBoolean(5, score.isInvisible());
            statement.setInt(6, score.getEasyDifficultyTime());
            statement.setInt(7, score.getMediumDifficultyTime());
            statement.setInt(8, score.getHardDifficultyTime());
            statement.setInt(9, score.getScore());
            //statement.setInt(10, score.getIdent());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GamestudioException("Problem inserting score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    //score.addScore(new Score(2,"nazar", "maze",3,new Date(),true,6,0,0,46));
                    //scores.add(new Score(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getTimestamp(5), rs.getBoolean(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),rs.getInt(10)));

                     scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4), rs.getBoolean(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new GamestudioException("Problem selecting score", e);
        }
    }


    @Override
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

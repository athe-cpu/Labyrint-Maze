package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.RatingAndComments;

import java.util.List;

public interface RatingAndCommentsService {
    void addRC(RatingAndComments ratingAndComments);

    List<RatingAndComments> getTopScores(String game);

    void updateByName(int id );

    int getAverage(String game);

    void reset();
}

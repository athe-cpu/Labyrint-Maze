package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.RatingAndComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class RatingAndCommentsRestClient implements RatingAndCommentsService {
    private final String url = "http://localhost:8080/api/rating_and_comments";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addRC(RatingAndComments ratingAndComments) {
        restTemplate.postForEntity(url,ratingAndComments,RatingAndComments.class);
    }

    @Override
    public List<RatingAndComments> getTopScores(String game) {
        return Arrays.asList(restTemplate.getForEntity(url+"/"+game,RatingAndComments[].class).getBody());
    }

    @Override
    public void updateByName(int id ) {
        restTemplate.getForEntity(url+"/",RatingAndComments[].class).getBody();
    }

    @Override
    public int getAverage(String game) {
        RatingAndComments[] rating = restTemplate.getForEntity(url+"/"+game,RatingAndComments[].class).getBody();
       float avr = 0;
        int i = 0;
        for (RatingAndComments ratingAndComment : rating) {
            avr = avr + ratingAndComment.getRate();
            i++;
        }
        avr = avr/i;
        return Math.round(avr);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not suported on eb interface");
    }
}

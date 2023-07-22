package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ScoreServiceRestClient implements ScoreService{
   private String url = "http://localhost:8080/api/score";

   @Autowired
   private RestTemplate restTemplate;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url,score,Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
    return Arrays.asList(restTemplate.getForEntity(url+"/"+game,Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Reset is not suported on eb interface");
    }
}

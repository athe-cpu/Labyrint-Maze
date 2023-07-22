package sk.tuke.gamestudio.server.webservice;

import sk.tuke.gamestudio.entity.RatingAndComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.service.RatingAndCommentsService;

import java.util.List;

@RestController
@RequestMapping("/api/rating_and_comments")
public class RatingAndCommentsRest {

    @Autowired
    private RatingAndCommentsService ratingAndCommentsService;

    @GetMapping("/{game}")
    public List<RatingAndComments> getTopScores(@PathVariable String game){
        return ratingAndCommentsService.getTopScores(game);
    }
    @GetMapping("/{game}/rate")
    public int getAverage(@PathVariable String game){
        return ratingAndCommentsService.getAverage(game);
    }
    @PostMapping
    public void addScore(@RequestBody RatingAndComments ratingAndComments){
        ratingAndCommentsService.addRC(ratingAndComments);
    }
}

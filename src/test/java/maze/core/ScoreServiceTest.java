package maze.core;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest {
    private ScoreService score = new ScoreServiceJDBC();
    @Test
    public void testReset(){
        score.reset();
        assertEquals(0,score.getTopScores("maze").size());
    }
    @Test
    public void testAdd(){
        score.reset();
        score.addScore(new Score("nazar", "maze",3,new Date(),true,6,0,0,46));
        assertEquals(1,score.getTopScores("maze").size());
    }
    @Test
    public void TestGetTopScores(){
        score.reset();
        score.addScore(new Score("nazar", "maze",3,new Date(),true,6,0,0,46));
        score.addScore(new Score("william", "maze",3,new Date(),true,778,0,0,12));
        var scores = score.getTopScores("maze");
        assertEquals(2,score.getTopScores("maze").size());
    }
}

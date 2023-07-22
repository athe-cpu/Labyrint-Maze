package sk.tuke.gamestudio.entity;

import lombok.AllArgsConstructor;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String player;

    private String game;

    private int levelPassed;

    private Date played_at;

    private boolean invisible;

    private int easyDifficultyTime;

    private int mediumDifficultyTime;

    private int hardDifficultyTime;

    private int score;

    public Score(String player,String game,int levelPassed,Date played_at,boolean invisible,int easyDifficultyTime,int mediumDifficultyTime,int hardDifficultyTime,int score){
        this.player = player;
        this.game = game;
        this.levelPassed = levelPassed;
        this.played_at = played_at;
        this.invisible = invisible;
        this.easyDifficultyTime = easyDifficultyTime;
        this.mediumDifficultyTime = mediumDifficultyTime;
        this.hardDifficultyTime = hardDifficultyTime;
        this.score = score;
    }
}

package sk.tuke.gamestudio.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RatingAndComments {
    @Id
    @GeneratedValue
    private int ident;

    private String player;

    private String game;

    private int rate;

    private String comment;

    public RatingAndComments(String player, String game, int rate, String comment){
        this.player = player;
        this.game = game;
        this.rate = rate;
        this.comment = comment;
    }
}

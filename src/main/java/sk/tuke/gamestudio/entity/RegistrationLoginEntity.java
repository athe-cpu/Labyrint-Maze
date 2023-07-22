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
public class RegistrationLoginEntity {
    @Id
    @GeneratedValue
    private int ident;

    private String player;

    private String game;

    private String password;

    public RegistrationLoginEntity(String player, String password, String game){
        this.player = player;
        this.password = password;
        this.game = game;
    }
}

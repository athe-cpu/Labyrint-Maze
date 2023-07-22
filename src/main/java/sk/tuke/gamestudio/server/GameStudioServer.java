package sk.tuke.gamestudio.server;

import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.RegistrationLoginEntity;
import sk.tuke.gamestudio.server.webservice.RatingAndCommentsRest;
import sk.tuke.gamestudio.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
public class GameStudioServer {
    public static void main(String[] args ){
        SpringApplication.run(GameStudioServer.class);
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }
    @Bean
    public RatingAndCommentsService ratingAndCommentsService(){
        return new RatingAndCommentsJPA();
    }
    @Bean
    public RegisterService registerService(){
        return new RegisterJPA();
    }
}

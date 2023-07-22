package sk.tuke.gamestudio;

import sk.tuke.gamestudio.game.maze.ConsoleUI.ConsoleUI;
import sk.tuke.gamestudio.service.*;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {
    public static void main(String[] args ){
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI){
        return args -> consoleUI.play();
    }

    @Bean
    public ConsoleUI consoleUI(){
        return new ConsoleUI(scoreService(),ratingAndCommentsService());
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }
    @Bean
    public RatingAndCommentsService ratingAndCommentsService(){
        return new RatingAndCommentsRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

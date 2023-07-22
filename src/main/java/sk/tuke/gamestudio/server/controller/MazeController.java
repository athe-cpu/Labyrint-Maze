package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.maze.core.*;
import sk.tuke.gamestudio.service.RatingAndCommentsService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Controller

@RequestMapping("/maze")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MazeController {
    private int levelPassed =0;
    private int averageRate;
    private boolean isShowRate = false;
    private int secondsPassed;
    private Timer timer;
    private TimerTask task ;
    private int randomX;
    private int randomY;
    private int level;
    private int easy, medium, hard;
    private Random random = new Random();
    private GameState gameState;
    private boolean invisible = false;
    private Field field;
    private Treasure treasure;
     private Player player;
    private PlayerMove playerMove;
    @Autowired
    private UserController userController;
    @Autowired
    private RatingAndCommentsService ratingAndCommentsService;
    @Autowired
    private ScoreService scoreService;
    @RequestMapping
    public String maze(Model scoreModel,Model rateModel){
            prepareScoreModel(scoreModel);
            prepareRateModel(rateModel);
            return "maze";
    }


    @RequestMapping("/menu")
    public String toMenu(){
        secondsPassed =0;
        levelPassed=0;
        timer.cancel();
        timer.purge();
        return "redirect:/MainMenu";
    }

    @RequestMapping("/rate")
    public String rate() {
        isShowRate = !isShowRate;
        if (isShowRate) {
            averageRate = ratingAndCommentsService.getAverage("maze");
        }
        return "redirect:/maze";
    }
    public int getAverageRate(){
        return averageRate;
    }
    @RequestMapping("/new")
    public String newGame(Model scoreModel,Model rateModel){
            timer.cancel();
            timer.purge();
        gameState = GameState.PLAYING;
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        if(easy==1){
            return "redirect:/maze/easy";
        }else if(medium == 1){
            return "redirect:/maze/medium";
        }else if (hard == 1){
            return "redirect:/maze/hard";
        }
         return "redirect:/maze/easy";
    }

    @RequestMapping("/up")
    public String Up(Model scoreModel,Model rateModel){
        playerMove = PlayerMove.UP;
        playerNextState();
        field.stringBuilder = new StringBuilder();
        if(treasure.found()){
            gameState = GameState.SOLVED;
            return "redirect:/maze/win";
        }
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }

    @RequestMapping("/down")
    public String Down(Model scoreModel,Model rateModel){
        playerMove = PlayerMove.DOWN;
        playerNextState();
        field.stringBuilder = new StringBuilder();
        if(treasure.found()){
            gameState = GameState.SOLVED;
            return "redirect:/maze/win";
        }
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }

    @RequestMapping("/left")
    public String Left(Model scoreModel,Model rateModel){
        playerMove = PlayerMove.LEFT;
        playerNextState();
        field.stringBuilder = new StringBuilder();
        if(treasure.found()){
            gameState = GameState.SOLVED;
            return "redirect:/maze/win";
        }
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }
    @RequestMapping("/win")
    public String Win(){
        if (levelPassed<2){
            levelPassed ++;
            return "redirect:/maze/new";
        }

        levelPassed = 0;
        addScore();
        secondsPassed = 0;
        return "win";
    }
    @RequestMapping("/right")
    public String Right(Model scoreModel,Model rateModel){
        playerMove = PlayerMove.RIGHT;
        playerNextState();
        field.stringBuilder = new StringBuilder();
        if(treasure.found()){
            gameState = GameState.SOLVED;
            return "redirect:/maze/win";
        }
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }

    @RequestMapping("/easy")
    public String easy(Model scoreModel,Model rateModel){
        level = 1;
        resetTime();
        hard = 0; easy = 1; medium = 0;
        randomX = random.nextInt(4,7);
        randomY = random.nextInt(4,7);
        initialise();
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }
    @RequestMapping("/invisible")
    public String invisible(){
        invisible  =!invisible;
        return "redirect:/play";
    }
    @RequestMapping("/medium")
    public String medium(Model scoreModel,Model rateModel){
        level = 2;
        resetTime();
        hard = 0; easy = 0; medium = 1;
        randomX = random.nextInt(8,14);
        randomY = random.nextInt(8,14);
        initialise();
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }
    @RequestMapping("/hard")
    public String hard(Model scoreModel,Model rateModel){
        level = 3;
        resetTime();
        hard = 1; easy = 0; medium = 0;
        randomX = random.nextInt(16,22);
        randomY = random.nextInt(16,22);
        initialise();
        prepareScoreModel(scoreModel);
        prepareRateModel(rateModel);
        return "maze";
    }
    private void initialise(){
        field = new Field(randomX,randomY);
        treasure = new Treasure(field,randomX-1,randomY-1);
        player = new Player(field,0,0);
    }
    private String getField(){
        field.stringBuilder.append("<table>\n");
        for (int i = 0; i < field.getColumnCounts(); i++) {
            // draw the north edge
            field.stringBuilder.append("<tr>\n");
            for (int j = 0; j < field.getRowCounts(); j++) {
                field.displayCeilingWEB(j, i);
            }
            field.stringBuilder.append("<td>\n");
            field.stringBuilder.append("+\n");
            field.stringBuilder.append("</td>\n");
            field.stringBuilder.append("</tr>\n");
            // draw the west edge
            field.stringBuilder.append("<tr>\n");
            for (int j = 0; j < field.getRowCounts(); j++) {
                field.displayCellWEB(j, i);
            }
            field.stringBuilder.append("<td>\n");
            field.stringBuilder.append("|\n");
            field.stringBuilder.append("</td>\n");
            field.stringBuilder.append("</tr>\n");
        }
        // draw the bottom line
        field.stringBuilder.append("<tr>\n");
        for (int j = 0; j < field.getRowCounts(); j++) {
            field.stringBuilder.append("<td>\n");
            field.stringBuilder.append("+---\n");
            field.stringBuilder.append("</td>\n");
        }
        field.stringBuilder.append("<td>\n");
        field.stringBuilder.append("+\n");
        field.stringBuilder.append("</td>\n");
        field.stringBuilder.append("</tr>\n");
        field.stringBuilder.append("</table>\n");
        return field.stringBuilder.toString();
    }

    public String FieldControl(){
        field.stringBuilder = new StringBuilder();
        if(gameState!=GameState.SOLVED) {
            if (!invisible) {
                return getField();
            }
            field.stringBuilder = new StringBuilder();
            return field.displayInvisibleWEB();
        }
        return field.stringBuilder.toString();
    }
    private void playerNextState(){
        switch (playerMove) {
            case RIGHT -> player.moveRight();
            case LEFT -> player.moveLeft();
            case DOWN -> player.moveDown();
            case UP -> player.moveUp();
            default -> throw new IllegalStateException("Unexpected value: " + playerMove);
        }
    }
    private void prepareScoreModel(Model model){
        model.addAttribute("scores",scoreService.getTopScores("maze"));
    }
    private void prepareRateModel(Model model){
        model.addAttribute("ratingAndComments", ratingAndCommentsService.getTopScores("maze"));
    }

    private void addScore(){
        if(userController.getLoggedUser()==null){
            return;
        }
        if(invisible) {
            if (level == 1) {
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "maze", 2 + 1, new Date(), invisible, secondsPassed, 0, 0, (2 + 1) * 100 - secondsPassed));
            } else if (level == 2) {
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "maze", 2 + 1, new Date(), invisible, 0, secondsPassed, 0,((2 + 1) * 100)*2 - secondsPassed));
            } else if (level == 3) {
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "maze", 2 + 1, new Date(), invisible, 0, 0, secondsPassed,((2 + 1) * 100)*3 - secondsPassed));
            }
        }else {
            if (level == 1) {
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "maze", 2 + 1, new Date(), invisible, secondsPassed, 0, 0, (2 + 1)*80 - secondsPassed));
            } else if (level == 2) {
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "maze", 2 + 1, new Date(), invisible, 0, secondsPassed, 0,(2 + 1)*100 - secondsPassed));
            } else if (level == 3) {
                scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "maze", 2 + 1, new Date(), invisible, 0, 0, secondsPassed,(2 + 1)*100 - secondsPassed));
            }
        }
    }
    private void resetTime() {
        this.timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                secondsPassed++;
            }
        };
        this.timer.schedule(task,1000,1000);
    }
public int getSecondsPassed(){
        return secondsPassed;
}
public int getLevelPassed(){
        return levelPassed;
    }
    public boolean getInvisible(){
        return invisible;
    }
    public boolean getShowRate(){
        return isShowRate;
    }
}
